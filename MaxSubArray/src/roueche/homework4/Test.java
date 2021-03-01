package roueche.homework4;

public class Test {

	public static void main(String[] args) {
		
		int[] arr = {6, 20, 3, -5, -30, -1, -7, 2, 1, 70};
		
		int b_force = MaxSubArraySumAlgos.bruteForce(arr);
		int[] divide = MaxSubArraySumAlgos.divideAndConq(arr, 0, arr.length - 1);
		int kadane = MaxSubArraySumAlgos.kadaneAlgo(arr);
		
		System.out.print("Divide First Indice: 	 " + divide[0]);
		System.out.println("	Divide Second Indice:   " + divide[1]);
		System.out.println();
		
		System.out.println("SubArray sum with brute force:        	" + b_force);
		System.out.println("SubArray sum with divide and conquer: 	" + divide[2]);
		System.out.println("SubArray sum with Kadane's Algorithm: 	" + kadane);
	}

}
