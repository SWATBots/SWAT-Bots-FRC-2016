package org.usfirst.frc.team5015.robot;

import edu.wpi.first.wpilibj.AnalogGyro;

public class TiltAuto extends AutoMode{

	int currentStep = 1;
	AnalogGyro tiltGyro;
	
	public TiltAuto(AnalogGyro angleGyro)
	{
		tiltGyro = angleGyro;
	}
	
	private double getAngle()
	{
		return -tiltGyro.getAngle();
	}
	
	public void runAuto(double finalTurnAngle)
	{
		switch (currentStep) {
		case 1:
    		super.driveSystem.Halo_Drive(-0.67, -0.05*driveGyro.getAngle());
    		if(this.getAngle() > 10.0)
    		{
    			currentStep++;
    		}
			break;
			
		case 2:
    		super.driveSystem.Halo_Drive(-0.67, -0.05*driveGyro.getAngle());
    		if(this.getAngle() < -10.0)
    		{
    			currentStep++;
    			super.autoTimer.start();
    			super.autoTimer.reset();
    		}
			break;
			
		case 3:
    		//super.driveSystem.Halo_Drive(-0.67, -0.05*driveGyro.getAngle());
			super.driveSystem.Halo_Drive(0.0, 0.0);
    		if(super.autoTimer.get() > 1.0)
    		{
    			currentStep++;
    		}
    		break;
    		
    	default:
    		super.driveSystem.Halo_Drive(0.0, 0.0);
    		super.shooterSystem.setShooterPower(0.0);
    		break;
		}
	}
}
