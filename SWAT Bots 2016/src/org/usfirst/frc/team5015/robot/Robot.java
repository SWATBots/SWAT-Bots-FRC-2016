
package org.usfirst.frc.team5015.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	SendableChooser autoSelector;

	Joystick driveStick = new Joystick(0);
	Joystick gunnerStick = new Joystick(1);
	Compressor airCompressor = new Compressor(0);
	Talon leftDrive = new Talon(1);
	Talon rightDrive = new Talon(0);
	AnalogGyro driveGyro = new AnalogGyro(0);
	AnalogGyro tiltGyro = new AnalogGyro(1);
	Drive driveTrain = new Drive(leftDrive, rightDrive, driveGyro);
	Timer autoTimer = new Timer();
	
	DoubleSolenoid intakePosition = new DoubleSolenoid(4, 5);
	VictorSP intakeMotor = new VictorSP(4);
	VictorSP holdingMotor = new VictorSP(5);
	Talon leftOuterIntake = new Talon(2);
	Talon rightOuterIntake = new Talon(3);

	DigitalInput holdingSwitch = new DigitalInput(0);
	Intake intakeMechanism;

	DoubleSolenoid shooterPosition = new DoubleSolenoid(6, 7);

	CANTalon rightShooterWheel = new CANTalon(1);
	CANTalon leftShooterWheel = new CANTalon(2);
	Shooter shooterMechanism = new Shooter(leftShooterWheel, rightShooterWheel, shooterPosition);
	boolean startingRev = true, highGoalSpeed = false, lowGoalSpeed = false;
	boolean shootingSpeed = false, wasFiring = true;
	Timer revUpTimer = new Timer();
		
	Prioritizer holdingMotorPower = new Prioritizer();
	Prioritizer shooterMotorPower = new Prioritizer();
	
	DriveStraightAuto UnevenTerrain = new DriveStraightAuto("Straight over Uneven Terrain", 0.05, 3, -0.67);
	DriveStraightAuto Moat = new DriveStraightAuto("Straight over Moat", 0.05, 3, -0.80);
	DriveStraightAuto Rampart = new DriveStraightAuto("Straight over Rampart", 0.05, 2, -0.80);
	AutoMode Nothing = new AutoMode();
	TwoPartDriveAuto Rockwall = new TwoPartDriveAuto("Straight over the Rockwall", 0.05, 0.85, 3.0, -0.55, -1.0);
	TiltAuto testTiltAuto = new TiltAuto(tiltGyro);
	DriveStraightAuto reachDefense = new DriveStraightAuto("Reach the Defense", 0.05, 2, -0.50);
	AutoMode selectedAuto;
	
		
	double rpmTarget = 0;
	boolean rpmShootingSpeed = false;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	leftOuterIntake.setInverted(true);
    	intakeMotor.setInverted(true);
    	intakeMechanism = new Intake(intakePosition, intakeMotor, leftOuterIntake, rightOuterIntake);
    	driveGyro.calibrate();
    	driveGyro.reset();
    	
    	tiltGyro.calibrate();
    	tiltGyro.reset();
    	
    	leftDrive.setInverted(true);
    	rightDrive.setInverted(true);
    	
    	intakeMechanism.lowerIntake();
    	
    	airCompressor.setClosedLoopControl(true);
    	
        rightShooterWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        rightShooterWheel.enableBrakeMode(false);
        /* set the peak and nominal outputs, 12V means full */
        rightShooterWheel.configNominalOutputVoltage(+0.0f, -0.0f);
        
        
        rightShooterWheel.configPeakOutputVoltage(+0.0f, -12.0f);
        
        
        /* set closed loop gains in slot0 */
        rightShooterWheel.setProfile(0);
        rightShooterWheel.setF(0.024040671);
        rightShooterWheel.setP(1.0);
        rightShooterWheel.setI(0.0); 
        rightShooterWheel.setD(0.0);
        /*rightShooterWheel.setP(0.2063);
        rightShooterWheel.setI(0.000165); 
        rightShooterWheel.setD(1.75);*/
        
        leftShooterWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        leftShooterWheel.reverseSensor(true);
        /* set the peak and nominal outputs, 12V means full */
        leftShooterWheel.configNominalOutputVoltage(+0.0f, -0.0f);
        
        
        leftShooterWheel.configPeakOutputVoltage(+0.0f, -12.0f);
        
        
        /* set closed loop gains in slot0 */
        leftShooterWheel.setProfile(0);
        leftShooterWheel.setF(0.024040671);
        leftShooterWheel.setP(1.0);
        leftShooterWheel.setI(0.0); 
        leftShooterWheel.setD(0.0);
        //leftShooterWheel.setP(0.2063);
        //leftShooterWheel.setI(0.000165); 
        //leftShooterWheel.setD(1.75);
        
    	intakeMechanism.raiseIntake();

    	autoSelector = new SendableChooser();
    	Nothing.setName("Do nothing");
    	testTiltAuto.setName("Test Tilt Auto EXPERIMENTAL DO NOT USE!!!");
    	autoSelector.addObject(Nothing.getName(), Nothing);
    	autoSelector.addDefault(Moat.getName(), Moat);
    	autoSelector.addObject(Rampart.getName(), Rampart);
    	autoSelector.addDefault(UnevenTerrain.getName(), UnevenTerrain);
    	autoSelector.addObject(Rockwall.getName(), Rockwall);
    	autoSelector.addObject(testTiltAuto.getName(), testTiltAuto);
    	SmartDashboard.putData("Auto Selector", autoSelector);
    }

    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	intakeMechanism.raiseIntake();
    	selectedAuto = (AutoMode) autoSelector.getSelected();
    	selectedAuto.initializeAuto(driveTrain, shooterMechanism, intakeMechanism, autoTimer, driveGyro);
    }


    public void autonomousPeriodic() {
    	selectedAuto.runAuto(0.0);
    }

    public void teleopInit() {
    	intakeMechanism.raiseIntake();
    }
    /**
     * This function is called periodically during operator control
     */
	public void teleopPeriodic() {
		
		rpmShootingSpeed = false;
		
		if(driveStick.getRawButton(5) || driveStick.getRawButton(7) || driveStick.getRawButton(8))
		{
			driveTrain.setMaxSpeed(0.75);
		}
		else {
			driveTrain.setMaxSpeed(1.0);
		}
		
        driveTrain.Halo_Drive(driveStick.getRawAxis(1), driveStick.getRawAxis(2));
        
        rpmTarget = 0;
        //Raise and lower the intake mechanism.
        if(gunnerStick.getRawButton(5)) {
        	if(shooterMechanism.isShooterUp() == false)
        	{
            	intakeMechanism.raiseIntake();
        	}
        }
        else {
        	if(gunnerStick.getRawAxis(2) >= 0.5) {
        		intakeMechanism.lowerIntake();
        	}
        }
        
        //Raise and lower the shooter mechanism.
        if(gunnerStick.getRawButton(6)) {
        	if(intakeMechanism.isIntakeUp() == false)
        	{
        		shooterMechanism.raiseShooter();
        	}
        }
        else {
        	if(gunnerStick.getRawAxis(3) >= 0.5) {
        		shooterMechanism.lowerShooter();
        	}
        }

                
        //Press forward on the POV to fire the shooter
        if(gunnerStick.getPOV() == 0)
        {
        	holdingMotorPower.addPriority(0.50, 3);
        	wasFiring = true;
        }
        else {
        	holdingMotorPower.addPriority(0.0, 0);
        	if(wasFiring == true)
        	{
        		this.stopLowSpeed();
        		wasFiring = false;
        	}
        }

        
    	//Allow the intake mechanism to run if the holding switch is not pressed.
        if(gunnerStick.getPOV() == 180 && holdingSwitch.get() == true){
          intakeMechanism.runIntake();
          shooterMotorPower.addPriority(-0.68, 2);
          holdingMotorPower.addPriority(-0.70, 2);
        }
        else {
        	if(Math.abs(gunnerStick.getRawAxis(1)) > 0.1)
        	{
        		//Manual joystick control of the intake and shooter is only allowed if the ball is being shot out
        		//or if the boulder has not yet reached the switch.
        		
        		boolean canPower = false;
        		if(-gunnerStick.getRawAxis(1) > 0)
        		{
        			canPower = true;
        		}
        		else {
        			if(this.isBallLoaded() == false)
        			{
        				canPower = true;
        			}
        		}
        		
        		if(gunnerStick.getRawButton(9) == false)
        		{
        			canPower = false;
        		}
        		
        		if(canPower)
        		{
        			 intakeMechanism.runIntake(gunnerStick.getRawAxis(1));
                     shooterMotorPower.addPriority(-gunnerStick.getRawAxis(1), 1);
                     holdingMotorPower.addPriority(-gunnerStick.getRawAxis(1), 1);
        		}
        		else{
                	intakeMechanism.stopIntake();
                	shooterMotorPower.addPriority(0.00, 0);
                	holdingMotorPower.addPriority(0.0, 0);	
        		}

        	}
        	else {
            	intakeMechanism.stopIntake();
            	shooterMotorPower.addPriority(0.00, 0);
            	holdingMotorPower.addPriority(0.0, 0);
        	}


        }
        
        
      //Press the "back" button to stop the shooter wheels.
        if(gunnerStick.getRawButton(7))
        {
        	this.stopShooter();
        }
        
        //Rev up the shooter wheels.
        if(gunnerStick.getRawButton(4))
        {
        	highGoalSpeed = true;
        }
        
        if(gunnerStick.getRawButton(1))
        {
        	lowGoalSpeed = true;
        }
        
        
        if(lowGoalSpeed || highGoalSpeed)
        {
        	
        	if(highGoalSpeed)
        	{
            	shooterMotorPower.addPriority(1.0, 3); //*-6233 to change to RPM
            	rpmTarget = -4400.0;
        	}
        	else {
            	shooterMotorPower.addPriority(0.50, 3);
            	rpmTarget = -2655;
        	}
        	        	
        	if(revUpTimer.get() >= 4.5)
        	{
        		shootingSpeed  = true;

        	}
        	else {
        		shootingSpeed = false;
        	}
        	
        	if(shooterMechanism.absSpeedAbove(rpmTarget))
            {
            	rpmShootingSpeed = true;
            }
            else {
            	rpmShootingSpeed = false;
            }
        	
        	 	
        }
        else {
        	startingRev = true;
        	revUpTimer.start();
        	revUpTimer.reset();
        }
        
        holdingMotor.set(holdingMotorPower.getHighestPriorityValue());
        holdingMotorPower.resetPrioritizer();
        
        shooterMechanism.setShooterPower(shooterMotorPower.getHighestPriorityValue());
        
        
        shooterMotorPower.resetPrioritizer();
        
        //SmartDashboard.putBoolean(" Firing Speed", shootingSpeed);
		SmartDashboard.putBoolean(" Ball Loaded", this.isBallLoaded());
		SmartDashboard.putBoolean(" Firing Speed", shootingSpeed);
		SmartDashboard.putBoolean(" RPM Firing Speed", rpmShootingSpeed);
		SmartDashboard.putNumber("Tilt Angle", tiltGyro.getAngle());
		SmartDashboard.putNumber("Turn Angle", driveGyro.getAngle());
    }
    
	
    @Override
    public void disabledPeriodic() {
    	
    	SmartDashboard.putData("Auto Selector", autoSelector);
    	selectedAuto = (AutoMode) autoSelector.getSelected();
    	SmartDashboard.putString("Selected Auto Mode", selectedAuto.getName());
    }
    
    public boolean isBallLoaded()
    {
    	return !holdingSwitch.get();
    }
    
    public void stopShooter()
    {
    	highGoalSpeed = false;
    	lowGoalSpeed = false;
    }
    
    public void stopLowSpeed()
    {
    	lowGoalSpeed = false;
    }
    
}
