package roueche.homework2;

public class InsertionSort {

	public static void main(String[] args) {
		
		int n = 1000;
		
		for(int i = 0; i < 4; i++) {		//random values
			double[] arr = new double[n];
			
			for(int j = 0; j < arr.length - 1; j++) {
				arr[j] = Math.random() * 100;
			}
			
			double startTime = System.currentTimeMillis();
			iS(arr);
			double endTime = System.currentTimeMillis();
			
			double runTime = (endTime - startTime) / 1000.0;
			
			System.out.println("Runtime: " + runTime + " " + n);
			
			n += 1000;
		}
		
		System.out.println();
		n = 1000;
		
		for(int i = 0; i < 4; i++) {		//sorted values
			double[] arr = new double[n];
			
			for(int j = 0; j < arr.length - 1; j++) {
				arr[j] = j;
			}
			
			double startTime = System.currentTimeMillis();
			iS(arr);
			double endTime = System.currentTimeMillis();
			
			double runTime = (endTime - startTime) / 1000.0;
			
			System.out.println("Runtime: " + runTime + " " + n);
			
			n += 1000;
		}
	}
	
	public static void iS(double[] array) {
		
		for (int i = 1; i < array.length; i++) { 
	        double key = array[i]; 
	        int j = i - 1;
	        while (j >= 0 && array[j] > key) {
	        	array[j + 1] = array[j];
	            j = j - 1;
	        }
	        array[j + 1] = key; 
	    }
		
	}

}
