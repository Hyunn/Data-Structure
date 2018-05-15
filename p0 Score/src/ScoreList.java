///////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017 
// PROJECT:          p0
// FILE:             Score.java, ScoreList.java, 
//							ScoreListADT.java, Test_ScoreList.java
//
// Author: Hyunho Choi
//
// ---------------- OTHER ASSISTANCE CREDITS 

//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class makes Score lists that each Score contains student's name, 
 * scores, and maximum scores. Also, there is a number that shows how many
 * ScoreLists contains Scores.
 * This class implements ScoreListADT interface, which contains methods to
 * add, remove, get Score to ScoreLists, and get the size of ScoreLists.
 *
 * @author Hyunho Choi
 */

public class ScoreList implements ScoreListADT {
	
	//declare Score array to store Score
	private Score[] scores;
	//the number that indicates how many ScoreLists contains Score
	private int numScores;
	
	/**
	 * The constructor that initializes number of scores as 0 and 
	 * Score array that can contain 100 Scores.
	 */
	public ScoreList() {
		//initialize a Score count, indicating number of Scores in ScoreList
		numScores = 0;
		//initialize array of Score to store Scores with 100 spaces
		scores = new Score[100];
	}

	/** 
	 * Returns the number of Scores in the ScoreList or zero
	 * @return the number of scores in this ScoreList
	 */
	@Override
	public int size() {
		return numScores;
	}

	/** 
	 * Adds the score to the end of this list. 
	 * It will throws IllegalArgumentException if Score is null.
	 * If the array is full, then the space of array will be expanded twice.
	 * 
	 * @param s a non-null Score to place as the last item in the list.
	 * @throws IllegalArgumentException
	 */
	@Override
	public void add(Score s) throws IllegalArgumentException {
		if(s == null)
			throw new IllegalArgumentException();
		
		//if the array is full, expand array twice
		if(numScores == scores.length)
			expandArray();
		
		//store the Score to the end of the ScoreList
		scores[numScores] = s;
		//increment the number of scores
		numScores++;
		
	}

	/**
	 * Removes and returns the item at index position i.
	 * After removing the item in index, the rest of items will 
	 * be moved forward one index.
	 * If i is less than zero or greater than size()-1,
	 * will throw an IndexOutOfBoundsException.
	 * 
	 * @param i must be greater than or equal to zero and less than size()
	 * @return the item at index i
	 * @throws IndexOutOfBoundsException
	 */
	@Override
	public Score remove(int i) throws IndexOutOfBoundsException {
		//when i is not appropriate, it throws exception
		if( i < 0 || i > numScores -1 )
			throw new IndexOutOfBoundsException();
		
		//temporary stores scores
		Score temp = scores[i];
		//all items after index i will be moved forward by one index
		for(int j = i; j < numScores - 1; j++)
			scores[j] = scores[j+1];
		
		//delete the last Score in ScoreList as it was moved forward
		scores[numScores - 1] = null;
		//decrements the number of Scores
		numScores--;
		
		return temp;
	}

	/**
	 * Returns (without removing) the item at index position i.
	 * If i is less than zero or greater than size()-1,
	 * will throw an IndexOutOfBoundsException.
	 * @param i must be greater than or equal to zero and less than size()
	 * @return the item at index i
	 * @throws IndexOutOfBoundsException
	 */
	@Override
	public Score get(int i) throws IndexOutOfBoundsException {
		//when i is not appropriate, it throws exception
		if( i < 0 || i > numScores -1 )
			throw new IndexOutOfBoundsException();
		//store Scores at index i temporarily for return purpose
		Score temp = scores[i];
		return temp;
	}
	
	/**
	 * This method will expand Array space by twice.
	 */
	public void expandArray() {
		//make temporal ScoreList with double size of current ScoreList
		Score[] temp = new Score[scores.length * 2];
		
		//move elements from current ScoreList to temporal ScoreList
		for(int i=0; i<numScores; i++)
			temp[i] = scores[i];
		//assign current ScoreList as temporal ScoreList 
		scores = temp;
		
	}

}
