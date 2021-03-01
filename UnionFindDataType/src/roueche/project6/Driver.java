// Ben Roueche, 06/05/20, Purpose: to make UnionFind

package roueche.project6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		try {
			UnionFindForest groups = new UnionFindForest();
			
			Scanner input = new Scanner(new File("SampleInput1.txt")); // import file
			int num = input.nextInt(); // stores number at top of file
			
			for(int i = 0; i < num; i++) {
				groups.makeSet(input.next()); // makes set with names
			}
			
			while(input.hasNext()) {
				// bring names together
				String x = input.next().trim(); // trim deletes spaces
				String y = input.next();
				
				groups.union(x.substring(0, x.length() - 1), y);
			}
				
			System.out.println("Tests begin");
			assert groups.find("Barbara").equals(groups.find("Joseph")) : " Barbara and Joseph should be in the same group!";
			assert groups.find("Joseph").equals(groups.find("Patricia")) : " Joseph and Patricia should be in the same group!";
			assert groups.find("Mary").equals(groups.find("Linda")) : " Mary and Linda should be in the same group!";
			assert groups.find("Charles").equals(groups.find("William")) : " Charles and William should be in the same group!";
				
			assert !groups.find("Michael").equals(groups.find("William")) : " Michael and William should not be in the same group!";
			assert !groups.find("Joseph").equals(groups.find("Steven")) : " Joseph and Steven should not be in the same group!";
			assert !groups.find("Richard").equals(groups.find("Mary")) : " Richard and Mary should not be in the same group!";
			assert !groups.find("William").equals(groups.find("Barbara")) : " William and Barbara should not be in the same group!";
	
			System.out.println("all tests passed!");
		
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
