public class Outcast {
    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int distance = 0;
        String outcast = null;

        for (String i : nouns) {
            int d = 0;

            for (String j : nouns) {
                int dist = wordnet.distance(i, j);
                // StdOut.println("distance(" + i + "," + j + ") = " + dist);
                d += dist;
            }

            // StdOut.println("distance: " + d);

            if (d > distance) {
                distance = d;
                outcast = i;
            }

            // StdOut.println("distance: " + d + " outcast: " + outcast);
        }

        assert outcast != null;
        return outcast;
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}