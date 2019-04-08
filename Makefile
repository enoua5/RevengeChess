rc.jar: AI.java Bishop.java Board.java build Canvas.java Context.java Empty.java Frame.java King.java Knight.java LogoCanvas.java LogoFrame.java Main.java Move.java Pawn.java Piece.java Queen.java Rook.java ValueAction.java
	javac Main.java
	jar -cvfm build/rc.jar manifest.txt *.class
	rm *.class
	sudo chmod +x build/rc.jar
