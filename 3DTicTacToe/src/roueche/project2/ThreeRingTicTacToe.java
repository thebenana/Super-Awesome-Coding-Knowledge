package roueche.project2;

public class ThreeRingTicTacToe {
	
	//TODO: Declare the enumerated type called Contents here
	enum Contents {
		EMPTY, RED, BLUE, BLOCKED;
	}
	
	//TODO: Declare Cell inner class here
	private class Cell {
		private Contents LAST, MIDDLE, FIRST;
		
		public Cell() {
			LAST = Contents.EMPTY;
			MIDDLE = Contents.EMPTY;
			FIRST = Contents.EMPTY;
		}
	}

	// Array of Cells for the Tic Tac Toe Board 
	private Cell[][] board;

	// Message that changes based on turn and game end condition
	private String message;

	// Boolean to determine turns
	private boolean Xturn;

	public ThreeRingTicTacToe() {
		StdDraw.setCanvasSize(600, 700);
		StdDraw.setXscale(0, 3);
		StdDraw.setYscale(0, 3.5);

		//TODO: Initialize the board array
		board = new Cell[3][3];
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				board[i][j] = new Cell();
			}
		}
		board[1][1].MIDDLE = Contents.BLOCKED;

		this.message = "It's Blue's turn";
		this.Xturn = true;
	}

	public void draw() {
		StdDraw.clear(StdDraw.WHITE);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.line(1, 0, 1, 3);
		StdDraw.line(2, 0, 2, 3);
		StdDraw.line(0, 1, 3, 1);
		StdDraw.line(0, 2, 3, 2);
		StdDraw.text(1.5, 3.25, this.message);
		
		// check FIRST
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if(board[i][j].FIRST == Contents.BLUE) {
					StdDraw.setPenColor(StdDraw.BLUE);
					StdDraw.filledCircle(i + 0.5, j + 0.5, 0.5);
				} else if(board[i][j].FIRST == Contents.RED){
					StdDraw.setPenColor(StdDraw.RED);
					StdDraw.filledCircle(i + 0.5, j + 0.5, 0.5);
				} else if (board[i][j].FIRST == Contents.BLOCKED) {
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledCircle(i + 0.5, j + 0.5, 0.5);
				} else {
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.filledCircle(i + 0.5, j + 0.5, 0.5);
				}
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.circle(i + 0.5, j + 0.5, 0.5);
			}
		}
		
		// check MIDDLE
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if(board[i][j].MIDDLE == Contents.BLUE) {
					StdDraw.setPenColor(StdDraw.BLUE);
					StdDraw.filledCircle(i + 0.5, j + 0.5, 0.325);
				} else if(board[i][j].MIDDLE == Contents.RED){
					StdDraw.setPenColor(StdDraw.RED);
					StdDraw.filledCircle(i + 0.5, j + 0.5, 0.325);
				} else if (board[i][j].MIDDLE == Contents.BLOCKED) {
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledCircle(i + 0.5, j + 0.5, 0.325);
				} else {
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.filledCircle(i + 0.5, j + 0.5, 0.325);
				}
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.circle(i + 0.5, j + 0.5, 0.325);
			}
		}
		
		//check inner
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if(board[i][j].LAST == Contents.BLUE) {
					StdDraw.setPenColor(StdDraw.BLUE);
					StdDraw.filledCircle(i + 0.5, j + 0.5, 0.125);
				} else if(board[i][j].LAST == Contents.RED){
					StdDraw.setPenColor(StdDraw.RED);
					StdDraw.filledCircle(i + 0.5, j + 0.5, 0.125);
				} else if (board[i][j].LAST == Contents.BLOCKED) {
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledCircle(i + 0.5, j + 0.5, 0.125);
				} else {
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.filledCircle(i + 0.5, j + 0.5, 0.125);
				}
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.circle(i + 0.5, j + 0.5, 0.125);
			}
		}
		
		
		StdDraw.show();

	}

	public void playGame() {
		StdDraw.enableDoubleBuffering();
		do {
			//TODO: Draw the board
			draw();
			//TODO: Check mouse pressed event
			// If the mouse has been pressed, populate the corresponding
			// cell with the proper value.
			// Also, change this.message to state the correct turn
			// and toggle this.Xturn
			if(StdDraw.isMousePressed()) {
	
				int x = (int)StdDraw.mouseX();
				int y = (int)StdDraw.mouseY();
				
				double xD = x + 0.5 - StdDraw.mouseX();
				double yD = y + 0.5 - StdDraw.mouseY();
				
				double r = Math.sqrt(xD * xD + yD * yD);
				if (r > 0.325 && r < 0.5  && board[x][y].FIRST == Contents.EMPTY) {
					if (this.Xturn) {
						board[x][y].FIRST = Contents.BLUE;
						this.message = "Commence Red's Turn";
						this.Xturn = !this.Xturn;
					} else {
						board[x][y].FIRST = Contents.RED;
						this.message = "Commence Blue's turn";
						this.Xturn = !this.Xturn;
					}
				}
				if (r > 0.125 && r < 0.325 && board[x][y].MIDDLE == Contents.EMPTY) {
					if (this.Xturn) {
						board[x][y].MIDDLE = Contents.BLUE;
						this.message = "Commence Red's Turn";
						this.Xturn = !this.Xturn;
					} else {
						board[x][y].MIDDLE = Contents.RED;
						this.message = "Commence Blue's turn";
						this.Xturn = !this.Xturn;
					}
				}
				if (r < 0.125 && board[x][y].LAST == Contents.EMPTY) {
					if (this.Xturn) {
						board[x][y].LAST = Contents.BLUE;
						this.message = "Commence Red's Turn";
						this.Xturn = !this.Xturn;
					} else {
						board[x][y].LAST = Contents.RED;
						this.message = "Commence Blue's turn";
						this.Xturn = !this.Xturn;
					}
				}
			}
			
			StdDraw.show();
			StdDraw.pause(100);

			// Check game end conditions
		} while (!gameWon() && !allFilled());
		if (gameWon()) {
			message = Xturn ? "Game over, Red wins!" : "Game over, Blue wins!";
		} else {
			message = "Game over, it's a tie!";
		}
		draw();
		StdDraw.show();
	}

	// Check if someone has won the game
	private boolean wins(Contents c1, Contents c2, Contents c3) {
		if (c1 == Contents.EMPTY)
			return false;
		return c1 == c2 && c1 == c3;
	}
	
	public boolean gameWon() {
		// Returns true if a row, column or diagonal contains all O's or X's
		// Note: you may want to make use of the wins() method below.
		if(
				wins(board[0][0].FIRST, board[1][0].FIRST, board[2][0].FIRST)||
				wins(board[0][1].FIRST, board[1][1].FIRST, board[2][1].FIRST)||
				wins(board[0][2].FIRST, board[1][2].FIRST, board[2][2].FIRST)||
				
				wins(board[0][2].FIRST, board[1][1].FIRST, board[2][0].FIRST)||
				wins(board[2][2].FIRST, board[1][1].FIRST, board[0][0].FIRST)||
				
				wins(board[0][0].FIRST, board[0][1].FIRST, board[0][2].FIRST)||
				wins(board[1][0].FIRST, board[1][1].FIRST, board[1][2].FIRST)||
				wins(board[2][0].FIRST, board[2][1].FIRST, board[2][2].FIRST)||
				
				wins(board[0][0].MIDDLE, board[1][0].MIDDLE, board[2][0].MIDDLE)||
				wins(board[0][1].MIDDLE, board[1][1].MIDDLE, board[2][1].MIDDLE)||
				wins(board[0][2].MIDDLE, board[1][2].MIDDLE, board[2][2].MIDDLE)||
				
				wins(board[0][2].MIDDLE, board[1][1].MIDDLE, board[2][0].MIDDLE)||
				wins(board[2][2].MIDDLE, board[1][1].MIDDLE, board[0][0].MIDDLE)||
				
				wins(board[0][0].MIDDLE, board[0][1].MIDDLE, board[0][2].MIDDLE)||
				wins(board[1][0].MIDDLE, board[1][1].MIDDLE, board[1][2].MIDDLE)||
				wins(board[2][0].MIDDLE, board[2][1].MIDDLE, board[2][2].MIDDLE)||
				
				wins(board[0][0].LAST, board[1][0].LAST, board[2][0].LAST)||
				wins(board[0][1].LAST, board[1][1].LAST, board[2][1].LAST)||
				wins(board[0][2].LAST, board[1][2].LAST, board[2][2].LAST)||
				
				wins(board[0][2].LAST, board[1][1].LAST, board[2][0].LAST)||
				wins(board[2][2].LAST, board[1][1].LAST, board[0][0].LAST)||
				
				wins(board[0][0].LAST, board[0][1].LAST, board[0][2].LAST)||
				wins(board[1][0].LAST, board[1][1].LAST, board[1][2].LAST)||
				wins(board[2][0].LAST, board[2][1].LAST, board[2][2].LAST)||
				
				wins(board[0][0].LAST, board[0][1].MIDDLE, board[0][2].FIRST)||
				wins(board[0][2].LAST, board[0][1].MIDDLE, board[0][0].FIRST)||
				wins(board[1][0].LAST, board[1][1].MIDDLE, board[1][2].FIRST)||
				wins(board[1][2].LAST, board[1][1].MIDDLE, board[1][0].FIRST)||
				wins(board[2][0].LAST, board[2][1].MIDDLE, board[2][2].FIRST)||
				wins(board[2][2].LAST, board[2][1].MIDDLE, board[2][0].FIRST)||
				
				wins(board[0][0].LAST, board[1][0].MIDDLE, board[2][0].FIRST)||
				wins(board[2][0].LAST, board[1][0].MIDDLE, board[0][0].FIRST)||
				wins(board[0][1].LAST, board[1][1].MIDDLE, board[2][1].FIRST)||
				wins(board[2][1].LAST, board[1][1].MIDDLE, board[0][1].FIRST)||
				wins(board[0][2].LAST, board[1][2].MIDDLE, board[2][2].FIRST)||
				wins(board[2][2].LAST, board[1][2].MIDDLE, board[0][2].FIRST)||
				
				wins(board[0][0].LAST, board[0][0].MIDDLE, board[0][2].FIRST)||
				wins(board[1][0].LAST, board[1][0].MIDDLE, board[1][0].FIRST)||
				wins(board[0][1].LAST, board[0][1].MIDDLE, board[0][1].FIRST)||
				wins(board[2][0].LAST, board[2][0].MIDDLE, board[2][0].FIRST)||
				wins(board[2][1].LAST, board[2][1].MIDDLE, board[2][1].FIRST)||
				wins(board[2][2].LAST, board[2][2].MIDDLE, board[2][2].FIRST)||
				wins(board[0][2].LAST, board[0][2].MIDDLE, board[0][2].FIRST)||
				wins(board[1][2].LAST, board[1][2].MIDDLE, board[1][2].FIRST)||
				wins(board[2][2].LAST, board[2][2].MIDDLE, board[2][2].FIRST)
				
		) {
			return true;
		}

		return false;

	}

	// Check if three squares are the same (and not empty) 

	private boolean allFilled() {
		
		if(
				//Method returns true if all cells are filled
				board[0][0].FIRST != Contents.EMPTY && board[0][1].FIRST != Contents.EMPTY && board[0][2].FIRST != Contents.EMPTY &&
				board[1][0].FIRST != Contents.EMPTY && board[1][1].FIRST != Contents.EMPTY && board[1][2].FIRST != Contents.EMPTY &&
				board[2][0].FIRST != Contents.EMPTY && board[2][1].FIRST != Contents.EMPTY && board[2][2].FIRST != Contents.EMPTY &&
				
				board[0][0].MIDDLE != Contents.EMPTY && board[0][1].MIDDLE != Contents.EMPTY && board[0][2].MIDDLE != Contents.EMPTY &&
				board[1][0].MIDDLE != Contents.EMPTY && board[1][1].MIDDLE != Contents.EMPTY && board[1][2].MIDDLE != Contents.EMPTY &&
				board[2][0].MIDDLE != Contents.EMPTY && board[2][1].MIDDLE != Contents.EMPTY && board[2][2].MIDDLE != Contents.EMPTY &&
				
				board[0][0].LAST != Contents.EMPTY && board[0][1].LAST != Contents.EMPTY && board[0][2].LAST != Contents.EMPTY &&
				board[1][0].LAST != Contents.EMPTY && board[1][1].LAST != Contents.EMPTY && board[1][2].LAST != Contents.EMPTY &&
				board[2][0].LAST != Contents.EMPTY && board[2][1].LAST != Contents.EMPTY && board[2][2].LAST != Contents.EMPTY
				
		) {
			return true;
		}
		
		return false;
		
	}
	
}