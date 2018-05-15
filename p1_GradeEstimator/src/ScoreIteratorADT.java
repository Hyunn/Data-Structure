/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          program 1
// FILE:             ScoreIteratorADT.java
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
 * Interface for ScoreIteratorADT. This ADT includes the next() and hasNext() methods. 
 *
 * <p>Bugs: no known bugs. 
 *
 * @author Dan Bondi
 * @author Yong Jae Cho
 * @author Hyunho Choi
 * @author Eric Jore
 * @author Dana Paz
 * @author Lingou Zhu
 */
public interface ScoreIteratorADT {
	
	/**
	 * Return true if and only if there are more Score items to iterate through 
	 * 
	 * @return true if there is a next Score; otherwise it returns false. 
	 */
	boolean hasNext();
	
	/**
	 * Returns the current item and advances to the next item. 
	 * 
	 * @return the current item. 
	 */
	Score next();
	
}
