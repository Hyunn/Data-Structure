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
 * 
 * This class stores student's name and his or her score and maximum score.
 * THis class contains methods to get name, scores, first character of name,
 * maximum score, and percentage of score.
 *
 *
 * @author Hyunho Choi
 */
public class Score {
	
	//declare some variables that will be stored in Score.
	private String name;
	private double points;
	private double maxPoints;
	
	/**
	 * This constructor is for storing name, points, and maximum points
	 * when it is declared. 
	 *
	 * @param name of student. It cannot be null. 
	 * @param points that student earn. It must be greater than or equal to 0.
	 * @param maxPoints Maximum points that students can earn. It must be 
	 * 					greater than or equal to 0.
	 */
	public Score(String name, double points, double maxPoints) 
			throws IllegalArgumentException {		
		//throws IllegalArgumentException when name is null, points are less
		//	than 0, or maximum points are less than 0.
		 if(name == null || points < 0.0 || maxPoints < 0.0 || 
				 points / maxPoints > 1) throw new IllegalArgumentException();
		
		//store given values to Score
		this.name = name;
		this.points = points;
		this.maxPoints = maxPoints;
	}
	
	/**
	 * 
	 * A method that can get name outside of this class
	 * 
	 * @return name full name of student
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * A method that can get points outside of this class
	 * 
	 * @return points students earned
	 */
	public double getPoints() {
		return points;
	}
	
	/**
	 * 
	 * A method that users can get maximum points outside of this class.
	 * 
	 * @return maxPoints maximum points that student could earn.
	 */
	public double getMaxPossible() {
		return maxPoints;
	}
	
	/**
	 * 
	 * A method that user can get first character of student's name for
	 * category purpose.
	 * 
	 * @return first character of student's name as a String
	 */
	public String getCategory() {
		return name.substring(0, 1);
	}
	
	/**
	 * 
	 * A method that user can get percentage of students' score 
	 * outside of this class.
	 * 
	 * @return percentage of scores by points divided by maximum points
	 */
	public double getPercent() {
		return points / maxPoints * 100;
	}
}
