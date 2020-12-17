package edu.iup.cosc310.graph;

import java.util.ArrayList;
import java.util.Collections; 
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

/**
 * An adjacency list implementation for a generic graph.
 * 
 * @author dtsmith
 *
 * @param <V> data type for the vertices of the graph
 * @param <W> data type for the weight of edges of a graph. Use Boolean for an
 *            unweighted graph
 */
public class ALGraph<V, W> implements Graph<V, W> {
	private boolean directed;
	private boolean weighted;

	/**
	 * Inner class to represent a vertex.
	 * 
	 * Value attribute is the vertex as seen by the applications using the ALGraph.
	 */
	private class Vertex implements Comparable<Vertex> {
		private V value;

		// add destination value for vertex
		private Double distance = Double.MAX_VALUE;
		
		//Tracks the previous vertex
		private Vertex vertexFrom = null;
		
		private Map<V, Edge> adjacencies = new TreeMap<V, Edge>();

		public Vertex(V value) {
			this.value = value;
		}

		public V getValue() {
			return value;
		}

		public void addEdge(Vertex to, W weight) {
			adjacencies.put(to.value, new Edge(this, to, weight));
		}

		public Edge getEdge(V to) {
			return adjacencies.get(to);
		}

		public W getWeight(V to) {
			Edge edge = getEdge(to);

			if (edge == null) {
				return null;
			} else {
				return edge.weight;
			}
		}

		public Iterator<Edge> edges() {
			return adjacencies.values().iterator();
		}

		public Double getDistance() {
			return this.distance;
		}

		public void setDistance(Double distance) {
			this.distance = distance;
		}

		public Vertex getVertexFrom() {
			return this.vertexFrom;
		}

		public void setVertexFrom(Vertex v) {
			this.vertexFrom = v;
		}
		/**
		 * Compares two Vertex 
		 * @param o 
		 */
		@Override
		public int compareTo(ALGraph<V, W>.Vertex o) {
			return Double.compare(this.distance, o.getDistance());
		}
	}

	/**
	 * Inner class to represent an edge.
	 * 
	 * Weight attribute is the weight of the edge as seen by the applications using
	 * the ALGraph.
	 */
	private class Edge {
		private Vertex vertexFrom;
		private Vertex vertexTo;
		
		private W weight;

		public Edge(Vertex vertexFrom, Vertex vertexTo, W weight) {
			super();
			this.vertexFrom = vertexFrom;
			this.vertexTo = vertexTo;
			this.weight = weight;
		}

		public Vertex getVertexFrom() {
			return vertexFrom;
		}

		public Vertex getVertexTo() {
			return vertexTo;
		}

		public W getWeight() {
			return weight;
		}

		public void setWeight(W weight) {
			this.weight = weight;
		}
	}

	private Map<V, Vertex> verticies = new HashMap<V, Vertex>();

	/**
	 * Constructor
	 * 
	 * @param directed - indicates if the graph is directed
	 */
	public ALGraph(boolean directed, boolean weighted) {
		super();
		this.directed = directed;
		this.weighted = weighted;
	}

	/**
	 * Returns true if the graph is directed
	 */
	public boolean isDirected() {
		return directed;
	}

	/**
	 * Returns true if the graph is directed
	 */
	public boolean isWeighted() {
		return weighted;
	}

	/**
	 * Add a vertex to the graph. Value is the the value stored at the vertex.
	 * Values must be unique within the graph.
	 * 
	 * @param value the value stored at the vertex
	 */
	public void addVertex(V value) {
		verticies.put(value, new Vertex(value));
	}

	private Vertex getVertex(V value) {
		return verticies.get(value);
	}

	/**
	 * Add an unweighted edge between two vertices. The values stored is each vertex
	 * is used to identify the vertices. If the graph is undirected then two edges
	 * will be added; an edge from the to vertex to the from vertex will also be
	 * added.
	 * 
	 * @param from value of the from vertex
	 * @param to   value of the to vertex
	 */
	public void addEdge(V from, V to) {
		if (isWeighted()) {
			throw new RuntimeException("Weight must be provided for a weighted graph");
		}

		addEdge(from, to, (W) new Boolean(true));
	}

