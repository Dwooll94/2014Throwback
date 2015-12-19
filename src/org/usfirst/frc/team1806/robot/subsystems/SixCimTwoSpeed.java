package org.usfirst.frc.team1806.robot.subsystems;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.RobotMap;
import org.usfirst.frc.team1806.robot.commands.AutoShiftToHigh;
import org.usfirst.frc.team1806.robot.commands.AutoShiftToLow;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SixCimTwoSpeed extends Subsystem {
	// init hardware
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

	// init software variables
	private double power;
	private double turn;
	private int cimsRunningPerSide;
	private boolean autoShift;
	public boolean driverControl = true;
	private double currentSpeed;
	private double lastSpeed;
	// CIM stress tracking variables
	private Timer timer = new Timer();
	private double lastTrackedTime;
	private double period;

	public SixCimTwoSpeed() {

		// initialize left side CIMs
		leftDrive0 = new Talon(RobotMap.leftCim0);
		leftDrive1 = new Talon(RobotMap.leftCim1);
		leftDrive2 = new Talon(RobotMap.leftCim2);
		// initialize left side Encoder
		leftEncoder = new Encoder(RobotMap.leftEncoderA, RobotMap.leftEncoderB);

		// initialize right side CIMs
		rightDrive0 = new Talon(RobotMap.rightCim0);
		rightDrive1 = new Talon(RobotMap.rightCim1);
		rightDrive2 = new Talon(RobotMap.rightCim2);
		// initialize right side Encoder
		rightEncoder = new Encoder(RobotMap.rightEncoderA, RobotMap.rightEncoderB);

		// initialize shifter
		shifter = new DoubleSolenoid(RobotMap.shiftSolenoidA, RobotMap.shiftSolenoidB);
		// initialize PDP
		PDP = new PowerDistributionPanel();

		// initialize Software values
		power = 0;
		turn = 0;
		cimsRunningPerSide = 3;
		autoShift = true;
		currentSpeed = 0;
		lastSpeed = 0;

		// initialize time tracking
		timer.start();
		lastTrackedTime = 0;

	}

	protected void initDefaultCommand() {

	}

	// Private Methods for setting the left and right drive all at once
	private void setLeftDrive(double speed, int numCimsRunning) {
		// clip speed to [-1, 1]
		if (speed > 1) {
			speed = 1;
		}else if (speed < -1) {
			speed = -1;
		}
		// set power
		if (numCimsRunning < 1) {
			leftDrive0.set(0);
			leftDrive1.set(0);
			leftDrive2.set(0);
		} // end if numCimsRunning is less than 1
		else if (numCimsRunning == 1) {
			leftDrive0.set(speed);
			leftDrive1.set(0);
			leftDrive2.set(0);
		} /// end 1 CIM running
		else if (numCimsRunning == 2) {
			leftDrive0.set(speed);
			leftDrive1.set(speed);
			leftDrive2.set(0);
		} // end 2 CIMS running
		else if (numCimsRunning > 2) {
			leftDrive0.set(speed);
			leftDrive1.set(speed);
			leftDrive2.set(speed);
		} else {
			System.out.println("Something has gone horribly wrong with setLeftDrive.");
			leftDrive0.set(0);
			leftDrive1.set(0);
			leftDrive2.set(0);
		}
	}// end setLeftDrive

	private void setRightDrive(double speed, int numCimsRunning) {
		// clip speed to [-1, 1]
		if (speed > 1) {
			speed = 1;
		}
		if (speed < -1) {
			speed = -1;
		}
		if (numCimsRunning < 1) {
			rightDrive0.set(0);
			rightDrive1.set(0);
			rightDrive2.set(0);
		} // end if numCimsRunning is less than 1
		else if (numCimsRunning == 1) {
			rightDrive0.set(speed);
			rightDrive1.set(0);
			rightDrive2.set(0);
		} /// end 1 CIM running
		else if (numCimsRunning == 2) {
			rightDrive0.set(speed);
			rightDrive1.set(speed);
			rightDrive2.set(0);
		} // end 2 CIMS running
		else if (numCimsRunning > 2) {
			rightDrive0.set(speed);
			rightDrive1.set(speed);
			rightDrive2.set(speed);
		} else {
			System.out.println("Something has gone horribly wrong with setRightDrive.");
			leftDrive0.set(0);
			leftDrive1.set(0);
			leftDrive2.set(0);
		}
	}// end setRightDrive
		// private method for determining when to shift

	private void shiftAutomatically() {
		// shifts if neccessary, returns whether shifting was done
		if (getDriveSpeedFPS() > Constants.drivetrainUpshiftSpeedThreshold
				&& Math.abs(power) > Constants.drivetrainUpshiftPowerThreshold && isSpeedingUp() && isInLowGear()) {
			// Normal Upshift
			// if fast enough to need to upshift, driver is applying sufficient
			// throttle, the robot is speeding up and it's in low gear, upshift
			new AutoShiftToHigh().start();
		} else if (getDriveSpeedFPS() > Constants.drivetrainMaxLowGearSpeed && isInLowGear()) {
			// the rev limiter was hit because driver wasn't hitting the
			// throttle hard enough to change gear
			new AutoShiftToHigh().start();
		} else if (getDriveSpeedFPS() < Constants.drivetrainPowerDownshiftSpeedThreshold
				&& Math.abs(power) > Constants.drivetrainPowerDownshiftPowerThreshold && isSlowingDown()
				&& isInHighGear()) {
			// if the robot is slowing down while the driver is applying
			// sufficient power, and is at a reasonable speed to be in low gear,
			// downshift.
			// Think of a pushing match that started at high speed
			new AutoShiftToLow().start();
		} else if (getDriveSpeedFPS() < Constants.drivetrainDownshiftSpeedThreshold
				&& Math.abs(power) < Constants.drivetrainDownshiftPowerThreshold && isInHighGear()) {
			// if the robot is slowing down, not being given considerable
			// throttle
			// a coasting/stopping downshift
			new AutoShiftToLow().start();
		}
	}

	// Methods for getting encoder values
	public double getDriveDistance() {
		// returns an the average distance of the left and right encoders
		return ((leftEncoder.getDistance() + rightEncoder.getDistance()) / 2) / Constants.drivetrainInchesPerCount;
	}

	public double getLeftDistance() {
		return leftEncoder.getDistance() / Constants.drivetrainInchesPerCount;
	}

	public double getRightDistance() {
		return rightEncoder.getDistance() / Constants.drivetrainInchesPerCount;
	}

	public double getDriveVelocity() {
		// returns the average speed of the left and right side in inches per
		// second
		return ((leftEncoder.getRate() + rightEncoder.getRate()) / 2) / Constants.drivetrainInchesPerCount;
	}

	public double getDriveVelocityFPS() {
		return getDriveSpeed() / 12;
	}

	public double getDriveSpeed() {
		return Math.abs(getDriveVelocity());
	}

	public double getDriveSpeedFPS() {
		return Math.abs(getDriveVelocityFPS());
	}

	public double getDriveAccelFPSPS() {
		return (currentSpeed - lastSpeed) / period;
	}

	public boolean isSpeedingUp() {
		return getDriveAccelFPSPS() > Constants.drivetrainAccelerationThreshold;
	}

	public boolean isSlowingDown() {
		return getDriveAccelFPSPS() < Constants.drivetrainAccelerationThreshold;
	}

	// Methods for getting and setting user input
	public void arcadeDrive(double newPower, double newTurn) {
		power = newPower;
		turn = newTurn;
	}

	public double getPower() {
		return power;
	}

	public double getTurn() {
		return turn;
	}

	// methods for getting diagnostic data
	public double getAmperage() {
		return PDP.getTotalCurrent();
	}

	public int getCimsRunningPerSide() {
		return cimsRunningPerSide;
	}

	// methods regarding shifting
	public void shiftLow() {
		// TODO: Is killing the power needed?
		power = 0;
		turn = 0;
		shifter.set(DoubleSolenoid.Value.kReverse);
	}

	public void shiftHigh() {
		power = 0;
		turn = 0;
		shifter.set(DoubleSolenoid.Value.kForward);
	}

	public boolean isInLowGear() {
		return shifter.get() == DoubleSolenoid.Value.kReverse;
	}

	public boolean isInHighGear() {
		return shifter.get() == DoubleSolenoid.Value.kForward;
	}

	public void disableAutoShift() {
		autoShift = false;
	}

	public void enableAutoShift() {
		autoShift = true;
	}

	public boolean isAutoShiftActive() {
		return autoShift;
	}

	public void execute(double pPower, double pTurn) {

		// Sets desired power and turn.
		// The p stands for parameter :)
		// #MASTERCODER
		power = pPower;
		turn = pTurn;

		// Warn console if loop hasn't been run recently.
		period = timer.get() - lastTrackedTime;
		if ((period) > Constants.drivetrainExecuteWarnTime) {
			System.out.println("SixCimTwoSpeed execute() hasn't run in:" + (timer.get() - lastTrackedTime));
		}
		lastTrackedTime = timer.get();

		lastSpeed = currentSpeed;
		currentSpeed = getDriveSpeedFPS();

		if (PDP.getTotalCurrent() > Constants.max1CimAmps) {
			// Stop drivetrain to avoid brown out
			cimsRunningPerSide = 0;

		} /// end stop drivetrain to avoid brown out
		else if (PDP.getTotalCurrent() > Constants.max2CimAmps) {
			// Run only 1 cim to avoid brown out
			cimsRunningPerSide = 1;
		} // end run 1 cim
		else if (PDP.getTotalCurrent() > Constants.max3CimAmps) {
			// Run 2 cims to avoid brown out
			cimsRunningPerSide = 2;
		} // end run 2 cims
		else {
			// ALL SYSTEMS, FULL POWER
			// ^ tru
			cimsRunningPerSide = 3;
		} // end 3 cims, also end deciding how many CIMs to run
			// shiftAutomatically and set power
		if (autoShift) {
			shiftAutomatically();
		}

		setLeftDrive(power + turn, cimsRunningPerSide);
		setRightDrive(power - turn, cimsRunningPerSide);

	}
}
