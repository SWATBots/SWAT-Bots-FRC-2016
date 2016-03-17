package org.usfirst.frc.team5015.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Shooter {
	private CANTalon leftShooterMotor, rightShooterMotor;
	private DoubleSolenoid shooterPosition;
	private boolean shooterUp = false;
	
	public Shooter(CANTalon leftTalon, CANTalon rightTalon, DoubleSolenoid positionSolenoid)
	{
		leftShooterMotor = leftTalon;
		rightShooterMotor = rightTalon;
		shooterPosition = positionSolenoid;
	}
	
	public void setShooterPower(double power)
	{
    	leftShooterMotor.changeControlMode(TalonControlMode.PercentVbus);
    	rightShooterMotor.changeControlMode(TalonControlMode.PercentVbus);

    	leftShooterMotor.set(-power);
    	rightShooterMotor.set(-power);
	}
	
	public void raiseShooter()
	{
		shooterUp = true;
		shooterPosition.set(DoubleSolenoid.Value.kForward);
	}
	
	public void lowerShooter()
	{
		shooterUp = false;
    	shooterPosition.set(DoubleSolenoid.Value.kReverse);
	}
	
	public boolean isShooterUp()
	{
		return shooterUp;
	}
}
