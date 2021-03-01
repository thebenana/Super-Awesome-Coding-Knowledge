package roueche.program3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

@SuppressWarnings("serial")
public class Circle extends PaintingPrimitive {

	protected Color colors;
	protected Point center;
	protected Point radiusPoint;

	public Circle(Point center, Point bottomRight, Color colors) {
		super(colors);
		this.center = center;
		this.radiusPoint = bottomRight;
	}

	public void drawGeometry(Graphics g) {
		int radius = (int) ((Math.abs(center.distance(radiusPoint))));

		g.drawOval(center.x - radius, center.y - radius, radius*2, radius*2);
	}
}
