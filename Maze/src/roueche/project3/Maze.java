package roueche.project3;

import java.util.ArrayList;
import java.util.Collections;

import edu.princeton.cs.introcs.StdDraw;

public class Maze {
	
	private int width, height;
	
	private enum CellValue {
		WALL,
		OPEN,
		EXPLORED
	};
	
	private Cell[][] maze;
	
	public Maze(int w, int h) {
		StdDraw.enableDoubleBuffering();
		this.width = w;
		this.height = h;
		
		StdDraw.setCanvasSize(500, 500);
		StdDraw.setXscale(0, this.width);
		StdDraw.setYscale(0, this.height);
		
		maze = new Cell[this.height][this.width];
		
		for(int i = 0; i < maze.length; i++) {
			for(int j = 0; j < maze[i].length; j++) {
				maze[i][j] = new Cell(i, j);
			}
		}
	}
	
	private class Cell {
		private int row, col;
		private CellValue value;
		
		public Cell(int row, int col) {
			value = CellValue.WALL;
			this.row = row;
			this.col = col;
		}
		
		public String toString() {
			return value.toString();
		}
	}
	
	public void generate() { // uses stack
		StackDLList<Cell> stack = new StackDLList<Cell>();
		Cell currentCell = maze[1][1];
		currentCell.value = CellValue.OPEN;
		
		stack.push(currentCell); // pushes currentCell
		
		while(!stack.isEmpty()) {
			draw();
			currentCell = stack.pop();
			ArrayList<Cell> neighbor = new ArrayList<>(); // initializes a list of neighbors
			if(currentCell.row + 2 < height) {
				Cell north = maze[currentCell.row + 2][currentCell.col];
				if(north.value == CellValue.WALL) {
					maze[currentCell.row + 1][currentCell.col].value = CellValue.OPEN;
					north.value = CellValue.OPEN;
					neighbor.add(north);
				}
			}
			if(currentCell.row - 2 > 0) {
				Cell south = maze[currentCell.row - 2][currentCell.col];
				if(south.value == CellValue.WALL) {
					maze[currentCell.row - 1][currentCell.col].value = CellValue.OPEN;
					south.value = CellValue.OPEN;
					neighbor.add(south);
				}
			}
			if(currentCell.col + 2 < width) {
				Cell east = maze[currentCell.row][currentCell.col + 2];
				if(east.value == CellValue.WALL) {
					maze[currentCell.row][currentCell.col + 1].value = CellValue.OPEN;
					east.value = CellValue.OPEN;
					neighbor.add(east);
				}
			}
			if(currentCell.col - 2 > 0) {
				Cell west = maze[currentCell.row][currentCell.col - 2];
				if(west.value == CellValue.WALL) {
					maze[currentCell.row][currentCell.col - 1].value = CellValue.OPEN;
					west.value = CellValue.OPEN;
					neighbor.add(west);
				}
			}
			Collections.shuffle(neighbor);
			for(Cell c : neighbor) {
				stack.push(c);
			}
			StdDraw.show();
		}
	}
	public void draw() {
		StdDraw.clear(); // sets canvas to black to run faster
		for(int row = 0; row < maze.length; row++) {
			for(int col = 0; col < maze[row].length; col++) {
				if(maze[row][col].value == CellValue.OPEN) {
					StdDraw.setPenColor(StdDraw.BOOK_BLUE);
				} else if(maze[row][col].value == CellValue.WALL){
					StdDraw.setPenColor(StdDraw.BLACK);
				} else if(maze[row][col].value == CellValue.EXPLORED){
					StdDraw.setPenColor(StdDraw.BOOK_RED);
				}
				StdDraw.filledSquare(1 + row, 1 + col, 1);
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.square(1 + row, 1 + col, 1);
			}
		}
		StdDraw.show();
	}
	public void solve() { // uses queues
		QueueDoublyLinkedList<Cell> queue = new QueueDoublyLinkedList<Cell>();
		Cell start = maze[1][1];
		Cell goal = maze[height - 2][width - 2];
		Cell currentCell = start;
		
		currentCell.value = CellValue.EXPLORED;
		queue.enqueue(currentCell);
		while(!queue.isEmpty()) {
			draw();
			currentCell = queue.dequeue();
			if(currentCell.row == goal.row && currentCell.col == goal.col) {
				break;
			}
			if(currentCell.row + 1 < height) {
                Cell north = maze[currentCell.row + 1][currentCell.col];
                if(north.value == CellValue.OPEN) {
                    north.value = CellValue.EXPLORED;
                    queue.enqueue(north);
                }
            }
            if(currentCell.row - 1 > 0) {
                Cell south = maze[currentCell.row - 1][ currentCell.col];
                if(south.value == CellValue.OPEN) {
                    south.value = CellValue.EXPLORED;
                    queue.enqueue(south);
                }
            }
            if(currentCell.col + 1 < width) {
                Cell east = maze[currentCell.row][ currentCell.col + 1];
                if(east.value == CellValue.OPEN) {
                    east.value = CellValue.EXPLORED;
                    queue.enqueue(east);
                }
            }
            if(currentCell.col - 1 > 0) {
                Cell west = maze[currentCell.row][currentCell.col - 1];
                if(west.value == CellValue.OPEN) {
                    west.value = CellValue.EXPLORED;
                    queue.enqueue(west);
                }
            }
            StdDraw.show();
		}
	}
}