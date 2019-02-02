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
import frc.robot.commands.CompassDriving;
import frc.robot.commands.DrivingTheLine;
import frc.robot.commands.MoveHatchLevel;
import frc.robot.commands.PistonMovement;

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
    //illuminationButton
    buttonHold(xBox, 5, new DrivingTheLine(-90, 2.5));//CHECK BUTTON WITH OLLIE
     //luminenceButton
    buttonHold(xBox, 6, new DrivingTheLine(90, 1.5));//CHECK BUTTON WITH OLLIE

    //Secondary Joystick Buttons
    //skisButton
    buttonHold(secondaryJoystick, 3, new PistonMovement(Robot.skis, PistonMovement.extend));
    //hatchButton
    buttonPress(secondaryJoystick, 5, new MoveHatchLevel(0.5));
    //extendButton
    buttonHold(secondaryJoystick, 6, new PistonMovement(Robot.gaston, PistonMovement.extend));
    //retractButton
    buttonHold(secondaryJoystick, 7, new PistonMovement(Robot.gaston, PistonMovement.retract));
    //northButton
    buttonHold(secondaryJoystick, 11, new CompassDriving(0, 1.3));
    //westButton
    buttonHold(secondaryJoystick, 4, new CompassDriving(-90, 2));

    //drive without gyro on the line
    buttonHold(secondaryJoystick, 2, new CompassDriving(CompassDriving.noGyro, 2));

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

}
