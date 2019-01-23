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
    Button illuminationButton = new JoystickButton(xBox, 5); //CHECK BUTTON WITH OLLIE 
    illuminationButton.whileHeld(new DrivingTheLine());
    Button luminescenceButton = new JoystickButton(xBox, 6); //CHECK BUTTON WITH OLLIE 
    luminescenceButton.whileHeld(new DrivingTheLine());

    //Secondary Joystick Buttons
    Button skiButton = new JoystickButton(secondaryJoystick, 3);
    skiButton.whileHeld(new BasicMovement(Robot.skis, 0.5));

    Button hatchButton = new JoystickButton(secondaryJoystick, 5);
    hatchButton.whenPressed(new MoveHatchLevel(0.5)); //TODO: rename command

    Button extendbutton = new JoystickButton(secondaryJoystick, 6);
    extendbutton.whileHeld(new PistonMovement(Robot.gaston, PistonMovement.extend));

    Button retractbutton = new JoystickButton(secondaryJoystick, 7);
    retractbutton.whileHeld(new PistonMovement(Robot.gaston, PistonMovement.retract));

    Button northButton = new JoystickButton(secondaryJoystick,11);
    northButton.whileHeld(new CompassDriving(0));

    Button westButton = new JoystickButton(secondaryJoystick, 4);
    westButton.whileHeld(new CompassDriving(-90));
    //TO DO: finish Maisie's gyro suggestion
  }
}
