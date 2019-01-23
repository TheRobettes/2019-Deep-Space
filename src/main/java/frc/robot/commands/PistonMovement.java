/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.PistonController;

public class PistonMovement extends Command {
  private PistonController targetSubsystem;
  public static final boolean extend = true;
  public static final boolean retract = false;
  private boolean direction;
  public PistonMovement(PistonController targetSubsystem, boolean direction) {
    this.direction = direction;
    this.targetSubsystem = targetSubsystem;
    requires(targetSubsystem);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("I'm going to start to give info about pistons");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(direction) 
    this.targetSubsystem.extend();
   
   else {
    this.targetSubsystem.retract();
   } 

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("Piston is not still going");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    System.out.println("Piston has been interrupted");
    if(!direction) 
    this.targetSubsystem.extend();
   
    else {
      this.targetSubsystem.retract();
    }
   
  }
}
