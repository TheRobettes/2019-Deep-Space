/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.vision.Snapshot;
import frc.robot.vision.TargetAnalysis;

public class EnVISIONing extends Command {

  private static int imageCycles = 0;
  private static final int MAX_IMAGE_CYCLES = 5; //TODO: check value
  private static final int MAX_OVERSHOOT_CORRECTION = 25;
  private static double driveRate;
  private static boolean shoppingAtTarget = false;
  private static boolean missedTarget = false;
  private static final double SPEED_LIMIT = 4;
  private static final double MINIMUM_SPEED = 1;
  private static final double APPROACH_SPEED = (SPEED_LIMIT + MINIMUM_SPEED) / 2;
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
    Robot.statusMessage("starting vision command");
    Robot.driveChassis.enable();
    Robot.driveChassis.compassDrive(0, turningDirection);
    TargetAnalysis.updateValues();
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
        System.out.println("Image Updated Without Contours" + RobotMap.gyro.getAngle());
      }

    //stationary safety code for debugging

    //turningDirection = driveRate = 0;
    
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
    if(Math.abs(TargetAnalysis.targetAngleFromVision) < 7) {
      shoppingAtTarget = true;
    }
    else {
      missedTarget = true;
    }
  }
  
  //distance: 1.5 = 42.25% width; distance: 4 = 14.5%
  double distance = (-.09 * TargetAnalysis.targetWidthPct) + 5.3;

  //TODO: likely will have to be adjusted by a this.-targetheading-
  double currentRobotHeading = Robot.driveChassis.getDirection();
  double currentVisionHeading = TargetAnalysis.targetAngleFromVision;

  /* 2/13; keep until above logic is proven feasible 
  double expectedOffset = distance * Math.sin(Math.toRadians(currentHeading));
  double lateralOffset = expectedOffset - TargetAnalysis.targetAngleFromVision; //how far is the robot from the line
  
  
  String lateralMessage = " LATERALDISTANCE: (expected " + expectedOffset 
    + "; VISUAL OFFSET: " + lateralOffset + ")";
  Robot.statusMessage(lateralMessage);*/

  //a temporary value for how much we will steer right or left of the 
  double headingCorrection = 0;  

  double combinedHeading = currentRobotHeading + currentVisionHeading;

  if(Math.abs(currentRobotHeading) > 5
  || Math.abs(currentVisionHeading) > 5) { 

    if(Math.abs(combinedHeading) > 5){
      //result actions for tacking (spin without movement)
      headingCorrection = Math.copySign(MAX_OVERSHOOT_CORRECTION, currentVisionHeading); //plus or minus 20 based on direction of lateralOffset
      driveRate = 0;
    }
    
    else{
      //result actions for approaching center
      driveRate = APPROACH_SPEED;
      headingCorrection = -2*currentVisionHeading;
    }
 
    //return; if this if statement is executed, don't do anything else in the funtion
  }

  else {
    headingCorrection = combinedHeading;

    //calculate drive rate based on distance from vision target
    double driveRatePct = 25 * distance; 

    if(driveRatePct > 100)
      driveRatePct = 100;
    
    if(driveRatePct < 0)
      driveRatePct = 0;

    //want our vision to see where the targets are on our image
  
    driveRate = .01 * driveRatePct * (SPEED_LIMIT - MINIMUM_SPEED) + MINIMUM_SPEED; 
    }
    turningDirection += headingCorrection;
    String visionMessage = " DISTANCE: " + distance + " Drive Rate: " + driveRate
    + "; ROBOT: " + currentRobotHeading
    + "; VISION: " + TargetAnalysis.targetAngleFromVision
    + "; Turning Correction: " + turningDirection;
  Robot.statusMessage(visionMessage);
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
    Robot.driveChassis.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
