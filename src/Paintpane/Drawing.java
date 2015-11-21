package Paintpane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.Serializable;




public class Drawing implements Serializable { // class Drawing is used to create all kinds of shape 

	int x1, x2, y1, y2; // define coordinate
	int R, G, B; // define color
	float stroke; // define thickness of pen
	int type; // define type of font
	String s1; // define style of font
	String s2; // define style of font

	void draw(Graphics2D g2d) {
	}
}

class Line extends Drawing { 
	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		g2d.drawLine(x1, y1, x2, y2);

	}
}

class Rect extends Drawing { 
	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawRect(Math.min(x1, x2), Math.min(y2, y2), Math.abs(x1 - x2),
				Math.abs(y1 - y2));
	}
}

class Oval extends Drawing {
	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawOval(Math.min(x1, x2), Math.min(y2, y2), Math.abs(x1 - x2),
				Math.abs(y1 - y2));
	}
}

class Circle extends Drawing {
	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawOval(Math.min(x1, x2), Math.min(y2, y2),
				Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)),
				Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));
	}
}

class RoundRect extends Drawing {
	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawRoundRect(Math.min(x1, x2), Math.min(y2, y2),
				Math.abs(x1 - x2), Math.abs(y1 - y2), 50, 35);
	}
}

class Pencil extends Drawing {
	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		g2d.drawLine(x1, y1, x2, y2);
	}
}

class Rubber extends Drawing {
	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(255, 255, 255));
		g2d.setStroke(new BasicStroke(stroke + 4, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		g2d.drawLine(x1, y1, x2, y2);
	}
}

class Word extends Drawing {
	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setFont(new Font(s2, x2 + y2, ((int) stroke) * 18));
		if (s1 != null)
			g2d.drawString(s1, x1, y1);
	}
}