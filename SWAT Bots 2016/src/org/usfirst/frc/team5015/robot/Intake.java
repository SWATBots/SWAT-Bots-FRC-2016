package org.usfirst.frc.team5015.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Talon;

public class Intake {
	
	DoubleSolenoid intakePosition;
	VictorSP intakeMotor;
	Talon leftOuterWheel, rightOuterWheel;
	boolean intakeUp = true;
	
	public Intake(DoubleSolenoid intakeSolenoid, VictorSP intakeTalon, Talon leftWheel, Talon rightWheel)
	{
		this.intakePosition = intakeSolenoid;
		this.intakeMotor = intakeTalon;
		this.leftOuterWheel = leftWheel;
		this.rightOuterWheel = rightWheel;
	}
	
	public void raiseIntake()
	{
		intakeUp = true;
		intakePosition.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void lowerIntake()
	{
		intakeUp = false;
		intakePosition.set(DoubleSolenoid.Value.kForward);
	}
	
	public boolean isIntakeUp()
	{
		return intakeUp;
	}
	
	public void runIntake()
	{
		this.runIntake(1.0);
	}
	
	public void runIntake(double speed)
	{
		intakeMotor.set(speed);
		leftOuterWheel.set(speed);
		rightOuterWheel.set(speed);
	}
	
	public double getLeftWheelSpeed()
	{
		return leftOuterWheel.get();
	}
	
	public double getRightWheelSpeed()
	{
		return rightOuterWheel.get();
	}
	
	public void stopIntake()
	{
		this.runIntake(0.0);
	}

}
