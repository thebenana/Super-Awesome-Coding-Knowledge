package roueche.program3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ObjectThreads implements Runnable {
	
	/*
	 * This class is responsible for creating an array of shape and message threads and assigning
	 * each thread depending on the number of Painters() connected to it
	 */

	private ArrayList<PaintingPrimitive> ppArray;
	private PaintingPrimitive storedShape;
	
	ObjectInputStream ois;
	ObjectOutputStream oos;
	ArrayList<ObjectInputStream> incomingObjects;
	ArrayList<ObjectOutputStream> outgoingObjects;
	ArrayList<String> messageList;
	
	public ObjectThreads(ArrayList<PaintingPrimitive> ppArray, ObjectInputStream ois, ObjectOutputStream oos, ArrayList<ObjectOutputStream> outgoingObjects, ArrayList<String> messageList) {
		this.ppArray = ppArray;
		this.ois = ois;
		this.oos = oos;
		this.outgoingObjects = outgoingObjects;
		this.messageList = messageList;
	}
	
	
	@Override
	public synchronized void run() {
		
		try {
			while(true) {
				
				Object storedObject = ois.readObject(); //initialize object storage
				
				if(storedObject instanceof PaintingPrimitive) { //compare to see if it is a shape
					
					//stores shape from painter and blocks
					storedShape = (PaintingPrimitive) storedObject;
					
					//adds to all pp's
					ppArray.add(storedShape);
					
					//loop through object array and output shapes to pp
					for(int i = 0; i < outgoingObjects.size(); i++) {
						if(oos != outgoingObjects.get(i)) { //used to handle duplicates
							outgoingObjects.get(i).writeObject(storedShape); //write across painters
						}
					}
				 } else if (storedObject instanceof String) { //compare to see if it is a message
					
					//implement message handling
					String msg = (String) storedObject + ": ";
					msg = msg + (ois.readObject() + "\n");
					messageList.add(msg);
					
					//same as above but message
					for(int i = 0; i < outgoingObjects.size(); i++) {
						if(oos != outgoingObjects.get(i)) {
							outgoingObjects.get(i).writeObject(msg);
						}
					} 
				} else {
					for(int i = 0; i < outgoingObjects.size(); i++) { //used to handle when a painter is closed
						if(oos == outgoingObjects.get(i)) {
							outgoingObjects.remove(i); //removes from array
						}
					} 
					ois.close();
					oos.close();
					return;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
