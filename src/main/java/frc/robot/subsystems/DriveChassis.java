/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.TeletubbyDrive;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveChassis extends Subsystem {
  private DifferentialDrive driving = new DifferentialDrive(RobotMap.leftDrive, RobotMap.rightDrive);

 public void arcadeDrive(double speed, double rotation ){
     driving.arcadeDrive(speed, rotation); 
     System.out.println("Speed:" + speed + " rotation:" + rotation);
}


  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TeletubbyDrive());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
