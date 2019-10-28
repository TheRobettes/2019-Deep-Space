/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.HatchLifter;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
 
  //TODO: put in CommandBase (command) as protected variables 
  public static Joystick xBox  = new Joystick(0);
  public static Joystick secondaryJoystick = new Joystick(1);
  public static Joystick cargoJoystick = new Joystick(2);

  private static SendableChooser<String> m_chooser = new SendableChooser<>();
  public OI() {
    
    
    m_chooser.setDefaultOption("CARGO", "CARGO");
    m_chooser.addOption("HATCH", "HATCH");
    SmartDashboard.putData("Auto Mode", m_chooser);
    //Primary Joystick Buttons


    if(RobotMap.isKimmie()){
      //illuminationButton
      buttonHold(xBox, 5, new DrivingTheLine(0, 1.5));//CHECK BUTTON WITH OLLIE
      //luminenceButton
      buttonHold(xBox, 6, new DrivingTheLine(180, 1.5));//CHECK BUTTON WITH OLLIE
      //drive without gyro on the line
      buttonHold(secondaryJoystick, 2, new DrivingTheLine(CompassDriving.noGyro, 2));
    }
    
    
    
    buttonHold(secondaryJoystick, 1, new EnVISIONing());

    Robot.statusMessage("OI-command-buttons pretest... " + RobotMap.isKimmie() + RobotMap.isVictoria());
    

       Robot.statusMessage("Creating DEEPSPACE/Aisha command buttons...");
    
  //non-PID hatch movement
    buttonHold(secondaryJoystick, 9, newManualHatch(HatchLifter.maxDiveBomb)); //manual hatch down //at ND switched signs because comp robot is opposite 
    buttonHold(secondaryJoystick, 8, newManualHatch(HatchLifter.maxBlastOff)); //manual hatch up
  
     //PID based hatch-levels...
      //hatchButton Aiysha has range 50 (bottom) - 105 (top)
      buttonPress(secondaryJoystick, 4, newHatchLevel(55, 55+10)); //(RobotMap.IS_PRACTICE_ROBO)? 103: 20));//low 20
      buttonPress(secondaryJoystick, 3, newHatchLevel(77, 77+10)); //(RobotMap.IS_PRACTICE_ROBO)? 120: 10));//middle 10
      buttonPress(secondaryJoystick, 5, newHatchLevel(130, 130+10));//(RobotMap.IS_PRACTICE_ROBO)? 136: 0));//high 0
      buttonPress(secondaryJoystick, 2, newResetEncoder());
    /* 
      //Hatch Buttons
    //extendButton
    buttonHold(secondaryJoystick, 6, newGastonMovement(false, 0.5)); //gaston close, hatch intake
    //retractButton
    buttonHold(secondaryJoystick, 7, newGastonMovement(false, -0.5)); // gaston close, hatch place
    //cargo Buttons
     //extendButton
     buttonHold(cargoJoystick, 6, newGastonMovement(true, -0.5)); //gaston open, cargo intake
     //retractButton
     buttonHold(cargoJoystick, 7, newGastonMovement(true, 0.5)); // gaston open, cargo place
     */

     //dual purpose get/place buttons
     buttonHold(secondaryJoystick, 6, 
      new ConditionalCommand(
        newGastonMovement(false, 0.5), //gaston close, hatch intake
        newGastonMovement(true, -0.5) //gaston open, cargo intake
     //retractButton
      ){
     
        @Override
        protected boolean condition() {
          return m_chooser.getSelected().equals("HATCH");
        }
      });

      buttonHold(secondaryJoystick, 7, 
      new ConditionalCommand(
        newGastonMovement(false, -0.5), //gaston close, hatch place
        newGastonMovement(true, 0.5) //gaston open, cargo place
     //retractButton
      ){
     
        @Override
        protected boolean condition() {
          return m_chooser.getSelected().equals("HATCH");
        }
      });
     

    if((!RobotMap.isKimmie()) && (!RobotMap.isVictoria()) ) {
    buttonPress(secondaryJoystick, 10, new PistonMovement (Robot.gastonUpDown, true)); //hatch down 

    buttonPress(secondaryJoystick, 11, new PistonMovement (Robot.gastonUpDown, false)); //hatch up 
    }

//DriveChassis calibration testing routines
    //northButton
   buttonHold(xBox, 4, new CompassDriving(0, 1.0));
    //westButton
   buttonHold(xBox, 3, new CompassDriving(-90, 1.5));
   //southButton
   buttonHold(xBox, 1, new CompassDriving(-180, 2.0));
  
   
    
    
  }
    //condesed whileHeld into 1 function 
    public void buttonHold (Joystick joystick, int buttonNumber, Command buttoncommand){
      Button button = new JoystickButton(joystick, buttonNumber);
      button.whileHeld(buttoncommand);
      button.close();
    }

    //condesing whenPressed buttons into 1 function 
    public void buttonPress (Joystick joystick, int buttonNumber, Command buttoncommand){
      Button button = new JoystickButton(joystick, buttonNumber);
      button.whenPressed(buttoncommand);
      button.close();
    }

