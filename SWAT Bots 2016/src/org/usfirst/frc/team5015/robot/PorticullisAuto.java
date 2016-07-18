package org.usfirst.frc.team5015.robot;

public class PorticullisAuto extends AutoMode {
	/**
	 * An experimental auto mode designed so that the robot will drive backwards to release its passive 
	 * porticullis mechanism. Then the robot drives backwards under the porticullis.
	 */
	double kp;
	int lastStep;
	
	public PorticullisAuto(String autoName, double gyroProportionalConstant)
	{
		name = autoName;
		kp = gyroProportionalConstant;
	}
	

	public void runAuto(double finalTurnAngle)
	{
		super.intakeSystem.lowerIntake();
    	if(autoTimer.get() < 0.2)
    	{
    		lastStep = 1;
    		super.driveSystem.controlDrive(1.0, -kp*driveGyro.getAngle());
    	}
    	else {
    		if(autoTimer.get() < 0.7)
    		{
    			lastStep = 2;
        		super.driveSystem.controlDrive(0.0, 0.0);
    		}
    		else {
    			if(autoTimer.get() < 6)
    			{
    	    		super.driveSystem.controlDrive(0.6, -kp*driveGyro.getAngle());
    			}
    			else {
            		super.driveSystem.controlDrive(0.0, 0.0);
            		System.out.println("Stopped");
    			}
    		}
    	}
	}
}
