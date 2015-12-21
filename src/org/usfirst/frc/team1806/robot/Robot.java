
package org.usfirst.frc.team1806.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import org.usfirst.frc.team1806.robot.commands.utility.IntegrityTestingOff;
import org.usfirst.frc.team1806.robot.commands.utility.IntegrityTestingOn;
import org.usfirst.frc.team1806.robot.subsystems.SixCimTwoSpeed;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static SixCimTwoSpeed drivetrainSS;
	public static OI oi;
	public static SmartDashboardInterface dashboard;
	public static SendableChooser sc;
	Command IntegrityCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	drivetrainSS = new SixCimTwoSpeed();
		oi = new OI();
		dashboard = new SmartDashboardInterface();
		sc = new SendableChooser();
		sc.addDefault("Integrity Testing: Off", new IntegrityTestingOff());
		sc.addObject("Integrity Testing: On", new IntegrityTestingOn());
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        //if (autonomousCommand != null) autonomousCommand.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	
    	//For integrity testing feature.
    	IntegrityCommand = (Command) sc.getSelected();
    	IntegrityCommand.start();
    	
    	
        //if (autonomousCommand != null) autonomousCommand.cancel();
    }

    public void disabledInit(){

    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        oi.update();
        dashboard.updateData();
    }
    
    public void testPeriodic() {
        LiveWindow.run();
    }
}
