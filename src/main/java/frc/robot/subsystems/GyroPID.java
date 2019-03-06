/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class GyroPID extends DriveChassis { //TODO: figure out these numbers 
  private static final double gyro_P_Value = (RobotMap.isVictoria()) ? 0.003
   :0.065;
  private static final double gyro_I_Value = 0.0045;
  private static final double gyro_D_Value = 0.2;
  protected double speedPower = 0.0;
  protected double rotationPower = 0.0;
  protected double angleOffset = 0.0;
  //TODO: finish tuning PID 

  public GyroPID() {
    // Inert a subsystem name and PID values here
    super("Gyro", gyro_P_Value, gyro_I_Value, gyro_D_Value);
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

  public double getDirection() {
    return RobotMap.gyro.getAngle(); 
  }

  public void compassDrive (double speed, double angle){
    setSetpoint(angle);
    SmartDashboard.putNumber("Angle Desired" , angle); //the angle we want to go
    
  }

  /*
  There is no "initDefaultCommand" because the executed code is already defined for us in the super class
  */
double currentAngle;
  @Override
  protected double returnPIDInput() {
    currentAngle = RobotMap.gyro.getAngle();
    SmartDashboard.putNumber("Current Angle Number" , currentAngle); //the direction we are going
    this.angleOffset = getSetpoint() - currentAngle; //how many degrees are we off target?
    
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    return currentAngle;
  }
//long printFilterPID = 0;
  @Override
  protected void usePIDOutput(double rotation) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    this.rotationPower = rotation;
    super.arcadeDrive(speedPower, rotation);
    SmartDashboard.putNumber("pidDrivePower", speedPower);
    /*String gameTime = "" + summerTime.get();
    //DriverStation.getInstance().getMatchTime();
    //if (printFilterPID ++ %5 == 0 && Math.abs(rotation)>0.008 && Math.abs(rotation)!= 1.0)
        
    System.out.println(gameTime +  "\t pidSpeed: \t"  + speedPower + "(" + RobotMap.rightDriveEncoder.getRate() + ")"
        //+ " rotation:" + rotation + "(" + currentAngle + ")"
        );
        */
  }  
}
