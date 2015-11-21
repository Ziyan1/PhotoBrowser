package Paintpane;


import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Paintpane.ImageArea;
import Paintpane.ImagePad;


public class FileClass {
	private ImagePad imgPad;
	ImageArea imgArea = null;
	String imageFile = null;
	boolean fp;
	FileClass(ImagePad ip, ImageArea ia) {
		imgPad = ip;
		imgArea = ia;
	}

	public void deleteImg() {
		// TODO clear draw area
		imgArea.setIndex(0);
		imgArea.setCurrentChoice(1);// default tool pencil
		imgArea.setColor(Color.black);// default color
		imgArea.setStroke(1.0f);// default thickness
		imgArea.createNewitem();
		imgArea.repaint();
	}

	public void importImg() {
		// TODO open file

		String imageFile = null;
		 
		 JFileChooser filechooser = new JFileChooser(System.getProperty("user.dir")
                + "\\images");
		 filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//image format filter
		  FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "JPG & GIF Images", "jpg", "gif","png");
		   filechooser.setFileFilter(filter);
		    int returnVal = filechooser.showOpenDialog(imgPad);
		    
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	imageFile = filechooser.getSelectedFile().getPath();
               Image im = new ImageIcon(imageFile).getImage();
               imgArea.setImage(im,fp);
		    }	
}
}