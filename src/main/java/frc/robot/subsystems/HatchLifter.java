/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class HatchLifter extends PIDSubsystem {
  private final SpeedController hatchmotor = RobotMap.hatch;

 //gotten through testing- converts volts to degrees
  private static double fullRange = 2000;

  // gotten though testing-  the number subtracted from top range (2000) to make it be a good range of 40-140
  private static double offset = -1690;

  private static final AnalogPotentiometer hatchpotential = new AnalogPotentiometer(0, fullRange, offset);


  //protected DoubleSolenoid brakePiston = RobotMap.brake;
  /**
   * Add your docs here.
   */
  public HatchLifter() {
    // Intert a subsystem name and PID values here
    super("SubsystemName", .015, 0, 0.07); //TODO: Correct PID values
  
  }
 
  @Override
  public void enable() {
    //brakePiston.set(Value.kReverse); //TODO: uncomment if we are using a brake 
    super.enable();

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  protected double returnPIDInput() {
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    double hatchPosition = hatchpotential.get();
    Robot.statusMessage("POSITION:" + hatchPosition);
    return hatchPosition;
  }

  @Override
  protected void usePIDOutput(double speed) {
    Robot.statusMessage("HATCH_OUTPUT:" + speed);
    hatchmotor.set(speed);
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
  }

  @Override
  public void disable() {
    //brakePiston.set(Value.kForward); TODO: uncomment if we are using a brake 
    super.disable();
  }
}
