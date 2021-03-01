package roueche.program3;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class PaintingPrimitive implements Serializable {

	protected Color colors;

	public PaintingPrimitive(Color colours){
		this.colors = colours;
	}

	public final void draw(Graphics g) {
		g.setColor(this.colors);
		drawGeometry(g);
	}

	protected abstract void drawGeometry(Graphics g);
}
