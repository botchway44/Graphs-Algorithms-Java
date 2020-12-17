package edu.iup.cosc310.graph;
/**
 * Road Object that stores the distance and speed limit
 * @author Amma Darkwa
 */
public class Road {

	private double distance;
	private double speed_limit;
	
	/**
	 * Road Constructor
	 * @param distance
	 * @param speed_limit
	 */
	public Road(double distance, double speed_limit) {
		this.distance = distance;
		this.speed_limit = speed_limit;
	}
	
	/**
	 * Returns the distance traveled
	 * @return the distance traveled
	 */
	public double getDistance() {
		return this.distance;
	}
	
	/**
	 * Returns the time traveled 
	 * @return the distance per time traveled
	 */
	public double getTravelTime() {
		return this.distance/this.speed_limit;
	}
}
