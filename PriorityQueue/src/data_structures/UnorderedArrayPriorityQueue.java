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

public class UnorderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
	private E[] array;
	private int size = 0;
	private int max;

	@SuppressWarnings("unchecked")
	public UnorderedArrayPriorityQueue(int size) {
		array = (E[]) new Comparable[size];
		max = size;
	}

	@SuppressWarnings("unchecked")
	public UnorderedArrayPriorityQueue() {
		array = (E[]) new Comparable[DEFAULT_MAX_CAPACITY];
	}

	@Override
	// The insert method adds the object into the priority queue.
	// When the next value is inserted the size of the queue increases.
	public boolean insert(E object) {
		if(size == array.length) {
			return false;
		}
		array[size] = object;
		size++;
		return true;
		}

	// the remove method, in this case, loops each value in the queue
	// for the highest priority. If the value in the queue is higher than
	// the object in array[high] then it replaces it and decrements
	// size in the next for loop
	@Override
	public E remove() {
		int high = 0;
		if (isEmpty()) {
			return null;
		}
		for (int j = 1; j < size; j++) {
			if (array[j].compareTo(array[high]) < 0) {
				high = j;
			}
		}
		E number = array[high];
		for (int i = high; i < size - 1; i++) {
			array[i] = array[i + 1];
		}
		size--;

		return number;
	}

	// The delete method checks for the value that is inputed by the user if it's in
	// the array. The
	// boolean flag variable returns true if the value is removed.
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

	// The peek method checks for the highest priority value in the array. If the
	// array has no objects,
	// then it returns null otherwise returns the highest priority in the array.
	@Override
	public E peek() {
		int highPriorty = 0;
		if (size == 0) {
			return null;
		}
		for (int i = 1; i < size; i++) {

			if (array[i].compareTo(array[highPriorty]) < 0) {
				highPriorty = i;
			}
		}
		return array[highPriorty];
	}

	// This method uses a for loop to determine if the object is in the queue.
	// The for loop compares the object in array[i] to obj and sees if they are the
	// same.
	// If the object is in the queue then it's true otherwise it's false.
	@Override
	public boolean contains(E obj) {

		for (int i = 0; i < array.length; i++) {
			if (array[i].compareTo(obj) == 0) {
				return true;
			}
		}
		return false;
	}

	// Returns the size of the queue
	@Override
	public int size() {
		return size;
	}

	// sets the size of the queue to 0
	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		size = 0;
	}

	// if the size is 0 then it returns true otherwise it's false
	@Override
	public boolean isEmpty() {
		return size == 0;

	}

	// if the size is equal to the number of objects in the queue, then it returns
	// true
	// otherwise it returns false
	@Override
	public boolean isFull() {
		return size == array.length;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int index;

			@Override
			public boolean hasNext() {
				return index < size;
			}

			@Override
			public E next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				return array[index++];
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}