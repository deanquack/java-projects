/**
       *  Program 2
       *  This program utilizes the priority queue of an ordered 
       *  linked list. The highest priority is the lowest number.
       *  CS310-1 
       *  Date 2/19/2020
       *  @author  Dean Quach cssc1261
       */

package data_structures;

import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class OrderedLinkedListPriorityQueue <E extends Comparable<E>> implements PriorityQueue<E>{
	
	public int modCounter;
	private int size;
	private Node <E> head;

	public OrderedLinkedListPriorityQueue(E object) {
		head = null;
		size = 0;
	}
	public OrderedLinkedListPriorityQueue() {
			head = null;
			size = 0;
	}
	
	public class Node <E extends Comparable<E>>{
		public E data;
		public Node <E> next;
		
		public Node(E obj) {
			data = obj;
			next = null;
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	/*
	 * this method inserts objects to the linked list in 
	 * order from highest priority to lowest priority
	 * It iterates through the list to see where the object should be placed
	 */
	public boolean insert(E object) {
		Node <E> current = head;
		Node<E> newNode = new Node<E>(object);
		if (isFull()) {
			return false;
		}
		if(isEmpty()) {// if list is empty set newNode as head
			head = newNode;
			size++;
			modCounter++;
			return true;
		}
		if(object.compareTo(head.data) < 0) {
			newNode.next = head;
			head = newNode;
			size++;
			modCounter++;
			return true;
		}
		//if the value in object is greater than or equal to head set current to the value after head
		while(current.next != null && current.next.data.compareTo(object) <= 0) {
			current = current.next;

			if(current.next == null) {
				break;
			}
		}
		newNode.next = current.next;
		current.next = newNode;
		size++;
		modCounter++;
		return true;
	}
	// this removes the object at the head in the linked list 
	@Override
	public E remove() {
		if(isEmpty()) {
			return null;
		}
		E temp = head.data;
		head = head.next;
		size--;
		modCounter++;
		return temp;
	}
	
	//This method iterates through the Linked list to find the priority that
	//needs to be deleted if it's not found then it should return false
	@Override
	public boolean delete(E object) {
		Node<E> current = head;
		Node<E> previous = head;
		int tempSize = size;
		boolean flag = false;
		if (size == 0) {
			return flag;
		}
		while(current != null) {
			if (current.data.compareTo(object) == 0) {
				current = current.next;
				previous.next = current;
				size--;
				tempSize--;
				flag = true;
			}
			else {
			previous = current;
			current = current.next;
			}
		}
		return true;
	}

	// This method checks for the highest priority at the head
	public E peek() {
		if (size == 0) {
			return null;
		}
		Node<E> current = head;
		return current.data;
	}

	//Iterates through the list to see if the element is in the list
	//if the element isn't in the list, then return false 
	public boolean contains(E obj) {
		Node<E> current = head;
		Node<E> previous = head; 
		while(current != null) {
			if (current.data.compareTo(obj) == 0) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	//This method returns the size of the Linked List
	public int size() {
		return size;
	}


	//The method clears the list just by setting the head to null and size to 0.
	public void clear() {
		head = null;
		size = 0;
		modCounter++;
	}

	// This method returns true if the list is empty or if the head is null.
	//If not then it returns false 
	public boolean isEmpty() {
		return head == null;
	}
	
	//This method should return false if the list is full
	@Override
	public boolean isFull() {
		return false;
	}
	
	//This is a Fail-Fast Iterator that checks if the counter and modCounter 
	//are the same. If not then the data changed incorrectly and the program stops 
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int count = modCounter;
			Node <E> ptr = head;
			
			@Override
			public boolean hasNext() {
				return ptr != null;
			}

			@Override
			public E next() {
				
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				if(count != modCounter) {
					throw new ConcurrentModificationException();
				}
				E temp = ptr.data;
				ptr = ptr.next;
				
				return temp;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

}	
		
	
	
	
	