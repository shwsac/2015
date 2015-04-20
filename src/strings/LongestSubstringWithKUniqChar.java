package strings;

import java.util.PriorityQueue;
import java.util.Comparator;

//http://www.geeksforgeeks.org/find-the-longest-substring-with-k-unique-characters-in-a-given-string/

/*Given a string you need to print longest possible substring that has exactly M unique characters. If there are more than one substring of longest possible length, then print any one of them.

 Examples:

 "aabbcc", k = 1
 Max substring can be any one from {"aa" , "bb" , "cc"}.

 "aabbcc", k = 2
 Max substring can be any one from {"aabb" , "bbcc"}.

 "aabbcc", k = 3
 There are substrings with exactly 3 unique characters
 {"aabbcc" , "abbcc" , "aabbc" , "abbc" }
 Max is "aabbcc" with length 6.

 "aaabbb", k = 3
 There are only two unique characters, thus show error message.
 * 
 * */
public class LongestSubstringWithKUniqChar {

	static class CharIndex {
		char c;
		Integer index;

		@Override
		public boolean equals(Object obj) {
			CharIndex two = (CharIndex) obj;
			return this.c == two.c;
		}

		public CharIndex(Character c, int i) {
			this.c = c;
			index = i;
		}
	}

	public static String getLongestSubstringWithKChars(String s, int k) {
		if (s == null || k <= 0) {
			return "";
		}

		PriorityQueue<CharIndex> window = new PriorityQueue<CharIndex>(k,
				new Comparator<CharIndex>() {
					@Override
					public int compare(CharIndex one, CharIndex two) {
						return one.index.compareTo(two.index);
					}
				});

		int startIndex = 0;
		int tempStart = 0;
		int length = 0;
		int tempLength = 0;
		for (int i = 0; i < s.length(); i++) {
			CharIndex n = new LongestSubstringWithKUniqChar.CharIndex(
					s.charAt(i), i);
			if (!window.contains(n)) {
				if (window.size() == k) {
					CharIndex r = window.remove();
					if (tempLength > length) {
						length = tempLength;
						startIndex = tempStart;
					}

					tempLength = tempLength - r.index + tempStart - 1;
					if (!window.isEmpty()) {
						tempStart = window.peek().index;
					}else{
						tempStart = i;
					}

				}
				window.add(n);
				tempLength++;
			} else {
				window.remove(n);
				window.add(n);
				tempLength++;
			}
		}
		if (tempLength > length) {
			length = tempLength;
			startIndex = tempStart;
		}

		return s.substring(startIndex, startIndex + length);
	}

	public static void main(String[] args) {

		System.out.println(getLongestSubstringWithKChars("aabacbebebe", 2));
		System.out.println(getLongestSubstringWithKChars("aabacbebebe", 3));
		System.out.println(getLongestSubstringWithKChars("aabacbebebe", 4));
		System.out.println(getLongestSubstringWithKChars("aabacbebebe", 1));
		System.exit(0);
	}
}
