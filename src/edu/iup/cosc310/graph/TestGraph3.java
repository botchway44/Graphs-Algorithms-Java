package edu.iup.cosc310.graph;

import java.util.Iterator;
import java.util.List;

/**
 * Test program to test given graph cases.
 * 
 * @author dtsmith
 * @author Amma Darkwa
 */
public class TestGraph3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph<String, Road> cities = new ALGraph<String, Road>(false, true);
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
		cities.addEdge("Chicago", "Ann Arbor", new Road(20.0, 25.0));
		cities.addEdge("Chicago", "Fort Wayne", new Road(20.0, 80.0)); // check
		cities.addEdge("Chicago", "Indianapolis", new Road(20.0, 25.0));
		cities.addEdge("Indianapolis", "Fort Wayne", new Road(20, 80.0)); // check
		cities.addEdge("Detriot", "Ann Arbor", new Road(20.0, 25.0));
		cities.addEdge("Toledo", "Ann Arbor", new Road(20.0, 25.0));
		cities.addEdge("Detriot", "Toledo", new Road(20.0, 25.0));
		cities.addEdge("Cleveland", "Toledo", new Road(20.0, 25.0));
		cities.addEdge("Columbus", "Toledo", new Road(20.0, 25.0));
		cities.addEdge("Indianapolis", "Columbus", new Road(20.0, 80.0)); // check
		cities.addEdge("Cleveland", "Columbus", new Road(20.0, 80.0)); // check
		cities.addEdge("Cleveland", "Pittsburgh", new Road(20.0, 80.0)); // check
		cities.addEdge("Columbus", "Pittsburgh", new Road(20.0, 25.0));
		cities.addEdge("Philadelphia", "Pittsburgh", new Road(20.0, 25.0));
		cities.addEdge("Detriot", "Pittsburgh", new Road(20.0, 25.0));

		for (Iterator<String> iter = cities.adjacentTo("Columbus"); iter.hasNext();) {
			String city = iter.next();
			Road road = cities.getWeight("Columbus", city);
			System.out.println(city + " " + road.getDistance() + " " + road.getTravelTime());
		}

		System.out.println("\n\n");

		System.out.println("Breath First Search");
		List<String> cityPathBFS1 = cities.breadthFirstSearch("Philadelphia", "Chicago");
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
		List<String> cityPathDFS1 = cities.depthFirstSearch("Philadelphia", "Chicago");
		if (cityPathDFS1 == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (String city : cityPathDFS1) {
				System.out.print(" " + city);

			}
		}

		System.out.println("\n\n");

		Costable<Road> roadDistanceCostable = new Costable<Road>() {
			public double getCost(Road road) {
				return road.getDistance();
			}
		};
		
		Costable<Road> roadTimeCostable = new Costable<Road>() {
			public double getCost(Road road) {
				return road.getTravelTime();
			}
		};

		System.out.println("Best Search - Distance");
		List<String> cityPathBSDistance = cities.bestSearch("Philadelphia", "Chicago", roadDistanceCostable);
		if (cityPathBSDistance == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (String city : cityPathBSDistance) {
				System.out.print(" " + city);

			}
		}

		System.out.println("\n\n");

		System.out.println("Best Search - Time Travel");
		List<String> cityPathBSTimeTravel = cities.bestSearch("Philadelphia", "Chicago", roadTimeCostable);
		if (cityPathBSTimeTravel == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (String city : cityPathBSTimeTravel) {
				System.out.print(" " + city);

			}
		}

	}
}
