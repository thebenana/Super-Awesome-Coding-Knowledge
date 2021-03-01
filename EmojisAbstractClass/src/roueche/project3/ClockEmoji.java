package roueche.project3;

import edu.princeton.cs.introcs.StdDraw;

public class ClockEmoji extends Emoji{

	private double hour;
	
	public ClockEmoji(double x, double y, double size, double hour) {
		super(x, y, size);
		this.hour = hour;
	}
	
	public void draw() {
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.circle(x, y, size);

		double pointer = (Math.PI * 2) / 12; // Calculates 
		
		// Draws hand - math taken from clock lab
		StdDraw.line(x, y, x + Math.cos(pointer * (hour - 3)) * size, y + Math.sin(pointer * (hour + 3)) * size);
	}
	
}
