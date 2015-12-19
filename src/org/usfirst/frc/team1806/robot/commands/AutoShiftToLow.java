package org.usfirst.frc.team1806.robot.commands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoShiftToLow extends Command {
	
	Timer timer = new Timer();
    
	public AutoShiftToLow() {
        requires(Robot.drivetrainSS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.drivetrainSS.shiftLow();
        //TODO: see if setting power to zero actually lets the robot shift
        Robot.drivetrainSS.arcadeDrive(0, 0);
        Robot.drivetrainSS.driverControl = false;
        timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() >= Constants.drivetrainShiftWaitTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrainSS.driverControl = true;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrainSS.driverControl = true;
    }
}
