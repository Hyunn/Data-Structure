/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          program 1
// FILE:             Score.java
//
// Authors: 
// Author1: Dan Bondi, dbondi@wisc.edu, netID, Lecture 002
// Author2: Yong Jae Cho, ycho55@wisc.edu, netID, Lecture 002
// Author3: Hyunho Choi, hchoi225@wisc.edu, netID, Lecure 002
// Author4: Eric Jore, jore@wisc.edu, netID, Lecure 002
// Author5: Dana Paz, dmpaz@wisc.edu, 9067727140, Lecture 002
// Author6: Lingou Zhu, lzhu@cs.wisc.edu
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: Identify persons by name, relationship to you, and email. 
// Describe in detail the the ideas and help they provided. 
// 
// Online sources: avoid web searches to solve your problems, but if you do 
// search, be sure to include Web URLs and description of 
// of any information you find. 
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Stores individual scores for an assignment. This includes the 
 * assignment name, points earned and possible points.
 *
 * @author Dan Bondi
 * @author Yong Jae Cho
 * @author Hyunho Choi
 * @author Eric Jore
 * @author Dana Paz
 * @author Lingou Zhu
 */
public class Score {
	
	//instance variables
	private String assignName;	// assignment name
	private double ptsEarned;	// points earned
	private double possPts;		// possible points for that assignment
	
	/**
	 * A constructor that accepts an assignment name, the points earned, 
	 * and the points possible for the given assignment.
	 *
	 * @param name The assignment name
	 * @param earned The total points earned for the assignment 
	 * @param possible The maximum amount of points possible for the assignment 
	 */
	public Score( String name, double earned, double possible ){
		if( !(name instanceof String) || earned < 0  || possible <= 0
				|| possible < earned) 
			throw new IllegalArgumentException();
		
		assignName = name;
		ptsEarned = earned;
		possPts = possible;
	}
	
	/**
	 * Returns the assignment name.
	 *
	 * @return assignName The assignment name 
	 */
	public String getName(){
		return assignName;
	}
	
	/**
	 * Returns the total points that the student earned on the assignment
	 *
	 * @return ptsEarned The total points that the student earned on the assignment
	 */
	public double getPoints(){
		return ptsEarned;
	}
	
	/**
	 * Returns the maximum possible points for the assignment.
	 *
	 * @return possPts The maximum possible points for the assignment 
	 */
	public double getMaxPossible(){
		return possPts;
	}
	
	/**
	 * Returns the first character of the assignment name which 
	 * is the category of the assignment.
	 *
	 * @return category The first character of the assignment name
	 */
	public String getCategory(){
		String category = Character.toString(assignName.charAt(0));
		return category; 
	}
	
	/**
	 * Returns the percentage of (points earned)/(possible points) times 100
	 *
	 * @param 
	 * @return the percentage of (points earned)/(possible points) times 100
	 */
	public double getPercent(){
		double percent = ptsEarned / possPts * 100;
		return percent;	
	}
}