package edu.iastate.cs228.hw3;
/*
 *  @author
 *
 *  An implementation of List<E> based on a doubly-linked list with an array for indexed reads/writes
 *
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class AdaptiveList<E> implements List<E> {
	public class ListNode // private member of outer class
	{
		public E data; // public members:
		public ListNode link; // used outside the inner class
		public ListNode prev; // used outside the inner class

		public ListNode(E item) {
			data = item;
			link = prev = null;
		}
	}

	public ListNode head; // dummy node made public for testing.
	public ListNode tail; // dummy node made public for testing.
	private int numItems; // number of data items
	private boolean linkedUTD; // true if the linked list is up-to-date.

	public E[] theArray; // the array for storing elements
	private boolean arrayUTD; // true if the array is up-to-date.

	public AdaptiveList() {
		clear();
	}

	@Override
	public void clear() {
		head = new ListNode(null);
		tail = new ListNode(null);
		head.link = tail;
		tail.prev = head;
		numItems = 0;
		linkedUTD = true;
		arrayUTD = false;
		theArray = null;
	}

	public boolean getlinkedUTD() {
		return linkedUTD;
	}

	public boolean getarrayUTD() {
		return arrayUTD;
	}

	public AdaptiveList(Collection<? extends E> c) {
		clear();
		addAll(c);
	}

	// Removes the node from the linked list.
	// This method should be used to remove a node from the linked list.
	private void unlink(ListNode toRemove) {
		if (toRemove == head || toRemove == tail)
			throw new RuntimeException("An attempt to remove head or tail");
		toRemove.prev.link = toRemove.link;
		toRemove.link.prev = toRemove.prev;
	}

	// Inserts new node toAdd right after old node current.
	// This method should be used to add a node to the linked list.
	private void link(ListNode current, ListNode toAdd) {
		if (current == tail)
			throw new RuntimeException("An attempt to link after tail");
		if (toAdd == head || toAdd == tail)
			throw new RuntimeException("An attempt to add head/tail as a new node");
		toAdd.link = current.link;
		toAdd.link.prev = toAdd;
		toAdd.prev = current;
		current.link = toAdd;
	}

	private void updateArray() // makes theArray up-to-date.
	{
		if (numItems < 0)
			throw new RuntimeException("numItems is negative: " + numItems);
		if (!linkedUTD)
			throw new RuntimeException("linkedUTD is false");

		theArray = (E[]) (new Object[numItems]);
		AdaptiveListIterator ali = new AdaptiveListIterator();

		for (int i = 0; i < numItems; i++) {
			theArray[i] = ali.next();
		}
		arrayUTD = true;
	}

	private void updateLinked() // makes the linked list up-to-date.
	{
		if (numItems < 0)
			throw new RuntimeException("numItems is negative: " + numItems);
		if (!arrayUTD)
			throw new RuntimeException("arrayUTD is false");

		if (theArray == null || theArray.length < numItems)
			throw new RuntimeException("theArray is null or shorter");

		for (int i = 0; i < numItems; i++) {
			head.link = tail;
			tail.prev = head;
			for (E element : theArray)
				add(element);
			linkedUTD = true;
		}
	}

	@Override
	public int size() {
		return numItems;
	}

	@Override
	public boolean isEmpty() {
		// TODO
		return head.link == tail;
	}

	@Override
	public boolean add(E obj) {
		ListNode n = new ListNode(obj);
		n.prev = tail.prev;
		n.link = tail;
		n.prev.link = n;
		tail.prev = n;
		numItems++;
		// System.out.println(toString());
//		updateArray();
		return true; // may need to be revised.
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (c.isEmpty())
			return false;

		Iterator<? extends E> iter = c.iterator();
		int size = c.size();

		for (int i = 0; i < size; i++) {
			add(iter.next());
		}
		return true; // may need to be revised.
	} // addAll 1

	@Override
	public boolean remove(Object obj) {
		AdaptiveListIterator adli = new AdaptiveListIterator();
		while (adli.next() != obj)
			;
		adli.remove();
		numItems--;
		return true;
	}

	private void checkIndex(int pos) // a helper method
	{
		if (pos >= numItems || pos < 0)
			throw new IndexOutOfBoundsException("Index: " + pos + ", Size: " + numItems);
	}

	private void checkIndex2(int pos) // a helper method
	{
		if (pos > numItems || pos < 0)
			throw new IndexOutOfBoundsException("Index: " + pos + ", Size: " + numItems);
	}

	private void checkNode(ListNode cur) // a helper method
	{
		if (cur == null || cur == tail)
			throw new RuntimeException("numItems: " + numItems + " is too large");
	}

	private ListNode findNode(int pos) // a helper method
	{
		ListNode cur = head;
		for (int i = 0; i < pos; i++) {
			checkNode(cur);
			cur = cur.link;
		}
		checkNode(cur);
		return cur;
	}

	@Override
	public void add(int pos, E obj) {
		if (pos < 0 || pos > size())
			throw new IndexOutOfBoundsException();
		
		AdaptiveListIterator adli = new AdaptiveListIterator();
		// get cursor in correct spot
		int index = 0;
		while (adli.hasNext() && index < pos)
			System.out.println(adli.next());

		adli.add(obj);

		numItems++;
		updateArray();
	}

	@Override
	public boolean addAll(int pos, Collection<? extends E> c) {
		if (c == null || c.isEmpty())
			return false;

		if (pos < 0 || pos > size())
			throw new IndexOutOfBoundsException();

		AdaptiveListIterator adli = new AdaptiveListIterator();
		// loop to get cursor to right position
		for (int i = 0; i < pos; i++) {
			adli.next();
		}

		for (E element : c) {
			adli.add(element);
			numItems++;
		}

		updateArray();
		return true; // may need to be revised.
	} // addAll 2

	@Override
	public E remove(int pos) {
		if (pos < 0 || pos > size())
			throw new IndexOutOfBoundsException();

		AdaptiveListIterator adli = new AdaptiveListIterator();
		int counter = 0;
		while (adli.hasNext() && counter++ < pos) {
			adli.next();
		}
		E e = adli.next();
		adli.remove();
		numItems--;
		updateArray();
		return e; // may need to be revised.
	}

	@Override
	public E get(int pos) {
		if (pos < 0 || pos > size())
			throw new IndexOutOfBoundsException();

		AdaptiveListIterator adli = new AdaptiveListIterator();
		for (int i = 0; i < pos; i++) {
			adli.next();
		}
		updateArray();
		return adli.next(); // may need to be revised.
	}

	@Override
	public E set(int pos, E obj) {
		if (pos < 0 || pos > size())
			throw new IndexOutOfBoundsException();

		AdaptiveListIterator adli = new AdaptiveListIterator();
		for (int i = 0; i <= pos; i++) {
			System.out.println(adli.next());
		}
		adli.set(obj);
		updateArray();
		System.out.println(this);
		return obj; // may need to be revised.
	}

	// If the number of elements is at most 1, the method returns false.
	// Otherwise, it reverses the order of the elements in the array
	// without using any additional array, and returns true.
	// Note that if the array is modified, then linkedUTD needs to be set to false.
	public boolean reverse() {
		E temp;
		if (numItems <= 1)
			return false;
		for (int i = 0; i < (numItems / 2); i++) {
			temp = theArray[numItems - i];
			theArray[numItems - i] = theArray[i];
			theArray[i] = temp;
		}
		return true; // may need to be revised.
	}

	@Override
	public boolean contains(Object obj) {
		AdaptiveListIterator c = new AdaptiveListIterator();
		for (int i = 0; i < numItems; i++) {
			if (c.next() == obj)
				return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		Iterator<?> temp = c.iterator();

		while (temp.hasNext()) {
			if (!contains(temp.next())) {
				return false;
			}
		}
		return true; // may need to be revised.
	} // containsAll

	@Override
	public int indexOf(Object obj) {
		// TODO
		return -1; // may need to be revised.
	}

	@Override
	public int lastIndexOf(Object obj) {
		// TODO
		return -1; // may need to be revised.
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (c == null)
			throw new NullPointerException();
		
		if (c.isEmpty())
			return false;
		
		Iterator<?> iter = c.iterator();

		for (Object o : c) {
			AdaptiveListIterator adli = new AdaptiveListIterator();
			while (adli.hasNext()) {
				if (o == adli.next()) {
					adli.remove();
					numItems--;
				}
			}
		}

		return true; // may need to be revised.
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		updateLinked();
		if (c != null)
			return false;

		AdaptiveListIterator adli = new AdaptiveListIterator();

		for (int i = 0; i < numItems; i++) {
			if (!c.contains(adli.next()))
				adli.remove();
		}
		updateArray();
		return true; // may need to be revised.
	}

	@Override
	public Object[] toArray() {
		updateArray();
		Object[] temp = new Object[numItems];
		for (int i = 0; i < numItems; i++) {
			temp[i] = theArray[i];
		}
		return temp; // may need to be revised.
	}

	@Override
	public <T> T[] toArray(T[] arr) {
		updateArray();
		T[] tmp = (T[]) (new String[numItems]);
		System.out.println(Arrays.toString(theArray));
		for (int i = 0; i < numItems; i++) {
			tmp[i] = (T) theArray[i];
		}
		
//		tmp = (T[]) theArray;
		return tmp; // may need to be revised.
	}

	@Override
	public List<E> subList(int fromPos, int toPos) {
		throw new UnsupportedOperationException();
	}

	private class AdaptiveListIterator implements ListIterator<E> {
		private int index; // index of next node;
		private ListNode cur; // node at index - 1
		private ListNode last; // node last visited by next() or previous()

		public AdaptiveListIterator() {
			if (!linkedUTD)
				updateLinked();
			cur = new ListNode(null);
			cur.link = head.link;
			cur.prev = head;
			last = null;
			index = 0;
		}

		public AdaptiveListIterator(int pos) {
			if (!linkedUTD)
				updateLinked();
			cur = new ListNode(null);
			cur.link = head.link;
			cur.prev = head;
			index = 0;
			for (int i = 0; i < pos; i++) {
				next();
			}
			last = null;
		}

		@Override
		public boolean hasNext() {
			return cur.link != tail;
		}

		@Override
		public E next() {
			if (hasNext()) {
				cur.prev = cur.link;
				cur.link = cur.link.link;
				last = cur.prev;
				index++;
				return last.data;
			} else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public boolean hasPrevious() {
			return cur.prev != head;
		}

		@Override
		public E previous() {
			if (hasPrevious()) {
				cur.link = cur.prev;
				cur.prev = cur.prev.prev;
				last = cur.link;

				index--;
				return cur.link.data;
			} else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public int nextIndex() {
			return index;
		}

		@Override
		public int previousIndex() {
			return index;
		}

		public void remove() {
			if (last != null) {
				cur = last;
				cur.link.prev = cur.prev;
				cur.prev.link = cur.link;

				index--;
				last = null;
			} else {
				throw new IllegalStateException();
			}
		}

		public void add(E obj) {
			ListNode n = new ListNode(obj);
			cur.prev = n;

			n.link = cur.link;
			n.prev = cur.link.prev;
			cur.prev = n;
			n.prev.link = n;
			n.link.prev = n;

			index++;
			last = null;
		} // add

		@Override
		public void set(E obj) {
			if (last != null) {
				last.data = obj;
			} else {
				throw new IllegalStateException();
			}
		} // set
	} // AdaptiveListIterator

	@Override
	public boolean equals(Object obj) {
		if (!linkedUTD)
			updateLinked();
		if ((obj == null) || !(obj instanceof List<?>))
			return false;
		List<?> list = (List<?>) obj;
		if (list.size() != numItems)
			return false;
		Iterator<?> iter = list.iterator();
		for (ListNode tmp = head.link; tmp != tail; tmp = tmp.link) {
			if (!iter.hasNext())
				return false;
			Object t = iter.next();
			if (!(t == tmp.data || t != null && t.equals(tmp.data)))
				return false;
		}
		if (iter.hasNext())
			return false;
		return true;
	} // equals

	@Override
	public Iterator<E> iterator() {
		return new AdaptiveListIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new AdaptiveListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int pos) {
		checkIndex2(pos);
		return new AdaptiveListIterator(pos);
	}

	// Adopted from the List<E> interface.
	@Override
	public int hashCode() {
		if (!linkedUTD)
			updateLinked();
		int hashCode = 1;
		for (E e : this)
			hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
		return hashCode;
	}

	// You should use the toString*() methods to see if your code works as expected.
	@Override
	public String toString() {
		String eol = System.getProperty("line.separator");
		return toStringArray() + eol + toStringLinked();
	}

	public String toStringArray() {
		String eol = System.getProperty("line.separator");
		StringBuilder strb = new StringBuilder();
		strb.append("A sequence of items from the most recent array:" + eol);
		strb.append('[');
		if (theArray != null)
			for (int j = 0; j < theArray.length;) {
				if (theArray[j] != null)
					strb.append(theArray[j].toString());
				else
					strb.append("-");
				j++;
				if (j < theArray.length)
					strb.append(", ");
			}
		strb.append(']');
		return strb.toString();
	}

	public String toStringLinked() {
		return toStringLinked(null);
	}

	// iter can be null.
	public String toStringLinked(ListIterator<E> iter) {
		int cnt = 0;
		int loc = iter == null ? -1 : iter.nextIndex();

		String eol = System.getProperty("line.separator");
		StringBuilder strb = new StringBuilder();
		strb.append("A sequence of items from the most recent linked list:" + eol);
		strb.append('(');
		for (ListNode cur = head.link; cur != tail;) {
			if (cur.data != null) {
				if (loc == cnt) {
					strb.append("| ");
					loc = -1;
				}
				strb.append(cur.data.toString());
				cnt++;

				if (loc == numItems && cnt == numItems) {
					strb.append(" |");
					loc = -1;
				}
			} else
				strb.append("-");

			cur = cur.link;
			if (cur != tail)
				strb.append(", ");
		}
		strb.append(')');
		return strb.toString();
	}
}