public static Command newManualHatch(double power){
    Command hatchCommand = new BasicMovement(Robot.hatch, power) {
      @Override
      protected void execute(){
        super.execute();
        SmartDashboard.putNumber ("hatch position ", HatchLifter.getHatchPosition());
      }
      @Override
      protected void initialize() {
        super.initialize();
        MoveHatchLevel.logHatchLevel("beginning movement");
      }
      @Override
      protected void end() {
        super.end();
        MoveHatchLevel.logHatchLevel("ending movement");
      }
    };
  return hatchCommand;
}

public static Command newGastonMovement(boolean isOpenClosed, double speed) {

  Command pistonMovement = new PistonMovement(Robot.gaston, isOpenClosed) {
    @Override
    protected void interrupted() {}
  /*  @Override
    protected void initialize() {
      super.initialize();
      int currentAngle = (int)Robot.driveChassis.getDirection();
      int targetAngle = 0;
      String targetName = null;

      if(Math.abs(currentAngle) > 180 - 15) {
        targetAngle = 180;
        targetName = "Loading Station";
      }

      else if(currentAngle > 120 - 15) {
        targetAngle = 120;
        targetName = "Right Rocket Far";
      }

      else if(currentAngle > 90 - 15) {
        targetAngle = 90;
        targetName = "Cargo Left";
      }

      else if(currentAngle > 30 - 30) {
        targetAngle = 30;
        targetName = "Right Rocket Close";
      }

      else if(currentAngle > -30 - 15) {
        targetAngle = -30;
        targetName = "Left Rocket Close";
      }

      else if(currentAngle > -90 - 15) {
        targetAngle = -90;
        targetName = "Cargo Right";
      }

      else if(currentAngle > -120 - 15) {
        targetAngle = -120;
        targetName = "Left Rocket Far";
      }


      String driveAngleText = targetName + ": " + currentAngle + " (" + (targetAngle - currentAngle) + ")";
      DriverStation.reportWarning(driveAngleText, false);
    }*/
  };

  //convert our boolean direction into a numeric speed
 //double speed = (isOpenClosed)? 0.5 : -0.5;
  Command holderMovement = new BasicMovement(Robot.cargoHolderSystem, speed);
  CommandGroup multipartAction = new CommandGroup();
  multipartAction.addParallel(holderMovement);
  multipartAction.addParallel(pistonMovement);
  return multipartAction;
}

public static Command newHatchLevel(double hatchHeight, double cargoHeight){
  Command multiHatchLevel = new ConditionalCommand(
    new MoveHatchLevel(hatchHeight),
    new MoveHatchLevel(cargoHeight)
  ){
 
    @Override
    protected boolean condition() {
      return m_chooser.getSelected().equals("HATCH");
    }
  };
  return multiHatchLevel;
}

public static Command newResetEncoder(){
  return new BasicMovement(Robot.hatch, 0){
    @Override
    protected void initialize(){
      RobotMap.hatchEncoder.setPosition(0);
    }
  };
}
}


