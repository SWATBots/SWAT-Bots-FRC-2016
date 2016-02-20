
package org.usfirst.frc.team5955.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	Joystick driveStick = new Joystick(0);
	Joystick gunnerStick = new Joystick(1);
	Compressor airCompressor = new Compressor(0);
	Talon leftDrive = new Talon(1);
	Talon rightDrive = new Talon(0);
	Drive driveTrain = new Drive(leftDrive, rightDrive);
	
	DoubleSolenoid intakePosition = new DoubleSolenoid(4, 5);
	Talon intakeMotor = new Talon(3);
	Talon holdingMotor = new Talon(2);
	Intake intakeMechanism = new Intake(intakePosition, intakeMotor);

	DoubleSolenoid shooterPosition = new DoubleSolenoid(6, 7);

	CANTalon rightShooterWheel = new CANTalon(1);
	CANTalon leftShooterWheel = new CANTalon(2);
	Shooter shooterMechanism = new Shooter(leftShooterWheel, rightShooterWheel);
	boolean startingRev = true, highGoalSpeed = false, lowGoalSpeed = false, shooting = false;
	Timer revUpTimer = new Timer();
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	leftDrive.setInverted(true);
    	rightDrive.setInverted(true);
    	
    	intakeMechanism.lowerIntake();
    	
    	airCompressor.setClosedLoopControl(true);;
    	
        rightShooterWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        rightShooterWheel.enableBrakeMode(false);
        rightShooterWheel.setInverted(true);
        /* set the peak and nominal outputs, 12V means full */
        rightShooterWheel.configNominalOutputVoltage(+0.0f, -0.0f);
        rightShooterWheel.configPeakOutputVoltage(+12.0f, 0.0f);
        /* set closed loop gains in slot0 */
        rightShooterWheel.setProfile(0);
        rightShooterWheel.setF(0.024040671);
        rightShooterWheel.setP(0.2063);
        rightShooterWheel.setI(0.000165); 
        rightShooterWheel.setD(1.75);
        
        leftShooterWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        /* set the peak and nominal outputs, 12V means full */
        leftShooterWheel.configNominalOutputVoltage(+0.0f, -0.0f);
        leftShooterWheel.configPeakOutputVoltage(+12.0f, -12.0f);
        /* set closed loop gains in slot0 */
        leftShooterWheel.setProfile(0);
        leftShooterWheel.setF(0.024040671);
        leftShooterWheel.setP(0.2063);
        leftShooterWheel.setI(0.000165); 
        leftShooterWheel.setD(1.75);
        
        
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
    
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    public void teleopInit() {
    	intakeMechanism.lowerIntake();
    }
    /**
     * This function is called periodically during operator control
     */
	public void teleopPeriodic() {
        driveTrain.Halo_Drive(driveStick.getRawAxis(1), driveStick.getRawAxis(2));
        
        //Raise and lower the intake mechanism.
        if(gunnerStick.getRawButton(5)) {
        	intakeMechanism.raiseIntake();
        }
        else {
        	if(gunnerStick.getRawAxis(2) >= 0.5) {
        		intakeMechanism.lowerIntake();
        	}
        }
        
        //Raise and lower the shooter mechanism.
        if(gunnerStick.getRawButton(6)) {
        	shooterPosition.set(DoubleSolenoid.Value.kForward);
        }
        else {
        	if(gunnerStick.getRawAxis(3) >= 0.5) {
            	shooterPosition.set(DoubleSolenoid.Value.kReverse);
        	}
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
            	shooterMechanism.setShooterPower(1.0);
        	}
        	else {
            	shooterMechanism.setShooterPower(0.40);
        	}
        	
        	if(startingRev == true)
        	{
            	revUpTimer.start();
        	}
        	
        	startingRev = false;
        	
        	if(revUpTimer.get() > 5.5)
        	{
        		SmartDashboard.putBoolean("Firing", true);
        		holdingMotor.set(0.45);
        	}
        	else {
        		SmartDashboard.putBoolean("Firing", false);
        	}
        	
        	if(revUpTimer.get() > 6.5)
        	{
        		highGoalSpeed = false;
        		lowGoalSpeed = false;
        	}
        }
        else {
        	//Allow the intake mechanism to run if the shooter is not active.
              if(gunnerStick.getPOV() == 180){
                intakeMechanism.runIntake();
                shooterMechanism.setShooterPower(-0.40);
                holdingMotor.set(-0.45);
              }
              else {
              	intakeMechanism.stopIntake();
            	shooterMechanism.setShooterPower(0.00);
        		holdingMotor.set(0.0);
              }
            
        	startingRev = true;
        	revUpTimer.stop();
        	revUpTimer.reset();
    		SmartDashboard.putBoolean("Firing", false);
        }
               
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
