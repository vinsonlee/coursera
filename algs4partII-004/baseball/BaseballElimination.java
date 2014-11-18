public class BaseballElimination {
    private final int numberOfTeams;
    private String[] teams;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;
    private ST<String, Integer> st;
    private boolean[] isEliminated;
    private SET<String>[] certificateOfElimination;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);

        numberOfTeams = in.readInt();
        teams = new String[numberOfTeams];
        w = new int[numberOfTeams];
        l = new int[numberOfTeams];
        r = new int[numberOfTeams];
        g = new int[numberOfTeams][numberOfTeams];
        st = new ST<String, Integer>();
        isEliminated = new boolean[numberOfTeams];
        certificateOfElimination = (SET<String>[]) new SET[numberOfTeams];

        for (int i = 0; i < numberOfTeams; i++) {
            teams[i] = in.readString();
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();

            for (int j = 0; j < numberOfTeams; j++) {
                g[i][j] = in.readInt();
            }

            st.put(teams[i], i);
            certificateOfElimination[i] = new SET<String>();
        }

        trivialElimination();
        nonTrivialElimination();

        // null out certificates of teams still alive
        for (int i = 0; i < numberOfTeams; i++) {
            if (!isEliminated[i]) {
                assert certificateOfElimination[i].size() == 0;
                certificateOfElimination[i] = null;
            }
        }
    }

    private void trivialElimination() {
        for (int i = 0; i < numberOfTeams; i++) {
            for (int j = 0; j < numberOfTeams; j++) {
                if (i == j) {
                    assert g[i][j] == 0;
                    continue;
                }

                if (w[i] + r[i] < w[j]) {
                    isEliminated[i] = true;
                    certificateOfElimination[i].add(teams[j]);
                }
            }
        }
    }

    private void nonTrivialElimination() {
        final int numberOfGameVertices = ((numberOfTeams - 1)
                                          * (numberOfTeams - 2)) / 2;
        final int numberOfFlowNetworkVertices = 1
                                                + numberOfGameVertices
                                                + numberOfTeams - 1
                                                + 1;

        for (int team = 0; team < numberOfTeams; team++) {
            if (isEliminated[team]) {
                continue;
            }

            int vertexNumber;

            // Map team number to vertex number in flow network.
            vertexNumber = numberOfGameVertices + 1;
            ST<Integer, Integer> teamVertices = new ST<Integer, Integer>();
            for (int i = 0; i < numberOfTeams; i++) {
                if (i != team) {
                    teamVertices.put(i, vertexNumber);
                    vertexNumber++;
                }
            }

            FlowNetwork G = new FlowNetwork(numberOfFlowNetworkVertices);

            // Add vertices to flow network.
            vertexNumber = 1;
            for (int i = 0; i < numberOfTeams - 1; i++) {
                if (i == team) {
                    continue;
                }

                for (int j = i + 1; j < numberOfTeams; j++) {
                    if (j == team) {
                        continue;
                    }

                    FlowEdge e;

                    e = new FlowEdge(0, vertexNumber, g[i][j]);
                    G.addEdge(e);

                    e = new FlowEdge(vertexNumber,
                                     teamVertices.get(i),
                                     Double.POSITIVE_INFINITY);
                    G.addEdge(e);

                    e = new FlowEdge(vertexNumber,
                                     teamVertices.get(j),
                                     Double.POSITIVE_INFINITY);
                    G.addEdge(e);

                    vertexNumber++;
                }
            }
            assert vertexNumber == numberOfGameVertices + 1;

            // Add edges from team vertices to target.
            for (int i = 0; i < numberOfTeams; i++) {
                if (i == team) {
                    continue;
                }
                FlowEdge e = new FlowEdge(teamVertices.get(i), G.V() - 1,
                                          w[team] + r[team] - w[i]);
                G.addEdge(e);
            }

            FordFulkerson maxflow = new FordFulkerson(G, 0, G.V() - 1);

            for (FlowEdge e : G.adj(0)) {
                if (e.flow() != e.capacity()) {
                    isEliminated[team] = true;
                    break;
                }
            }

            // Find certificate of elimination.
            if (isEliminated[team]) {
                for (int i = 0; i < numberOfTeams; i++) {
                    if (i == team) {
                        continue;
                    }

                    if (maxflow.inCut(teamVertices.get(i))) {
                        certificateOfElimination[team].add(teams[i]);
                    }
                }
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return numberOfTeams;
    }

    // all teams
    public Iterable<String> teams() {
        return st.keys();
    }

    // number of wins for given team
    public int wins(String team) {
        if (st.get(team) == null) {
            throw new IllegalArgumentException();
        }
        return w[st.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        if (st.get(team) == null) {
            throw new IllegalArgumentException();
        }
        return l[st.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (st.get(team) == null) {
            throw new IllegalArgumentException();
        }
        return r[st.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (st.get(team1) == null || st.get(team2) == null) {
            throw new IllegalArgumentException();
        }
        return g[st.get(team1)][st.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (st.get(team) == null) {
            throw new IllegalArgumentException();
        }

        return isEliminated[st.get(team)];
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (st.get(team) == null) {
            throw new IllegalArgumentException();
        }
        return certificateOfElimination[st.get(team)];
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
