package org.usfirst.frc.team1806.robot;


import org.usfirst.frc.team1806.robot.commands.AutoShiftToHigh;
import org.usfirst.frc.team1806.robot.commands.AutoShiftToLow;
import org.usfirst.frc.team1806.robot.commands.ParkingBrake;

import util.Latch;

import java.lang.Math;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private XboxController dc = new XboxController(0);

	private double lsY;
	// private double lsY;
	private double rsX;
	// private double rsY;
	private double rt;
	private double lt;
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
		buttonA = dc.getButtonA();
		buttonBack = dc.getButtonBack();
		// arcade drive, with deazone

		if (Robot.drivetrainSS.driverControl){
			if (Math.abs(lsY) > .15) {
				if (Math.abs(rsX) > .15) {
					Robot.drivetrainSS.arcadeDrive(lsY, rsX);
				} // end both out of deadzone
				else {
					// only power is out of deadzone
					Robot.drivetrainSS.arcadeDrive(lsY, 0);
				} // end only turn is out of deadzone
			} // end turn is out of deadzone
			else if (Math.abs(rsX) > .15) {
				// only turn is out of deadzone
				Robot.drivetrainSS.arcadeDrive(0, rsX);
			} // end only turn is out of deadzone
			else if (buttonA){
				new ParkingBrake().start();
			}
			else {
				Robot.drivetrainSS.arcadeDrive(0, 0);
			}
		}
		//enable or disable automatic shifting based on back button
		if (Robot.drivetrainSS.isAutoShiftActive() && disableAutoShift.update(buttonBack)){
			Robot.drivetrainSS.disableAutoShift();
		}
		if((!Robot.drivetrainSS.isAutoShiftActive())&& disableAutoShift.update(buttonBack)){
			Robot.drivetrainSS.enableAutoShift();
		}
		if(lt > .15 && !Robot.drivetrainSS.isAutoShiftActive()){
			//shift to low manually
			new AutoShiftToLow().start();
		}
		else if(rt > .15 && !Robot.drivetrainSS.isAutoShiftActive()){
			new AutoShiftToHigh().start();
		}
	}
}
