/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017 
// PROJECT:          p3: Memory Efficient Merging of Sorted Files
// FILE:             Reducer.java
//
// TEAM:    Team 49
// Author1: Dan Bondi, dbondi@wisc.edu, dbondi, Lecture 002
// Author2: Hyunho Choi, hchoi225@wisc.edu, hchoi225, Lecure 002
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * Reducer solves the following problem: given a set of sorted input files (each
 * containing the same type of data), merge them into one sorted file. 
 *
 */
public class Reducer {
	// list of files for stocking the PQ
	private List<FileIterator> fileList;
	private String type,dirName,outFile;

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: java Reducer <weather|thesaurus> <dir_name> <output_file>");
			System.exit(1);
		}

		String type = args[0];
		String dirName = args[1];
		String outFile = args[2];

		Reducer r = new Reducer(type, dirName, outFile);
		r.run();

	}

	/**
	 * Constructs a new instance of Reducer with the given type (a string indicating which type of data is being merged),
	 * the directory which contains the files to be merged, and the name of the output file.
	 */
	public Reducer(String type, String dirName, String outFile) {
		this.type = type;
		this.dirName = dirName;
		this.outFile = outFile;
	}

	/**
	 * Carries out the file merging algorithm described in the assignment description. 
	 */
	public void run() {
		File dir = new File(dirName);
		File[] files = dir.listFiles();
		Arrays.sort(files);
		Record r = null;

		// list of files for stocking the PQ
		fileList = new ArrayList<FileIterator>();

		for(int i = 0; i < files.length; i++) {
			File f = files[i];
			if(f.isFile() && f.getName().endsWith(".txt")) {
				fileList.add(new FileIterator(f.getAbsolutePath(), i));
			}
		}

		switch (type) {
		case "weather":
			r = new WeatherRecord(fileList.size());
			break;
		case "thesaurus":
			r = new ThesaurusRecord(fileList.size());
			break;
		default:
			System.out.println("Invalid type of data! " + type);
			System.exit(1);
		}

		
		// create priority queue
		FileLinePriorityQueue queue = 
				new FileLinePriorityQueue(fileList.size(), r.getComparator());
		FileLine previous;
		FileLine current;
		Comparator<FileLine> comp = r.getComparator();
		int compareInt;
		try {
			for (int i = 0; i < fileList.size(); i++) {
				// insert fileList values into queue
				queue.insert(fileList.get(i).next());
			}
			// set previous to smallest value in queue
			previous = queue.removeMin();

			if (previous.getFileIterator().hasNext()) {
				queue.insert(previous.getFileIterator().next());
			}
			current = previous;
			// add current to "r"
			r.join(current);
			PrintWriter pw = new PrintWriter(outFile);
			// add values to "r" until queue is empty
			while (!queue.isEmpty()) {
				// set current to smallest value in queue
				current = queue.removeMin();
				// bring new queue
				if (current.getFileIterator().hasNext()) {
					queue.insert(current.getFileIterator().next());
				}
				compareInt = comp.compare(previous, current);
				// if previous and current are the same add current to "r"
				if (compareInt == 0) {
					r.join(current);
				} else {
					// if previous and current aren't the same write values in
					// "r" to output file, clear "r", and add current to "r"
					pw.println(r.toString());
					r.clear();
					r.join(current);
				}
				previous = current;
			}
			// add current to "r"
			r.join(current);
			// write values in r to output file
			pw.println(r.toString());
			pw.close();

			// catch PriorityQueueFullException
		} catch (PriorityQueueFullException e) {
			System.out.println("PriorityQueueFull Exception");
			e.printStackTrace();
			System.exit(1);
			// catch PriorityQueueEmptyException
		} catch (PriorityQueueEmptyException e) {
			System.out.println("PriorityQueueEmpty Exeception");
			e.printStackTrace();
			System.exit(1);
			// catch IOException
		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
