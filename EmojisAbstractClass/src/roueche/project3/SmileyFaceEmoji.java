package roueche.project3;

public class SmileyFaceEmoji extends FaceEmoji{

	public SmileyFaceEmoji(double x, double y, double size) {
		super(x, y, size);
	}
	
	public void draw() {
		super.draw();
		StdDraw.arc(x, y - size / 5.0, size / 2.0, 200, 340); // Tested for optimal location 
	}
	
}
