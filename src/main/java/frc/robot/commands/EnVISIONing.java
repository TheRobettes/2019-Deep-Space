/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.vision.Snapshot;
import frc.robot.vision.TargetAnalysis;

public class EnVISIONing extends Command {

  private static int imageCycles = 0;
  private static final int MAX_IMAGE_CYCLES = 5; //TODO: check value
  private static double driveRate;
  private static boolean shoppingAtTarget = false;
  private static boolean missedTarget = false;
  private static final double SPEED_LIMIT = 2; //TODO: after testing, raise this to go faster (4)
  private static final double MINIMUM_SPEED = 1;
  private static final double APPROACH_SPEED = (SPEED_LIMIT + MINIMUM_SPEED) / 2;
  private static double turningDirection;
  private static double direction;
  private static final double MAX_APPROACH_OVERSHOOT = 10;
  private static final double MINIMUM_APPROACH_ANGLE = 15;
  private static SendableChooser<Integer> pickWhichHatch = new SendableChooser<>();

  public EnVISIONing(){  //double direction) {
    this.direction = -1;
    
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveChassis);

    //choices on our dumb dashboard - that's not nice :(
    pickWhichHatch.setDefaultOption("Cargo front", new Integer(0));
    //pickWhichHatch.addOption("Cargo Left", new Integer(90)); 
    //pickWhichHatch.addOption("Cargo Right", new Integer(-90));
    pickWhichHatch.addOption("Loading Station", new Integer(180));
    pickWhichHatch.addOption("Left Rocket close", new Integer(-60));
    pickWhichHatch.addOption("Right Rocket close", new Integer(60));
    pickWhichHatch.addOption("Left Rocket far", new Integer(-120));
    pickWhichHatch.addOption("Right Rocket far", new Integer(120));
    SmartDashboard.putData("Auto mode", pickWhichHatch);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    direction = turningDirection = pickWhichHatch.getSelected().hashCode();
    Robot.driveChassis.enable();
    Snapshot.enablePipeline();
    System.out.println("~Initializing Vision~: " + direction);

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
        Robot.statusMessage("drive rate : " + driveRate);
      }

      else {
        System.out.println("Image Updated Without Contours");
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
  String driveRateStr = "";
  
  //TODO: need to complete when done code
  if(TargetAnalysis.targetWidthPct > 80.0) {
    if(Math.abs(TargetAnalysis.targetAngleFromVision) < 7) {
      shoppingAtTarget = true;
    }
    else {
      missedTarget = true;
    }
  }
  
  //distance: 1.2083 = 66.25% width; distance: 5.667 = 13.125%
  double distance = (-0.0845*TargetAnalysis.targetWidthPct) + 4.776; 

  //TODO: likely will have to be adjusted by a this.-targetheading-
  double currentRobotHeading = Robot.driveChassis.getDirection();
  double currentVisionHeading = TargetAnalysis.targetAngleFromVision;
  double combinedHeading = currentRobotHeading + currentVisionHeading;
  double headingCorrection = 0;

  String visionMessage = null;
  if(distance < 2.3){ //if we are really close and need to shut off 
    visionMessage = "we hit the bulls eye";
  }
  else if( Math.abs(combinedHeading) > 5)
  {
    //if we are not on the line
    headingCorrection = combinedHeading + Math.copySign(MAX_APPROACH_OVERSHOOT, combinedHeading);

    if(Math.abs(currentRobotHeading) > Math.abs(headingCorrection) - MINIMUM_APPROACH_ANGLE) {
      double crossingTheLineAngle = headingCorrection - currentRobotHeading;
      double driveRateFactor = -crossingTheLineAngle * (1.0/MINIMUM_APPROACH_ANGLE) + 1.0; //y = mx + b
      this.driveRate = (driveRateFactor > 1) ? this.APPROACH_SPEED : this.APPROACH_SPEED * driveRateFactor;
      //TODO: fix from going contours to no contours- it drives negative 
    driveRateStr = " (" + (driveRateFactor * 100.0) + "%)";

    }

  }

  else {
    //actions for final centering when near the perpendicular line
    
    //determining a relative speed based on our distance
    double driveRatePercent = distance * (100.0/1.5) - 100; //y = mx + b

    //eliminate over and underdriving
    if(driveRatePercent > 100.0){
      driveRatePercent = 100.0;

    }

    if(driveRatePercent < 0.0) {
      driveRatePercent = 0.0;
    }

    //convert our percent into a speed
    driveRate = ((SPEED_LIMIT - MINIMUM_SPEED) * 0.01 * driveRatePercent) + MINIMUM_SPEED;  
    
    driveRateStr = " (" + driveRatePercent + "%)";
  }
     
  //want our vision to see where the targets are on our image
  turningDirection += headingCorrection;

  if (visionMessage == null) 
    visionMessage = " DISTANCE: " + distance 
    //+ "; TARGET X OFFSET: " + TargetAnalysis.targetXOffset
    + "; Drive Rate: " + driveRate + driveRateStr 
    + "; Turning: " + currentRobotHeading + ", " + currentVisionHeading + ", " + headingCorrection;

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
    Robot.driveChassis.disable();
    Snapshot.disablePipeline();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
