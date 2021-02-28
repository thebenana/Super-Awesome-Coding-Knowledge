package roueche.homework3;

public class RearrangeAlgorithms {
	
	
	/*
	 * 
	 * Runtime = O(n) so this code is optimal
	 * 
	 */
	

	public static void main(String[] args) {

		int[] test = {4, 7, -2, 67, -9, -2, -8, 1};
		
		System.out.print("Original array: ");
		
		for(int i = 0; i < test.length; i++) {
			System.out.print(test[i] + " ");
		}
		
		System.out.println();
		System.out.print("Rearranged array: ");
		posNeg(test);
		
		for(int i = 0; i < test.length; i++) {
			System.out.print(test[i] + " ");
		}
		
	}
	
	/*	positive and negative elements	*/
	
	static void posNeg(int arr[]) {
        int value;
        int j;
        
        for (int i = 0; i < arr.length - 1; i++) {
            
        	value = arr[i]; //used to mark the current index
 
            j = i - 1; //use j as another way to search/compare through each iteration
            
            while (j >= 0 && arr[j] > 0) { //cgo backwards from j and check to see if index is positive
                arr[j + 1] = arr[j]; //move positive element to right
                j = j - 1;
            }
 

            arr[j + 1] = value; //move to correct position
        }
    }

}
