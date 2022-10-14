// Ben Roueche, 02/13/20, Purpose: to make emojis based off of a text file

package roueche.project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		
		Scanner raf = null; // Initializes raf outside of try catch so that outside variables can access
		
		try {
			raf = new Scanner(new File("Emoji.txt"));
		} catch(FileNotFoundException e) {
			System.exit(0);
		}
		
		int row = raf.nextInt(); // First int in file
		int col = raf.nextInt(); // Second int in file
		
		Emoji[][] arr = new Emoji[row][col];
		
		StdDraw.setCanvasSize(row * 100, col * 100); // Makes canvas big enough based on 100x100 pixel emojis
		StdDraw.setXscale(0, row * 100);
		StdDraw.setYscale(0, col * 100);
		
		double s = row * 100 / 8.0; // Used to scale emojis according to how many rows * pixels
		double x = 0; // Placeholder
		double y = row * 100;
		
		for(int i = 0; i < arr.length; i++) {
			x = 0; // Have to include this to initialize every row
			for(int j = 0; j < arr[0].length; j++) {
				String check = raf.next(); // Need to store this outside so there is only 1 value being assigned (in other words, the value won't be reassigned)
				
				if(check.equals("surprise")) {
					arr[i][j] = new SurprisedFaceEmoji(x + s, y - s, s);
				} else if(check.equals("smile")) {
					arr[i][j] = new SmileyFaceEmoji(x + s, y - s, s);
				} else {
					arr[i][j] = new ClockEmoji(x + s, y - s, s, raf.nextInt());
				}
				x = x + 2.0 * s; // Scales x according to size and current x value
			}
			y = y - 2.0 * s; // Scales y according to size and current y value
		}
		
		// Simple draw loop to make each emoji
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length; j++) {
				arr[i][j].draw();
			}
		}
	}
}
