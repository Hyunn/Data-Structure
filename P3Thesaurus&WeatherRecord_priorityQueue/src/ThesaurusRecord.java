/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017 
// PROJECT:          p3: Memory Efficient Merging of Sorted Files
// FILE:             ThesaurusRecord.java
//
// TEAM:    Team 49
// Author1: Dan Bondi, dbondi@wisc.edu, dbondi, Lecture 002
// Author2: Hyunho Choi, hchoi225@wisc.edu, hchoi225, Lecure 002
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The ThesaurusRecord class is the child class of Record to be used when merging thesaurus data.
 */

public class ThesaurusRecord extends Record{
	String word;
	ArrayList<String> synonyms = new ArrayList<String>(); //List of synonyms
	/**
	 * Constructs a new ThesaurusRecord by passing the parameter to the parent constructor
	 * and then calling the clear method()
	 */
	public ThesaurusRecord(int numFiles) {
		super(numFiles);
		clear();
	}

	/**
	 * This Comparator should simply behave like the default (lexicographic) compareTo() method
	 * for Strings, applied to the portions of the FileLines' Strings up to the ":"
	 * The getComparator() method of the ThesaurusRecord class will simply return an
	 * instance of this class.
	 */
	private class ThesaurusLineComparator implements Comparator<FileLine> {
		public int compare(FileLine l1, FileLine l2) {
			// can't compare a null value so throw NullPointerException
			if (l1 == null || l2 == null) {
				throw new NullPointerException();
			}
			// split up the fileLine so that first[0] and second[0] are the
			// words
			String[] first = l1.getString().split(":");
			String[] second = l2.getString().split(":");

			// if second[0] is greater than first[0] return -1
			if (first[0].compareTo(second[0]) < 0) {
				return -1;
			}

			// if first[0] is greater than second[0] return -1
			else if (first[0].compareTo(second[0]) > 0) {
				return 1;
			}

			else {
				return 0;
			}

		}

		public boolean equals(Object o) {
			return this.equals(o);
		}
	}

	/**
	 * This method should simply create and return a new instance of the ThesaurusLineComparator class.
	 */
	public Comparator<FileLine> getComparator() {
		return new ThesaurusLineComparator();
	}

	/**
	 * This method should (1) set the word to null and (2) empty the list of synonyms.
	 */
	public void clear() {
		//sets word to null
		word = null;				
		//removes every synonym
		while(!synonyms.isEmpty()){ 
			synonyms.remove(0);
		}
	}

	/**
	 * This method should parse the list of synonyms contained in the given FileLine and insert any
	 * which are not already found in this ThesaurusRecord's list of synonyms.
	 */
	public void join(FileLine w) {
		// split the word and synonyms
		String[] splitter = w.getString().split(":");
		// split the synonyms up
		String[] words = splitter[1].split(",");
		this.word = splitter[0];

		// add all the data if the array is empty
		if (synonyms.size() == 0) {
			for (int i = 0; i < words.length; i++) {
				synonyms.add(words[i]);
			}

			// if the array is not empty insert a synonym only if that synonym
			// hasn't been enter previously
		} else {

			for (int i = 0; i < words.length; i++) {
				for (int j = 0; j < synonyms.size(); j++) {
					// synonym already exists so don't add it
					if (words[i].equals(synonyms.get(j))) {
						break;
					}
					if (j == synonyms.size() - 1) {
						// add synonym because it was not found in the array
						// previously
						synonyms.add(words[i]);
					}
				}
			}
		}
	}

	/**
	 * See the assignment description and example runs for the exact output
	 * format.
	 */
	public String toString() {
		// sort alphabetically
		Collections.sort(synonyms);

		// returns String were word and synonyms are separated by ":" and every
		// synonym is separated by ","
		String temp = this.word + ":";
		for (int i = 0; i < synonyms.size() - 1; i++) {
			temp += synonyms.get(i) + ",";
		}
		temp += synonyms.get(synonyms.size() - 1);

		return temp;
	}
}
