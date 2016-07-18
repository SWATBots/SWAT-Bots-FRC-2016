package org.usfirst.frc.team5015.robot;

import edu.wpi.first.wpilibj.*;

public class Drive {
	
	Talon leftMotor, rightMotor;
	RobotDrive driveTrain;
	AnalogGyro driveGyro;
	private double maxSpeed = 1.0;
	
	public Drive(Talon Left_Motor, Talon Right_Motor, AnalogGyro Drive_Gyro)
	{
		leftMotor = Left_Motor;
		rightMotor = Right_Motor;
		driveTrain = new RobotDrive(leftMotor, rightMotor);
		driveGyro = Drive_Gyro;
	}
	
	public void controlDrive(double moveValue, double rotateValue)
	{
		driveTrain.arcadeDrive(moveValue * maxSpeed, rotateValue * maxSpeed);
	}
	
	public void setMaxSpeed(double maximumSpeed)
	{
		maxSpeed = maximumSpeed;
	}
	
	public boolean gyroTurn(double targetAngle)
	{
		double kp = -0.05;
		double error = driveGyro.getAngle() - targetAngle;
		boolean targetReached = false;
		
		if(Math.abs(error) > 5.0)
		{
			this.controlDrive(0.0, kp * error);
			targetReached = false;
		}
		else {
			this.controlDrive(0.0, 0.0);
			targetReached = true;
		}
		
		return targetReached;
	}

}
