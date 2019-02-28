/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public abstract class BasicController extends PIDSubsystem {
  protected SpeedController motor;
  
/*public BasicController(SpeedController motor){
   this.motor = motor;
}*/

public BasicController( String subsystemName, double pGain, double iGain, double  dGain){
   super(subsystemName, pGain, iGain, dGain);
}

  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public void move (double speed){
    motor.set(speed);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public abstract void activate();

  public abstract void deactivate();
}
