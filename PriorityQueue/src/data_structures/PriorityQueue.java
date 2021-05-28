package data_structures;
import java.util.Iterator; 

	 public interface PriorityQueue<E extends Comparable <E>> extends Iterable<E> {
		 
		public static final int DEFAULT_MAX_CAPACITY = 1000; 
	    //  Inserts a new object into the priority queue.  Returns true if     
	    //  the insertion is successful.  If the PQ is full, the insertion    
	    //  is aborted, and the method returns false.    	    
	    public boolean insert(E object);           
	    //  Removes the object of highest priority that has been in the    
	    //  PQ the longest, and returns it.  Returns null if the PQ is empty.    
	    public E remove();          
	    //  Deletes all instances of the parameter obj from the PQ if found, and     
	    //  returns true.  Returns false if no match to the parameter obj is found.     
	    public boolean delete(E object);        
	    //  Returns the object of highest priority that has been in the    
	    //  PQ the longest, but does NOT remove it.     
	    //  Returns null if the PQ is empty.    
	    public E peek();            
	    //  Returns true if the priority queue contains the specified element    
	    //  false otherwise.     
	    public boolean contains(E obj);          
	    //  Returns the number of objects currently in the PQ.     
	    public int size();  
	    //  Returns the PQ to an empty state.    
	    public void clear();        
	    //  Returns true if the PQ is empty, otherwise false     
	    public boolean isEmpty();        
	    //  Returns true if the PQ is full, otherwise false.  List based     
	    //  implementations should always return false.     
	    public boolean isFull();           
	    //  Returns an iterator of the objects in the PQ, in no particular    
	    //  order.       
	    public Iterator<E> iterator();                 
} 
