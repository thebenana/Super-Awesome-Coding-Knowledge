//Ben Roueche, 12/02/20 - Project 3

package roueche.program3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.*;


@SuppressWarnings("serial")
public class Painter extends JFrame implements ActionListener, MouseListener, WindowListener {

	private boolean circleButtonPress = false;
	private boolean lineButtonPress = false;
	
	private boolean redButtonPress = false;
	private boolean greenButtonPress = false;
	private boolean blueButtonPress = false;
	
	private PaintingPrimitive shape;
	
	private Point firstClick;
	private Point secondClick;
	
	private Color colors;
	
	//chat instance variables
	private String name;
	private JTextArea messageContentsBox;
	private JTextArea messageContainer;
	private JTextArea messageContents;
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	//panels that need to be global
	private JPanel p;
	private PaintingPanel pp;

	@SuppressWarnings({ "unchecked", "resource" })
	public Painter() {
		
		//canvas
		setSize(new Dimension(500, 500)); //size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //default close operation
		name = JOptionPane.showInputDialog("Enter your name: ");
		
		/*		PANELS		*/
		
		addWindowListener(this);
		
		//background
		p = new JPanel(); //default panel
		p.setBackground(Color.WHITE);
		p.setLayout(new BorderLayout());

		//panel for left buttons
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(3, 1));
		
		//panel for buttons on top
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 2));
		
		//panel for buttons on bottom
		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
		
		JPanel send = new JPanel();
        send.setLayout(new BoxLayout(send, BoxLayout.X_AXIS));
		
		/*		BUTTONS		*/
		
		//red
		JButton redPaint = new JButton();
		redPaint.setBackground(Color.RED);
		leftPanel.add(redPaint); //add to left panel

		//green
		JButton greenPaint = new JButton();
		greenPaint.setBackground(Color.GREEN);
		leftPanel.add(greenPaint); //add to left panel

		//blue
		JButton bluePaint = new JButton();
		bluePaint.setBackground(Color.BLUE);
		leftPanel.add(bluePaint); //add to left panel

		//line
		JButton lineButton = new JButton();
		lineButton.setText("Line");
		topPanel.add(lineButton); //add to top panel

		//circle
		JButton circleButton = new JButton();
		circleButton.setText("Circle");
		topPanel.add(circleButton); //add to top panel
		
		/*		CHAT		*/
		messageContents = new JTextArea();
		messageContents.setBackground(Color.LIGHT_GRAY);
		messageContents.setEditable(false);
		messageContents.setText(name + ": ");
		send.add(messageContents);
		
		messageContentsBox = new JTextArea();
		messageContentsBox.setBackground(Color.LIGHT_GRAY);
		messageContentsBox.setLineWrap(true);
		send.add(messageContentsBox);
		
		JButton sendChat  = new JButton("Send");
		sendChat.addActionListener(this);
		sendChat.setActionCommand("Chat");
		send.add(sendChat);
		bottom.add(send);
		
		messageContainer = new JTextArea(5, 1);
		messageContainer.setBackground(Color.GRAY);
		messageContainer.setEditable(false);
		messageContainer.setLineWrap(true);
		bottom.add(new JScrollPane(messageContainer));
        
        /*		ADD STATEMENTS		*/
        
		p.add(topPanel, BorderLayout.NORTH); //adds buttons
		p.add(leftPanel, BorderLayout.WEST); 
		p.add(bottom, BorderLayout.SOUTH);

		/*		PAINTING PANEL CREATION		*/
		
		pp = new PaintingPanel();
		pp.addMouseListener(this);
		p.add(pp, BorderLayout.CENTER); 

		/*		ACTION HANDLERS		*/
		
		//listens for button presses
		circleButton.setActionCommand("Circle");
		circleButton.addActionListener(this);
		
		lineButton.setActionCommand("Line");
		lineButton.addActionListener(this);
		
		redPaint.setActionCommand("Red");
		redPaint.addActionListener(this);
		
		greenPaint.setActionCommand("Green");
		greenPaint.addActionListener(this);
		
		bluePaint.setActionCommand("Blue");
		bluePaint.addActionListener(this);
		
		sendChat.setActionCommand("Chat");
		sendChat.addActionListener(this);
		
		//connect frame to holder then make visible
		setContentPane(p);
		setVisible(true);
		
		/*		SOCKET CREATION		*/
		
		try {

			Socket socket = new Socket("localhost", 7777);
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			
			//keeps history of shapes
			pp.ppArray = (ArrayList<PaintingPrimitive>) ois.readObject();
			repaint();

			//keeps history of messages 
			ArrayList<String> messageList = (ArrayList<String>) ois.readObject();
			for(int i = 0; i < messageList.size(); i++) {
				messageContainer.append(messageList.get(i));
			}

			//open threads
			MessageThread ls = new MessageThread(ois, pp, messageContainer);
			Thread th = new Thread(ls);
			th.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//default actionPerformed param and set each action to true/false depending on action
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Circle")) {
			circleButtonPress = true;
			lineButtonPress = false;
		} else if (e.getActionCommand().equals("Line")) {
			lineButtonPress = true;
			circleButtonPress = false;
		} else if (e.getActionCommand().equals("Red")) {
			redButtonPress = true;
			greenButtonPress = false;
			blueButtonPress = false;
		} else if (e.getActionCommand().equals("Green")) {
			redButtonPress = false;
			greenButtonPress = true;
			blueButtonPress = false;
		} else if (e.getActionCommand().equals("Blue")) {
			redButtonPress = false;
			greenButtonPress = false;
			blueButtonPress = true;
		} else if (e.getActionCommand().equals("Chat")) { //instead of making a true/false, append the message
			String message = messageContentsBox.getText();
			if(!message.equals("")) {
				messageContainer.append(name + ": " + message + "\n");
			}
			messageContentsBox.setText("");

			try {
				oos.writeObject(name);
				oos.writeObject(message);
			} catch (IOException u) {
				// TODO Auto-generated catch block
				u.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		firstClick = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		secondClick = e.getPoint();

		//color selection
		if (redButtonPress) {
			colors = Color.RED;
		} else if (greenButtonPress) {
			colors = Color.GREEN;
		} else if (blueButtonPress) {
			colors = Color.BLUE;
		}

		//shape selection
		if (circleButtonPress) {
			shape = new Circle(firstClick, secondClick, colors);
		} else if (lineButtonPress) {
			shape = new Line(firstClick, secondClick, colors);
		}
		pp.addPrimitive(shape);
		
		try {
			oos.writeObject(shape);
		} catch (IOException u) {
			// TODO Auto-generated catch block
			u.printStackTrace();
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			int num = -1;
			oos.writeObject(num); //signal window closing
		} catch (IOException u) {
			// TODO Auto-generated catch block
			u.printStackTrace();
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	//create main to make new Painter()
	public static void main(String[] args) {
		new Painter();
	}
}
