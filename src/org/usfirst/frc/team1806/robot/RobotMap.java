package org.usfirst.frc.team1806.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
// For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public final int leftMotor = 1;
    // public final int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public final int rangefinderPort = 1;
    // public final int rangefinderModule = 1;
	//Intialize Drivetrain current limits
	//TODO Find CIM PDP numbers
	
	//Initialize Left CIMs
	public final static int leftCim0 = 0;
	public final static int leftCim1 = 1;
	public final static int leftCim2 = 2;
	
	public final static int leftCim0PDP = 0;
	public final static int leftCim1PDP = 0;
	public final static int leftCim2PDP = 0;
	//Initialize Right CIMs 
	//
	public final static int rightCim0 = 3;
	public final static int rightCim1 = 4;
	public final static int rightCim2 = 5;
	
	public final static int rightCim0PDP = 0;
	public final static int rightCim1PDP = 0;
	public final static int rightCim2PDP = 0;
	//Initialize Encoders
	public final static int leftEncoderA = 0;
	public final static int leftEncoderB = 1;
	public final static int rightEncoderA = 2;
	public final static int rightEncoderB = 3;
	
	//Initialize Shifting
	public final static int shiftSolenoidA = 0;
	public final static int shiftSolenoidB = 0;
	
	//Initialize PDP
}
