/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          program 1
// FILE:             GradeFileFormatException.java
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
 * The exception that is thrown when the input file does not follow the 
 * correct file format. The file format is displayed in the Config.java file.
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
public class GradeFileFormatException extends Exception {
	
	/**
	 * Constructor method for GradeFileFormatException that 
	 * does not pass in an argument. 
	 */
	public GradeFileFormatException() 
	{
		super ();
	}
	
	/**
	 * Constructor method for GradeFileFormatException that 
	 * accepts a message. 
	 * 
	 * @param message Error message for GradeFileFormatException 
	 */
	public GradeFileFormatException(String message)
	{
		super(message);
	}
}
