package org.usfirst.frc.team5955.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;

public class Intake {
	
	DoubleSolenoid intakePosition;
	Talon intakeMotor;
	
	public Intake(DoubleSolenoid intakeSolenoid, Talon intakeTalon)
	{
		this.intakePosition = intakeSolenoid;
		this.intakeMotor = intakeTalon;
	}
	
	public void raiseIntake()
	{
		intakePosition.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void lowerIntake()
	{
		intakePosition.set(DoubleSolenoid.Value.kForward);
	}
	
	public void runIntake()
	{
		this.runIntake(-0.75);
	}
	
	public void runIntake(double speed)
	{
		intakeMotor.set(speed);
	}
	
	public void stopIntake()
	{
		intakeMotor.set(0.0);
	}

}
