package roueche.program3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Hub {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		ArrayList<PaintingPrimitive> ppArray = new ArrayList<PaintingPrimitive>();
		ArrayList<ObjectOutputStream> outgoingObjects = new ArrayList<ObjectOutputStream>();
		ArrayList<ObjectInputStream> incomingObjects = new ArrayList<ObjectInputStream>();
		ArrayList<String> messageList = new ArrayList<String>();
		
		try {
			ServerSocket ss = new ServerSocket(7777);
			System.out.println("Waiting...");
			
			while(true) {
				Socket s = ss.accept();
				System.out.println("Painter Connected Successfully");
				
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				outgoingObjects.add(oos);
				incomingObjects.add(ois);
				
				oos.writeObject(ppArray);
				oos.writeObject(messageList);
				
				//handles making threads and adding to array when new painter connects
				ObjectThreads pt = new ObjectThreads(ppArray, ois, oos, outgoingObjects, messageList);
				Thread thread = new Thread(pt); //initialize thread
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
