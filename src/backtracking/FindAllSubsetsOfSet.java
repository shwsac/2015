package backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAllSubsetsOfSet {

	public static ArrayList<ArrayList<Integer>> getSubsetWithoutDups(int[] input) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		for (int i : input) {
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			for (ArrayList<Integer> list : result) {
				temp.add(list);
			}

			for (ArrayList<Integer> list : temp) {
				list.add(i);
			}

			ArrayList<Integer> single = new ArrayList<Integer>();
			single.add(i);
			temp.add(single);

			result.addAll(temp);
		}
		result.add(new ArrayList<Integer>());

		return result;
	}

	public static ArrayList<ArrayList<Integer>> subsetsWithDup(int[] num) {
		if (num == null)
			return null;

		Arrays.sort(num);

		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> prev = new ArrayList<ArrayList<Integer>>();

		for (int i = num.length - 1; i >= 0; i--) {

			// get existing sets
			if (i == num.length - 1 || num[i] != num[i + 1]) {
				prev = new ArrayList<ArrayList<Integer>>();
				for (ArrayList<Integer> list : result) {
					// System.out.println("List: " + list);
					// prev.add(list);
					// Make new list instead of referencing old one.
					prev.add(new ArrayList(list));
				}

			}

			// add current number to each element of the set
			for (ArrayList<Integer> temp : prev) {
				temp.add(0, num[i]);
			}

			// add each single number as a set, only if current element is
			// different with previous
			if (i == num.length - 1 || num[i] != num[i + 1]) {
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.add(num[i]);
				prev.add(temp);
			}

			// add all set created in this iteration
			// result.addAll(prev);
			// Make new lists instead of refering old ones.
			for (ArrayList<Integer> temp : prev) {
				result.add(new ArrayList<Integer>(temp));
			}
		}

		// add empty set
		result.add(new ArrayList<Integer>());

		return result;
	}

	public static void main(String[] args) {
		int[] arr = { 1, 1, 2 };
		ArrayList<ArrayList<Integer>> res = subsetsWithDup(arr);
		for (ArrayList<Integer> l : res) {
			System.out.println(l);
		}

	}
}
