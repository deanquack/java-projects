package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class Hashtable<K extends Comparable<K>, V extends Comparable<V>> implements DictionaryADT<K, V> {
	private ListADT<DictionaryNode<K, V>>[] hashArray;
	private int size;
	private int max;
	private int tableSize;
	private int modCounter;

	@SuppressWarnings("unchecked")
	public Hashtable(int size) {
		modCounter = 0;
		max = size;
		size = 0;
		tableSize = (int) (max * 1.3f);
		hashArray = new LinkedListDS[tableSize];
		for (int i = 0; i < tableSize; i++) {
			hashArray[i] = new LinkedListDS<DictionaryNode<K, V>>();
		}
	}

	@SuppressWarnings("hiding")
	public class DictionaryNode<K, V> implements Comparable<DictionaryNode<K, V>> {
		K key;
		V value;

		public DictionaryNode(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@SuppressWarnings("unchecked")
		@Override
		public int compareTo(DictionaryNode<K, V> node) {
			return ((Comparable<K>) key).compareTo((K) node.key);
		}

	}


// Returns true if the key/value pair is inserted to the dictionary. 
// Returns false if the dictionary is full, or if the key is a duplicate.
	@Override
	public boolean put(K key, V value) {
		if (isFull()) {
			return false;
		}
		if((contains(key))){
			return false;
		}
		DictionaryNode<K, V> newNode = new DictionaryNode<K, V>(key, value);
		hashArray[findIndex(key)].addLast(newNode);
		size++;
		modCounter++;
		return true;
	}
	
// this method returns where the index is in the dictionary 
		private int findIndex(K key) {
			return (key.hashCode() & 0x7FFFFFFF % tableSize);
		}
//this method checks if the index is in the dictionary 
		public boolean contains(K key) {
			return hashArray[findIndex(key)].contains(new DictionaryNode<K, V>(key, null));
		}

//Returns true if the key or value pair is deleted from the dictionary. 
// Returns false if its not in the dictionary 
	@Override
	public boolean delete(K key) {
		if(isEmpty()) {
			return false;
		}
		if (!contains(key))
			return false;
		hashArray[findIndex(key)].remove(new DictionaryNode<K, V>(key, null));
		size--;
		modCounter++;
		return true;
	}

// Returns the value by using the search method given in the ListADT
	@Override
	public V get(K key) {
		DictionaryNode<K, V> index = hashArray[findIndex(key)].search(new DictionaryNode<K, V>(key, null));
		if (index == null)
			return null;
		return index.value;
	}
// this method returns the key if it's found if not then it returns null
	@Override
	public K getKey(V value) {
		for(int i = 0; i < hashArray.length; i++) {
			for(DictionaryNode<K,V> n: hashArray[i]) {
				if(n.value.compareTo(value) == 0) {
					return n.key;
				}
			}
		}
		return null;
	}

	@Override
	public int size() {
		return size = hashArray.length;

	}

	@Override
	public boolean isFull() {
		return size == max;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		for(int i = 0; i < hashArray.length; i++) {
			hashArray[i].makeEmpty();
		}
		int size = 0;
		modCounter++;
	}

	private abstract class IteratorHelper<E> implements Iterator<E> {
		public DictionaryNode<K, V>[] nodeArray;
		public int index;
		public int counter;

		@SuppressWarnings("unchecked")
		public IteratorHelper() {
			nodeArray = new DictionaryNode[size];
			index = 0;
			int j = 0;
			counter = modCounter;
			for (int i = 0; i < tableSize; i++)
				for (DictionaryNode n : hashArray[i]) {
					nodeArray[j++] = n;
				}
			quicksort(nodeArray, 0, size - 1);
		}

		public boolean hasNext() {
			if (counter != modCounter)
				throw new ConcurrentModificationException();
			return index < size;
		}

		public abstract E next();

		public void remove() {
		}
// Thus quicksort method was referenced by geeksforgeeks.com and the riggins reader
		public void quicksort(DictionaryNode<K, V>[] node, int lowerBound, int upperBound) {
			if (lowerBound >= upperBound)
				return;
			int i = lowerBound;
			int j = 0;
			int k = lowerBound;
			DictionaryNode<K, V> pivot = node[upperBound];
			DictionaryNode<K, V> comp = node[k];
			DictionaryNode<K, V> tmp;
			while (k < upperBound) {
				comp = node[k];
				if (((Comparable<K>) comp.key).compareTo(pivot.key) < 0) {
					j++;
					tmp = node[i];
					node[i] = node[k];
					node[k] = tmp;
					i++;
				}
				k++;
			}
			tmp = node[lowerBound + j];
			node[lowerBound + j] = pivot;
			node[upperBound] = tmp;
			quicksort(node, lowerBound, lowerBound + j - 1);
			quicksort(node, lowerBound + j + 1, upperBound);
		}
	}


	private class KeyIteratorHelper<K extends Comparable<K>> extends IteratorHelper<K> {
		public KeyIteratorHelper() {
			super();
		}

		public K next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			@SuppressWarnings("unchecked")
			K key = (K) nodeArray[index++].key;
			return key;
		}
	}

	private class ValueIteratorHelper<V extends Comparable<V>> extends IteratorHelper<V> {
		public ValueIteratorHelper() {
			super();
		}

		public V next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			@SuppressWarnings("unchecked")
			V value = (V) nodeArray[index++].value;
			return value;
		}

	}

	@Override
	public Iterator<K> keys() {
		return new KeyIteratorHelper<K>();
	}

	@Override
	public Iterator<V> values() {
		return new ValueIteratorHelper<V>();
	}

}

