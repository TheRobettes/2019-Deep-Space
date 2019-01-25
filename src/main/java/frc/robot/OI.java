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
    buttonHold(xBox, 5, new DrivingTheLine());//CHECK BUTTON WITH OLLIE
     //luminenceButton
    buttonHold(xBox, 6, new DrivingTheLine());//CHECK BUTTON WITH OLLIE

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
    buttonHold(secondaryJoystick, 11, new CompassDriving(0));
    //westButton
    buttonHold(secondaryJoystick, 4, new CompassDriving(-90));
  }
<<<<<<< HEAD
    public void buttonHold (Joystick joystick, int buttonNumber, Command buttoncommand){
      Button button = new JoystickButton(joystick, buttonNumber);
      button.whileHeld(buttoncommand);
      button.close();
    }
    public void buttonPress (Joystick joystick, int buttonNumber, Command buttoncommand){
      Button button = new JoystickButton(joystick, buttonNumber);
      button.whenPressed(buttoncommand);
      button.close();
    }
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
=======
>>>>>>> 0a9ab24528bcc40c8e53f24c889379b1fac57fb8
}
