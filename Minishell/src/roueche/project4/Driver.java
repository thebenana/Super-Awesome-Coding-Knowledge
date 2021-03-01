// Ben Roueche, 05/15/20, Purpose: to implement a FileSystem like Linux

package roueche.project4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) throws Exception {
		
		FileSystem f = new FileSystem();
		Scanner keyboard = new Scanner(System.in);
		
		String s; // used to store user input
		String[] line; // used to store lines
		
		try { // handles input streams
			FileInputStream fsIn = new FileInputStream("fs.data");
			ObjectInputStream obIn = new ObjectInputStream(fsIn);
			
			f = (FileSystem) obIn.readObject();
			
			fsIn.close(); // close streams
			obIn.close();
		} catch (IOException e) {
			f = new FileSystem();
		} catch (ClassNotFoundException e) {
			f = new FileSystem();
		}
		
		do {
			s = keyboard.nextLine();
			line = s.split(" ");
			
			if(line[0].equals("quit")) {
				break;
			} else if(line[0].equals("cd")) {
				try {
					f.cd(line[1]);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				} catch (IndexOutOfBoundsException e) {
					e.getMessage();
				}
			} else if(line[0].equals("ls")) {
				f.ls();
			} else if(line[0].equals("touch")) {
				try {
					f.touch(line[1]);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());				}
			} else if(line[0].equals("mkdir")) {
				try {
					f.mkdir(line[1]);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Please enter dir name");
				}
			} else if(line[0].equals("tree")) {
				System.out.println(f.tree());
			} else if(line[0].equals("pwd")) {
				System.out.println(f.pwd());
			} else if(line[0].equals("rm")) {
				try {
					f.rm(line[1]);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			} else if(line[0].equals("rmdir")) {
				try {
					f.rmdir(line[1]);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			} else {
				System.out.println("Invalid command :(");
			}
		} while(!(line[0].equals("quit")));
		
		try { // handles output streams
			FileOutputStream fsOut = new FileOutputStream("fs.data");
			ObjectOutputStream obOut = new ObjectOutputStream(fsOut);
			
			obOut.writeObject(f);
			fsOut.close();
			obOut.close();
		} catch(Exception e) {
			throw new Exception("Doesn't run");
		}
	}

}
