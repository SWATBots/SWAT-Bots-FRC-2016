package org.usfirst.frc.team5015.robot;

public class DriveStraightAuto extends AutoMode{
	
	double kp, driveTime, motorPwr;
	int lastStep = 0;
	
	public DriveStraightAuto(String autoName, double gyroProportionalConstant, double driveForwardTime, double motorPower)
	{
		name = autoName;
		kp = gyroProportionalConstant;
		driveTime = driveForwardTime;
		motorPwr = motorPower;
	}
	
	
	public void runAuto(double finalTurnAngle)
	{
    	if(autoTimer.get() < driveTime)
    	{
    		lastStep = 1;
    		super.driveSystem.Halo_Drive(motorPwr, -kp*driveGyro.getAngle());
    	}
    	else {
    		if(lastStep == 1)
    		{
    			super.driveSystem.driveGyro.reset();
    		}
    		//super.driveSystem.gyroTurn(finalTurnAngle);
    		lastStep = 2;
    	}
	}
}
