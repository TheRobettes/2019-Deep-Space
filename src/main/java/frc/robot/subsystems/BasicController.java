/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
<<<<<<< HEAD:src/main/java/frc/robot/subsystems/BasicController.java
public class BasicController extends Subsystem {
  private SpeedController motor;
  
public BasicController(SpeedController motor){
  this.motor = motor;
}
=======
public class ExampleSubsystem extends Subsystem {

  // Put class variables for this subsystem here
>>>>>>> f379f0445603a87a5822aa84085e8dc9ff386068:src/main/java/frc/robot/subsystems/ExampleSubsystem.java

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
}
