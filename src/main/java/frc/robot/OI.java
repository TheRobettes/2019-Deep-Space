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
import frc.robot.commands.BasicMovement;
import frc.robot.commands.DrivingTheLine;
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
    Button illuminationButton = new JoystickButton(xBox, 5); //CHECK BUTTON WITH OLLIE 
    illuminationButton.whileHeld(new DrivingTheLine());
    Button luminescenceButton = new JoystickButton(xBox, 6); //CHECK BUTTON WITH OLLIE 
    luminescenceButton.whileHeld(new DrivingTheLine());

    //Secondary Joystick Buttons
    Button skiButton = new JoystickButton(secondaryJoystick, 3);
    skiButton.whileHeld(new BasicMovement(Robot.skis, 0.5));

    Button hatchButton = new JoystickButton(secondaryJoystick, 5);
    hatchButton.whileHeld(new BasicMovement(Robot.hatch, 0.5));

    Button extendbutton = new JoystickButton(secondaryJoystick, 6);
    extendbutton.whileHeld(new PistonMovement(Robot.gaston, PistonMovement.extend));

    Button retractbutton = new JoystickButton(secondaryJoystick, 7);
    retractbutton.whileHeld(new PistonMovement(Robot.gaston, PistonMovement.retract));
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
}
