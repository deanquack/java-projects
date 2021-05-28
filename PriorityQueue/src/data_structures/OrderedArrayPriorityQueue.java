/**
       *  Program 1
       *  This program utilizes the priority queue of an unordered 
       *  array. The highest priority is array[0], the lowest number.
       *  CS310-1 
       *  Date 2/19/2020
       *  @author  Dean Quach cssc1261
       */

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
	private E[] array;
	private int size;
	private int max;

	@SuppressWarnings("unchecked")
	public OrderedArrayPriorityQueue(int size) {
		max = size;
		array = (E[]) new Comparable[max];
		size = 0;
	}

	public OrderedArrayPriorityQueue() {
		this(DEFAULT_MAX_CAPACITY);
	}

	// Referenced from the course reader
	private int findInsertionPoint(E obj, int low, int high) {
		if (high < low)
			return low;

		int mid = (low + high) >> 1;
		if (obj.compareTo(array[mid]) >= 0)
			return findInsertionPoint(obj, low, mid - 1); // Bin Search Left
		return findInsertionPoint(obj, mid + 1, high); // Bin Search Right
	}

	// The insert method adds the object into the priority queue.
	// When inserted the comparisons will check if the object is
	// inserted as high or low priority.
	// When the next value is inserted the size of the queue increases
	//Referenced from the course reader
	// the system.arraycopy() takes the array, takes the high & low 
	//priorities and copies the objects to the length of array as a reference
	public boolean insert(E object) {
		if (isFull())
			return false;

		int insertionPoint = 0;
		if (!isEmpty())
			insertionPoint = findInsertionPoint(object, 0, size - 1);
		System.arraycopy(array, insertionPoint, array, insertionPoint + 1, size() - insertionPoint);
		array[insertionPoint] = object;
		size++;

		return true;
	}

	// the remove method returns null if the size of pq is zero. Removes
	// the highest priority at array[0] and decrements the size of the pq.
	@Override
	public E remove() {
		if (isEmpty()) {
			return null;
		}
		return array[--size];
	}

	// The delete method checks for the value that needs to be deleted
	// It uses linear search to find the object and the for loop is shifting the
	// array down by 1
	// decreases size by 1
	// helped by TA
	@Override
	public boolean delete(E obj) {
		int tempSize = size;
		boolean flag = false;
		if (size == 0) {
			return false;
		}
		for (int j = 0; j < tempSize; j++) {
			if (array[j].compareTo(obj) == 0) {
				//array[j] = null;
				for (int i = j; i < size - 1; i++) {
					array[i] = array[i + 1];
				}
				size--;
				tempSize--;
				j--;
				flag = true;
			}
		}
		return flag;
	}

	// The peek method checks for the highest priority value in the queue. If the
	// queue has no objects, then it returns null
	// otherwise it returns the highest priority in the queue.
	@Override
	public E peek() {
		if (isEmpty())
			return null;
		return array[size - 1];
	}

	// only checks if the value is in the pq otherwise it returns
	// false if the value is not in the pq.
	@Override
	public boolean contains(E obj) {
		if (size == 0) {
			return false;
		}
		int high = size - 1;
		int low = 0;
		while (low <= high) {
			int mid = (low + high) / 2;
			if (array[mid].compareTo(obj) < 0) {
				low = mid + 1;
			} else if (array[mid].compareTo(obj) > 0) {
				high = mid - 1;
			} else if (array[mid].compareTo(obj) == 0) {
				return true;
			}
		}
		return false;

	}

	// Returns the size of the array
	@Override
	public int size() {
		return size;
	}

	// sets the size to 0
	@Override
	public void clear() {
		size = 0;
	}

	// returns true if the pq is empty. Otherwise
	// it's false
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	// returns true if the pq is full. Otherwise
	// it returns false.
	@Override
	public boolean isFull() {
		return size == array.length;
	}

	@Override
	public Iterator<E> iterator() {
		return new IteratorHelper();

	}

	private class IteratorHelper implements Iterator<E> {
		private int index;

		@Override
		public boolean hasNext() {
			return (index < size);
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return (E) array[index++];
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
