package org.usfirst.frc.team5015.robot;

import edu.wpi.first.wpilibj.*;

public class Drive {
	
	Talon leftMotor, rightMotor;
	RobotDrive driveTrain;
	AnalogGyro driveGyro;
	
	public Drive(Talon Left_Motor, Talon Right_Motor, AnalogGyro Drive_Gyro)
	{
		leftMotor = Left_Motor;
		rightMotor = Right_Motor;
		driveTrain = new RobotDrive(leftMotor, rightMotor);
		driveGyro = Drive_Gyro;
	}
	
	public void Halo_Drive(double moveValue, double rotateValue)
	{
		driveTrain.arcadeDrive(moveValue, rotateValue);
	}
	
	public boolean gyroTurn(double targetAngle)
	{
		double kp = 0.05;
		double error = driveGyro.getAngle() - targetAngle;
		boolean targetReached = false;
		
		if(Math.abs(error) > 5.0)
		{
			this.Halo_Drive(0.0, kp * error);
			targetReached = false;
		}
		else {
			this.Halo_Drive(0.0, 0.0);
			targetReached = true;
		}
		
		return targetReached;
	}

}
