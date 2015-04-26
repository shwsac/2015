public class NumberBaseConversions {

	static final String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static long convertToDecimal(String number, int base) {
		if (number == null || number.length() == 0) {
			return 0;
		}
		number = number.toUpperCase();
		int multiplier = 1;
		long result = 0;
		for (int i = number.length() - 1; i >= 0; i--) {
			char c = number.charAt(i);
			if (i == 0 && c == '-') {
				result = -result;
			} else {
				int digit = digits.indexOf(c);
				if (digit == -1 || digit >= base) {
					System.out.println("Unexcepted Input");
					System.exit(-1);
				}
				result = result + digit * multiplier;
				multiplier *= base;
			}

		}
		return result;
	}

	public static String convertFromDecimal(long decimal, int base) {
		int bitsInLong = 64;

		if (base < 2 || base > digits.length()) {
			System.out.println("Unexcepted Base");
			System.exit(-1);
		}
		if (decimal == 0) {
			return "0";
		}
		// String result = "";
		char[] result = new char[bitsInLong];

		int index = bitsInLong-1;
		long curr = decimal;
		if (decimal < 0) {
			curr = -curr;
		}
		while (curr != 0) {
			// get the last digit for new base
			int remainder = (int) curr % base;
			// get how it is represented in new base.
			result[index--] = (char) digits.charAt(remainder);
			curr = curr / base; // get rid of last digit.
		}
		// get from offset till count.
		String res = new String(result, index + 1, bitsInLong - index - 1);
		if (decimal < 0) {
			res = "-" + res;
		}

		return res;
	}

	public static void main(String[] args) {
		System.out.println(convertToDecimal("-F", 16)); // -15
		System.out.println(convertToDecimal("10", 2)); // 2
		System.out.println(convertToDecimal("P", 26)); // 25
		System.out.println(convertToDecimal("Z", 36)); // 35

		System.out.println(convertFromDecimal(-15, 16)); // -15
		System.out.println(convertFromDecimal(2, 2)); // 2
		System.out.println(convertFromDecimal(25, 26)); // 25
		System.out.println(convertFromDecimal(35, 36)); // 35

	}

}
