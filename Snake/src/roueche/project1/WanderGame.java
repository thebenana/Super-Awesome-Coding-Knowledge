package roueche.project1;

import java.awt.Color;
import java.util.ArrayList;

import edu.princeton.cs.introcs.Draw;
import edu.princeton.cs.introcs.DrawListener;


public class WanderGame implements DrawListener {

	private Color color;
	private double x;
	private double y;
	private double size;
	private Draw draw;
	private SinglyLinkedList<Food> food;
	private int numFood = 20;
	
	private static final int CANVAS_WIDTH = 500;
	private static final int CANVAS_HEIGHT = 500;
	
	
	
	public WanderGame(double x, double y, double size) {
		draw = new Draw();
		draw.setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		draw.setXscale(0, CANVAS_WIDTH);
		draw.setYscale(0, CANVAS_WIDTH);
		draw.addListener(this);
		food = new SinglyLinkedList<Food>();
		color = new Color(0, 255, 255);
		
		this.x = x;
		this.y = y;
		this.size = size;
		setFood();
	}
	
	public class Food {
		
		private double x;
		private double y;
		private double size;
		
		public Food() {
			this.x = (int)(Math.random() * CANVAS_WIDTH);
			this.y = (int)(Math.random() * CANVAS_HEIGHT);
			this.size = 5;
		}
	}
	
	public void setFood() {
		for(int i = 0; i < this.numFood; i++) {
			food.addFirst(new Food());
		}
	}
	

	public void mousePressed(double x, double y) {}

	public void mouseDragged(double x, double y) {}

	public void mouseReleased(double x, double y) {}

	public void keyTyped(char c) {
		if (c == 'w' || c == 't') {
			y += 5;
			eat();
		} else if (c == 'a' || c == 'f') {
			x -= 5;
			eat();
		} else if (c == 's' || c == 'g') {
			y -= 5;
			eat();
		} else if (c == 'd' || c == 'h') {
			x += 5;
			eat();
		} else if (c == 'q') {
			System.exit(0);
		}
		draw();
		
		
		
	}
	@Override
	public void mouseClicked(double x, double y) {
		// TODO Auto-generated method stub
		if(x < this.x + this.size && x > this.x - this.size && y > this.y - this.size && y < this.y + this.size) {
			color = new Color((int)(Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255));
		} else {
			this.x = Math.random() * CANVAS_WIDTH;
			this.y = Math.random() * CANVAS_HEIGHT;
		}
		draw();
	}
	
	public void draw() {
		this.draw.clear();
		for(int i = 0; i < food.size(); i++) {
			Food s = food.get(i);
			draw.setPenColor(color);
			draw.filledCircle(s.x, s.y, s.size);
		}
		draw.setPenColor(color);
		draw.filledCircle(x, y, size);
		
	}
	
	public void eat() {
		for(int i = 0; i < food.size(); i++) {
			Food a = food.get(i);
			double l = Math.sqrt((x - a.x) * (x - a.x) + (y - a.y) * (y - a.y));
			if (size > l + a.size || a.size > l + size) {
				this.size += 10;
				food.remove(a);
				break;
			}
		}
		
	}


	@Override
	public void keyPressed(int keycode) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(int keycode) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}


