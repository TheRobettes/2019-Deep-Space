/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CompassDriving extends Command {
  protected double direction;
  protected double speed;
  public static final int noGyro = -1;

  public CompassDriving(double direction, double speed) {
    this.direction = direction;
    this.speed = speed;
    requires(Robot.driveChassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.driveChassis.enable();
    Robot.statusMessage("Begining compass" + this.direction);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.driveChassis.compassDrive(speed, direction);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveChassis.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.driveChassis.disable();
  }
}
