package org.usfirst.frc.team5015.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Shooter {
	private CANTalon leftShooterMotor, rightShooterMotor;
	private DoubleSolenoid shooterPosition;
	private boolean shooterUp = false;
	private boolean pidEnabled = false;
	
	public Shooter(CANTalon leftTalon, CANTalon rightTalon, DoubleSolenoid positionSolenoid)
	{
		leftShooterMotor = leftTalon;
		rightShooterMotor = rightTalon;
		shooterPosition = positionSolenoid;
	}
	
	public void setShooterPower(double power)
	{
		pidEnabled = false;
		
    	leftShooterMotor.changeControlMode(TalonControlMode.PercentVbus);
    	rightShooterMotor.changeControlMode(TalonControlMode.PercentVbus);

    	leftShooterMotor.set(-power);
    	rightShooterMotor.set(-power);
    	
    	System.out.println(leftShooterMotor.getSpeed() + "       "+rightShooterMotor.getSpeed());
	}
	
	@Deprecated
	public void setShooterPID(double target)
	{
		pidEnabled = true;
		if(leftShooterMotor.getControlMode() != TalonControlMode.Speed)
		{
		    leftShooterMotor.changeControlMode(TalonControlMode.Speed);
		    rightShooterMotor.changeControlMode(TalonControlMode.Speed);
		}

		leftShooterMotor.set(target);
		rightShooterMotor.set(target);
    	System.out.println(target + "  "+leftShooterMotor.getClosedLoopError() + "  "+rightShooterMotor.getClosedLoopError());
    	System.out.println("     "+leftShooterMotor.getOutputVoltage()/leftShooterMotor.getBusVoltage() + "  "+rightShooterMotor.getOutputVoltage()/rightShooterMotor.getBusVoltage());

	}
	
	public boolean absSpeedAbove(double speed)
	{
		boolean leftIsReady = false, rightIsReady = false, bothAreReady = false;
		if(Math.abs(leftShooterMotor.getSpeed()) > Math.abs(speed)) {
			leftIsReady = true;
		}
		
		if(Math.abs(rightShooterMotor.getSpeed()) > Math.abs(speed)) {
			rightIsReady = true;
		}
		
		if(rightIsReady && leftIsReady)
		{
			bothAreReady = true;
		}
		
		return bothAreReady;
	}
	
	public boolean onTarget()
	{
		/*System.out.print(rightShooterMotor.getOutputVoltage() / rightShooterMotor.getBusVoltage());
		System.out.print("       ");
		System.out.println(rightShooterMotor.getClosedLoopError());*/
		
		if(pidEnabled)
		{
			if(leftShooterMotor.getClosedLoopError() > 100 && rightShooterMotor.getClosedLoopError() > 100)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void raiseShooter()
	{
		shooterUp = true;
		shooterPosition.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void lowerShooter()
	{
		shooterUp = false;
    	shooterPosition.set(DoubleSolenoid.Value.kForward);
	}
	
	public boolean isShooterUp()
	{
		return shooterUp;
	}
}
