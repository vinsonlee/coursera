all: BoggleSolver.java
	# checkstyle-algs4 $^
	javac-algs4 $^
	zip boggle.zip $^
	java-algs4 -ea BoggleSolver dictionary-algs4.txt board4x4.txt
	java-algs4 -ea BoggleSolver dictionary-algs4.txt board-q.txt
	java-algs4 -ea BoggleSolver dictionary-yawl.txt board-points4410.txt
	java-algs4 -ea BoggleSolver dictionary-yawl.txt board-points26539.txt

