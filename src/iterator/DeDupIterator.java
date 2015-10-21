package iterator;

import java.util.ArrayList;
import java.util.Iterator;

public class DeDupIterator<E> implements Iterator<E> {
    Iterator<E> itr;
    E prev;
    E next;

    public DeDupIterator(Iterator itr) {
	this.itr = itr;
    }

    @Override
    public boolean hasNext() {
	while (next == null || next.equals(prev)) {
	    if (itr.hasNext()) {
		next = itr.next();
	    }else{
		break;
	    }
	}
	return next != null && next.equals(prev) == false;

    }

    @Override
    public E next() {
	while (next == null || next.equals(prev)) {
	    next = itr.next();
	}
	prev = next;
	return next;
    }

    public static void main(String[] args) {
	ArrayList<Integer> arrL = new ArrayList<Integer>();
	Integer[] arr = { 1, 1, 1, 2, 3, 3, 4, 5, 5, 6, 7, 7 };
	for (Integer i : arr) {
	    arrL.add(i);
	}
	Iterator<Integer> itr = arrL.iterator();
	while (itr.hasNext()) {
	    System.out.print(itr.next() + " ");
	}
	System.out.println();

	itr = arrL.iterator();

	DeDupIterator<Integer> dItr = new DeDupIterator<Integer>(itr);
	while (dItr.hasNext()) {
	    System.out.print(dItr.next() + " ");
	}

    }

}
