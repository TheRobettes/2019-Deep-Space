/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.vision.Snapshot;
import frc.robot.vision.TargetAnalysis;

public class EnVISIONing extends Command {

  private static int imageCycles = 0;
  private static final int MAX_IMAGE_CYCLES = 5; //TODO: check value
  private static double driveRate;
  private static boolean shoppingAtTarget = false;
  private static boolean missedTarget = false;
  private static final double SPEED_LIMIT = 4;
  private static double turningDirection;
  private static double direction;


  public EnVISIONing(double direction) {
    this.direction = direction;

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveChassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Snapshot.isVisionCommandEnabled = true;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Snapshot.isImageReady) {
      TargetAnalysis.updateValues();
      imageCycles = 0;

      driveRate = 0;
      turningDirection = direction;

      if(isTracking()) {  
        calcDriveRates();
      }

      else {
        System.out.println("Image Updated Without Contours");
      }
    
    driveRate = 0;
    turningDirection = 0;

    if(imageCycles++ < MAX_IMAGE_CYCLES) {
        Robot.driveChassis.compassDrive(driveRate, turningDirection); //what happened to driveRate?
    }

    else {
      Robot.driveChassis.compassDrive(0, turningDirection);
    }
  }
}

 protected void calcDriveRates() {

  if(TargetAnalysis.targetWidthPct > 80.0) {
    if(Math.abs(TargetAnalysis.targetXOffset) < 7) {
      shoppingAtTarget = true;
    }
    else {
      missedTarget = true;
    }
  }
  
  //TODO: likely will have to be adjusted by a this.-targetheading-
  double currentHeading = Robot.driveChassis.getDirection();
  double distancePct = TargetAnalysis.targetWidthPct;
  double expectedOffset = distancePct * Math.sin(Math.toRadians(currentHeading));

  double headingCorrection = 0; //TODO: turning correction will be based on a factor of currentHeading and expectedOffset. 
  turningDirection += headingCorrection;

  double driveRatePct = distancePct; 

  if(Math.abs(headingCorrection) > 10){
    driveRatePct = 0;
  }
  
  driveRate = driveRatePct * SPEED_LIMIT; 

  System.out.println("TARGET WIDTH: " + TargetAnalysis.targetWidthPct 
  + "; TARGET X OFFSET: " + TargetAnalysis.targetXOffset
  + "Drive Rate: " + driveRate + "; Turning Direction: " + turningDirection);
  
 } 

 protected double calcDriveRatePct(){
   return -0.1 * TargetAnalysis.targetWidthPct + 1.0;
 }

 private boolean isTracking() {
   return TargetAnalysis.foundTarget;
 }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Snapshot.isVisionCommandEnabled = false;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
