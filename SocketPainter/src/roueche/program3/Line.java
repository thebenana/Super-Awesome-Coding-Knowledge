package roueche.program3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

@SuppressWarnings("serial")
public class Line extends PaintingPrimitive {

	protected Color colors;
	protected Point firstClick;
	protected Point secondClick;

	public Line(Point firstClick, Point secondClick, Color colors) {
		super(colors);
		this.firstClick = firstClick;
		this.secondClick = secondClick;
	}

	public void drawGeometry(Graphics g) {
		g.drawLine(firstClick.x, firstClick.y, secondClick.x, secondClick.y);
	}
}
