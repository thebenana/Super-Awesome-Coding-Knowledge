package roueche.program3;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PaintingPanel extends JPanel {

	ArrayList<PaintingPrimitive> ppArray;

	public PaintingPanel() {
		
		ppArray = new ArrayList<PaintingPrimitive>();
		setBackground(Color.WHITE);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (PaintingPrimitive obj : ppArray) {
			obj.draw(g);
		}
	}

	public void addPrimitive(PaintingPrimitive obj) {
		this.ppArray.add(obj);
	}

}
