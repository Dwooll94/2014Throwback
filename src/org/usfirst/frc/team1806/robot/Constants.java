package org.usfirst.frc.team1806.robot;

public class Constants {
	//Amperage Limits
	public final static int max3CimAmps = 200;
	public final static int max2CimAmps = 250;
	public final static int max1CimAmps = 300;
	
	//drivetrain encoder constants
	//lmao bruh did you memorize this number
	//it's almost as long as pi
	public final static double drivetrainInchesPerCount = 0.04908738521234051935097880286374;
	
	
	//TODO: Tweak shifting values
	//drive shifting constants
	public final static double drivetrainShiftWaitTime = .15;
	
	//TODO: Does it need a different power to shift up vs shifting down??
	public final static double drivetrainShiftPower = 0; //power with which the motors are given while shifting
	public final static double drivetrainAccelerationThreshold = .02; // applied for determining actual accel for shifting
	//a "Power" downshift is one in which the speed is low but applied power is high
	public final static double drivetrainPowerDownshiftPowerThreshold = .8 ; //power must be over this
	public final static double drivetrainPowerDownshiftSpeedThreshold = 6.0; //speed must be below this
	//a normal downshift is when the robot is more than likely coming to rest
	public final static double drivetrainDownshiftPowerThreshold = .2; // power must be less than this
	public final static double drivetrainDownshiftSpeedThreshold = 4; //speed must be less than this
	
	//normal upshift that would occur when going from a stop to full speed
	public final static double drivetrainUpshiftPowerThreshold = .9; //power must be more than this
	public final static double drivetrainUpshiftSpeedThreshold = 6.0; //speed must be more than this
	public final static double drivetrainMaxLowGearSpeed = 6.75; //exceding this speed will force an autoshift to high gear.
	
	
	//drive forward PID values
	public final static double brakepidP = 0.1;
	public final static double brakepidI = 0.1;
	public final static double brakepidD = 0;
	
	//the drivetrain execute loop will send a warning to the console if it doesn't run in the specified period.
	public final static double drivetrainExecuteWarnTime = .25;
	
	
}
