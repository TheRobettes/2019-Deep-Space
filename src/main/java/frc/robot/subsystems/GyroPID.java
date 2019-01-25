/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class GyroPID extends DriveChassis { //TODO: figure out these numbers 
  private static final double P_Value = 999;
  private static final double I_Value = 998;
  private static final double D_Value = 997;

  public GyroPID() {
    // Inert a subsystem name and PID values here
    super("Gyro", P_Value, I_Value, D_Value);
    RobotMap.gyro.reset();
    /*
    p- line, if far away go fast, as gets closer go slower proportionally
    i- if it is too close the robot won't turn bceause of the p (too little rotation power)
       so the i gives it a power boost to get it to the target
    d- (dampening) lowers the ocilations and keeps it closer to the target speed
    */

    // Use these to get going:
    // setSetpoint() - Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.
    
  }

  public void compassDrive (double angle){
    setSetpoint(angle);
    SmartDashboard.putNumber("Angle Desired" , angle); //the angle we want to go

  }

  /*
  There is no "initDefaultCommand" because the executed code is already defined for us in the super class
  */

  @Override
  protected double returnPIDInput() {
    double currentAngle = RobotMap.gyro.getAngle();
    SmartDashboard.putNumber("Current Angle Number" , currentAngle); //the direction we are going
    
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    return currentAngle;
  }

  @Override
  protected void usePIDOutput(double rotation) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    super.arcadeDrive(0.6, rotation);
  }
}
