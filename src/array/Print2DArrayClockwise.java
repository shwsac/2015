package array;

public class Print2DArrayClockwise {

	public static void printSpiral(int[][] arr, int layer) {
		//System.out.println("layer:" + layer);
		if(arr[0].length - layer -1 == layer){
			System.out.print(arr[layer][layer]);
		}

		for (int i = layer; i < arr[0].length - layer - 1; i++) {
			System.out.print(arr[layer][i]+ " ");
		}
		for (int i = layer; i < arr.length - layer - 1; i++) {
			System.out.print(arr[i][arr.length - layer - 1]+ " ");
		}
		for (int i = arr.length - layer - 1; i > layer + 1; i--) {
			System.out.print(arr[arr.length - layer - 1][i]+ " ");
		}
		for (int i = arr.length - layer; i > layer + 1; i--) {
			System.out.print(arr[i - 1][layer]+ " ");
		}

	}

	public static void main(String args[]) {

		//int[][] arr = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
//		 int[][] arr = { { 1, 2, 3, 4 ,5}, { 6, 7, 8 , 9, 10},{ 11, 12, 13,
//		 14, 15},{ 16, 17, 18, 19, 20 } ,{ 16, 17, 18, 19, 20 }};

//		int[][] arr = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
//				 { 13, 14, 15, 16 }};
		 int[][] arr = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
		 { 13, 14, 15, 16 },{ 17, 18, 19, 20 } };
		// print regular
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println();

		for (int i = 0; i <= Math.ceil(arr[0].length / 2); i++) {
			printSpiral(arr, i);
		}
	}
}
