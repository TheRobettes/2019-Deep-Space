/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class HatchLifter extends PIDSubsystem {
  private final SpeedController hatchmotor = RobotMap.hatch;
  private static boolean waxOn = true;

 //gotten through testing- converts volts to degrees
  private static double fullRange = 2000;

  // gotten though testing-  the number subtracted from top range (2000) to make it be a good range of 40-140
  private static double offset = -1705;

  private static final AnalogPotentiometer hatchpotential = new AnalogPotentiometer(0, fullRange, offset);


  protected Solenoid brakePiston = RobotMap.brake;
  

  public HatchLifter() {
    // Intert a subsystem name and PID values here
    super("SubsystemName", 0.02, 0.0, 0.07); //TODO: Correct PID values //previous was 0.015, 0, 0.07
  
  }

  public static double getHatchPosition() {
    return hatchpotential.get();
  }

 
  @Override
  public void enable() {
    brakePiston.set(!waxOn);
    System.out.println(" brake off" );
    super.enable();

  }

  @Override
  public void initDefaultCommand() {

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

  }

  @Override
  public void disable() {
    brakePiston.set(waxOn);
    System.out.println(" brake on");
    super.disable();
  }
}
