public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] sequence = new char[256];
        for (int i = 0; i < sequence.length; i++) {
            sequence[i] = (char) i;
        }

        // read the input
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        for (int i = 0; i < input.length; i++) {
            char in = input[i];

            int out;
            for (out = 0; out < sequence.length; out++) {
                if (sequence[out] == in) {
                    break;
                }
            }
            assert out < sequence.length;

            BinaryStdOut.write((char) out);

            System.arraycopy(sequence, 0, sequence, 1, out);
            sequence[0] = in;
        }

        // close output stream
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
