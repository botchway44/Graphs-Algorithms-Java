package edu.iup.cosc310.graph;

import java.util.Iterator;
import java.util.List;

/**
 * Test program to test given graph cases.
 * 
 * @author dtsmith
 *
 */
public class TestGraph {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph<String, Integer> cities = new ALGraph<String, Integer>(false, true);
		cities.addVertex("Chicago");
		cities.addVertex("Ann Arbor");
		cities.addVertex("Detriot");
		cities.addVertex("Toledo");
		cities.addVertex("Cleveland");
		cities.addVertex("Pittsburgh");
		cities.addVertex("Philadelphia");
		cities.addVertex("Columbus");
		cities.addVertex("Indianapolis");
		cities.addVertex("Fort Wayne");
		cities.addEdge("Chicago", "Ann Arbor", 260);
		cities.addEdge("Chicago", "Fort Wayne", 148);
		cities.addEdge("Chicago", "Indianapolis", 180);
		cities.addEdge("Indianapolis", "Fort Wayne", 120);
		cities.addEdge("Detriot", "Ann Arbor", 50);
		cities.addEdge("Toledo", "Ann Arbor", 40);
		cities.addEdge("Detriot", "Toledo", 60);
		cities.addEdge("Cleveland", "Toledo", 120);
		cities.addEdge("Columbus", "Toledo", 155);
		cities.addEdge("Indianapolis", "Columbus", 180);
		cities.addEdge("Cleveland", "Columbus", 150);
		cities.addEdge("Cleveland", "Pittsburgh", 130);
		cities.addEdge("Columbus", "Pittsburgh", 180);
		cities.addEdge("Philadelphia", "Pittsburgh", 180);
		cities.addEdge("Detriot", "Pittsburgh", 400);
		
		for (Iterator<String> iter = cities.adjacentTo("Columbus"); iter.hasNext();) {
			String city = iter.next();
			Integer distance = cities.getWeight("Columbus", city);
			System.out.printf("%s %d\n", city, distance);
		}
		

		System.out.println("\n\n");

		System.out.println("Breath First Search");
		List<String> cityPathBFS1 = cities.breadthFirstSearch("Philadelphia", "Detriot");
		if (cityPathBFS1 == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (String city : cityPathBFS1) {
				System.out.print(" " + city);
				
			}
		}
		System.out.println("\n\n");



		System.out.println("Depth First Search");
		List<String> cityPathDFS1 = cities.depthFirstSearch("Philadelphia", "Detriot");
		if (cityPathDFS1 == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (String city : cityPathDFS1) {
				System.out.print(" " + city);
				
			}
		}
		
		System.out.println("\n\n");

		Costable<Integer> intCost = new Costable<Integer>( ) {
			@Override
			public double getCost(Integer value) {
			return value;
			}
		};
		

		System.out.println("Best Search");
		List<String> cityPathBS1 = cities.bestSearch("Philadelphia", "Detriot",intCost);
		if (cityPathBS1 == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (String city : cityPathBS1) {
				System.out.print(" " + city);
				
			}
		}
		System.out.println("\n\n");

		System.out.println("Breath First Search");
		List<String> cityPathBFS2 = cities.breadthFirstSearch("Fort Wayne", "Toledo");
		if (cityPathBFS2 == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (String city : cityPathBFS2) {
				System.out.print(" " + city);
				
			}
		}
		System.out.println("\n\n");



		System.out.println("Depth First Search");
		List<String> cityPathDFS2 = cities.depthFirstSearch("Fort Wayne", "Toledo");
		if (cityPathDFS2 == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (String city : cityPathDFS2) {
				System.out.print(" " + city);
				
			}
		}

		
		System.out.println("\n\n");

		System.out.println("Best Search");
		List<String> cityPathBS2 = cities.bestSearch("Fort Wayne", "Toledo", intCost);
		if (cityPathBS2 == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (String city : cityPathBS2) {
				System.out.print(" " + city);
				
			}
		}
	}
}
