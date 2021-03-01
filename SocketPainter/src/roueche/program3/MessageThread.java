package roueche.program3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import javax.swing.JTextArea;

public class MessageThread implements Runnable {

	ObjectInputStream ois;
	PaintingPrimitive shapePrimitive;
	PaintingPanel pp;
	JTextArea messageList;
	
	public MessageThread(ObjectInputStream ois, PaintingPanel pp, JTextArea messageList) {
		this.ois = ois;
		this.pp = pp;
		this.messageList = messageList;
	}
	
	@Override
	public void run() {
		try {
			
			//adds message to stored list
			while(true) {
				
				Object storedObject = ois.readObject();
				
				if(storedObject instanceof PaintingPrimitive) { //compares to see if it is a PaintingPrimitive
					pp.addPrimitive((PaintingPrimitive) storedObject); //adds
					pp.repaint(); //repaint to show on screen
				
				 } else {
					
					String message = (String) storedObject;
					messageList.append(message);
				} 
			}
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

}
