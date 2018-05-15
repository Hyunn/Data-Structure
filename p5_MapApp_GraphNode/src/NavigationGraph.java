
/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017 
// PROJECT:          p5
// FILE:             NavigationGraph.java
//
// TEAM:    Team 178
// Author1: Dan Bondi, dbondi@wisc.edu, dbondi, Lecture 002
// Author2: Hyunho Choi, hchoi225@wisc.edu, hchoi225, Lecure 002
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.*;
import java.util.List;

public class NavigationGraph implements GraphADT<Location, Path> {

	// an array contains property names
	private String[] edgePropertyNames;
	// list of location
	private List<Location> vertices;
	// list of GraphNode
	private List<GraphNode<Location, Path>> GraphList;
	// unique int for each GraphNode
	private int id = 0;

	// construction of GraphNode with location and id
	private void GraphNode(Location vertexData, int id) {
		// if node is already existing, return
		for (int i = 0; i < GraphList.size(); i++) {
			if (GraphList.get(i).getVertexData().equals(vertexData))
				return;
		}
		// make and add new GraphNode
		GraphNode<Location, Path> node = 
				new GraphNode<Location, Path>(vertexData, id);
		GraphList.add(node);
		// increment id so other GraphNodes have different id
		id++;
	}

	/**
	 * Construction of NavigationGraph with list of property names
	 * 
	 * @param edgePropertyNames
	 *            list of property names
	 */
	public NavigationGraph(String[] edgePropertyNames) {
		this.edgePropertyNames = edgePropertyNames;
		this.vertices = new ArrayList<Location>();
		this.GraphList = new ArrayList<GraphNode<Location, Path>>();
	}

