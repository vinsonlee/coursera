//import java.util.ArrayList;
//import java.util.Arrays;

public class BaseballElimination {
    private int numberOfTeams;
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

        // trivial elimination
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

        // nontrivial elimination
        // TODO

        for (int i = 0; i < numberOfTeams; i++) {
            if (certificateOfElimination[i].size() == 0) {
                certificateOfElimination[i] = null;
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