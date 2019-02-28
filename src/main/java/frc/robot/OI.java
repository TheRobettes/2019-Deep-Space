/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
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

  public OI() {
    //Primary Joystick Buttons
    if(RobotMap.isKimmie()){
      //illuminationButton
      buttonHold(xBox, 5, new DrivingTheLine(0, 1.5));//CHECK BUTTON WITH OLLIE
      //luminenceButton
      buttonHold(xBox, 6, new DrivingTheLine(180, 1.5));//CHECK BUTTON WITH OLLIE
      //drive without gyro on the line
      buttonHold(secondaryJoystick, 2, new DrivingTheLine(CompassDriving.noGyro, 2));
    }
    
    //Aisha DEEPSPACE
    
    buttonHold(secondaryJoystick, 1, new EnVISIONing(0));

    //non-PID hatch movement
    buttonHold(secondaryJoystick, 9, newManualHatch(-0.6)); //manual hatch down
    buttonHold(secondaryJoystick, 8, newManualHatch(0.95)); //manual hatch up
  
     //PID based hatch-levels...
      //hatchButton
      buttonPress(secondaryJoystick, 4, new MoveHatchLevel(20  ));
      buttonPress(secondaryJoystick, 3, new MoveHatchLevel(90 - 22 ));
      buttonPress(secondaryJoystick, 5, new MoveHatchLevel(140 + 10 ));
    
    //Secondary Joystick Buttons
    //extendButton
    buttonPress(secondaryJoystick, 6, new PistonMovement(Robot.gaston, true)); //hatch open
    //retractButton
    buttonPress(secondaryJoystick, 7, new PistonMovement(Robot.gaston, false)); // hatch close
  

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
    };
  return hatchCommand;
}

}
