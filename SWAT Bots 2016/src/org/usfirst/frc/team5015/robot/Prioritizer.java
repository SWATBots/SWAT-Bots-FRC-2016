package org.usfirst.frc.team5015.robot;


public class Prioritizer {
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
