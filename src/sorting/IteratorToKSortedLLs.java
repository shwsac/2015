package sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/*You are given a list containing k sorted lists of integers,
 *  each list is of varying size, max of which is n.
 *  Implement an iterator in java that with each call to its next() function retrieves the next integer
 *  in the overall order of all the integers in all the lists combined.
 *  Answer should be efficient depending on the number of calls to next().
 *  Example:
 *  lists = [[1,2,3],[1,4],[2,5,7,8]]
 *  n = 4
 *  k = 3
 *  iterator should return (if called 9 times): 1,1,2,2,3,4,5,7,8
 * */
class Pair {
	Integer index;
	Integer val;

	Pair(Integer i, Integer v) {
		index = i;
		val = v;
	}
}

public class IteratorToKSortedLLs implements Iterable<Integer> {

	public LinkedList<Integer>[] lists;
	public PriorityQueue<Pair> minHeap = null;
	public int[] indexArray = null;

	@Override
	public Iterator<Integer> iterator() {
		Iterator<Integer> it = new Iterator<Integer>() {
			public boolean hasNext() {
				if (minHeap == null) {
					initializeMinHeap(3);
				}
				return !minHeap.isEmpty();
			}

			public Integer next() {
				Pair res = minHeap.remove();
				if (indexArray[res.index] < lists[res.index].size()) {

					minHeap.add(new Pair(res.index, lists[res.index]
							.get(indexArray[res.index])));
					indexArray[res.index]++;
				}
				return (Integer) res.val;
			}

			public void remove() {
			}

			void initializeMinHeap(int k) {
				indexArray = new int[lists.length];
				minHeap = new PriorityQueue<Pair>(k, new Comparator<Pair>() {
					@Override
					public int compare(Pair one, Pair two) {
						return Integer.compare(one.val, two.val);
					}
				});

				for (int i = 0; i < lists.length; i++) {
					minHeap.add(new Pair(i, (Integer) lists[i].get(0)));
					indexArray[i] = 1;
				}
			}

		};
		return it;

	}

	public static void main(String[] args) {
		int k = 3;
		LinkedList<Integer>[] lists = new LinkedList[k];
		for (int i = 0; i < k; i++) {
			lists[i] = new LinkedList<Integer>();
		}

		lists[0].addAll(Arrays.asList(1, 2, 3));
		lists[1].addAll(Arrays.asList(1, 4));
		lists[2].addAll(Arrays.asList(2, 5, 7, 8));

		IteratorToKSortedLLs is = new IteratorToKSortedLLs();
		is.lists = lists;

		Iterator it = is.iterator();
		while (it.hasNext()) {
			System.out.println((Integer) it.next());
		}
	}

}
