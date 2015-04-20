package strings;

import java.util.Stack;

public class SolvePrefixNotation {

	public static String operators = "+-*/";

	public static int solve(String in) {
		int res = 0;
		Stack<Character> oper = new Stack<Character>();
		int j = 0;
		while (j < in.length() && operators.indexOf(in.charAt(j)) != -1) {
			oper.push(in.charAt(j));
			j++;
		}
		res = in.charAt(j) - '0';
		for (int i = j + 1; i < in.length(); i++) {
			Character c = in.charAt(i);
			if (operators.indexOf(c) != -1) {
				oper.push(c);
			} else {
				int two = (int) c - '0';
				System.out.println(two);
				if (oper.isEmpty()) {
					return -1; // not enough oper
				}
				Character op = oper.pop();
				if(op == '*'){
					res = res * two;
				}else if(op == '/'){
					res = res / two;
				}else if(op == '+'){
					res = res + two;
				}else if(op == '-'){
					res = res - two;
				}
				

			}
		}

		if (oper.isEmpty()) {
			return res;
		} else {
			return -1;
		}
	}

	public static void main(String[] args) {

		String in = "**91*23";

		System.out.println(solve(in));
	}

}
