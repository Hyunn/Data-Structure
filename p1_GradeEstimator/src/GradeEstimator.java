/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          program 1
// FILE:             GradeEstimator.java
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter; 
import java.util.Iterator; 
import java.util.Scanner;

/**
 * This class reads in a file and outputs a final grade based on all of the 
 * assignments and weighted percentages. 
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
public class GradeEstimator {

	// Instance Variables
	String[] gradeLetter;
	double[] gradeThreshold;

	String[] categoryKey;
	double[] categoryWeight;

	ScoreList grades;

	/**
	 * GradeEstimator constructor 
	 *
	 * @param gradeLetter An array of the letter grades that can be assigned (i.e. A, B, C, D, F)
	 * @param gradeThreshold An array of the lower threshold for each of the letter grades
	 * @param categoryKey An array of the names of each type of assignment
	 * @param categoryWeight An array of the category weight (i.e. Homework is 20% of the final grade) 
	 * @param grades List of Scores 
	 */
	GradeEstimator(String[] gradeLetter, double[] gradeThreshold, String[] categoryKey, double[] categoryWeight,
			ScoreList grades) {
		this.gradeLetter = gradeLetter;
		this.gradeThreshold = gradeThreshold;
		this.categoryKey = categoryKey;
		this.categoryWeight = categoryWeight;
		this.grades = grades;		
	}

	/**
	 * GradeEstimatr constructor that is passed no parameters. It sets 
	 * all of the instance variables to the Config.java file inputs
	 */
	GradeEstimator() {
		try {
			GradeEstimator temp = createGradeEstimatorFromFile("Config.GRADE_INFO_FILE_FORMAT_EXAMPLE");
			this.gradeLetter = temp.gradeLetter;
			this.gradeThreshold = temp.gradeThreshold;
			this.categoryKey = temp.categoryKey;
			this.categoryWeight = temp.categoryWeight;
			this.grades = temp.grades;
		} catch (FileNotFoundException | GradeFileFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main method of the GradeEstimator class. Trys to create a 
	 * GradeEstimatorFromFile given the input. If there is no file, it 
	 * throws a FileNotFoundException. If the file type is incorrect, it 
	 * throws a GradeFileFormatException. If there is no exception, it 
	 * prints out the grade Estimate Report (getEstimateReport).
	 *
	 * @param args Input file name 
	 * @throws FileNotFoundException
	 * @throws GradeFileFormatException
	 */
	public static void main(String[] args) {
		boolean hasException = false;

		GradeEstimator a = null;

		if (args.length == 1) {
			try {
				a = createGradeEstimatorFromFile(args[0]);
			} catch (FileNotFoundException e) {
				System.out.println("java.io.FileNotFoundException: " + args[0] + " (No such file or directory)");
				hasException = true;
			} catch (GradeFileFormatException e) {
				System.out.println("GradeFileFormatException");
				hasException = true;
			}
		} else {
			System.out.println(Config.USAGE_MESSAGE);
			hasException = true;
			System.out.println("Grade estimate is based on 0 scores " + "\n  20.00% = 100.00% of 20% for homeworks "
					+ "\n  25.00% = 100.00% of 25% for programs" + "\n  34.00% = 100.00% of 34% for midterms"
					+ "\n  21.00% = 100.00% of 21% for final" + "\n--------------------------------"
					+ "\n 100.00% weighted percent" + "\nLetter Grade Estimate: A");
		}
		
		//if no exception is thrown, print the grade Estimate Report 
		if (!hasException)
			System.out.println(a.getEstimateReport());
	}

	/**
	 * Scans in a file, parses it and stores the data in the GradeEstimator 
	 * constructor variables
	 *
	 * @param gradeInfo Input file name (i.e. gradeInfo.txt)
	 * @return A GradeEstimator object with the input file data
	 * @throws FileNotFoundException
	 * @throws GradeFileFormatException
	 */
	public static GradeEstimator createGradeEstimatorFromFile(String gradeInfo)
			throws FileNotFoundException, GradeFileFormatException {

		GradeEstimator result;
		Scanner file;
		
		//Sets the gradeInfo to the Config.java file information. 
		//This happens when the input is null
		if (gradeInfo.equals("Config.GRADE_INFO_FILE_FORMAT_EXAMPLE")) { 
			file = new Scanner(Config.GRADE_INFO_FILE_FORMAT_EXAMPLE);
		
		//If the input file is not null it scans in the file. 	
		} else {
			try {
				file = new Scanner(new File(gradeInfo));
			} catch (NullPointerException e) {
				throw new FileNotFoundException();
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException();
			}
		}

		//Attempts to parse the file and pass through a GradeEstimator 
		//constructor if the file is formatted correctly
		try {
			String[] gL = file.nextLine().split("#")[0].split(" ");
			for (String s : gL)
				if (s.length() != 1)
					throw new GradeFileFormatException();
			String[] temp = file.nextLine().split("#")[0].split(" ");
			double[] gT = new double[temp.length];

			if (gT[gT.length - 1] > 0) {
				throw new GradeFileFormatException();
			}

			for (int i = 0; i < gT.length; i++)
				gT[i] = Double.parseDouble(temp[i]);
			
			String[] cK = file.nextLine().split("#")[0].split(" ");
			temp = file.nextLine().split("#")[0].split(" ");
			double[] cW = new double[temp.length];
			
			for (int i = 0; i < cW.length; i++) {
				cW[i] = Double.parseDouble(temp[i]);
			}
			
			//Create a new ScoreList to store the Scores 
			ScoreList grades = new ScoreList();
			
			while (file.hasNextLine()) {
				temp = file.nextLine().split("#")[0].split(" ");
				grades.add(new Score(temp[0], Double.parseDouble(temp[1]), Double.parseDouble(temp[2])));
			}

			//Create a new GradeEstimator
			result = new GradeEstimator(gL, gT, cK, cW, grades);
			
			//Ensures that each Score in ScoreList grades is part of
			//a category defined in the input file.
			for (int j = 0; j < grades.size(); j++) {
				Score temp_1 = grades.get(j);
				boolean hasKey = false;
				for (int i = 0; i < cK.length; i++) {
					if (temp_1.getCategory().toLowerCase().equals(cK[i].substring(0, 1).toLowerCase())) {
						hasKey = true;
					}
				}
				if(hasKey == false) throw new GradeFileFormatException();
			}
			
		} catch (NullPointerException e) {
			file.close();
			throw new GradeFileFormatException();
		} catch (NumberFormatException e) {
			file.close();
			throw new GradeFileFormatException();
		} catch (GradeFileFormatException e) {
			file.close();
			throw new GradeFileFormatException();
		}

		file.close();

		return result;

	}

	/**
	 * Uses the information from constructor to calculate the final grade 
	 * for the student. This method returns the message that will be 
	 * displayed to the user with the correct output format. 
	 *
	 * @return grade estimate report that will be displayed to user 
	 */
	public String getEstimateReport() {
		double[] totalPoints = new double[categoryKey.length]; // Total points in a category 
		double[] numCounted = new double[categoryKey.length]; // an array of the number of assignments of each type 

		//Initializing the string that will be returned 
		String result = "";

		//Takes scores from ScoreList grades and makes aggregate values in totalPoints and numCounted
		for (int i = 0; i < categoryKey.length; i++) {
			ScoreIteratorADT a = new ScoreIterator(grades,categoryKey[i]);
			while (a.hasNext()){
				Score temp = (Score) a.next();
				result += (temp.getName() + "   " + String.format("%7.2f", temp.getPercent()) + "\n");
				totalPoints[i] += temp.getPercent();
				numCounted[i]++;
			}
		}

		double[] weightedScores = new double[categoryKey.length];
		
		for (int i = 0; i < weightedScores.length; i++) {
			weightedScores[i] = (totalPoints[i] / numCounted[i]) * categoryWeight[i] * 0.01;
		}

		double total = 0;
		for (double d : weightedScores)
			total += d;

		result += "Grade estimate is based on " + grades.size() + " scores.\n";
		
		for (int i = 0; i < categoryKey.length; i++) {
			result += "  " + String.format("%7.2f", weightedScores[i]).replace(" ", "") + "% = ";
			result += "  " + String.format("%7.2f", (totalPoints[i] / numCounted[i])).replace(" ", "") + "% of ";
			result += "  " + String.format("%7.0f", categoryWeight[i]).replace(" ", "") + "% for " + categoryKey[i]
					+ "\n";
		}
		result += "--------------------------------\n";
		result += String.format("%7.2f", total) + "% weighted percent\n";
		result += "Letter Grade Estimate: ";

		// Checks to make sure the correct gradeThreshold is input 
		boolean gotLetter = false;
		int i = 0;
		try {
			while (!gotLetter) { 
				if (total >= gradeThreshold[i]) {
					result += gradeLetter[i];
					gotLetter = true;
				}
				i++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			result += "unable to estimate letter grade for " + total;
		}

		return result;
	}
}
