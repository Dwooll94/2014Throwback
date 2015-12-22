package org.usfirst.frc.team1806.robot;


import edu.wpi.first.wpilibj.Joystick.RumbleType;
import util.Latch;

import java.awt.Label;
import java.lang.Math;

import org.usfirst.frc.team1806.robot.commands.drivetrain.AutoShiftToHigh;
import org.usfirst.frc.team1806.robot.commands.drivetrain.AutoShiftToLow;
import org.usfirst.frc.team1806.robot.commands.drivetrain.ParkingBrake;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public XboxController dc = new XboxController(0);

	private double lsY;
	// private double lsY;
	private double rsX;
	// private double rsY;
	private double rt;
	private double lt;
	private boolean rb;
	private boolean lb;
	public boolean buttonA;
	public boolean buttonBack;
	private Latch disableAutoShift;
	public OI() {
		disableAutoShift = new Latch();
		// NOTHING
	}

	public void update() {
		lsY = dc.getLeftJoyY();
		// lsY = dc.getLeftJoyY();
		rsX = dc.getRightJoyX();
		// rsY = dc.getRightJoyY();
		rt = dc.getRightTrigger();
		lt = dc.getLeftTrigger();
		rb = dc.getButtonRB();
		lb = dc.getButtonLB();
		
		buttonA = dc.getButtonA();
		buttonBack = dc.getButtonBack();
		// arcade drive, with deadzone

		if (Robot.drivetrainSS.driverControl){
			
			/*
			 * Low gear lock
			 */
			if(lt > .5){
				Robot.drivetrainSS.lowGearLockEnable();
			}else{
				Robot.drivetrainSS.lowGearLockDisable();
			}
			
			
			/*
			 * Drivetrain things
			 */
			
			if (Math.abs(lsY) > Constants.xboxJoystickDeadzone) {
				if (Math.abs(rsX) > Constants.xboxJoystickDeadzone) {
					Robot.drivetrainSS.execute(lsY, rsX);					
				} // end both out of deadzone
				else {
					// only power is out of deadzone
					Robot.drivetrainSS.execute(lsY, 0);
				} // end only turn is out of deadzone
			} // end turn is out of deadzone
			else if (Math.abs(rsX) > Constants.xboxJoystickDeadzone) {
				// only turn is out of deadzone
				Robot.drivetrainSS.execute(0, rsX);
			} // end only turn is out of deadzone
			else if (buttonA){
				new ParkingBrake().start();
			}
			else {
				Robot.drivetrainSS.execute(0, 0);
			}
			
			//rumbles based on what gear the drivetrain is in
			if(Robot.drivetrainSS.isInLowGear()){
				dc.setRumble(RumbleType.kRightRumble, (float) .35);
			}else{
				dc.setRumble(RumbleType.kRightRumble, (float) .7);
			}
			
		}
		
		//enable or disable automatic shifting based on back button
		if(disableAutoShift.update(buttonBack)){
			if(Robot.drivetrainSS.isAutoShiftActive()){
				Robot.drivetrainSS.disableAutoShift();
			}else{
				Robot.drivetrainSS.enableAutoShift();
			}
		}
		
		/*if (Robot.drivetrainSS.isAutoShiftActive() && disableAutoShift.update(buttonBack)){
			Robot.drivetrainSS.disableAutoShift();
		}
		if((!Robot.drivetrainSS.isAutoShiftActive())&& disableAutoShift.update(buttonBack)){
			Robot.drivetrainSS.enableAutoShift();
		}*/
		
		if(lb && !Robot.drivetrainSS.isAutoShiftActive()){
			//shift to low manually
			new AutoShiftToLow().start();
		}
		else if(rb && !Robot.drivetrainSS.isAutoShiftActive()){
			new AutoShiftToHigh().start();
		}
	}
}
