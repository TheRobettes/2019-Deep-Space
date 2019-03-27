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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class HatchLifter extends BasicController {
  private static boolean waxOn = false;

 //gotten through testing- converts volts to degrees
  private static double fullRange = 200; //(!RobotMap.IS_PRACTICE_ROBO)? 20000: 2000; // practice robot 2000;

  // gotten though testing-  the number subtracted from top range (2000) to make it be a good range of 40-140
  private static double offset = -50; //(!RobotMap.IS_PRACTICE_ROBO)? -25:  -1705; //practice robot -1705;

  private static final AnalogPotentiometer hatchpotential = new AnalogPotentiometer(0, fullRange, offset);


  protected Solenoid brakePiston = RobotMap.brake;
  

  public HatchLifter() {
    // Intert a subsystem name and PID values here
    super("SubsystemName", 3, 0.15, 0.15); //TODO: Correct PID values //previous was 0.015, 0, 0.07
    this.motor = RobotMap.hatch;

    // determine tolerence (accuracy) for each stage
    this.setAbsoluteTolerance(5);

    // start with brake ON. 
    if(brakePiston!=null)
    brakePiston.set(waxOn);

  }

  public static double getHatchPosition() {
    return hatchpotential.get();
  }

 
  @Override
  public void enable() {
   activate();
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

    // apply a dampen-speed affect to all downward moves.
    if (speed < 0 )
      speed *= 1; //previous was *0.4

    System.out.println("Motor Speed: " + speed);
      motor.set(speed);

    //Robot.statusMessage("   " + this.getPosition() + "  --> " + speed);
  }

  @Override
  public void disable() {
    deactivate();
    super.disable();
  }

  @Override
  public  void activate(){
    brakePiston.set(!waxOn);
    Robot.statusMessage("  brake off ( at " + getPosition() + ")");
  }

  public  void deactivate(){
    brakePiston.set(waxOn);
    Robot.statusMessage(" brake on ( at " + getPosition() + ")");
  }
}