	/**
	 * Adds a vertex to the Graph
	 * 
	 * @param vertex
	 *            vertex to be added
	 */
	public void addVertex(Location vertex) {
		if (vertex == null)
			return;
		// if vertex is already existing, return
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getName().equals(vertex.getName()))
				return;
		}
		// add vertex to list of vertices and GraphList
		vertices.add(vertex);
		GraphNode(vertex, id);
	}

	/**
	 * Creates a directed edge from src to dest
	 * 
	 * @param src
	 *            source vertex from where the edge is outgoing
	 * @param dest
	 *            destination vertex where the edge is incoming
	 * @param edge
	 *            edge between src and dest
	 */
	public void addEdge(Location src, Location dest, Path edge) {
		// Add edge to adjacency list
		if (edge != null) {
			for (int i = 0; i < GraphList.size(); i++) {
				if (GraphList.get(i).getVertexData().equals(src))
					GraphList.get(i).getOutEdges().add(edge);
			}
		}
	}

	/**
	 * Getter method for the vertices
	 * 
	 * @return List of vertices of type V
	 */
	public List<Location> getVertices() {
		return vertices;
	}

	/**
	 * Returns edge if there is one from src to dest vertex else null
	 * 
	 * @param src
	 *            Source vertex
	 * @param dest
	 *            Destination vertex
	 * @return Edge of type E from src to dest
	 */
	public Path getEdgeIfExists(Location src, Location dest) {
		if (src == null || dest == null || src.equals(dest))
			return null;
		// traverse each nodes in GraphList
		try {
			for (int i = 0; i < GraphList.size(); i++) {
				List<Path> a = GraphList.get(i).getOutEdges();
				// comparing every paths of each nodes
				for (int j = 0; j < a.size(); j++) {
					if (a.get(j).getSource().equals(src) &&
							a.get(j).getDestination().equals(dest))
						return a.get(j);
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Illegal Argument Exception occurs");
		}
		return null;
	}

	/**
	 * Returns the outgoing edges from a vertex
	 * 
	 * @param src
	 *            Source vertex for which the outgoing edges need to be obtained
	 * @return List of edges of type E
	 */
	public List<Path> getOutEdges(Location src) {
		if (src == null)
			return null;
		// traverse each nodes in GraphList and return outEdges if exists
		for (int i = 0; i < GraphList.size(); i++) {
			if (GraphList.get(i).getVertexData().equals(src))
				return GraphList.get(i).getOutEdges();
		}
		return null;
	}

	/**
	 * Returns neighbors of a vertex
	 * 
	 * @param vertex
	 *            vertex for which the neighbors are required
	 * @return List of vertices(neighbors) of type V
	 */
	public List<Location> getNeighbors(Location vertex) {
		// get the outEdges of vertex
		List<Path> outEdges = getOutEdges(vertex);
		// make ArrayList with size of outEdges
		List<Location> neighbors = new ArrayList<Location>(outEdges.size());

		// add all locations connected from vertex
		for (int i = 0; i < outEdges.size(); i++) {
			neighbors.add(outEdges.get(i).getDestination());
		}
		return neighbors;
	}

	/**
	 * Calculate the shortest route from src to dest vertex using
	 * edgePropertyName
	 * 
	 * @param src
	 *            Source vertex from which the shortest route is desired
	 * @param dest
	 *            Destination vertex to which the shortest route is desired
	 * @param edgePropertyName
	 *            edge property by which shortest route has to be calculated
	 * @return List of edges that denote the shortest route by edgePropertyName
	 */
	public List<Path> getShortestRoute(Location src, Location dest, String edgePropertyName) {

		int propertyIndex = -1;
		// finds the integer associated with the edgePropertyName
		for (int i = 0; i < edgePropertyNames.length; i++) {
			if (edgePropertyNames[i].equals(edgePropertyName)) {
				propertyIndex = i;
			}
		}

		// fastest path to be returned
		List<Path> fastest = new ArrayList<Path>(GraphList.size());

		// value of the weight it takes to get to each vertex
		List<Double> vertexWeight = new ArrayList<Double>(GraphList.size());

		// the value at each index of this array represents the previous vertex
		// it that was used to get here
		List<Integer> previousVertex = new ArrayList<Integer>(GraphList.size());

		// keeps track of the vertex that have been visited
		List<Boolean> visitedVertex = new ArrayList<Boolean>(GraphList.size());

		// index representing the location of the source in the graphList
		int source = getVertexId(src);

		for (int i = 0; i < GraphList.size(); i++) {
			// represents a weight of infinity
			vertexWeight.add(0, Double.MAX_VALUE);
			// no vertices have previous vertices so set array values to null
			previousVertex.add(0, null);
			// all vertices have not been visited so set array values to false
			visitedVertex.add(0, false);
		}
		// takes a weight of zero to get to source vertex from source vertex
		vertexWeight.set(getVertexId(src), 0.0);

		for (int i = 0; i < GraphList.size(); i++) {
			// int representing next index of the graphList that should be gone
			// to
			int nextPath = smallestVertex(vertexWeight, visitedVertex);

			// nextPath==1 when there are no unvisited vertices to go to
			if (nextPath != -1) {
				// set current to visited
				visitedVertex.set(nextPath, true);

				if (!GraphList.get(nextPath).getOutEdges().isEmpty()) {

					List<Location> neighbors = getNeighbors(GraphList.
							get(nextPath).getOutEdges().get(0).getSource());

					// tests the path weight it takes to get to each neighbor of
					// the given vertex and replaces the weighted value if a
					// smaller value is found
					for (int k = 0; k < neighbors.size(); k++) {

						// index representing the location of the vertex
						// currently being visited in the graphList
						int vertex = getVertexId(neighbors.get(k));

						// finds the weight of the path between the vertex and
						// its neighbor
						double weightOfPath = getEdgeIfExists(GraphList.
								get(nextPath).getOutEdges().get(k).getSource(),
								GraphList.get(nextPath).getOutEdges().get(k)
								.getDestination()).getProperties().get(propertyIndex);

						double newVertexValue = vertexWeight.get(nextPath)
															+ weightOfPath;

						// tests if new weight is smaller than previous and
						// changes weight if it is smaller
						if (vertexWeight.get(vertex) > newVertexValue) {
							vertexWeight.set(vertex, newVertexValue);
							previousVertex.set(vertex, nextPath);

						}
					}
				}
			}
		}

		int destintation = getVertexId(dest);

		// goes through the path taking using the previousVertex array and adds
		// the paths to fastest
		while (destintation != source && previousVertex.get(destintation) != null) {
			GraphNode<Location, Path> sourceNew = 
							GraphList.get(previousVertex.get(destintation));
			GraphNode<Location, Path> destinationNew =
							GraphList.get(destintation);
			try {
				fastest.add(0, getEdgeIfExists(sourceNew.getOutEdges()
						.get(0).getSource(),
						destinationNew.getOutEdges().get(0).getSource()));

			} catch (IndexOutOfBoundsException e) {
				fastest.add(0, getEdgeIfExists(sourceNew.getOutEdges()
						.get(0).getSource(), dest));
			}
			destintation = previousVertex.get(destintation);

		}
		return fastest;

	}

	// private method for getting ID of node by searching Location
	private int getVertexId(Location src) {
		for (int i = 0; i < GraphList.size(); i++) {
			if (GraphList.get(i).getVertexData().equals(src)) {
				return i;
			}
		}
		return -1;
	}

	// private method that selects the vertex whose weight is the smallest and
	// hasnt been visited
	private int smallestVertex(List<Double> vertexValue, List<Boolean> visited) {
		double minValue = Integer.MAX_VALUE;
		int minVertex = -1;// will return negative 1 if no values are found

		// find min value
		for (int i = 0; i < vertexValue.size(); i++) {
			if (!visited.get(i) && vertexValue.get(i) < minValue) {
				minVertex = i;
				minValue = vertexValue.get(i);
			}
		}
		return minVertex;
	}

	/**
	 * Getter method for edge property names
	 * 
	 * @return array of String that denotes the edge property names
	 */
	public String[] getEdgePropertyNames() {
		return edgePropertyNames;
	}

	/**
	 * Return a string representation of the graph
	 * 
	 * @return String representation of the graph
	 */
	public String toString() {
		// string for output
		String outputString = "";
		// traverse and get the all possible outEdges of all nodes in GraphList
		for (int i = 0; i < GraphList.size(); i++) {
			if (!GraphList.get(i).getOutEdges().isEmpty()) {
				List<Path> outEdges = GraphList.get(i).getOutEdges();
				// get the first outEdges
				outputString += outEdges.get(0).toString();
				// get the rest outEdges
				for (int j = 1; j < outEdges.size(); j++) {
					outputString += ", " + outEdges.get(j).toString();
				}
				if (i != GraphList.size() - 1)
					outputString += "\n";
			}
		}
		return outputString;
	}

	/**
	 * Returns a Location object given its name
	 * 
	 * @param name
	 *            name of the location
	 * @return Location object
	 */
	public Location getLocationByName(String name) {
		// Do linear search for finding location by name
		for (int i = 0; i < GraphList.size(); i++) {
			if (GraphList.get(i).getVertexData().getName().equals(name))
				return GraphList.get(i).getVertexData();
		}
		return null;
	}

}
