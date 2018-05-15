/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          program 2
// FILE:             Game.java
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

public class Game{

    /**
     * A list of all jobs currently in the queue.
     */
    private ListADT<Job> list;
    /**
     * Whenever a Job is completed it is added to the scoreboard
     */
    private ScoreboardADT scoreBoard;
    private int timeToPlay;
    private JobSimulator jobSimulator;

    /**
     * Constructor. Initializes all variables.
     * @param seed
     * seed used to seed the random number generator in the Jobsimulator class.
     * @param timeToPlay
     * duration used to determine the length of the game.
     */
    public Game(int seed, int timeToPlay){
    	this.timeToPlay = timeToPlay;
    	this.scoreBoard = new Scoreboard();
    	this.jobSimulator = new JobSimulator(seed);
    	this.list = new JobList<Job>();
    	jobSimulator.simulateJobs(list,timeToPlay);
    	
    }

    /**
     * Returns the amount of time currently left in the game.
     * @returns the amount of time left in the game.
     */
    public int getTimeToPlay() {
        return timeToPlay;
        
    }

    /**
     * Sets the amount of time that the game is to be executed for.
     * Can be used to update the amount of time remaining.
     * @param timeToPlay
     *        the remaining duration of the game
     */
    public void setTimeToPlay(int timeToPlay) {
    	this.timeToPlay = timeToPlay;
    	
    }

    /**
     * States whether or not the game is over yet.
     * @returns true if the amount of time remaining in
     * the game is less than or equal to 0,
     * else returns false
     */
    public boolean isOver(){
    	if(timeToPlay<=0){
    		return true;
    	}
        return false;
    }
    /**
     * This method simply invokes the simulateJobs method
     * in the JobSimulator object.
     */
    public void createJobs(){
    	jobSimulator.simulateJobs(list, timeToPlay);
    }

    /**
     * @returns the length of the Joblist.
     */
    public int getNumberOfJobs(){
        return list.size();
    }

    /**
     * Adds a job to a given position in the joblist.
     * Also requires to calculate the time Penalty involved in
     * adding a job back into the list and update the timeToPlay
     * accordingly
     * @param pos
     *      The position that the given job is to be added to in the list.
     * @param item
     *      The job to be inserted in the list.
     */
    public void addJob(int pos, Job item){
    	int penalty = 0;
    	try{
    		list.add(pos, item);
    
    		if (pos < 0 || pos > getNumberOfJobs()){
    			penalty = getNumberOfJobs();
    		} 
    		else {
    			penalty = pos;
    		}
    		//penalty if index is valid
    		this.timeToPlay = timeToPlay- penalty;
    	} catch (IndexOutOfBoundsException e){
    		//penalty if index is invalid
    		timeToPlay = timeToPlay - list.size();
    		
    		//Adds the item to the end of the list if the pos
    		//is not a valid index.
    		list.add(item);
    	}
    		 
    }

    /**
     * Adds a job to the joblist.
     * @param item
     *      The job to be inserted in the list.
     */
    public void addJob(Job item){
    	list.add(item);
    }

    /**
     * Given a valid index and duration,
     * executes the given job for the given duration.
     * 
     * If an invalid index or duration is given,
     * the program will display an error message
     * and shut down.
     *
     * This function should remove the job from the list and
     * return it after applying the duration.
     *
     * This function should set duration equal to the
     * amount of time remaining if duration exceeds it prior
     * to executing the job.
     * 
     * After executing the job for a given amount of time, /
     * check if it is completed or not. If it is, then
     * it must be inserted into the scoreBoard.
     * 
     * This method should also calculate the time penalty involved in
     * executing the job and update the timeToPlay value accordingly  /
     * @param index
     *      The job to be inserted in the list.
     * @param duration
     *      The amount of time the given job is to be worked on for.
     */
    public Job updateJob(int index, int duration){
    	  	
    	// If an invalid index is given, the program will display 
    	// an error message and shut down.
    	if (index > getNumberOfJobs() || index < 0){
    		System.out.println("End Game: Invalid index given");
    		System.exit(0);
    	}
    	
    	// If an invalid duration is given, the program will display 
    	// an error message and shut down.    	
    	//if (duration < 0 || duration > getTimeToPlay()){
    	if (duration < 0){
    		//Handles exception of duration being negative
    		//If handling is in question, consult piazza cid=268
    		System.out.println("End Game: Invalid duration given");
    		System.exit(0);
    	}
    	// This function should set duration equal to the
        // amount of time remaining if duration exceeds it prior
        // to executing the job.
		if(duration > getTimeToPlay()){
    		duration = getTimeToPlay(); 
    	}

    	//This function should set duration equal to the
    	// amount of time remaining if duration exceeds it prior
        // to executing the job.
    	
    	//should never be null if there is no error
    	Job temp = null;
    	try{
    		temp = list.remove(index);     	//remove job from list
    	} catch (IndexOutOfBoundsException e){
    		//Handles exception of index out of bounds
    		//If handling is in question, consult piazza cid=233
    		System.out.println("End Game: Invalid index given");
    		System.exit(0);
    	}

		if(duration > temp.getTimeUnits()){
    		duration = temp.getTimeUnits(); 
    	}
    	
    	//If the updated steps exceeds timeUnits set steps to timeUnits 
    	//so that the isComplete() method of the Job will still work.
    	//Otherwise, increments steps by duration.
    	temp.setSteps((temp.getSteps() + duration > temp.getTimeUnits()) 
    			? temp.getTimeUnits() : (temp.getSteps() + duration));
    	
    	//This is the penalty
    	timeToPlay = timeToPlay - duration - index;
    	
    	//ifCompleted is true update scoreboard
    	if(temp.isCompleted()){
    		scoreBoard.updateScoreBoard(temp);
    	}
    	
    	jobSimulator.simulateJobs(list, timeToPlay);
    	  	
        return temp;
    }

    /**
     * This method produces the output for the initial Job Listing, IE:
     * "Job Listing
     *  At position: job.toString()
     *  At position: job.toString()
     *  ..."
     *
     */
    public void displayActiveJobs(){
    	Iterator<Job> iterator = list.iterator(); 
    	System.out.println("Job Listing");
    	int i = 0;
    	while (iterator.hasNext()){
    		System.out.println("At position: " + i + " " 
    							+ iterator.next().toString());
    		i++;
    	}
    }

    /**
     * This function simply invokes the displayScoreBoard method 
     * in the ScoreBoard class.
     */
    public void displayCompletedJobs(){
    	scoreBoard.displayScoreBoard();
    }

    /**
     * This function simply invokes the getTotalScore method of the ScoreBoard class.
     * @return the value calculated by getTotalScore
     */
    public int getTotalScore(){
        return scoreBoard.getTotalScore();
    }
}