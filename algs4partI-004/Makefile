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

collinear: Point.java Brute.java Fast.java
	checkstyle-algs4 $^
	javac-algs4 $^
	zip collinear.zip $^
	java-algs4 -enableassertions Point

8puzzle: Board.java Solver.java
	checkstyle-algs4 $^
	javac-algs4 $^
	#findbugs-algs4 Board.class Solver.class
	zip 8puzzle.zip $^
	#java-algs4 -enableassertions Solver 8puzzle/puzzle04.txt
	#java-algs4 -enableassertions Solver 8puzzle/puzzle11.txt
	#java-algs4 -enableassertions Solver 8puzzle/puzzle14.txt
	#java-algs4 -Xmx1600m PuzzleChecker 8puzzle/puzzle[0-9][0-9].txt
	#java-algs4 -Xmx1600m PuzzleChecker 8puzzle/puzzle*.txt
	#java-algs4 -Xmx2000m PuzzleChecker 8puzzle/puzzle*.txt
	#java-algs4 -enableassertions -Xmx1600m PuzzleChecker 8puzzle/puzzle3x3-unsolvable*.txt

kdtree: PointSET.java KdTree.java
	checkstyle-algs4 $^
	javac-algs4 $^
	zip kdtree.zip $^
