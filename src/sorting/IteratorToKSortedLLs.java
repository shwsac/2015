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
class Pair<E>{
	Integer index;
	E val;
}

public class IteratorToKSortedLLs implements Iterable{
	
	public LinkedList[] lists;
	public PriorityQueue<Pair<Integer>> minHeap = null;
	
	
	
	public boolean hasNext(){
		if(minHeap == null){
			initializeMinHeap(3);
		}
		return !minHeap.isEmpty();
	}
	
	public Integer next(){
		Pair res = minHeap.remove();
		
		return (Integer) res.val;
	}
	
	public void remove(){
		
	}
	
	public void initializeMinHeap(int k){
		minHeap = new PriorityQueue<Pair<Integer>>(k, new Comparator<Pair<Integer>>(){
			@Override
			public int compare(Pair one, Pair two){
				return ((java.lang.Integer) one.val).compareTo((java.lang.Integer) two.val);
			}
		});
	}
	
	
	public static void main(String[] args){
		int k =3;
//		LinkedList<Integer>[] lists = new LinkedList<Integer>[k];
//		lists[0].addAll(Arrays.asList(1,2,3));
//		lists[1].addAll(Arrays.asList(1,4));
//		lists[2].addAll(Arrays.asList(2,5,7,8));
		
		IteratorToKSortedLLs is = new IteratorToKSortedLLs();
		//is.lists = lists;
		
		for(LinkedList i: is){
			
		}
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
