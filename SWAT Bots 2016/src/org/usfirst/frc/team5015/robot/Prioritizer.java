package org.usfirst.frc.team5015.robot;


public class Prioritizer {
	/**
	 * This class is designed to accept double value and a integer priority. 
	 * It will then record the value inputed with the highest priority.
	 * This system is especially good when a single piece (such as a speed controller) has multiple sections of 
	 * code trying to control it. This system ensures that only the most important input signal is used.
	 */
	private int highestPriorityLevel = 0;
	private double topPriorityValue = 0.0;
	
	public Prioritizer()
	{
		this.resetPrioritizer();
	}
	
	public void resetPrioritizer()
	{
		this.highestPriorityLevel = 0;
		this.topPriorityValue = 0.0;
	}
	
	public void addPriority(double value, int priorityLevel)
	{
		if(priorityLevel > this.highestPriorityLevel)
		{
			this.highestPriorityLevel = priorityLevel;
			this.topPriorityValue = value;
		}
	}
	
	public double getHighestPriorityValue()
	{
		return topPriorityValue;
	}
}
