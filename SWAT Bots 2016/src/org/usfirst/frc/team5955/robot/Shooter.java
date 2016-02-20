package org.usfirst.frc.team5955.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class Shooter {
	public CANTalon leftShooterMotor, rightShooterMotor;
	
	public Shooter(CANTalon leftTalon, CANTalon rightTalon)
	{
		leftShooterMotor = leftTalon;
		rightShooterMotor = rightTalon;
	}
	
	public void setShooterPower(double power)
	{
    	leftShooterMotor.changeControlMode(TalonControlMode.PercentVbus);
    	rightShooterMotor.changeControlMode(TalonControlMode.PercentVbus);

    	leftShooterMotor.set(power);
    	rightShooterMotor.set(power);
	}
}
