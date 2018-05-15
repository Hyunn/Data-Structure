/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          program 2
// FILE:             Scoreboard.java
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

public class Scoreboard implements ScoreboardADT{
	 
	//Instance Variables 
	JobList<Job> completedjobs = new JobList<Job>();

	
	//constructor
	public Scoreboard(){}
	
	/**
     * Calculates the total combined number of points for every job in the scoreboard.
     * 
     * @return The summation of all the points for every job currently stored in the scoreboard.
     */
	public int getTotalScore(){
		int totalpoints = 0;
		Iterator<Job> iterator = completedjobs.iterator();
		
		while(iterator.hasNext()){
			totalpoints += iterator.next().getPoints();
		}
		
		return totalpoints;
	}
	
	 /**
     * Inserts the given job at the end of the scoreboard.
     * 
     * @param job 
     * 		The job that has been completed and is to be inserted into the list.
     */
    public void updateScoreBoard(Job job){
    	if(job.isCompleted()){
        	completedjobs.add(job);   		
    	}
    }

    /**
     * Prints out a summary of all jobs currently stored in the scoreboard. The formatting must match the example exactly.
     */
    public void displayScoreBoard(){
    	Iterator i = completedjobs.iterator();
    	while (i.hasNext()){
    		Job temp = (Job)i.next();
    		System.out.println("Job Name: " + temp.getJobName());
    		System.out.println("Points earned for this job: " + temp.getPoints());
    		System.out.println("--------------------------------------------");
    	}
    }

}
