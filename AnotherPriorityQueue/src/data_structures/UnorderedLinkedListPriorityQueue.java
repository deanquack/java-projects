/**
       *  Program 2
       *  This program utilizes the priority queue of an unordered 
       *  linked list. The highest priority is the lowest number.
       *  CS310-1 
       *  Date 2/19/2020
       *  @author  Dean Quach cssc1261
       */

package data_structures;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class UnorderedLinkedListPriorityQueue <E extends Comparable<E>> implements PriorityQueue<E>{
	
	public int modCounter;
	private int size;
	private Node <E> head;

	public UnorderedLinkedListPriorityQueue() {
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
	
	//This method inputs the first object at the head and if the next
	//object is inserted it goes after the head
	@Override
	public boolean insert(E object) {
		Node<E> newNode = new Node<E>(object);
		newNode.next = head;
		head = newNode;
		size++;
		modCounter++;
		return true;
	}
	/*This method iterates through the linked list to
	 * find the highest priority. The variable that 
	 * will remove the node will be pointer.
	 */
	//referenced from course reader, page 124
	@Override
	public E remove() {
		Node <E> previous = null;
		Node <E> current = head;
		Node<E> pointer = null;
		
		if(current == null) {//if list is empty
			return null;
		}
		E highestPriority = head.data;
		
		if(size == 1){ //head case, if there is only one item in list
			head = null; //sets the node to delete as the head
			size--;
			modCounter++;
			return highestPriority;
		}
		
		while(current != null) {
			//compares current to highestPriority 
			if(current.data.compareTo(highestPriority) <= 0) { 
				//sets the pointer as the previous node
				pointer = previous; 
				//sets the data as highestPriority
				highestPriority = current.data; 
			}
			previous = current; //sets the previous node as the current node
			current = current.next; //updates current
			
		}
		if(pointer == null) {
			head = head.next;
		}
		else {
			pointer.next = pointer.next.next;
		}
		size--;
		modCounter++; //modCounter increases
		return highestPriority;
	}

	//This method iterates through the Linked list to find the priority that
	//needs to be deleted if it's not found then it should return false
	public boolean delete(E object) { 
		Node <E> current = head;
		Node <E> previous = null;
		int tempSize = size;
		boolean flag = false;
		if (size == 0) {
			return false;
		}
		while(current != null) {
			if (contains(object)) {
				if(previous == null) {
					head = head.next;
				}
				else {
					previous.next = current.next;

				}
				current = current.next;
				size--;
				tempSize--;
				flag = true;
				continue;
			}
		previous = current;
		current = current.next;
		}
		modCounter++;
		return flag;
	}
	// Iterates through the list to find the highest 
	//priority 
	public E peek() {
		if (size == 0) {
			return null;
		}
		Node <E> high = head;
		Node <E> temp = head;
		while(temp.next != null) {
			if (temp.data.compareTo(high.data) == 0) {
				high = temp;
			}
			temp = temp.next;
		}
		return high.data;
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
	// 
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
	
	
	
	
	