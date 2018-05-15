/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017 
// PROJECT:          p3: Memory Efficient Merging of Sorted Files
// FILE:             FileLinePriorityQueue.java
//
// TEAM:    Team 49
// Author1: Dan Bondi, dbondi@wisc.edu, dbondi, Lecture 002
// Author2: Hyunho Choi, hchoi225@wisc.edu, hchoi225, Lecure 002
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.Comparator;

/**
 * An implementation of the MinPriorityQueueADT interface. This implementation stores FileLine objects.
 * See MinPriorityQueueADT.java for a description of each method. 
 *
 */
public class FileLinePriorityQueue implements MinPriorityQueueADT<FileLine> {
	//MinPriority Queue
	private FileLine[] heap; 
	private Comparator<FileLine> cmp;
	private int maxSize;
	//number of items added to heap
	private int numItems; 

	public FileLinePriorityQueue(int initialSize, Comparator<FileLine> cmp) {
		this.cmp = cmp;
		maxSize = initialSize;
		//creates fileLine array of length maxSize
		heap = new FileLine[maxSize+1]; 
		numItems = 0;
	}

	public FileLine removeMin() throws PriorityQueueEmptyException {

		if(numItems==0){
			//if heap is empty throw PriorityQueueEmptyException
			throw new PriorityQueueEmptyException(); 
		}

		//save value that will be removed
		FileLine returnValue = heap[1]; 
		int parent = 1;	
		//replace smallest value with last value in heap
		heap[1] = heap[numItems];  
		//set last value in heap to null
		heap[numItems]=null;		
		FileLine temp;
		
		boolean finish = false;	
		//edit queue so it is minPriority
		while(!finish) {
			//determine location of child nodes
			int child = parent*2; 
			//no children exist so don't change queue
			if (child >= numItems) { 
				finish = true;
			}
			
		   // set parent is smaller than both children so queue doesn't need to
		   // be change
			else if (cmp.compare(heap[parent], heap[child]) <= 0) {
				boolean done = true;
				if (child + 1 != numItems) {
					if (cmp.compare(heap[parent], heap[child + 1]) > 0)
						done = false;
				}
				if (done == true)
					finish = true;
			}

			else {
				// set index to smallest child
				int index = child;
				if (child + 1 != numItems) {
					if (cmp.compare(heap[child + 1], heap[child]) < 0)
						index = child + 1;
				}

				// swap the parent and child
				temp = heap[index];
				heap[index] = heap[parent];
				heap[parent] = temp;
				parent = index;

			}
		}
		numItems--;
		return returnValue;
	}

	public void insert(FileLine fl) throws PriorityQueueFullException {
		// if FileLine is null throw IllegalArgumentException
		if (fl == null) {
			throw new IllegalArgumentException();
		}
		// if FileLine is full throw IllegalArgumentException
		if (numItems == maxSize) {
			throw new PriorityQueueFullException();
		}

		// insert fileLine into last position of heap
		heap[numItems + 1] = fl;
		int child = numItems + 1;
		boolean finish = false;

		// edit queue so it is minPriority
		while (!finish) {
			int parent = child / 2;
			if (parent == 0)
				finish = true;
			// parent is larger than child so heap doesnt need to be changed
			else if (cmp.compare(heap[parent], heap[child]) <= 0)
				finish = true;
			else {
				// swap the parent with child
				FileLine tmp = heap[child];
				heap[child] = heap[parent];
				heap[parent] = tmp;
				child = parent;
			}
		}
		numItems++;

	}

	public boolean isEmpty() {
		// if the number of elements is equal to 0 the array is empty
		return numItems <= 0;
	}
}
