package sorting;

import java.util.*;

/*
 * Divide a list of numbers into group of consecutive numbers but their
 * original order should be preserved.
 * e.g. 
 * 8,2,4,7,1,0,3,6 
 * 2,4,1,0,3 and 8,7,6 
 * */

class SuperInt {
	Integer val;
	Integer idx;

	SuperInt(int v, int i) {
		val = v;
		idx = i;
	}
}

public class DivideInConsecutiveLists {

	public static void getConsecutiveNumLists(int[] arr) {
		LinkedList<ArrayList<SuperInt>> result = new LinkedList<ArrayList<SuperInt>>();
		LinkedList<SuperInt> input = new LinkedList<SuperInt>();
		int idx = 0;
		for (int i : arr) {
			input.add(new SuperInt(i, idx++));
		}

		Collections.sort(input, valComparer);
		ArrayList<SuperInt> curr = new ArrayList<SuperInt>();
		curr.add(input.get(0));
		int prev = input.get(0).val;
		for (int i = 1; i < arr.length; i++) {
			if ((input.get(i).val - prev) > 1) {
				Collections.sort(curr, idxComparer);				
				System.out.println();
				result.add(curr);
				curr = new ArrayList<SuperInt>();
				
			}
			curr.add(input.get(i));
			prev = input.get(i).val;
		}
		result.add(curr);
		for (List<SuperInt> l : result) {
			for (SuperInt si : l) {
				System.out.print(si.val + " ");
			}
			System.out.println();
		}

	}

	public static Comparator<SuperInt> valComparer = new Comparator<SuperInt>() {

		@Override
		public int compare(SuperInt one, SuperInt two) {
			return Integer.compare(one.val, two.val);
		}

	};

	public static Comparator<SuperInt> idxComparer = new Comparator<SuperInt>() {

		@Override
		public int compare(SuperInt one, SuperInt two) {
			return Integer.compare(one.idx, two.idx);
		}

	};

	public static void main(String[] args) {
		int[] arr = {8,2,4,7,1,0,3,6} ;
		getConsecutiveNumLists(arr);
	}
}
