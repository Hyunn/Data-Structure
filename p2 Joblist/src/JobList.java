/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          program 2
// FILE:             JobList.java
//
// Authors: 
// Author1: Dan Bondi, dbondi@wisc.edu, dbondi, Lecture 002
// Author2: Yong Jae Cho, ycho55@wisc.edu, ycho55, Lecture 002
// Author3: Hyunho Choi, hchoi225@wisc.edu, hchoi225, Lecure 002
// Author4: Eric Jore, jore@wisc.edu, jore, Lecure 002
// Author5: Dana Paz, dmpaz@wisc.edu, dmpaz, Lecture 002
// Author6: Lingou Zhu, lzhu@cs.wisc.edu, lzhu, Lecture 002
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.Iterator;

public class JobList<Job> implements ListADT<Job> {

	// implemented as a singly-linked chain of Listnode<Job> nodes  
	// with a header node and without a tail reference
			
	//Instance Variables 
	private Listnode<Job> head;
	private int numItems;
			
	//Constructor 
	public JobList() {	
		head = new Listnode<Job>(null);
		numItems = 0;
	}
	
	
	public Iterator<Job> iterator() {
		return new JobListIterator<Job>(head);
	}

	public void add(Job item) {
		if (item == null) throw new IllegalArgumentException();		
		Listnode<Job> newnode = new Listnode<Job>(item);
		
		// non-empty or empty list both have a header node
		Listnode<Job> curr = head; // header node
		while (curr.getNext() != null)
			curr = curr.getNext();
		curr.setNext(newnode);
		numItems++;
	}

	public void add(int pos, Job item) {
		// check for invalid position and for full array
		if (pos < 0 || pos > numItems) throw new IndexOutOfBoundsException();	

		// if asked to add to end, let the other add method do the work
		if (pos == numItems) {
			add(item);
			return;
		}

		// find the node n after which to add a new node and add the new node
		Listnode<Job> n = head;
		for (int i = 0; i < pos; i++) {
			n = n.getNext();	
		}
		n.setNext(new Listnode<Job>(item, n.getNext()));
		numItems++;				
	}

	public boolean contains(Job item) {
		// TODO Auto-generated method stub
		if (item == null)
			throw new IllegalArgumentException();
		
		Listnode<Job> curr = head;
		
		for (int i = 0; i < numItems; i++) {
			if (curr.getNext().getData().equals(item))
				return true;
		curr = curr.getNext();
		}
		return false;
	}

	public Job get(int pos) {
		// check for invalid position
		if (pos < 0 || pos >= numItems) throw new IndexOutOfBoundsException();	
		Listnode<Job> curr = head;
		for (int p = 0; p <= pos; p++)
			curr = curr.getNext();
		return curr.getData();
	}

	public boolean isEmpty() {
		if (numItems == 0) 	return true;					
	return false;
	}

	public Job remove(int pos) {
		// check invalid position
		if (pos < 0 || pos >= numItems)	 throw new IndexOutOfBoundsException();		

		Listnode<Job> curr = head;
		
		for (int p = 0; p < pos; p++ ){
			curr = curr.getNext();			
		}
		
		Job temp = curr.getNext().getData();
		curr.setNext(curr.getNext().getNext());
		
		//decrement number of items by one
		numItems--;
		
		return temp;
	}

	public int size() {
		return numItems;
	}

}
