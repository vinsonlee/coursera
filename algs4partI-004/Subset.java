public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            q.enqueue(item);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}
