package roueche.project3;

public abstract class Emoji {

	protected double x;
	protected double y;
	protected double size;
	
	public Emoji(double x, double y, double size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public abstract void draw();
	
}
