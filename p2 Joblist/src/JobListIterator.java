/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          program 2
// FILE:             JobListIterator.java
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
import java.util.NoSuchElementException;

public class JobListIterator<Job> implements Iterator<Job> {

	//Instance Variables
	private Listnode<Job> curr;
	
	//constructor 
	public JobListIterator(Listnode<Job> head){
		curr = head;
	}
	
	public boolean hasNext() {
		if(curr.getNext() != null){
			return true;
		}
		return false;
	}

	public Job next() {
		if (!hasNext()) throw new NoSuchElementException();	
		curr = curr.getNext();
		return curr.getData();
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