	/**
	 * Add a weighted edge between two vertices. The values stored is each vertex is
	 * used to identify the vertices. If the graph is undirected then two edges will
	 * be added; an edge from the to vertex to the from vertex will also be added.
	 * 
	 * @param from   value of the from vertex
	 * @param to     value of the to vertex
	 * @param weight the weight stored on the edge
	 */
	public void addEdge(V from, V to, W weight) {
		if (!weighted && !(weight instanceof Boolean)) {
			throw new RuntimeException("Weight must be of type Boolean for unweighted graphs");
		}

		Vertex vertexFrom = getVertex(from);

		if (vertexFrom == null) {
			throw new RuntimeException("Invalid from vertex " + from);
		}

		Vertex vertexTo = getVertex(to);

		if (vertexTo == null) {
			throw new RuntimeException("Invalid to vertex " + to);
		}

		Edge edge = this.getEdge(vertexFrom, vertexTo);

		if (edge != null) {
			edge.setWeight(weight);
			if (!isDirected()) {
				edge = this.getEdge(vertexTo, vertexFrom);
				edge.setWeight(weight);
			}
		} else {
			vertexFrom.addEdge(vertexTo, weight);

			if (!isDirected()) {
				vertexTo.addEdge(vertexFrom, weight);
			}
		}
	}

	/**
	 * Inner class to iterate over the adjacency list of a vertex
	 *
	 */
	private class AdjacencyIterator implements Iterator<V> {
		private Iterator<Edge> edgeIterator;

		public AdjacencyIterator(Vertex from) {
			edgeIterator = from.adjacencies.values().iterator();
		}

		public boolean hasNext() {
			return edgeIterator.hasNext();
		}

		public V next() {
			Edge nextEdge = edgeIterator.next();
			return nextEdge.vertexTo.value;
		}

		public void remove() {
			throw new RuntimeException("Unsupported operation");
		}
	}

	/**
	 * Create an iterator of vertices that are adjacent to a given vertex. The given
	 * vertex is identified by the value of that vertex. The iterator will return
	 * the value of the vertices for which there exists an edge from the from vertex
	 * to a to vertex.
	 * 
	 * @param from the value of the vertex from which to identify adjacent vertices
	 * @return an iterator of vertex values
	 */
	public Iterator<V> adjacentTo(V from) {
		Vertex vertexFrom = getVertex(from);

		if (vertexFrom == null) {
			throw new RuntimeException("Invalid from vertex " + from);
		}

		return new AdjacencyIterator(vertexFrom);
	}

	private Iterator<Edge> edges(V from) {
		Vertex vertexFrom = getVertex(from);

		if (vertexFrom == null) {
			throw new RuntimeException("Invalid from vertex " + from);
		}

		return vertexFrom.edges();
	}

	/**
	 * Get the weight of an edge between two vertices. The vertices are identified
	 * by their values. Returns null if an edge does not exist between the vertices.
	 * 
	 * @param from value of the from vertex
	 * @param to   value of the to vertex
	 * @return
	 */
	public W getWeight(V from, V to) {
		Vertex vertexFrom = getVertex(from);

		if (vertexFrom == null) {
			return null;
		}

		return vertexFrom.getWeight(to);
	}

	private Edge getEdge(Vertex vertexFrom, Vertex to) {
		if (vertexFrom == null) {
			return null;
		}

		return vertexFrom.getEdge(to.value);
	}

	@Override
	public Iterator<V> breadthFirstIterator(V start) {
		return null;
	}

