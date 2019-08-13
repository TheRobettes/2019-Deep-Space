/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.vision.Snapshot;

/**
 * An example command.  You can replace me with your own command.
 */
public class TeletubbyDrive extends Command {
  private double practiceSpeed=0; 
  public TeletubbyDrive() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.driveChassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (practiceSpeed > 0)
      SmartDashboard.putNumber("tele-init-practiceSpeed", practiceSpeed);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    practiceSpeed = -OI.secondaryJoystick.getTwist() * 0.25 + 0.75; //changed for demo purposes
    Robot.driveChassis.arcadeDrive(practiceSpeed * -OI.xBox.getY(), OI.xBox.getRawAxis(4)); //changed for demo purposes

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
