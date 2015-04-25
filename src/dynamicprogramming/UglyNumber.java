package dynamicprogramming;

/* Ugly numbers are numbers whose only prime factors are 2, 3 or 5. The sequence
 * 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, …
 * shows the first 11 ugly numbers. By convention, 1 is included.
 * Write a program to find and print the n’th ugly number.
 * */
public class UglyNumber {
	public static int getNthUglyNumber(int n) {
		int arr[] = new int[n];
		arr[0] = 1;
		int i2 = 0;
		int i3 = 0;
		int i5 = 0;
		int idx = 1;
		int nextUglyNum = 0;
		while (idx < n) {
			int nextMultipleOf2 = 2 * arr[i2];
			int nextMultipleOf3 = 3 * arr[i3];
			int nextMultipleOf5 = 5 * arr[i5];
			nextUglyNum = Math.min(Math.min(nextMultipleOf2, nextMultipleOf3),
					nextMultipleOf5);
			if (nextUglyNum == nextMultipleOf2) {
				i2++;
			}
			if (nextUglyNum == nextMultipleOf3) {
				i3++;
			}
			if (nextUglyNum == nextMultipleOf5) {
				i5++;
			}
			arr[idx] = nextUglyNum;
			System.out.print(arr[idx] + "  "); // comment to stop printing intermediate results
			idx++;
		}
		return arr[n - 1];
	}

	public static void main(String[] args) {
		getNthUglyNumber(10);
	}
}
