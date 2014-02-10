percolation: Percolation.java PercolationStats.java
	checkstyle-algs4
	javac-algs4 $^
	zip percolation.zip $^
	java-algs4 PercolationStats 200 100
	java-algs4 PercolationStats 2 10000

queues: Deque.java RandomizedQueue.java Subset.java
	checkstyle-algs4 $^
	javac-algs4 $^
	zip queues.zip $^
	#java-algs4 -ea Deque
	#java-algs4 -ea RandomizedQueue
	echo "A B C D E F G H I" | java-algs4 Subset 3
	echo "A B C D E F G H I" | java-algs4 Subset 3
	echo "AA BB BB BB BB BB CC CC " | java-algs4 Subset 8
