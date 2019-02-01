/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Gyro;

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
  
  //Pneumatic solenoid numbers (VRM - Voltage Regulator Module)
  private static int gastonOpenPort = 1;
  private static int gastonClosedPort = 2; 
  private static int brakeOn = 3;
  private static int brakeOff = 4;
  private static int skisExtend = 5;

  //currently kimmie speedcontrollers
  public static SpeedController leftDrive = new Spark(leftDrivePort);
  public static SpeedController rightDrive = new Spark(rightDrivePort);
  public static SpeedController hatch = new Spark (hatchPort);
  
  //VRM
  public static DoubleSolenoid gaston = new DoubleSolenoid(gastonOpenPort, gastonClosedPort);
  public static DoubleSolenoid brake = new DoubleSolenoid(brakeOn, brakeOff);
  public static DoubleSolenoid skis = new DoubleSolenoid(skisExtend , 0); //TODO: verify if this is a double or single
  
  //DIO
  private static int leftLightPort = 7;
  private static int middleLightPort = 8;
  private  static int rightLightPort = 9;
  private static int leftDriveEncoderPort = 0;
  private static int rightDriveEncoderPort = 2;
  
  //Analog
  private static int hatchPotentialPort = 0;

  //Currently Kimmie light sensors
  public static DigitalInput leftLightSensor = new DigitalInput(leftLightPort);
  public static DigitalInput middleLightSensor = new DigitalInput(middleLightPort);
  public static DigitalInput rightLightSensor = new DigitalInput(rightLightPort);
  public static Encoder leftDriveEncoder = new Encoder(leftDriveEncoderPort, leftDriveEncoderPort+1);
  public static Encoder rightDriveEncoder = new Encoder(rightDriveEncoderPort, rightDriveEncoderPort+1);

  public static AnalogPotentiometer hatchpotential = new AnalogPotentiometer(hatchPotentialPort);
  
  //current Kimmie gyroscope 
  public static Gyro gyro = new ADXRS450_Gyro();


  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}
