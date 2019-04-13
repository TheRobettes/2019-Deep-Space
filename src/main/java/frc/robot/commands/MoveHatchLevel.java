/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.HatchLifter;

public class MoveHatchLevel extends Command {
  private double targetHeight;
  private double previousAngle;

  public MoveHatchLevel(double targetHeight) {
    requires(Robot.hatch);
    this.targetHeight = targetHeight;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  public static void logHatchLevel(String movementType){
    String warningMessage = movementType + " Hatch Position " + HatchLifter.getHatchPosition();
    DriverStation.reportWarning(warningMessage, false);

  }
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.hatch.enable();
    previousAngle = Robot.hatch.getPosition();
    logHatchLevel("beginning automatic preset");

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() { //TODO:test these and see if they are necessary or can be avoided with better I and D vlues 
    if(targetHeight == 75 && previousAngle <= 55) { // for practice robo - middle angle = 120, low angle (with range) = 108;
      targetHeight = 60; //practice was (115) 
    }

    else if(targetHeight == 75 && previousAngle >= 100) { //for practice robo - high angle (with range) = 131; (for aisha used to be 118 & 176)
      targetHeight = 95; //practice was (124) (for Aisha used to be 126.7)

    }

    Robot.hatch.setSetpoint(targetHeight);

    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Robot.hatch.onTarget())
      previousAngle = targetHeight;

    return Robot.hatch.onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.hatch.disable();
    logHatchLevel("ending automatic preset");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.hatch.disable();
  }
}
