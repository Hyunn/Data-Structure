
/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          program 2
// FILE:             GameApp.java
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

import java.util.Scanner;

public class GameApp {
	/**
	 * Scanner instance for reading input from console
	 */
	private static final Scanner STDIN = new Scanner(System.in);

	Game myGame;

	/**
	 * Constructor for instantiating game class
	 * 
	 * @param seed:
	 *            Seed value as processed in command line
	 * @param timeToPlay:
	 *            Total time to play from command line
	 */
	public GameApp(int seed, int timeToPlay) {

		myGame = new Game(seed, timeToPlay);
	}

	/**
	 * Main function which takes the command line arguments and instantiate the
	 * GameApp class. The main function terminates when the game ends. Use the
	 * getIntegerInput function to read inputs from console
	 *
	 * @param args:
	 *            Command line arguments <seed> <timeToPlay>
	 */
	public static void main(String[] args) {

		//Instance Variables 
		int seed = 0;
		int timeToPlay = 0;
		
		// Quit the program if the seed or timeToPlay is invalid
		try {
			seed = Integer.parseInt(args[0]);
			if (seed < 0)
				throw new Exception();
			timeToPlay = Integer.parseInt(args[1]);
			if (timeToPlay < 0)
				throw new Exception();
		} catch (Exception e) {
			System.exit(0);
		}

		// Create a new GameApp
		GameApp app = new GameApp(seed, timeToPlay);

		// Start the game 
		app.start();
	}

	/**
	 * Displays the welcome message, starts the loop of the game,
	 * and displays the final score once game is over.
	 */
	private void start() {

		System.out.println("Welcome to the Job Market!");
		
		// runs the program while the user has not lost and the input is valid
		while (!myGame.isOver()) {
			mainMenuLoop();
		}
		
		// game over
		System.out.println("Game Over!");
		System.out.println("Your final score: " + myGame.getTotalScore());

	}

	/**
	 * Runs the main body of the game
	 */
	private void mainMenuLoop() {
		System.out.println("You have " + myGame.getTimeToPlay() 
							+ " left in the game!");
		myGame.displayActiveJobs();
		System.out.println();
		
		int indexInput = getIntegerInput("Select a job to work on: ");

		int durationInput = getIntegerInput("For how long would you like to "
											+ "work on this job?: ");

		// Create a temporary Job
		Job tempJob = myGame.updateJob(indexInput, durationInput);

		// Print display if the Job is completed
		if (tempJob.isCompleted()) {
			System.out.println("Job completed! Current Score: " 
								+ myGame.getTotalScore());
			System.out.println("Total Score: " + myGame.getTotalScore());
			System.out.println("The jobs completed:");
			myGame.displayCompletedJobs();
		} 
		// if the job was not completed, insert the job back into the active list
		else {
			int intInput = getIntegerInput(
					"At what position would you like to insert the job back "
					+ "into the list? " + "\n");
			myGame.addJob(intInput, tempJob);

		}
	}

	/**
	 * Displays the prompt and returns the integer entered by the user to the
	 * standard input stream.
	 *
	 * Does not return until the user enters an integer. Does not check the
	 * integer value in any way.
	 * 
	 * @param prompt
	 *            The user prompt to display before waiting for this integer.
	 */
	public static int getIntegerInput(String prompt) {
		System.out.print(prompt);
		while ( ! STDIN.hasNextInt() ) {
            System.out.print(STDIN.next()+" is not an int.  "
            				+ "Please enter an integer.");
        }
        return STDIN.nextInt();
	}
	
	
}