package org.usfirst.frc.team5015.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AnalogGyro;

public class AutoMode {
	
	Intake intakeSystem;
	Shooter shooterSystem;
	Drive driveSystem;
	Timer autoTimer;
	AnalogGyro driveGyro;
	String name;
	
	public void initializeAuto(Drive driveTrain, Shooter shooter, Intake intake, Timer autonomousTimer, AnalogGyro gyro)
	{
		intakeSystem = intake;
		shooterSystem = shooter;
		driveSystem = driveTrain;
		autoTimer = autonomousTimer;
		driveGyro = gyro;
		
    	driveGyro.reset();
    	autoTimer.reset();
    	autoTimer.start();
	}
	
	public void runAuto()
	{
		
	}
	
	public String getName()
	{
		return name;
	}
}
