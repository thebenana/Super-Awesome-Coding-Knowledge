package roueche.homework4;

public class MaxSubArraySumAlgos {

	/*	BRUTEFORCE PER LECTURE	*/
	
	public static int bruteForce(int[] arr) {
		
		int output = Integer.MIN_VALUE;
		int firstIndice = 0;
		int lastIndice = 0;
		
		for(int i = 0; i < arr.length; i++) {

			int temp = 0;
			
			for(int j = i; j < arr.length; j++) {
				temp += arr[j];
		
				if(temp > output) {
					output = temp;
					firstIndice = i;
					lastIndice = j;
				}
			}
		}
		
		System.out.print("Bruteforce First Indice: " + firstIndice);
		System.out.println("	Bruteforce Last Indice: " + lastIndice);
		
		return output;
	}
	
	/*	DIVIDE AND CONQUER PER LECTURE	*/
	
	/*
	 * In order to get the indices for this one, I had to make an array that contained
	 * the firstIndice, lastIndice, and SUM. Then, to retrieve the necessary information
	 * you can just use the appropriate array index in MAIN.
	 */

	public static int[] divideAndConq(int ar[], int low, int high) {
	  if (high == low) {
		  
		  int[] base = new int[3];
		  base[0] = low;
		  base[1] = high;
		  base[2] = ar[low];
		  
		  return base;
	  }
	    
	  

	  // middle element of the array
	  int mid = (high + low) / 2;
	  
	  int[] left_subarray = new int[3];
	  int[] right_subarray = new int[3];
	  int[] cross_subarray = new int[3];
	  
	  left_subarray = divideAndConq(ar, low, mid);
	  right_subarray = divideAndConq(ar, mid + 1, high);
	  cross_subarray = crossingSumHelper(ar, low, mid, high);
	  
	  // checking conditions
	  
	  int leftLow = left_subarray[0];
	  int leftHigh = left_subarray[1];
	  int leftSum = left_subarray[2];
	  
	  int rightLow = right_subarray[0];
	  int rightHigh = right_subarray[1];
	  int rightSum = right_subarray[2];
	  
	  int crossLow = cross_subarray[0];
	  int crossHigh = cross_subarray[1];
	  int crossSum = cross_subarray[2];
	  
	  if (leftSum >= rightSum && leftSum >= crossSum) {
		  return left_subarray;
	  } else if (rightSum >= leftSum && rightSum >= crossSum) {
		  return right_subarray;
	  } else {
		  return cross_subarray;
	  }
	}
	
	/*	KADANE'S ALGORITHM PER LECTURE	*/
	
	public static int kadaneAlgo(int[] arr) {
		
		int size = arr.length;
		
		int maxSumSoFar = 0;
		int maxSumTok = 0;
		int firstIndice = 0;
		int lastIndice = 0;
		
		for(int i = 0; i < size; i++) {
			
			if(arr[i] > maxSumTok + arr[i]) {
				firstIndice = i;
				maxSumTok = arr[i];
			} else {
				maxSumTok += arr[i];
			}
			
			if(maxSumSoFar < maxSumTok) {
				maxSumSoFar = maxSumTok;
				lastIndice = i;
			}
		}
		
		System.out.print("Kadane First Indice: 	 " + firstIndice);
		System.out.println("	Kadane Last Indice: 	" + lastIndice);
		
		return maxSumSoFar;
	}
	
	/*	CROSSING SUM HELPER FOR DIVIDE AND CONQUER	*/
	
	private static int[] crossingSumHelper(int[] arr, int l, int m, int h) {
		
		int sum = 0;
		int firstIndice = 0;
		int lastIndice = 0;
																									// compute left section sum (backwards FOR loop)
		int leftSection = Integer.MIN_VALUE;
		
		for(int i = m; i >= l; i--) {
			sum += arr[i];
			
			if(sum > leftSection) {
				leftSection = sum;
				firstIndice = i;
			}
		}
		
		sum = 0;																					// compute right section sum (reverse of left FOR loop)
		int rightSection = Integer.MIN_VALUE;
		
		for(int i = m + 1; i <= h; i++) {
			sum += arr[i];
			
			if(sum > rightSection) {
				rightSection = sum;
				lastIndice = i;
			}
		}
		
		int[] storage = new int[3];
		
		storage[0] = firstIndice;
		storage[1] = lastIndice;
		storage[2] = leftSection + rightSection;
		
		return storage;																				// return the array used for divide
	}
}
