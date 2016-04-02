package org.usfirst.frc.team5015.robot;

public class TwoPartDriveAuto extends AutoMode {
	double kp, firstDriveTime, secondDriveTime, firstMotorPwr, secondMotorPwr;
	int lastStep;
	
	public TwoPartDriveAuto(String autoName, double gyroProportionalConstant, double initialDriveForwardTime, double secondDriveForwardTime, double initialMotorPower, double finalMotorPower)
	{
		name = autoName;
		kp = gyroProportionalConstant;
		firstDriveTime = initialDriveForwardTime;
		secondDriveTime = secondDriveForwardTime;
		firstMotorPwr = initialMotorPower;
		secondMotorPwr = finalMotorPower;
	}
	

	public void runAuto(double finalTurnAngle)
	{
    	if(autoTimer.get() < firstDriveTime)
    	{
    		lastStep = 1;
    		super.driveSystem.Halo_Drive(firstMotorPwr, -kp*driveGyro.getAngle());
    	}
    	else {
    		if(autoTimer.get() < secondDriveTime)
    		{
    			lastStep = 2;
        		super.driveSystem.Halo_Drive(secondMotorPwr, -kp*driveGyro.getAngle());
    		}
    		else {
    			//If this is the first time that the turning step has been called reset the turning gyro.
    			if(lastStep == 2)
    			{
    				super.driveSystem.driveGyro.reset();
    			}
    			//super.driveSystem.gyroTurn(finalTurnAngle);
    			lastStep = 3;
    		}
    	}
	}
}
