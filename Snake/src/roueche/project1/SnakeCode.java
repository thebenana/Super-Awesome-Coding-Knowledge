package roueche.project1;

import edu.princeton.cs.introcs.StdDraw;


public class SnakeCode {
	
	private enum DIR {
		up, down, right, left
	}
	
	private DIR d;
	private int width, height;
	private double lB, rB, tB, bB;
	private SinglyLinkedList<Snake> list;
	private Food food;
	private boolean end;
	
	public SnakeCode() {
		StdDraw.enableDoubleBuffering();
		list = new SinglyLinkedList<Snake>();
		
		end = false;
		
		width = 500;
		height = 500;
		
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
		
		// used to store boundaries
		d = DIR.up;
		lB = 0;
		rB = width;
		tB = height;
		bB = 0;
		
		// adds a food to the screen
		food = new Food();
		
		// instantiates the snake
		for(int i = 0; i < 2; i++) {
			list.addLast(new Snake(100 - (i * 20), 100));
		}
	}
	
	// controls food
	public class Food {
		
		private double x;
		private double y;
		
		public Food() {
			this.x = (((int) (Math.random() * 20))*20); // makes a grid that lines evenly with the snake head
			this.y = (((int) (Math.random() * 20))*20);
		}
		
		public void instantiate() {
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.filledSquare(x, y, 10);
		}
	}
	
	public void run() {
		while(end != true) {
			eat(); // checks to see if eaten
			list.removeLast();
			// runs the snake infinitely 
			if(d == DIR.left) {
				list.addFirst(new Snake(list.get(0).x - 20, list.get(0).y)); // used to direct the snake and change the direction
			}
			if(d == DIR.right) {
				list.addFirst(new Snake(list.get(0).x + 20, list.get(0).y));
			}
			if(d == DIR.up) {
				list.addFirst(new Snake(list.get(0).x, list.get(0).y + 20));
			}
			if(d == DIR.down) {
				list.addFirst(new Snake(list.get(0).x, list.get(0).y - 20));
			}
			StdDraw.pause(400); // speeds up/slows down the game
			if(StdDraw.hasNextKeyTyped()) {
				char key = StdDraw.nextKeyTyped(); // keeps track of keys pressed
				if(key == 'q') {
					System.exit(0);
				}
				
				// for other key types 
				if((key == 'w') && d != DIR.down) {
					d = DIR.up;
				}
				if((key == 'a') && d != DIR.right) {
					d = DIR.left;
				}
				if((key == 's') && d != DIR.up) {
					d = DIR.down;
				}
				if((key == 'd') && d != DIR.left) {
					d = DIR.right;
				}
			}
			bounds(); // checks to see if hits bounds
			draw(); // draws the canvas
		}
	}
	
	// controls the snake
	public class Snake {
		private double x;
		private double y;
		
		public Snake(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
		public void instantiate() {
			StdDraw.filledSquare(this.x, this.y, 10);
		}
	}
	
	public void draw() {
		Snake snakeBod;
		StdDraw.clear();
		for(int i = 0; i < list.size(); i++) {
			snakeBod = list.get(i); // gets elements in the singlylinkedlist
			snakeBod.instantiate(); // instantiates those elements
		}
		food.instantiate();
		StdDraw.show();
	}
	
	public void bounds() {
		Snake snakeBod = list.get(0);
		if((snakeBod.x + 10 > rB) || (snakeBod.x - 10 < lB) || (snakeBod.y + 10 > tB && snakeBod.y > 0) || (snakeBod.y - 10 < bB)) { // calculates bounds
			end = true;
			System.exit(0); // exits under this condition
		}
	}
	
	public void eat() {
		Snake head = list.get(0);
		if(food.x == head.x && food.y == head.y) {
			list.addLast(new Snake(list.get(list.size() - 1).x - 10, list.get(list.size() - 1).y - 10)); // adds to body if eaten
			food = new Food(); // spawns new food
		} else {
			for(int i = 2; i < list.size(); i++) { // takes into account the length of the snake (2)
				if(head.x == list.get(i).x && head.y == list.get(i).y) {
					System.exit(0); // exits if runs into self
				}
			}
		}
	}
}


