/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Snapshot;
import frc.robot.Snapshot.WhichSensor;

public class DrivingTheLine extends CompassDriving {
  private Snapshot.WhichSensor previousState = WhichSensor.noney;
  private boolean isEnabled = false;
  public DrivingTheLine( double direction, double speed) {
    super(direction, speed);
    // Use requires() here to declare subsystem dependencies
  }

  @Override
  protected void initialize() {
   //don't enable PID until we have a line state
   //super.initialize(); 
   isEnabled = false;
  }

  @Override
  protected void interrupted() {
    if(isEnabled){
      Robot.driveChassis.disable();
    }
   //don't enable PID until we have a line state
   //super.initialize(); 
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Snapshot.WhichSensor currentSensor = Snapshot.getLineStatus();
    if(currentSensor != previousState) {
      System.out.println("previous state " + previousState);
      previousState = currentSensor;
      //System.out.println("Speed:" + speed + " rotation:" + rotation);

    }
    if(direction == noGyro) {
      executeNoGyro(currentSensor);
    }
    else {
      executeYesGyro(currentSensor);
    }
  }

  protected void executeYesGyro(Snapshot.WhichSensor currentSensor) {
    double lineDrivingSpeed = 0; //the no-sensor speed
    double turningDirection = this.direction;

    if(currentSensor == Snapshot.WhichSensor.lefty) {
      lineDrivingSpeed = 0.75*this.speed; //go slower until we're back to middle
      turningDirection -= 5; //add 5 degree correction to hatch angle
    }

    if(currentSensor == Snapshot.WhichSensor.righty) {
      lineDrivingSpeed = 0.75*this.speed; //go slower until we're back to middle
      turningDirection += 5; //add 5 degree correction to hatch angle
    }

    if(currentSensor == Snapshot.WhichSensor.middley) {
      lineDrivingSpeed = this.speed;
    }

    if(Snapshot.WhichSensor.noney == currentSensor) {
      if(isEnabled){
        Robot.driveChassis.disable();
        isEnabled = false;
        }
      Robot.driveChassis.arcadeDrive(0, 0); //non-PID drive
    }
    else {
      //PIDdrive while on line
      if(!isEnabled){
      Robot.driveChassis.enable();
      isEnabled = true;
      }
      Robot.driveChassis.compassDrive(lineDrivingSpeed, turningDirection);
      System.out.println("Speed:" + lineDrivingSpeed + " rotation:" + turningDirection);
    }
  }

  protected void executeNoGyro(Snapshot.WhichSensor currentSensor){
    double power = 0;
    double rotation = 0; 
    if(currentSensor == Snapshot.WhichSensor.lefty) {
      power = 0.5; //0.5
      rotation = -0.35; //0.4
    }

    if(currentSensor == Snapshot.WhichSensor.righty) {
      power = 0.5; //0.5
      rotation = 0.35; //0.4
    }

    if(currentSensor == Snapshot.WhichSensor.middley) {
      power = 0.5; //0.5
    }

    
      //System.out.println("Power:" + power + " rotation:" + rotation);

      Robot.driveChassis.arcadeDrive(power, rotation); //this is non-PID drive control  
    
    
  }

}
