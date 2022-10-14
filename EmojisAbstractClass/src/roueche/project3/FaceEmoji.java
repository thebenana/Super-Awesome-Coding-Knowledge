package roueche.project3;

public abstract class FaceEmoji extends Emoji{

	public FaceEmoji(double x, double y, double size) {
		super(x, y, size);
	}
	
	public void draw() {
		
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.filledCircle(x, y, size);
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.circle(x, y, size);
		
		StdDraw.filledCircle(x - size / 3.0, y + size / 3.0, size / 10);
		StdDraw.filledCircle(x + size / 3.0, y + size / 3.0, size / 10);
	}
}
