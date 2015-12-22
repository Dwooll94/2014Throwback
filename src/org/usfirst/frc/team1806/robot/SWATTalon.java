package org.usfirst.frc.team1806.robot;

import edu.wpi.first.wpilibj.Talon;

public class SWATTalon extends Talon{

	private int pdpChannel;
	private int deadCycles = 0;
	public boolean dead = false;
	
	public SWATTalon(int channel, int PDPChannel) {
		super(channel);
		this.pdpChannel = PDPChannel;
	}
	
	public void integrityTest(){
		if(this.get() > Constants.motorMinPower && Robot.drivetrainSS.PDP.getCurrent(pdpChannel) > Constants.motorMinCurrent){
			//Sending power but not drawing current
			deadCycles++;
		}else{
			//Sending power and drawing current
			deadCycles = 0;
		}
	}
	
	public boolean isDead(){
		
		if(deadCycles >= Constants.cyclesUntilDead){
			dead = true;
		}else{
			dead = false;
		}
		
		return dead;
	}

}
