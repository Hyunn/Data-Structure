
/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017 
// PROJECT:          p3: Memory Efficient Merging of Sorted Files
// FILE:             WeatherRecord.java
//
// TEAM:    Team 49
// Author1: Dan Bondi, dbondi@wisc.edu, dbondi, Lecture 002
// Author2: Hyunho Choi, hchoi225@wisc.edu, hchoi225, Lecure 002
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * The WeatherRecord class is the child class of Record to be used when merging weather data. Station and Date
 * store the station and date associated with each weather reading that this object stores.
 * l stores the weather readings, in the same order as the files from which they came are indexed.
 */
public class WeatherRecord extends Record{
    int stationID;            //declare data structures         
    int date;
    double[] measurements;

	/**
	 * Constructs a new WeatherRecord by passing the parameter to the parent constructor
	 * and then calling the clear method()
	 */
    public WeatherRecord(int numFiles) { 
		super(numFiles);
		clear();
    }
	
	/**
	 * This comparator should first compare the stations associated with the given FileLines. If 
	 * they are the same, then the dates should be compared. 
	 */
    private class WeatherLineComparator implements Comparator<FileLine> {
		public int compare(FileLine l1, FileLine l2) {
			if((l1==null) || (l2 == null)){     //If either file is null throw NullPointerException
				throw new NullPointerException();
			}
			String[] first = l1.getString().split(","); //split FileLine into array by separating FileLine at each comma
			String[] second = l1.getString().split(",");
			
			if(first[0].compareTo(second[0]) < 0){ //If second[0] is greater than first[0] return -1;
				return -1;
			}
			if(first[0].compareTo(second[0]) > 0){ //If first[0] is greater than second[0] return 1;
				return 1;
			}
			
			//first[0] must equal second[0] so compare first [1] and second[1]
			
			if(first[1].compareTo(second[1]) < 0){ //If second[1] is greater than first[1] return -1;
				return -1;                         
			}
			if(first[1].compareTo(second[1]) > 0){ //If first[1] is greater than second[1] return 1;
				return 1;
			}
			
			//return zero if first[0] equals second[0] and first[1] equals second[1]
			return 0;
		}
		
		public boolean equals(Object o) {
			return this.equals(o);
		}
    }
    
	/**
	 * This method should simply create and return a new instance of the WeatherLineComparator
	 * class.
	 */
    public Comparator<FileLine> getComparator() {
		return new WeatherLineComparator();
    }
	
	/**
	 * This method should fill each entry in the data structure containing
	 * the readings with Double.MIN_VALUE
	 */
    public void clear() {
    	measurements = new double[getNumFiles()];
    	Arrays.fill(measurements, Double.MIN_VALUE); //set each value in measurements to Double.MIN_VALUE
    }

	/**
	 * This method should parse the String associated with the given FileLine to get the station, date, and reading
	 * contained therein. Then, in the data structure holding each reading, the entry with index equal to the parameter 
	 * FileLine's index should be set to the value of the reading. Also, so that
	 * this method will handle merging when this WeatherRecord is empty, the station and date associated with this
	 * WeatherRecord should be set to the station and date values which were similarly parsed.
	 */
    public void join(FileLine li) {
    	String[] weatherLine = li.getString().split(",");
    	this.stationID = Integer.valueOf(weatherLine[0]);
    	this.date = Integer.valueOf(weatherLine[1]);
    	double now = Double.valueOf(weatherLine[2]);
    	
    	int index = li.getFileIterator().getIndex();
    	measurements[index] = now;
    }
    
//    	String[] splitter = li.getString().split(","); //split FileLine into array by separating FileLine at each comma
//		String[] words = splitter[1].split(",");
//    	double data = Double.parseDouble(splitter[2]); //get measurement at position 3
//		this.stationID = Integer.parseInt(splitter[0]);
//		this.date = Integer.parseInt(splitter[1]);
//		
//    	if(measurements.size() == 0) {
//    		
//    	}
//    	else{
//    		for(int i=0; i<measurements.size(); i++){}
//    	}
//    		
//		if(stationID == 0){ 
//			this.stationID = Integer.parseInt(splitter[0]);
//			this.date = Integer.parseInt(splitter[1]);
//		}
//		
//		//compare stationID
//		if(stationID == Integer.parseInt(splitter[0])){
//			if(date == Integer.parseInt(splitter[1]))
//			{
//				measurements.add(data); //FileLine with the same stationID and date does exists so add data
//			}
//			else{
//				measurements.add(0.0); //FileLine with the same stationID and date doesn't exist so return 0
//			}
//		}
//		else{
//			measurements.add(0.0); //FileLine with the same stationID and date doesn't exist so return 0
//		}
//		/*
//		this.stationID = Integer.parseInt(splitter[0]);
//		this.date = Integer.parseInt(splitter[1]);
//		measurements.add(data);
//		*/
//		
//    }
	
	/**
	 * See the assignment description and example runs for the exact output format.
	 */
    public String toString() {
		String temp = stationID + ",";
		String output = temp;
		temp = "" + date;
		output += temp;
		for (int i=0; i<measurements.length; i++) {
			if(measurements[i] == Double.MIN_VALUE)
				output += "-";
			else
				output += measurements[i];
			if(i != measurements.length -1)
				output += ",";
		}
		return output; 
    }
//	StringBuilder buildString = new StringBuilder();
//	buildString.append(stationID);
//	buildString.append(",");
//	buildString.append(date);
//	buildString.append(",");
//	for (int i = 0; i < measurements.size(); i++) {
//		Double d = measurements.get(i);
//		if (d != Double.MIN_VALUE) {
//			buildString.append(d);
//		} else {
//			buildString.append("-");
//		}
//		if (i < measurements.size() -1) {
//			buildString.append(",");
//		}
//	}
//	return buildString.toString();
}
