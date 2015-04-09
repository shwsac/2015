// 	Check if int has same value when reversed aka palindrome.
public class CheckIntegerPalindrome {
	public static boolean checkIntegerPalindrome(int input) {
		if (input < 0) {
			input = input * -1;
		}
		int tens = 1;
		while (input / tens >= 10) {
			tens *= 10;
		}
		while (input > 0) {
			if ((input / tens) != input % 10) {
				return false;
			}
			input = input % tens;
			input = input / 10;
			tens /= 100; // compared two digits , start and end.
		}

		return true;
	}

	public static int reverseInt(int input) {
		boolean isNeg = false;
		if (input < 0) {
			isNeg = true;
			input *= -1;
		}
		int res = 0;
		while (input > 0) {
			res = res * 10 + input % 10;
			input /= 10;
		}
		if (isNeg) {
			return res * -1;
		}
		return res;

	}

	public static void main(String args[]) {
		System.out.println(checkIntegerPalindrome(12345));
		System.out.println(checkIntegerPalindrome(343));
		System.out.println(checkIntegerPalindrome(121));
		System.out.println(checkIntegerPalindrome(-1));
	}

}
