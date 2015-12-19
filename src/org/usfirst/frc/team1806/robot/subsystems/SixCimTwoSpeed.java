package org.usfirst.frc.team1806.robot.subsystems;


import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.RobotMap;
import org.usfirst.frc.team1806.robot.commands.AutoShiftToHigh;
import org.usfirst.frc.team1806.robot.commands.AutoShiftToLow;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SixCimTwoSpeed extends Subsystem {
	//init hardware
	private Talon leftDrive0;
	private Talon leftDrive1;
	private Talon leftDrive2;
	
	private Encoder leftEncoder;
	
	private Talon rightDrive0;
	private Talon rightDrive1;
	private Talon rightDrive2;
	
	private Encoder rightEncoder;
	
	private DoubleSolenoid shifter;
	
	private PowerDistributionPanel PDP;
	//init software variables
	private double power;
	private double turn;
	private int cimsRunningPerSide;
	
	public boolean driverControl = true;

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
		//initialize left side CIMs
		leftDrive0 = new Talon(RobotMap.leftCim0);
		leftDrive1 = new Talon(RobotMap.leftCim1);
		leftDrive2 = new Talon(RobotMap.leftCim2);
		//initialize left side Encoder
		leftEncoder = new Encoder(RobotMap.leftEncoderA, RobotMap.leftEncoderB);
		
		
		//initialize right side CIMs
		rightDrive0 = new Talon(RobotMap.rightCim0);
		rightDrive1 = new Talon(RobotMap.rightCim1);
		rightDrive2 = new Talon(RobotMap.rightCim2);
		//initialize right side Encoder
		rightEncoder = new Encoder(RobotMap.rightEncoderA, RobotMap.rightEncoderB);
		
		//initialize shifter
		shifter = new DoubleSolenoid(RobotMap.shiftSolenoidA, RobotMap.shiftSolenoidB);
		//initialize PDP
		PDP = new PowerDistributionPanel();
		
		//initialize Software values
		power = 0;
		turn = 0;
		cimsRunningPerSide = 3;
	}
	//Private Methods for setting the left and right drive all at once
	private void setLeftDrive(double speed, int numCimsRunning){
		//clip speed to [-1, 1]
		if(speed > 1){
			speed = 1;
		}
		if (speed < -1){
			speed = -1;
		}
		//set power
		if(numCimsRunning <1){
			leftDrive0.set(0);
			leftDrive1.set(0);
			leftDrive2.set(0);
		}//end if numCimsRunning is less than 1
		else if(numCimsRunning == 1){
			leftDrive0.set(speed);
			leftDrive1.set(0);
			leftDrive2.set(0);
		}/// end 1 CIM running
		else if (numCimsRunning == 2){
			leftDrive0.set(speed);
			leftDrive1.set(speed);
			leftDrive2.set(0);			
		}// end 2 CIMS running
		else if (numCimsRunning > 2){
			leftDrive0.set(speed);
			leftDrive1.set(speed);
			leftDrive2.set(speed);			
		}
		else{
			System.out.println("Something has gone horribly wrong with setLeftDrive.");
		}
	}//end setLeftDrive
	
	private void setRightDrive(double speed, int numCimsRunning){
		//clip speed to [-1, 1]
		if(speed > 1){
			speed = 1;
		}
		if (speed < -1){
			speed = -1;
		}
		if(numCimsRunning <1){
			rightDrive0.set(0);
			rightDrive1.set(0);
			rightDrive2.set(0);
		}//end if numCimsRunning is less than 1
		else if(numCimsRunning == 1){
			rightDrive0.set(speed);
			rightDrive1.set(0);
			rightDrive2.set(0);
		}/// end 1 CIM running
		else if (numCimsRunning == 2){
			rightDrive0.set(speed);
			rightDrive1.set(speed);
			rightDrive2.set(0);			
		}// end 2 CIMS running
		else if (numCimsRunning > 2){
			rightDrive0.set(speed);
			rightDrive1.set(speed);
			rightDrive2.set(speed);		
		}
		else{
			System.out.println("Something has gone horribly wrong with setRightDrive.");
		}
	}//end setRightDrive
	
	
	//Methods for getting encoder values
	public double getDriveDistance(){
		//returns an the average distance of the left and right encoders
		return ((leftEncoder.getDistance() + rightEncoder.getDistance()) / 2)/ Constants.drivetrainInchesPerCount;
	}
	public double getLeftDistance(){
		return leftEncoder.getDistance() / Constants.drivetrainInchesPerCount;
	}
	public double getRightDistance(){
		return rightEncoder.getDistance() / Constants.drivetrainInchesPerCount;
	}
	public double getDriveSpeed(){
		//returns the average speed of the left and right side in inches per second
		return (( leftEncoder.getRate() + rightEncoder.getRate() ) / 2)/ Constants.drivetrainInchesPerCount;
	}
	public double getDriveSpeedFPS(){
		return getDriveSpeed()/12;
	}
	
	//Methods for getting and setting user input
	public void arcadeDrive(double newPower, double newTurn){
		power = newPower;
		turn = newTurn;		
	}
	
	public double getPower(){
		return power;
	}
	
	public double getTurn(){
		return turn;
	}
	
	//methods for getting diagnostic data
	public double getAmperage(){
		return PDP.getTotalCurrent();
	}
	
	public int getCimsRunningPerSide(){
		return cimsRunningPerSide;
	}
	
	public void shiftLow(){
		power = 0;
		turn = 0;
		shifter.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void shiftHigh(){
		power = 0;
		turn = 0;
		shifter.set(DoubleSolenoid.Value.kForward);
	}
	
	public boolean isInLowGear(){
		return shifter.get() == DoubleSolenoid.Value.kReverse;
	}
	
	public boolean isInHighGear(){
		return shifter.get() == DoubleSolenoid.Value.kForward;
	}
	
	public void execute(){
		if (PDP.getTotalCurrent() > Constants.max1CimAmps){
			//Stop drivetrain to avoid brown out
			cimsRunningPerSide = 0;

		}///end stop drivetrain to avoid brown out
		else if (PDP.getTotalCurrent() > Constants.max2CimAmps){
			//Run only 1 cim to avoid brown out
			cimsRunningPerSide = 1;
		}//end run 1 cim
		else if (PDP.getTotalCurrent() > Constants.max3CimAmps){
			//Run 2 cims to avoid brown out
			cimsRunningPerSide = 2;
		}//end run 2 cims
		else{
			//ALL SYSTEMS, FULL POWER
			cimsRunningPerSide = 3;
		}//end 3 cims, also end deciding how many CIMs to run
		//shift and set power as needed
		if(Math.abs(getDriveSpeedFPS()) > 6.0 && isInLowGear()){
			new AutoShiftToHigh().start();
		}
		else if(Math.abs(getDriveSpeedFPS()) < 4.0 && isInHighGear()){
			new AutoShiftToLow().start();
		}
		else{
			setLeftDrive(power + turn, cimsRunningPerSide);
			setRightDrive(power - turn, cimsRunningPerSide);
		}

		
	}
}
