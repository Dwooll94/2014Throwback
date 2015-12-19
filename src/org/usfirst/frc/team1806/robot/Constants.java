package org.usfirst.frc.team1806.robot;

public class Constants {
	//Amperage Limits
	public final static int max3CimAmps = 200;
	public final static int max2CimAmps = 250;
	public final static int max1CimAmps = 300;
	
	//drivetrain encoder consts
	public final static double drivetrainInchesPerCount = 0.04908738521234051935097880286374;
	
	//drive shifting consts
	public final static double drivetrainShiftWaitTime = .25;
	public final static double drivetrainShiftPower = 0; //power with which the motors are given while shifting
	//a "Power" downshift is one in which the speed is low but applied power is high
	public final static double drivetrainPowerDownshiftPowerThreshold = .8 ; //power must be over this
	public final static double drivetrainPowerDownshiftSpeedThreshold = 6.0; //speed must be below this
	//a normal downshift is when the robot is more than likely coming to rest
	public final static double drivetrainDownshiftPowerThreshold = .2; // power must be less than this
	public final static double drivetrainDownshiftSpeedThreshold = 2;
	public final static double drivetrainUpshiftPowerThreshold = .9;
	public final static double drivetrainUpshiftSpeedThreshold = 6.0;
	
	
	//drive forward PID values
	public final static double drivepidP = 0.1;
	public final static double drivepidI = 0.1;
	public final static double drivepidD = 0;
	
	//the drivetrain execute loop will send a warning to the console if it doesn't run in the specified period.
	public final static double drivetrainExecuteWarnTime = .25;
	
	
}
