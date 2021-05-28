package data_structures;

import java.util.ConcurrentModificationException;

import java.util.Iterator;

import java.util.NoSuchElementException;

public class BinaryHeapPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {

	public int modCounter;
	private Wrapper<E>[] heapArray;
	private int size;
	private int max;
	private int value;
	private int child;

	@SuppressWarnings("unchecked")

	public BinaryHeapPriorityQueue(int size) {
		max = size;
		value = 0;
		heapArray = (Wrapper<E>[]) new Wrapper[max];			
		this.size = 0;

	}

	public BinaryHeapPriorityQueue() {
		this.size = 0;
		value = 0;
		max = DEFAULT_MAX_CAPACITY;
		heapArray = (Wrapper<E>[]) new Wrapper[max];
	}
	// this is a wrapper class that defines the type of array 
	protected class Wrapper<E extends Comparable<E>> implements Comparable<Wrapper<E>> {
		long number;
		E data;
		public Wrapper(E d) {
			number = value++;
			data = d;
		}
		@Override
		public int compareTo(Wrapper<E> o) {
			if (data.compareTo(o.data) == 0)
				return (int) (number - o.number);
			return (data.compareTo(o.data));
		}
	}

	// referenced from the course reader
	// this method inserts the value in the priority queue
	// if the array is full then return false
	// this method calls the trickle up method to determine the order of the 
	//highest priority as the root 
	@Override
	public boolean insert(E object) {
		if (isFull()) {
			return false;
		}
		else {
			heapArray[size] = new Wrapper<E>(object);
			size++;
			trickleup();
			modCounter++;
		}
		return true;
	}
	// helps order the array with the highest priority (lowest number) 
	// be the root/parent 
	private void trickleup() {
		int newValue = size - 1;
		int parent = (newValue - 1) >> 1;
		Wrapper<E> value = heapArray[newValue];
		while (parent >= 0 && value.compareTo(heapArray[parent]) < 0) {
			heapArray[newValue] = heapArray[parent];
			newValue = parent;
			parent = (parent - 1) >> 1;
		}
		heapArray[newValue] = value;
	}
	
	//removes the first element in the array by calling trickledown
	//and decreases the size by 1 
	@Override
	public E remove() {
		if (isEmpty()) {
			return null;
		}
		else {
			E removeValue = heapArray[0].data;
			heapArray[0] = heapArray[size - 1];			
			size--;
			trickledown();
			modCounter++;
			return removeValue;
		}
	}
	
	//this method replaces the very last element with the root element and moves down 
	//so that the second element replaces the root
	private void trickledown() {
		int current = 0;
		child = getNextChild(current);
		while ((child != -1 && heapArray[size].compareTo(heapArray[child]) > 0)) {
			heapArray[current] = heapArray[child];
			current = child;
			child = getNextChild(current);			
		}
		heapArray[current] = heapArray[size];		
	}
	//makes it so that the element is placed correctly in the array
	private int getNextChild(int current) {
		int left = (current << 1) + 1;
		int right = left + 1;
		if (right < size) {
			if (heapArray[left].compareTo(heapArray[right]) < 0) {
				return left;
			}
			else {
				return right;
			}
		}
		if (left < size) {
			return left;
		}
		else {
			return -1;
		}
	}
	
	//delete removes a certain element(s) in the array using a for loop
	@Override
	public boolean delete(E object) {
		int tempSize = size;
		boolean flag = false;
		if (size == 0) {
			return false;
		}
		for (int j = 0; j < tempSize; j++) {
			if (object.compareTo(heapArray[j].data) == 0) {
				for (int i = j; i < size - 1; i++) {
				heapArray[i] = heapArray[i + 1];
				}
				size--;
				tempSize--;
				modCounter++;
				j--;
				flag = true;
			}
		}
		return flag;
	}
	
	//returns the first element in the array
	@Override
	public E peek() {
		if (isEmpty()) {
			return null;
		}
		return (E) heapArray[0].data;
	}

	//returns true if the element is in the array
	//returns false if the element is not in the array
	@Override
	public boolean contains(E obj) {
		if (size == 0) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			if (heapArray[i].data.compareTo(obj) == 0) {
				return true;
			}
		}
		return false;
	}

	//returns the size of the array
	@Override
	public int size() {
		return size;
	}
	
	//sets the size of the array to be 0
	@Override
	public void clear() {
		size = 0;
		modCounter++;
	}
	
	//returns true if it's empty 
	//returns false if it's not
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	//returns true if it's full
	//returns false if it's not 
	@Override
	public boolean isFull() {
		return size == max;
	}

	//if any element is modified than the fail-fast iterator 
	//throws a concurrent modification error 
	@Override
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}

	private class IteratorHelper implements Iterator<E> {
		private int index;
		private int counter;

		public IteratorHelper() {
			index = 0;
			counter = modCounter;
		}

		public boolean hasNext() {
			if (counter != modCounter) 
				throw new ConcurrentModificationException();
			return index < size;
		}

		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return heapArray[index++].data;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}