	/**
	 * Return a list containing the path found between two vertices using a breath
	 * first search
	 * 
	 * @param start - starting vertex
	 * @param end   - ending vertex
	 * @return list containing the found path
	 */
	public List<V> breadthFirstSearch(V start, V end) {
		Queue<ArrayList<V>> queue = new LinkedList<ArrayList<V>>();
		Set<V> visited = new HashSet<V>();

		Vertex vertex = this.getVertex(start);

		if (vertex == null)
			throw new RuntimeException("Invalid from Vertex " + start);

		// Add the starting point to the stack
		ArrayList<V> startingPoint = new ArrayList<V>();
		startingPoint.add(vertex.value);
		queue.add(startingPoint);

		// while the stack is not empty
		while (!queue.isEmpty()) {
			// Get item on stack
			ArrayList<V> currentPath = queue.remove();

//			System.out.println("Current Node +> " + currentPath.toString());

			// Get the last element in the path and process
			V current = currentPath.get(currentPath.size() - 1);

			// Check if its no seen or visited
			if (!visited.contains(current)) {

				// Add to set of visited nodes
				visited.add(current);

				if (current.equals(end)) {
					return currentPath;
				}

				// Iterate the edges of the current node and add to stack
				Iterator<V> iter = this.adjacentTo(current);

				while (iter.hasNext()) {
					V edge = iter.next();

//					System.out.println(current + " --> " + edge);

					ArrayList<V> newPaths = (ArrayList<V>) currentPath.clone();
					newPaths.add(edge);

					queue.add(newPaths);
				}
			}

//			System.out.println("\n\n");

		}

//		System.out.println("No Path Found");
		return null;
	}

	/**
	 * Return a list containing the path found between two vertices using a depth
	 * first search
	 * 
	 * @param start - starting vertex
	 * @param end   - ending vertex
	 * @return list containing the found path
	 */
	public List<V> depthFirstSearch(V start, V end) {
		ArrayList<V> path = new ArrayList<V>();
		Stack<ArrayList<V>> stack = new Stack<ArrayList<V>>();

		Set<V> visited = new HashSet<V>();

		Vertex vertex = this.getVertex(start);

		if (vertex == null)
			throw new RuntimeException("Invalid from Vertex " + start);

		// Add the starting point to the stack
		ArrayList<V> startingPoint = new ArrayList<V>();
		startingPoint.add(vertex.value);

		stack.add(startingPoint);

		// while the stack is not empty
		while (!stack.isEmpty()) {
			// Get item on stack
			ArrayList<V> currentPath = stack.pop();

//			System.out.println("Current Node +> " + currentPath.toString());

			// Get the last element in the path and process
			V current = currentPath.get(currentPath.size() - 1);

			// Check if its no seen or visited
			if (!visited.contains(current)) {

				// Add to set of visited nodes
				visited.add(current);

				if (current.equals(end)) {
					return currentPath;
				}

				// Iterate the edges of the current node and add to stack
				Iterator<V> iter = this.adjacentTo(current);

				while (iter.hasNext()) {
					V edge = (V) iter.next();

//					System.out.println(current + " --> " + edge);

					ArrayList<V> newPaths = (ArrayList<V>) currentPath.clone();
					newPaths.add(edge);

					stack.push(newPaths);
				}
			}

//			System.out.println("\n\n");

		}

		return null;
	}

	/**
	 * Return a list containing the path found between two vertices using a best
	 * first search. A Costable is used to get the cost of an edge
	 * 
	 * @param start  - starting vertex
	 * @param end    - ending vertex
	 * @param coster - costable used to the cost of an edge
	 * @return list containing the found path
	 */
	public List<V> bestSearch(V start, V end, Costable<W> costable) {
		Set<V> visited = new HashSet<V>();
		Map<V,Vertex> vms = new HashMap<V,Vertex>();
		Map<V, Double> dest = new HashMap<V, Double>();
		PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();

		this.resetVertices();
		
		Vertex vertex = this.getVertex(start);

		if (vertex == null)
			throw new RuntimeException("Invalid from Vertex " + start);

		//Init starting point distance to 0
		vertex.setDistance(0.0);
		pq.add(vertex);

		while (!pq.isEmpty()) {
			Vertex current = pq.poll();

			Iterator<V> iter = this.adjacentTo(current.value);

			// add to seen vertex
			visited.add(current.value);
			
				while (iter.hasNext()) {
					Vertex node = this.getVertex(iter.next());

					if (!visited.contains(node.value)) {
						//compute distance from node
						double distance = current.getDistance() + costable.getCost(current.getWeight(node.value));
						
						//Check if the current computed distance is
						//lessthan the node distance
						if(distance < node.getDistance()) {
							node.setDistance(distance);
							dest.put(node.value, distance);
							node.setVertexFrom(current);
						}
						
						pq.add(node);//Add the node to the priority queue
						
						vms.put(node.value,node); //Update the list of paths with the new node
				}

			}
		}
		

		//Check if the ending vertex exist in the list of destinations
		if(dest.get(end) == null) return null;
		
		ArrayList<V> path = new ArrayList<V>();
		
		for(Vertex vert= vms.get(end) ;vert!=null;vert=vert.getVertexFrom()){
			path.add(vert.value);
		}
 
		Collections.reverse(path);
		return path;

	}
	

