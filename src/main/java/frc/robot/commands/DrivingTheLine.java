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

public class DrivingTheLine extends CompassDriving {
  public DrivingTheLine( double direction, double speed) {
    super(direction, speed);
    // Use requires() here to declare subsystem dependencies
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double speed = 0;
    double rotation = 0; //TODO: super class has protected target direction- change to +/- 5 that target direction

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

    Robot.driveChassis.compassDrive(speed, rotation); //TODO: make speed ft per sec- convert later 

    System.out.println("Speed:" + speed + " rotation:" + rotation);
  }

}
