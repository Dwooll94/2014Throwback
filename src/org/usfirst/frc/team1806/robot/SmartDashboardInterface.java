package org.usfirst.frc.team1806.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardInterface {

	public void init(){
		
	}
	
	public void updateData(){
		SmartDashboard.putNumber("Power", Robot.drivetrainSS.getPower());
		SmartDashboard.putNumber("Turn", Robot.drivetrainSS.getTurn());
		SmartDashboard.putNumber("Drive Distance", Robot.drivetrainSS.getDriveDistance());
		SmartDashboard.putNumber("Drive Speed (ft/s)", Robot.drivetrainSS.getDriveSpeedFPS());
		SmartDashboard.putBoolean("High Gear?", Robot.drivetrainSS.isInHighGear());
		SmartDashboard.putNumber("Total Amperage", Robot.drivetrainSS.getAmperage());
		SmartDashboard.putNumber("Number of CIMs Running Per Side", Robot.drivetrainSS.getCimsRunningPerSide());
		SmartDashboard.putBoolean("AutoShift:", Robot.drivetrainSS.isAutoShiftActive());
		SmartDashboard.putNumber("Acceleration (ft/s/s)", Robot.drivetrainSS.getDriveAccelFPSPS());
	
	}
	
}