	/**
	 * Return a list containing the path found between two vertices using a best
	 * first search. A Costable is used to get the cost of an edge
	 * 
	 * @param start  - starting vertex
	 * @param end    - ending vertex
	 * @param coster - costable used to the cost of an edge
	 * @return list containing the found path
	 */
	public List<V> aStart(V start, V end, Costable<W> costable) {
		Set<V> visited = new HashSet<V>();
		Map<V,Vertex> vms = new HashMap<V,Vertex>();
		Map<V, Double> dest = new HashMap<V, Double>();
		Map<V, ArrayList<V>> paths = new HashMap<V, ArrayList<V>>();
		PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();

		this.resetVertices();
		
		Vertex vertex = this.getVertex(start);

		if (vertex == null)
			throw new RuntimeException("Invalid from Vertex " + start);

		System.out.println("valu "+ vertex.value);
		System.out.println("distance "+ vertex.getDistance());
		
		vertex.setDistance(0.0);
		pq.add(vertex);

		//Store the current vertex so we can retrieve the last vertex
		Vertex current;
		while (!pq.isEmpty()) {
		 current = pq.poll();

			Iterator<V> iter = this.adjacentTo(current.value);

			// add to seen vertex
			visited.add(current.value);
			System.out.println("Visited "+ current.value);

			
			System.out.println("PQ Size "+ pq.size());
			
				while (iter.hasNext()) {
					Vertex temp = this.getVertex(iter.next());

					if (!visited.contains(temp.value)) {
						
						double distance = current.getDistance() + costable.getCost(current.getWeight(temp.value));
						
						System.out.println("Cost on "+ current.value+ " "+ current.getDistance() +" Cost To " + temp.value +" From " + current.value+" " + costable.getCost(current.getWeight(temp.value)));
						
						if(distance < temp.getDistance()) {
							temp.setDistance(distance);

							dest.put(temp.value, distance);
							temp.setVertexFrom(current);
							
							System.out.println("New Path "+start + " Through "+ temp.value +" => " + current.value + " ==> "
									+ current.getDistance() +" " + distance);

						

						}else {
							System.out.println("Old Path From "+start + " Through  => "+ current.value+" " + temp.value + " ==> "
									+ temp.getDistance() + " "+ distance);

						}

						pq.add(temp);
						vms.put(temp.value,temp);
				}

			}
		}
		

		
		//Check if current exist
		
		
		//Check if the ending vertex exist in the list of destinations
		if(dest.get(end) == null) return null;
		
		ArrayList<V> path = new ArrayList<V>();
		
		for(Vertex vert= vms.get(end) ;vert!=null;vert=vert.getVertexFrom()){
			path.add(vert.value);
		}
 
//		for (V v : dest.keySet()) {
//			System.out.println(v + " cost " + dest.get(v));
//
//		}
		
//		Iterator<Vertex> iter = vms.iterator();
//		
//		while(iter.hasNext()) {
//			Vertex v = iter.next();
//			
//			System.out.println(v.value + " predecessor " + v.getVertexFrom().value);
//
//		}

		Collections.reverse(path);
		return path;

	}
	
	
	
	private void resetVertices() {
		
		for(Vertex v : this.verticies.values()) {
			v.setDistance(Double.MAX_VALUE);
			v.setVertexFrom(null);
		}
		
	}

}
