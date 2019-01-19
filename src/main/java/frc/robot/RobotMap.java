/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

  //currently kimmie port numbers (PWM - Roborio/PWM Ports)
  private static int leftDrivePort = 2;
  private static int rightDrivePort = 8;
  private static int hatchPort = 3;
  private static int skiPort = 6;
  
  //Pneumatic solenoid numbers (VRM - Voltage Regulator Module)
  private static int gastonOpenPort = 1;
  private static int gastonClosedPort = 2; 

  //currently kimmie speedcontrollers
  public static SpeedController leftDrive = new Spark(leftDrivePort);
  public static SpeedController rightDrive = new Spark(rightDrivePort);
  public static SpeedController hatch = new Spark (hatchPort);
  public static SpeedController skis = new Spark (skiPort);
  
  //VRM
  public static DoubleSolenoid gaston = new DoubleSolenoid(gastonOpenPort, gastonClosedPort);

  //DIO
  public static int leftLightPort = 7;
  public static int middleLightPort = 8;
  public static int rightLightPort = 9;

  //Currently Kimmie light sensors
  public static DigitalInput leftLightSensor = new DigitalInput(leftLightPort);
  public static DigitalInput middleLightSensor = new DigitalInput(middleLightPort);
  public static DigitalInput rightLightSensor = new DigitalInput(rightLightPort);


  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}
