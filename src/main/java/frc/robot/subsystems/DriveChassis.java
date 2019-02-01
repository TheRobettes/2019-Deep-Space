/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.TeletubbyDrive;


public abstract class DriveChassis extends PIDSubsystem {
  private DifferentialDrive driving = new DifferentialDrive(RobotMap.leftDrive, RobotMap.rightDrive);

  public DriveChassis(String variableName, double gyro_P_Value, double gyro_I_Value, double gyro_D_Value){
    super(variableName, gyro_P_Value, gyro_I_Value, gyro_D_Value);
    driving.setExpiration(.5);
  }
 public void arcadeDrive(double speed, double rotation ){
     driving.arcadeDrive(speed, rotation); 
}

  

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TeletubbyDrive());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
