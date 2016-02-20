package org.usfirst.frc.team5955.robot;

import edu.wpi.first.wpilibj.*;

public class Drive {
	
	Talon leftMotor, rightMotor;
	RobotDrive driveTrain;
	
	public Drive(Talon Left_Motor, Talon Right_Motor)
	{
		leftMotor = Left_Motor;
		rightMotor = Right_Motor;
		driveTrain = new RobotDrive(leftMotor, rightMotor);
	}
	
	public void Halo_Drive(double moveValue, double rotateValue)
	{
		driveTrain.arcadeDrive(moveValue, rotateValue);
	}

}
