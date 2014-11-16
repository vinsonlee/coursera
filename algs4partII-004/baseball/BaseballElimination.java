import java.util.ArrayList;
//import java.util.Arrays;

public class BaseballElimination {
    private int numberOfTeams;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;
    private ArrayList<String> teams;
    private ST<String, Integer> teamToInt;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        teamToInt = new ST<String, Integer>();

        In in = new In(filename);
        numberOfTeams = Integer.parseInt(in.readLine());

        teams = new ArrayList<String>(numberOfTeams);
        w = new int[numberOfTeams];
        l = new int[numberOfTeams];
        r = new int[numberOfTeams];
        g = new int[numberOfTeams][numberOfTeams];

        for (int i = 0; i < numberOfTeams; i++) {
            String line = in.readLine().trim();
            String[] tokens = line.split("\\s+");
            // StdOut.println(Arrays.toString(tokens));
            assert tokens.length == 4 + numberOfTeams;

            teams.add(i, tokens[0]);
            w[i] = Integer.parseInt(tokens[1]);
            l[i] = Integer.parseInt(tokens[2]);
            r[i] = Integer.parseInt(tokens[3]);

            for (int j = 0; j < numberOfTeams; j++) {
                g[i][j] = Integer.parseInt(tokens[j + 4]);
            }

            teamToInt.put(tokens[0], i);
        }
    }

    // number of teams
    public int numberOfTeams() {
        return numberOfTeams;
    }

    // all teams
    public Iterable<String> teams() {
        return teams;
    }

    // number of wins for given team
    public int wins(String team) {
        return w[teamToInt.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        return l[teamToInt.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return r[teamToInt.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return g[teamToInt.get(team1)][teamToInt.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return null;
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