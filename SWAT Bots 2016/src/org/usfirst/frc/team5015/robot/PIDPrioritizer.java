package org.usfirst.frc.team5015.robot;

public class PIDPrioritizer {
	/**
	 *Based on the original Prioritizer this prioritizer is designed to have a boolean enablePID value which is
	 *recorded entered along with the double value and the integer priority.
	 *This was designed so that multiple subsystems could determine which had a higher priority to access a
	 *robot subsystem in particular a speed controller. The value and the enablePID entered with the highest
	 *priority level will be saved and returned. This is intended so that a program can enable and disable
	 *PID on a speed controller depending on the function which the speed controller is currently being used by.
	 */
	private int highestPriorityLevel = 0;
	private double topPriorityValue = 0.0;
	private boolean PIDControlEnabled = false;
	
	public PIDPrioritizer()
	{
		this.resetPrioritizer();
	}
	
	public void resetPrioritizer()
	{
		this.highestPriorityLevel = 0;
		this.topPriorityValue = 0.0;
		this.PIDControlEnabled = false;
	}
	
	public void addPriority(double value, boolean enablePID, int priorityLevel)
	{
		if(priorityLevel > this.highestPriorityLevel)
		{
			this.highestPriorityLevel = priorityLevel;
			this.topPriorityValue = value;
			this.PIDControlEnabled = enablePID;
		}
	}
	
	public double getHighestPriorityValue()
	{
		return topPriorityValue;
	}
	
	public boolean getPIDState()
	{
		return PIDControlEnabled;
	}
}
