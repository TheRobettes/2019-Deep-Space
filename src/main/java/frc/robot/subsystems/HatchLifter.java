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
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class HatchLifter extends PIDSubsystem {
  private final SpeedController hatchmotor = RobotMap.hatch;
  private AnalogPotentiometer hatchpotential = RobotMap.hatchpotential;
  protected DoubleSolenoid brakePiston = RobotMap.brake;
  /**
   * Add your docs here.
   */
  public HatchLifter() {
    // Intert a subsystem name and PID values here
    super("SubsystemName", 1, 2, 3); //TODO: Correct PID values
  
  }
 
  @Override
  public void enable() {
    brakePiston.set(Value.kReverse);
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
    return hatchpotential.get();
  }

  @Override
  protected void usePIDOutput(double speed) {
    hatchmotor.set(speed);
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
  }

  @Override
  public void disable() {
    brakePiston.set(Value.kForward);
    super.disable();
  }
}
