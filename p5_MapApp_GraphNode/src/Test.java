import java.io.FileNotFoundException;
import java.util.*;

public class Test {

	public static void main(String[] args) {
		Location com = new Location("com");
		Location psy = new Location("psy");
		List<Double> pathh = new ArrayList<Double>();
		pathh.add(10.01);
		pathh.add(15.01);
		Path cp = new Path(com, psy, pathh);
		Path cp2 = new Path(psy, com, pathh);
		Path cp3 = new Path(psy, com, pathh);
		String[] names = new String[2];
		names[0] = "time";
		names[1] = "cost";
		
		List<Location> loc = new ArrayList<Location>();
		loc.add(new Location(names[0]));
		loc.add(psy);
		String st = cp.toString();
		st += ", " + cp2.toString();
		st += ", " + cp3.toString();
		
		List<Path> ls = new ArrayList<Path>();
		ls.add(cp);
		ls.add(cp2);
		ls.add(cp3);
		
		String stt= ls.get(0).toString();
		for(int i=1; i<ls.size(); i++) {
			stt += ", " + ls.get(i).toString();
		}
		stt += "\n";
		stt += "2";
		System.out.println(stt);
//		for(int j=1; j<outEdges.size(); j++) {
//			outputString += ", " + outEdges.get(j).toString();
//			if(j == (outEdges.size()-1))
//				outputString += "\n";
//		}
		
		//NavigationGraph graph = new NavigationGraph()
		
//		System.out.println(st);
//		System.out.println(loc.contains(psy));
		
		
		/*
		 try {
		 locationFileName = 
			NavigationGraph graph = createNavigationGraphFromMapFile(locationFileName);
			MapApp appInstance = new MapApp(graph);
			appInstance.startService();

		} catch (FileNotFoundException e) {
			System.out.println("GRAPH FILE: " + locationFileName + " was not found.");
			System.exit(1);
		} catch (InvalidFileException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		 */

	}

}
