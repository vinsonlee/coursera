public class WordNet {
    // maps synset id to set of nouns

    // maps nouns to synset id
    private ST<String, Integer> nounMap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        StdOut.println("synsets: " + synsets + " hypernyms: " + hypernyms);
        nounMap = new ST<String, Integer>();

        // http://algs4.cs.princeton.edu/35applications/LookupCSV.java.html
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");

            int id = Integer.parseInt(tokens[0]);
            String[] nouns = tokens[1].split(" ");

            for (String noun : nouns) {
                nounMap.put(noun, id);
            }
        }

        // In h = new In(hypernyms);

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounMap.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nounMap.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return -1;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of
    // nounA and nounB in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return "";
    }

    // do unit testing of this class
    public static void main(String[] args) {
        StdOut.println("hello");
        // In in = new In(args[0]);
        StdOut.println(args[0]);
        StdOut.println(args[1]);

        String test = "aaa bbb ccc";
        StdOut.println(test.split(" "));

        WordNet wordnet = new WordNet(args[0], args[1]);
    }
}