/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          program 1
// FILE:             ScoreList.java
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

/**
 * Implements the ScoreListADT interface for storing a list of Scores.  This 
 * class is not generic as it only needs to store Score items.
 *
 *
 * @author Dan Bondi
 * @author Yong Jae Cho
 * @author Hyunho Choi
 * @author Eric Jore
 * @author Dana Paz
 * @author Lingou Zhu
 */
public class ScoreList implements ScoreListADT{

	//instance variables 
	private int numItems;
	private Score[] items;
	
	/**
	 * A constructor that sets the number of items to zero and creates an 
	 * array of Score items with an index of 100 
	 */
	public ScoreList(){
		numItems = 0;
		items = new Score[100];
	}
	
	/**
	 * Returns the number of Scores in the list (not the size of the list)
	 *
	 * @return numItems The number of Scores in the list
	 */
	public int size() {
		return numItems;
	}

	/**
	 * Adds a Score object to the end of the list of Scores. If the list 
	 * is full it will expand the list with the expandArray() method. 
	 * Increments numItems. It will throw an IllegalArguemntException 
	 * if the object trying to be added is not a Score.
	 *
	 * @param s Object of type Score
	 */
	public void add(Score s) throws IllegalArgumentException {
		if(!(s instanceof Score)) throw new IllegalArgumentException();

	    // if array is full, get new array of double size,
	    // and copy items from old array to new array
	    if (items.length == numItems) {
	        expandArray();
	    }
		
	    // add new item; update numItems
	    items[numItems] = s;
	    numItems++;
	}

	/**
	 * Doubles the size of the array and stores the old array in the 
	 * new array. Sets the array items to be equal to the new array
	 */
	private void expandArray() {
	    Score[] newArray = new Score[numItems*2];
	    for (int k = 0; k < numItems; k++) {
	        newArray[k] = items[k];
	    }
	    items = newArray;
	}
	
	/**
	 * Removes an item at a specified location and shifts the 
	 * list down. Throws an IndexOutOfBoundsException
	 *
	 * @param i The position of the item to be removed
	 * @return removedItem The item that was removed from the list
	 */
	public Score remove(int i) throws IndexOutOfBoundsException {
		if( i < 0) throw new IndexOutOfBoundsException();
		
		Score removedItem = items[i];
		
		for( int j=i; j < numItems; j++){
			items[j]=items[j+1];
		}
		
		items[numItems]=null;
		numItems--;
		return removedItem;
	}

	/**
	 * Gets the score at a desired index and returns it. Throws an
	 * IndexOutOfBoundsException. 
	 *
	 * @param i The index of the score desired
	 * @return items The Score at position i
	 */
	public Score get(int i) throws IndexOutOfBoundsException {
		return items[i];
	}
}
