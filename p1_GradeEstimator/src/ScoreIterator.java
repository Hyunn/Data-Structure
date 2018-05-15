/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          program 1
// FILE:             ScoreIterator.java
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


import java.util.*;

/**
 * An indirect access iterator that iterates through a given ScoreList 
 * considering only the items in the ScoreList that match a given category. 
 * 
 * <p>Bugs: no known bugs
 *
 * @author Dan Bondi
 * @author Yong Jae Cho
 * @author Hyunho Choi
 * @author Eric Jore
 * @author Dana Paz
 * @author Lingou Zhu
 */
public class ScoreIterator implements ScoreIteratorADT {
	
	//Instance variables 
	private ScoreList scoreList;
	private int currPos;
	
	private String category;
	
	/**
	 * Constructor for the ScoreIterator class. Sets the scoreList 
	 * equal to the list of scores and the current position to zero.
	 * 
	 * @param list A list of Scores
	 * @param category A String indicating the given category 
	 */
	public ScoreIterator(ScoreList list, String category) {
		this.scoreList = list;
		this.currPos = 0;
		this.category = category.toLowerCase();
	}
	
	/**
	 * Checks to see if there is another Score in the list. This 
	 * method checks to see if the current position is less than 
	 * the size of the score list 
	 * 
	 * @return true if there is a next Score. 
	 */
	public boolean hasNext() {
		for (int temp = currPos; temp < scoreList.size(); temp++){
			if (scoreList.get(temp).getCategory().toLowerCase().equals(
					category.substring(0, 1))){
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the current Score and advances to the next Score. 
	 * 
	 * @return The current item.
	 * @throws NoSuchElementException
	 */
	public Score next() {
		if (!hasNext()) throw new NoSuchElementException();
		
		Score result = null;
		boolean foundNext = false;
		
		while (currPos < scoreList.size() && !foundNext){
			if (scoreList.get(currPos).getCategory().toLowerCase().equals(
					category.substring(0, 1))){
				result = scoreList.get(currPos);
				foundNext = true;
			}
			currPos++;
		}
		return result;
	}
	
}
