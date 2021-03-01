package roueche.homework2;

public class MergeSort {

	public static void main(String[] args) {

		int n = 1000;
		for(int i = 0; i < 4; i++) {		//random values
			double[] arr = new double[n];
			
			for(int j = 0; j < arr.length - 1; j++) {
				arr[j] = Math.random() * 100;
			}
			
			double startTime = System.currentTimeMillis();
			mS(arr, 0, arr.length - 1);
			double endTime = System.currentTimeMillis();
			
			double runTime = (endTime - startTime) / 1000.0;
			
			System.out.println("Runtime: " + runTime + " " + n);
			
			n += 1000;
		}
		
	}
	
	static double[] mS(double[] array, int p, int r) {
		
		if(p == r) {
			return array;
		} else {
			int q = (p + r) / 2;
			mS(array, p, q);
			mS(array, q + 1, r);
			merge(array, p, q, r);
		}
		return array;
	}
	
	public static void merge(double[] array, int p, int q, int r) {
		
		int n1 = q - p + 1; 	//mid - first + 1
		int n2 = r - q;			//last - mid
		
		double[] left = new double[n1 + 1];
		double[] right = new double[n2 + 1];
		
		for(int i = 0; i < n1; i++) {
			left[i] = array[p + i];
		}
		
		for(int i = 0; i < n2; i++) {
			right[i] = array[q + i + 1];
		}
		
		left[n1] = Integer.MAX_VALUE;
		right[n2] = Integer.MAX_VALUE;
		
		int i = 0; int j = 0;
		
		for(int k = p; k < r + 1; k++) {
			if(left[i] <= right[j]) {
				array[k] = left[i];
				i = i + 1;
			} else {
				array[k] = right[j];
				j = j + 1;
			}
		}
		
	}

}
