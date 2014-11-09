public class WordNet {
    // maps synset id to synsets string
    private ST<Integer, String> idMap;

    // maps nouns to set of synset ids
    private ST<String, SET<Integer>> nounMap;

    private Digraph G;

    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        nounMap = new ST<String, SET<Integer>>();
        idMap = new ST<Integer, String>();

        // The synsets ids are in increasing order.
        // Save the last synset id to determine digraph size.
        int id = -1;

        // http://algs4.cs.princeton.edu/35applications/LookupCSV.java.html
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");

            id = Integer.parseInt(tokens[0]);
            String[] nouns = tokens[1].split(" ");

            SET<String> set = new SET<String>();
            for (String noun : nouns) {
                set.add(noun);
            }
            idMap.put(id, tokens[1]);

            for (String noun : nouns) {
                if (nounMap.contains(noun)) {
                    nounMap.get(noun).add(id);
                } else {
                    SET<Integer> s = new SET<Integer>();
                    s.add(id);
                    nounMap.put(noun, s);
                }
            }
        }

        assert id != 1;
        this.G = new Digraph(id + 1);

        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");

            int synsetId = Integer.parseInt(tokens[0]);

            for (int i = 1; i < tokens.length; i++) {
                // StdOut.println("addEdge " + synsetId + "->" + tokens[i]);
                G.addEdge(synsetId, Integer.parseInt(tokens[i]));
            }
        }

        sap = new SAP(G);
        // StdOut.println(G);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounMap.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new NullPointerException();
        }

        return nounMap.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        SET<Integer> idsA = nounMap.get(nounA);
        SET<Integer> idsB = nounMap.get(nounB);

        // StdOut.println(nounA + ": " + idsA.toString());
        // StdOut.println(nounB + ": " + idsB.toString());

        return sap.length(idsA, idsB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of
    // nounA and nounB in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        SET<Integer> idsA = nounMap.get(nounA);
        SET<Integer> idsB = nounMap.get(nounB);

        int ancestor = sap.ancestor(idsA, idsB);
        return idMap.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        StdOut.println(args[0]);
        StdOut.println(args[1]);
        WordNet wordnet = new WordNet(args[0], args[1]);

        StdOut.println(wordnet.distance("liberalism", "spider_nevus"));
        StdOut.println(wordnet.sap("liberalism", "spider_nevus"));

        StdOut.println(wordnet.distance("cool_medium", "palm_nut"));
        StdOut.println(wordnet.sap("cool_medium", "palm_nut"));
    }
}