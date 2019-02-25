/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;


/**
 * Add your docs here.
 */
public class EncoderPID extends GyroPID {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
private static final double ENCODER_P_VALUE = 0.005;
private static final double ENCODER_I_VALUE = 0.0;
private static final double ENCODER_D_VALUE = 0.5;
private static final boolean temporaryInputHack = true;
private static final boolean temporaryOutputHack = true;
public static final double distancePerPulse = 
(RobotMap.isVictoria())? 3.0 / 228 
                       : 6.3 / 793; //DEEPSPACE distance
private double previousSpeed = 0;
private double previousAcceleration = 0;
private final double maxAccel = 0.5; 

private final PIDController encoderPID = new PIDController(ENCODER_P_VALUE, ENCODER_I_VALUE, ENCODER_D_VALUE, 
  new PIDSource(){
    PIDSourceType m_SourceType = PIDSourceType.kRate;

    @Override
    public double pidGet() {
      double currentSpeed = (RobotMap.rightDriveEncoder.getRate() + RobotMap.leftDriveEncoder.getRate()) / 2;
      double currentAcceleration = currentSpeed - previousSpeed;
      if (Math.abs(currentAcceleration) > maxAccel && temporaryInputHack){
        if (currentAcceleration < 0) 
          currentSpeed = previousSpeed - maxAccel; //if I am going backwards fast, i want to take my slower speed from before and minus 0.5 so instead of going -8 I am going -0.5 if my previous speed was 0 
        else 
          currentSpeed = previousSpeed + maxAccel;
    }
      SmartDashboard.putNumber("Current Speed" , currentSpeed);

      if ((previousAcceleration < 0 && currentAcceleration > 0) //when speeding down to speeding up 
      || (previousAcceleration > 0 && currentAcceleration < 0)) //when speeding up to speeding down
      
      {
         String encoderMessage = "\t pidSpeed: \t"  + speedPower + "(" + currentSpeed + ")  "
         + "rotation " + rotationPower + " Angle offset (" + angleOffset + ") ";
         Robot.statusMessage(encoderMessage);
      }

      previousAcceleration = currentAcceleration;
      previousSpeed = currentSpeed;
      // this return is equivalent to the more familiar getPidInput()...
      return currentSpeed;
      }
      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {
      m_SourceType = pidSource;
      }
      @Override
      public PIDSourceType getPIDSourceType() {
      return m_SourceType;
      }
    },

  speedPower -> pidSpeedCorrection(speedPower)
  /*the following lines are the "long version" of the previous lines
  PIDOutput tempvar =  
  new PIDOutput() {
    void pidWrite(double output) {
        speedPower = output;
    }
  }*/
    

    ); // end of encoder-pid's anonymous declaration.
   
    
    public EncoderPID (){
      RobotMap.rightDriveEncoder.setDistancePerPulse(distancePerPulse);
    }
   
    public void pidSpeedCorrection(double PIDOutput){
      speedPower = PIDOutput;
      if (Math.abs(this.angleOffset) > 15.0 && temporaryOutputHack)
        speedPower = 0.4; //TODO: trying to prevent stuttering at "initialize" reduce or eliminate later
    }
   
    @Override
    public void compassDrive (double speed, double angle){
      this.encoderPID.setSetpoint(speed);
      SmartDashboard.putNumber("Speed Desired" , speed); //the speed we want to go
      super.compassDrive(speed, angle);

      double currentDistance = RobotMap.rightDriveEncoder.getDistance();
      SmartDashboard.putNumber("Current Distance" , currentDistance);
    }

    @Override
    public void enable() {
      encoderPID.enable();
      super.enable();
    }

    @Override
    public void disable() {
      encoderPID.disable();
      super.disable();
    }
    

}
