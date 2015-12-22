package org.usfirst.frc.team1806.robot.commands.drivetrain;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoShiftToHigh extends Command {

	Timer timer = new Timer();
	
    public AutoShiftToHigh() {
        requires(Robot.drivetrainSS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.drivetrainSS.shiftHigh();
        Robot.drivetrainSS.arcadeDrive(Constants.drivetrainShiftPower, 0);
        Robot.drivetrainSS.driverControl = false;
        timer.start();
        oi.shifting = true;
        oi.dc.setRumble(RumbleType.kLeftRumble, (float) 1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//this time is how long the driver won't have control
    	//it is so the gearbox can shift,  because it needs to not be powered when it shifts
        return timer.get() >= Constants.drivetrainShiftWaitTime;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.drivetrainSS.driverControl = true;
        oi.shifting = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        Robot.drivetrainSS.driverControl = true;
        oi.shifting = false;
    }
}
