package roueche.project3;

import edu.princeton.cs.introcs.StdDraw;

public class SurprisedFaceEmoji extends FaceEmoji {

	public SurprisedFaceEmoji(double x, double y, double size) {
		super(x, y, size);
	}
	
	public void draw() {
		super.draw();
		
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledCircle(x, y - (size / 3), size / 3);
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.circle(x, y - (size / 3), size / 3);
	}
}
