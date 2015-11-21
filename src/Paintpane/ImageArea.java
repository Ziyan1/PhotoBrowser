package Paintpane;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Paintpane.Drawing;

public class ImageArea extends JPanel {
	ImagePad imgPad = null;
	Drawing[] itemList = new Drawing[5000]; 
	Image image = null;
	Image image2;
	Graphics2D g2d;
	boolean flipable;

	private int currentChoice = 1;// default tool is pencil
	int index = 0;// current nb of drawing shape
	private Color color = Color.black;// default drawing color
	int R, G, B;
	int f1, f2;// default font style
	String stytle;// default font type
	float stroke = 1.0f;// default pencil thickness is 1

	ImageArea(ImagePad ip) {
		imgPad = ip;
		addMouseListener(new MouseA());
		addMouseMotionListener(new MouseB());
		createNewitem();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.lightGray); // background color is lightGray
		g.fillRect(0, 0, getSize().width, getSize().height); 

		if (image != null && flipable == false) { // display image
			g.drawImage(image, 0, 0, this);
			
		}
		if (image != null && flipable == true) { //double click to back of the image

			image2 = createImage(image.getWidth(this), image.getHeight(this));
			g2d = (Graphics2D) image2.getGraphics();
			g2d.setColor(Color.white); // image's back's color is white
			g2d.fillRect(0, 0, getSize().width, getSize().height);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			int j = 0;
			while (j <= index) {
				draw(g2d, itemList[j]);
				j++;
			}
			g.drawImage(image2, 0, 0, null);

		}

	}

	void draw(Graphics2D g2d, Drawing i) {
		i.draw(g2d);
	}

	void createNewitem() {
		if (currentChoice == 7&&flipable==true)// text tool mouse change shape
			setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		else if (flipable == false)
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		else
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));// mouse changt to cross
		switch (currentChoice) {
		case 0:
			itemList[index] = new Line();
			break;
		case 1:
			itemList[index] = new Pencil();
			break;
		case 2:
			itemList[index] = new Rect();
			break;
		case 3:
			itemList[index] = new Oval();
			break;
		case 4:
			itemList[index] = new Circle();
			break;
		case 5:
			itemList[index] = new RoundRect();
			break;
		case 6:
			itemList[index] = new Rubber();
			break;
		case 7:
			itemList[index] = new Word();
			break;
		}
		itemList[index].type = currentChoice;
		itemList[index].R = R;
		itemList[index].G = G;
		itemList[index].B = B;
		itemList[index].stroke = stroke;

	}

	public void setIndex(int x) {
		index = x;
	}

	public int getIndex() {
		return index;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setStroke(float f)
	{
		stroke = f;
	}

	public void chooseColor()// default color
	{
		color = JColorChooser.showDialog(imgPad, "Color", color);
		try {
			R = color.getRed();
			G = color.getGreen();
			B = color.getBlue();
		} catch (Exception e) {
			R = 0;
			G = 0;
			B = 0;
		}
		itemList[index].R = R;
		itemList[index].G = G;
		itemList[index].B = B;
	}

	public void setStroke()// set thickness
	{
		String input;
		input = JOptionPane.showInputDialog("please input the thickness of pen ( >0 )");
		try {
			stroke = Float.parseFloat(input);

		} catch (Exception e) {
			stroke = 1.0f;

		}
		itemList[index].stroke = stroke;

	}

	public void setCurrentChoice(int i)// input text
	{
		currentChoice = i;
	}

	public void setFont(int i, int font)// set text
	{
		if (i == 1) {
			f1 = font;
		} else
			f2 = font;
	}

	
	class MouseA extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent me) {

			if (me.getClickCount() == 2) {
				if (flipable == false) {
					flipable = true;
				} else {
					flipable = false;
				}

			}
			setImage(image, flipable);
			super.mouseClicked(me);
		}

		@Override
		public void mouseEntered(MouseEvent me) {
			// TODO mouse in
			imgPad.setStratBar("mouse entered position£∫[" + me.getX() + " ," + me.getY() + "]");
		}

		@Override
		public void mouseExited(MouseEvent me) {
			// TODO mouse out
			imgPad.setStratBar("mouse exited position£∫[" + me.getX() + " ," + me.getY() + "]");
		}

		@Override
		public void mousePressed(MouseEvent me) {
			// TODO mouse pressed
			imgPad.setStratBar("mouse pressed position£∫[" + me.getX() + " ," + me.getY() + "]");// …Ë÷√◊¥Ã¨¿∏Ã· æ

			itemList[index].x1 = itemList[index].x2 = me.getX();
			itemList[index].y1 = itemList[index].y2 = me.getY();
			if (flipable==true){

			// if current tool is rubber or pencil
			if (currentChoice == 1 || currentChoice == 6) {
				itemList[index].x1 = itemList[index].x2 = me.getX();
				itemList[index].y1 = itemList[index].y2 = me.getY();
				index++;
				createNewitem();
			}
			// if current tool is Text
			if (currentChoice == 7 ) {
				itemList[index].x1 = me.getX();
				itemList[index].y1 = me.getY();
				String input;
				input = JOptionPane.showInputDialog("Please input your text£°");
				
				itemList[index].s1 = input;
				itemList[index].x2 = f1;
				itemList[index].y2 = f2;
				itemList[index].s2 = stytle;

				index++;
				currentChoice = 7;
				createNewitem();
				repaint();
			}

		}}

		@Override
		public void mouseReleased(MouseEvent me) {
			// TODO mouse released
			imgPad.setStratBar("mouse released position£∫[" + me.getX() + " ," + me.getY() + "]");
			if(flipable==true){
			if (currentChoice == 1 || currentChoice == 6) {
				itemList[index].x1 = me.getX();
				itemList[index].y1 = me.getY();
			}
			itemList[index].x2 = me.getX();
			itemList[index].y2 = me.getY();
			
			repaint();
			index++;
			createNewitem();
			
			}
		}

	}

	
	class MouseB extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent me)// mouse dragged 
		{
			imgPad.setStratBar("mouse dragged positon£∫[" + me.getX() + " ," + me.getY() + "]");
			if(flipable){
			if (currentChoice == 1 || currentChoice == 6) {
				itemList[index - 1].x1 = itemList[index].x2 = itemList[index].x1 = me
						.getX();
				itemList[index - 1].y1 = itemList[index].y2 = itemList[index].y1 = me
						.getY();
				index++;
				
				createNewitem();
				
			} else {
				itemList[index].x2 = me.getX();
				itemList[index].y2 = me.getY();
			}
			
			repaint();
			}
		}

		public void mouseMoved(MouseEvent me)// mouse move
		{
			imgPad.setStratBar("mouse position£∫[" + me.getX() + " ," + me.getY() + "]");
		}
	}

	public void setImage(Image image, boolean flipable) {
		this.image = image;
		this.flipable = flipable;

		if (image != null) {

			setPreferredSize(new Dimension(image.getWidth(this),
					image.getHeight(this)));

		}
		
		repaint();
	}
}
