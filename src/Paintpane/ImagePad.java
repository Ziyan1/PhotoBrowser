package Paintpane;

import java.awt.*;
import java.awt.event.*;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.*;


public class ImagePad extends JFrame implements ActionListener {

	private static final long serialVersionUID = -2551980583852173918L;
	private JToolBar buttonpanel;
	private JMenuBar bar;
	private JMenu file, view;
	private JMenuItem importImg, deleteImg, quit;
	private JRadioButtonMenuItem photoViewer, browser, splitMode;
	private Icon importIcon, deleteIcon, quitIcon;
	private JLabel statusBar;
	private ImageArea imgArea;// define class the area of drawing
	private JScrollPane jsp;

	private FileClass fileclass;
	String[] fontName;
	// names of the tools
	private String names[] = { "line", "pen", "rect", "oval", "circle",
			"roundrect", "rubber", "color", "stroke", "word" };

	private Icon icons[];

	private String tiptext[] = {// tip text when mouse on the tools' icon
	"Line", "Pencil", "Rectangle", "Oval", "Circle ", "Rounded rectangle", "Rubber", "Color", "Thinck",
			"Text" };
	JToggleButton button[];
	private JCheckBox bold, italic;
	private JComboBox stytles;

	public ImagePad(String string) {
		
		super(string);
		// initialize menu
		file = new JMenu("File");
		view = new JMenu("View");
		bar = new JMenuBar();

		// add menu item
		bar.add(file);
		bar.add(view);

		// add menu bar
		setJMenuBar(bar);

		// initialize File
		try {
			Reader reader = new InputStreamReader(getClass()
					.getResourceAsStream("/icon"));
		} catch (Exception e) {
			// TODO file read error
			JOptionPane.showMessageDialog(this, "File input error！", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		importIcon = new ImageIcon(getClass().getResource("/icon/openfile.jpg"));
		deleteIcon = new ImageIcon(getClass().getResource("/icon/deletefile.jpg"));
		quitIcon = new ImageIcon(getClass().getResource("/icon/quit.jpg"));
		importImg = new JMenuItem("Import", importIcon);
		deleteImg = new JMenuItem("Delete", deleteIcon);
		quit = new JMenuItem("Quit", quitIcon);

		// add File menu item
		file.add(importImg);
		file.add(deleteImg);
		file.addSeparator();
		file.add(quit);

		// add File action listener
		importImg.addActionListener(this);
		deleteImg.addActionListener(this);
		quit.addActionListener(this);

		// add View menu item :photoViewer, browser, splitMode
		photoViewer = new JRadioButtonMenuItem("Photo Viewer");
		browser = new JRadioButtonMenuItem("Browser");
		splitMode = new JRadioButtonMenuItem("Split Mode");
		photoViewer.addActionListener(this);
		browser.addActionListener(this);
		splitMode.addActionListener(this);
		view.add(photoViewer);
		view.add(browser);
		view.add(splitMode);

		// initialize tool bar
		buttonpanel = new JToolBar(JToolBar.HORIZONTAL);

		icons = new ImageIcon[names.length];
		button = new JToggleButton[names.length];
		for (int i = 0; i < names.length; i++) {
			icons[i] = new ImageIcon(getClass().getResource(
					"/icon/" + names[i] + ".jpg"));// get icon
			button[i] = new JToggleButton("", icons[i]);// create tool button
			button[i].setToolTipText(tiptext[i]);// tools' tip text 
			buttonpanel.add(button[i]);
			// button[i].setBackground(Color.gray);
			if (i < 3)
				button[i].addActionListener(this);
			else if (i <= 16)
				button[i].addActionListener(this);
		}
		CheckBoxHandler CHandler = new CheckBoxHandler();// 字体样式处理类
		bold = new JCheckBox("Bold");
		bold.setFont(new Font(Font.DIALOG, Font.BOLD, 22));// 设置字体
		bold.addItemListener(CHandler);
		italic = new JCheckBox("Italic");
		italic.addItemListener(CHandler);
		italic.setFont(new Font(Font.DIALOG, Font.ITALIC, 22));// 设置字体
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();// available font family in your computer
		fontName = ge.getAvailableFontFamilyNames();
		stytles = new JComboBox(fontName);
		stytles.addItemListener(CHandler);
		stytles.setMaximumSize(new Dimension(250, 30));
		stytles.setMinimumSize(new Dimension(150, 30));
		stytles.setFont(new Font(Font.DIALOG, Font.BOLD, 16));// set text style

		
		buttonpanel.add(bold);
		buttonpanel.add(italic);
		buttonpanel.add(stytles);

		// initialize status bar
		statusBar = new JLabel("I'm a Status Bar.");

		
		imgArea = new ImageArea(this);

		fileclass = new FileClass(this, imgArea);

		// jsp settings
		jsp = new JScrollPane(imgArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setViewportView(imgArea);

		Container con = getContentPane();
		con.add(buttonpanel, BorderLayout.NORTH);
		con.add(jsp, BorderLayout.CENTER);
		con.add(statusBar, BorderLayout.SOUTH);
		// get the screen size
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		setSize(850, 500);
		setLocation((dim.width - this.getSize().width) / 2,
				(dim.height - this.getSize().height) / 2);
		setMinimumSize(new Dimension(400, 250));
		setVisible(true);
		validate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	
	public void setStratBar(String s) {
		statusBar.setText(s);
	}

	public void actionPerformed(ActionEvent e) {
		
		for (int i = 0; i <= 6; i++) {
			if (e.getSource() == button[i]) {
				imgArea.setCurrentChoice(i);
				imgArea.createNewitem();
				imgArea.repaint();

			}

		}
		if (e.getSource() == deleteImg) //clear draw area
		{
			fileclass.deleteImg();
		} else if (e.getSource() == importImg) 
		{
			fileclass.importImg();
		} else if (e.getSource() == quit) 
		{
			System.exit(0);
		} else if (e.getSource() == button[7])// Color tool
		{
			imgArea.chooseColor();
		} else if (e.getSource() == button[8])// thickness tool
		{
			imgArea.setStroke();
		} else if (e.getSource() == button[9])// Text tool
		{		
			imgArea.setCurrentChoice(7);
			imgArea.createNewitem();
			imgArea.repaint();
		}

	}

	// Handle the font's style
	public class CheckBoxHandler implements ItemListener {

		public void itemStateChanged(ItemEvent ie) {
			
			if (ie.getSource() == bold)
			{
				if (ie.getStateChange() == ItemEvent.SELECTED)
					imgArea.setFont(1, Font.BOLD);
				else
					imgArea.setFont(1, Font.PLAIN);
			} else if (ie.getSource() == italic)
			{
				if (ie.getStateChange() == ItemEvent.SELECTED)
					imgArea.setFont(2, Font.ITALIC);
				else
					imgArea.setFont(2, Font.PLAIN);

			} else if (ie.getSource() == stytles)
			{
				imgArea.stytle = fontName[stytles.getSelectedIndex()];
			}
		}

	}
}
