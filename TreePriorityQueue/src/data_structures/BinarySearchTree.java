package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class BinarySearchTree <K extends Comparable<K>, V extends Comparable<V>> implements DictionaryADT <K,V>{
	
	private node<K, V> head;
	private int size;
	private int modCounter;
	private int currentIndex;
	
	private class node <K extends Comparable<K>, V extends Comparable<V>> implements Comparable <node <K, V>> {
		K key;
		V value;
		node <K, V> rightChild;
		node <K, V> leftChild;
		
		public node (K k, V v) {
			key = k;
			value = v;
			rightChild = null;
			leftChild = null;
		}
		
		@Override
		public int compareTo(node<K, V> o) {
			return this.key.compareTo(o.key);
		}
	}

	@Override
	public boolean put(K key, V value) {
		if(isFull() || contains(key)) {
			return false;
		}
		node <K, V> newNode = new node(key, value);
		node <K, V> current = head;
		node <K, V> previous = null;
		while(current != null) {
			if(current.compareTo(newNode) > 0) {
				previous = current;
				current = current.leftChild;
			}
			else {
				previous = current;
				current = current.rightChild;
			}
		}
		if(previous == null) {
			head = newNode;
		}
		else {
			if(previous.compareTo(newNode) > 0){ 
				previous.leftChild = newNode;
			}
			else {
				previous.rightChild = newNode;
			}
		}
		size++;
		modCounter++;
		return true;
	}
	
	private boolean contains(K key) {
		node <K, V> current = head;
		node <K ,V> temp = new node(key,null);
		while(current != null) {
			if(current.compareTo(temp) < 0) { //highest priority is on left
				current = current.rightChild;
			}
			else if (current.compareTo(temp) > 0) {//child is on the right
				current = current.leftChild;
			}
			else {
				return true;
			}
		}
		return false;
	}


	@SuppressWarnings("unused")
	@Override
	public boolean delete(K key) {
		node <K,V> current = head;
		node<K, V> previous = null;
		node<K,V> temp = new node(key, null);
		int tempSize = size;
		boolean flag = false;
		
		if (head == null) {
			return false;
		}
		while(current != null) {
			if (current.key.compareTo(key) == 0) {
				break;	
			}
			previous = current;
			if(current.compareTo(temp) < 0) { //highest priority is on left
				current = current.rightChild;
			}
			else if (current.compareTo(temp) > 0) {//child is on the right
				current = current.leftChild;
			}
		}
		if(current == null) {// if the node is not found 
			return flag;
		}
		else if(current != null) { 
			if(previous == null) { //case where the root node is deleted and needs to be reassigned 
				if(current.leftChild == null && current.rightChild == null) {//no children
					head = null;
					flag = true;
				}
				else if(current.leftChild == null || current.rightChild == null) {//one children
					if(current.leftChild == null) {
						head = current.rightChild;
						flag = true;
					}
					else {
						head = current.leftChild;
						flag = true;
					}		
				}
				else {//two children
					temp = findNextSuccessor(current);
					head = temp;
					head.leftChild = current.leftChild;
					head.rightChild = current.rightChild;
					flag = true;
					}
				}
			else if(previous != null) { // if the node is found 
				if(current != null) { //case where the parent or child node is deleted and needs to be reassigned 
					if(current.leftChild == null && current.rightChild == null) {//no children
						if(previous.leftChild == null && previous.leftChild.compareTo(current) == 0) {//check if previous is current
							previous.leftChild = null;
						}
						else {
							previous.leftChild = null;
						}
						flag = true;
					}
					else if(current.leftChild == null || current.rightChild == null) {//one children
						if(current.leftChild == null) {
							if(previous.rightChild != null && previous.rightChild.compareTo(current) == 0) {
								previous.rightChild = current.rightChild;
								flag = true;
							}
							else {
								previous.leftChild = current.rightChild;
								flag = true;
							}
						}
						else {
							if(previous.rightChild != null && previous.rightChild.compareTo(current) == 0) {
								previous.rightChild = current.leftChild;
								flag = true;
							}
							else {
								previous.leftChild = current.leftChild;
								flag = true;
							}
						}
					}
					else { //two children
						temp = findNextSuccessor(current);
						temp.leftChild = current.leftChild;
						temp.rightChild = current.rightChild;
						flag = true;
						if(previous.rightChild != null && previous.rightChild.compareTo(current) == 0) {
							previous.rightChild = temp;
						}
						else {
							previous.leftChild = temp;
						}
					}
				}
			}
		}
		tempSize--;
		size--;
		modCounter++;
		return flag;
	}

	private node findNextSuccessor(node current) {//find the successor 
		node parent = current;
		node temp = current.rightChild;
		
		while(temp.leftChild != null) {
			parent = temp;
			temp = temp.leftChild;
		}
		if(parent.compareTo(current) == 0) {
			parent.rightChild = temp.rightChild;
		}
		else {
			parent.leftChild = temp.rightChild;
		}
		return temp;
		
		
	}

	@Override
	public V get(K key) {
		node <K, V> current = head;
		node <K, V> previous = null;
		if(isEmpty()) {
			return null;
		}
		return getHelper(key, current);
	}

	private V getHelper(K key, node<K,V> current) {
		while(current != null) {
			if(((Comparable <K>)key).compareTo(current.key) < 0) { //search left/right for the key and return it
				return getHelper(key, current.leftChild);
			}
			else {
				return getHelper(key, current.rightChild);
			}
		}
		return null;
	}
	
	@Override
	public K getKey(V value) {
		node <K, V> current = head;
		node <K, V> previous = null;
		if(isEmpty()) {
			return null;
		}
		return getKeyHelper(current, value);
	}
	
	private K getKeyHelper(node<K,V> current, V value) {
		if(current == null) {
			return null;
		}
		K key = getKeyHelper(current.leftChild, value);
		if (key != null) {
			return key;
		}
		if(current.value.compareTo(value) == 0) {
			return current.key;
		}
		return getKeyHelper(current.rightChild, value);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public void clear() {
		head = null;
		
	}
	private node[] makeArray() {
		node nodeArray[] = new node[size];
		helper(nodeArray, head);
		return nodeArray;
	}
	
	private void helper(node array[], node current) {
		//put current
		if(current == null) {
			return;
		}
		helper(array, current.leftChild);
		array[currentIndex++] = current;
		helper(array, current.rightChild);
	}

	@Override
	public Iterator<K> keys() {
		return new Iterator<K>() {
		private int count = modCounter;
		private node <K,V> ptr = head;
		private int index = 0;
		private node nodeArray[] = makeArray();
		
		
			@Override
			public boolean hasNext() {
				return index < nodeArray.length;
			}

			@Override
			public K next() {
				
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				else if(count != modCounter) {
					throw new ConcurrentModificationException();
				}
				else {
			
					return (K) nodeArray[index++].key;
				}
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public Iterator<V> values() {
		return new Iterator<V>() {
		private int count = modCounter;
		private node <K,V> ptr = head;
		private int index = 0;
		private node nodeArray[] = makeArray();
		
		
			@Override
			public boolean hasNext() {
				return index < nodeArray.length;
			}

			@Override
			public V next() {
				
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				else if(count != modCounter) {
					throw new ConcurrentModificationException();
				}
				else {
			
					return (V) nodeArray[index++].value;
				}
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
