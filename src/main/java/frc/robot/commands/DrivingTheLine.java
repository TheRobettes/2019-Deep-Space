/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Snapshot;

public class DrivingTheLine extends Command {
  public DrivingTheLine() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveChassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double speed = 0;
    double rotation = 0;

    if(Snapshot.getLineStatus() == Snapshot.WhichSensor.lefty) {
      speed = 0.45; //0.5
      rotation = -0.35; //0.4
    }

    if(Snapshot.getLineStatus() == Snapshot.WhichSensor.righty) {
      speed = 0.45; //0.5
      rotation = 0.35; //0.4
    }

    if(Snapshot.getLineStatus() == Snapshot.WhichSensor.middley) {
      speed = 0.45; //0.5
    }

    Robot.driveChassis.arcadeDrive(speed, rotation);

    System.out.println("Speed:" + speed + " rotation:" + rotation);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
