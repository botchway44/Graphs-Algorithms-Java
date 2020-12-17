package edu.iup.cosc310.graph;

import java.util.Iterator;
import java.util.List;

/**
 * An abstract data type for a generic graph.
 * @author dtsmith
 *
 * @param <V> data type for the vertices of the graph
 * @param <W> data type for the weight of edges of a graph.  Use Boolean for an unweighted graph
 */
public interface Graph<V, W> {
	/**
	 * Identifies if the the graph is directed.
	 * 
	 * @return true if the graph is directed, otherwise false.
	 */
	public boolean isDirected();
	
	/**
	 * Identifies if the the graph is weighted.
	 * 
	 * @return true if the graph is directed, otherwise false.
	 */
	public boolean isWeighted();
	
	/**
	 * Add a vertex to the graph.  Value is the the value stored at the vertex. Values must be
	 * unique within the graph.
	 * 
	 * @param value the value stored at the vertex
	 */
	public void addVertex(V value);	
	
	/**
	 * Add an unweighted edge between two vertices.  The values stored is each vertex is used to identify
	 * the vertices.  If the graph is undirected then two edges will be added; an edge from the to vertex
	 * to the from vertex will also be added.
	 * 
	 * @param from value of the from vertex
	 * @param to value of the to vertex
	 */
	public void addEdge(V from, V to); 
	
	/**
	 * Add a weighted edge between two vertices.  The values stored is each vertex is used to identify
	 * the vertices.  If the graph is undirected then two edges will be added; an edge from the to vertex
	 * to the from vertex will also be added.
	 * 
	 * @param from value of the from vertex
	 * @param to value of the to vertex
	 * @param weight the weight stored on the edge
	 */
	public void addEdge(V from, V to, W weight); 
	
	/**
	 * Create an iterator of vertices that are adjacent to a given vertex.  The given vertex is 
	 * identified by the value of that vertex.  The iterator will return the value of the vertices
	 * for which there exists an edge from the from vertex to a to vertex.
	 * 
	 * @param from the value of the vertex from which to identify adjacent vertices
	 * @return an iterator of vertex values
	 */
	public Iterator<V> adjacentTo(V from);
	
	/**
	 * Get the weight of an edge between two vertices.  The vertices are identified by their values.
	 * Returns null if an edge does not exist between the vertices.
	 * 
	 * @param from value of the from vertex
	 * @param to value of the to vertex
	 * @return the weight of the edge if defined, otherwise null
	 */
	public W getWeight(V from, V to);
	
	/**
	 * Create an iterator to return the vertices from a start vertex in breadth first order.
	 * 
	 * @param start the value of the vertex from which to identify adjacent vertices
	 * @return an iterator of vertex values in breath first order
	 */
	public Iterator<V> breadthFirstIterator(V start);
	
	/**
	 * Return a list containing the path found between two vertices using a breadth first search
	 * @param start - starting vertex
	 * @param end - ending vertex
	 * @return list containing the found path
	 */
	public List<V> breadthFirstSearch(V start, V end);

	/**
	 * Return a list containing the path found between two vertices using a depth first search
	 * @param start - starting vertex
	 * @param end - ending vertex
	 * @return list containing the found path
	 */
	public List<V> depthFirstSearch(V start, V end);
	
	/**
	 * Return a list containing the path found between two vertices using a best first search. 
	 * A Costable is used to get the cost of an edge
	 * @param start - starting vertex
	 * @param end - ending vertex
	 * @param coster - costable used to the cost of an edge
	 * @return list containing the found path
	 */
	public List<V> bestSearch(V start, V end, Costable<W> coster);


}
