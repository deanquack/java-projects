package data_structures;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.TreeMap;


public class BalancedTreeDictionary <K extends Comparable<K>, V extends Comparable<V>> implements DictionaryADT<K,V>{
	
	private TreeMap <K,V> tree;
	
	public BalancedTreeDictionary() {
		tree = new TreeMap <K,V>();
	}

	@Override
	public boolean put(K key, V value) {
		if(tree.containsKey(key)) {
			return false;
		}
		tree.put(key, value);
		return true;
	}
	
	@Override
	public boolean delete(K key) {
		 return tree.remove(key) != null;
	}

	@Override
	public V get(K key) {
		return tree.get(key);
	}

	@Override
	public K getKey(V value) {
		K currentKey = tree.firstKey();
		while(tree.lowerEntry(currentKey) != null) {
			if(tree.get(currentKey).compareTo(value) == 0) {
				return currentKey;
			}
			currentKey = tree.lowerEntry(currentKey).getKey();
		}
		return currentKey;
	}
	

	@Override
	public int size() {
		return tree.size();
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return tree.isEmpty();
	}

	@Override
	public void clear() {
		tree.clear();
	}
	
	@Override
	public Iterator<K> keys() {
		return tree.keySet().iterator();
	}

	@Override
	public Iterator<V> values() {
		return tree.values().iterator();
	}

}
