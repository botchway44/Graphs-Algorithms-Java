package edu.iup.cosc310.graph;

import java.util.Iterator;
import java.util.List;

/**
 * Test program to test given graph cases.
 * 
 * @author dtsmith
 * @author Amma Darkwa
 */
public class TestGraph2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph<Integer, Integer> paths = new ALGraph<Integer, Integer>(true, true);
		paths.addVertex(1);
		paths.addVertex(2);
		paths.addVertex(3);
		paths.addVertex(4);
		paths.addVertex(0);
		paths.addEdge(0, 1, 10);
		paths.addEdge(0, 4, 100); 
		paths.addEdge(1, 2, 50);
		paths.addEdge(2, 3, 20);
		paths.addEdge(3, 4, 60);
		paths.addEdge(2, 4, 10);
		paths.addEdge(0, 3, 30);

		for (Iterator<Integer> node = paths.adjacentTo(0); node.hasNext();) {
			Integer city = node.next();
			Integer distance = paths.getWeight(0, city);
			System.out.println(city + " " + distance  );
		}

		System.out.println("\n\n");

		System.out.println("Breath First Search 0 -> 4");
		List<Integer> pathNodesBFS = paths.breadthFirstSearch(0, 4);
		if (pathNodesBFS == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (Integer node : pathNodesBFS) {
				System.out.print(" " + node);

			}
		}
		System.out.println("\n\n");

		System.out.println("Depth First Search 0 -> 4");
		List<Integer> pathNodesDFS = paths.depthFirstSearch(0, 4);
		if (pathNodesDFS == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (Integer node : pathNodesDFS) {
				System.out.print(" " + node);

			}
		}
		System.out.println("\n\n");

		Costable<Integer> intCost = new Costable<Integer>( ) {
			@Override
			public double getCost(Integer value) {
			return value;
			}
		};
		

		System.out.println("Best Search 0 -> 4");
		List<Integer> cityPathBS1 = paths.bestSearch(0, 4,intCost);
		if (cityPathBS1 == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (Integer node : cityPathBS1) {
				System.out.print(" " + node);
				
			}
		}
		
		System.out.println("\n\n");

		System.out.println("Breath First Search 1 -> 0");
		List<Integer> pathNodesBFS2 = paths.breadthFirstSearch(1, 0);
		if (pathNodesBFS2 == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (Integer node : pathNodesBFS2) {
				System.out.print(" " + node);

			}
		}
		System.out.println("\n\n");

		System.out.println("Depth First Search 1 -> 0");
		List<Integer> pathNodesDFS2 = paths.depthFirstSearch(1, 0);
		if (pathNodesDFS2 == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (Integer node : pathNodesDFS2) {
				System.out.print(" " + node);

			}
		}
		System.out.println("\n\n");


		System.out.println("Best Search 1 -> 0");
		List<Integer> cityPathBS2 = paths.bestSearch(1, 0,intCost);
		if (cityPathBS2 == null) {
			System.out.println("No path found");
		} else {
			System.out.print("Path found:");
			for (Integer node : cityPathBS2) {
				System.out.print(" " + node);
				
			}
		}
		
		System.out.println("\n\n");

		
	}
}
