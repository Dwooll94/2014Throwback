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

	
	//Initialize Left CIMs
	//TODO Find CIM Port Numbers
	public final static int leftCim0 = 0;
	public final static int leftCim1 = 1;
	public final static int leftCim2 = 2;
	
	public final static int rightCim0 = 3;
	public final static int rightCim1 = 4;
	public final static int rightCim2 = 5;
	//Initialize Encoders
	//TODO Find Encoder Port Numbers
	public final static int leftEncoderA = 0;
	public final static int leftEncoderB = 1;
	public final static int rightEncoderA = 2;
	public final static int rightEncoderB = 3;
	
	//Initialize Shifting
	//TODO Find shift solenoid port number
	public final static int shiftSolenoidA = 0;
	public final static int shiftSolenoidB = 0;
	
	//Initialize PDP
}
