/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.vision.Snapshot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

  public static final String KIMMIE = "Kimmie";
  public static final String VICTORIA = "Victoria";
  
  private static boolean ISKIMMIE = false;
  private static boolean ISVICTORIA = false;

  //currently speedcontrollers
  public static SpeedController leftDrive;
  public static SpeedController rightDrive;
  public static SpeedController hatch;
  
  //VRM
  public static DoubleSolenoid gaston = null;
  public static DoubleSolenoid brake = null;
  public static DoubleSolenoid skis = null;
  
  
  //Analog
  private static int hatchPotentialPort = 0;

  //DIO Sensors
  public static DigitalInput leftLightSensor = null;
  public static DigitalInput middleLightSensor = null;
  public static DigitalInput rightLightSensor = null;
  public static Encoder leftDriveEncoder = null;
  public static Encoder rightDriveEncoder = null;

  public static AnalogPotentiometer hatchpotential = new AnalogPotentiometer(hatchPotentialPort);
  
  //current gyroscope 
  public static Gyro gyro = new ADXRS450_Gyro();

  public RobotMap(String robotID){
    if (robotID.equals (KIMMIE)){
      ISKIMMIE = true;

      leftDrive = new Spark(2);
      rightDrive = new Spark(8);
      hatch = new Spark (3);

      //DIO
      leftLightSensor = new DigitalInput(7);
      middleLightSensor = new DigitalInput(8);
      rightLightSensor = new DigitalInput(9);

      int leftDriveEncoderPort = 0, rightDriveEncoderPort = 2;
      leftDriveEncoder = new Encoder(leftDriveEncoderPort, leftDriveEncoderPort+1);
      rightDriveEncoder = new Encoder(rightDriveEncoderPort, rightDriveEncoderPort+1);

     //pneumatics
      gaston = new DoubleSolenoid(0, 1);
      brake = new DoubleSolenoid(2, 3);
      skis = new DoubleSolenoid(4 , 5);
    }
    else if (robotID.equals (VICTORIA)){
      ISVICTORIA = true;

        SpeedController leftFront = new Spark(0);
        leftFront.setInverted(false);
        SpeedController rightFront = new Spark(7);
        rightFront.setInverted(true);
        SpeedController leftBack = new Spark(3);
        leftBack.setInverted(false);
        SpeedController rightBack = new Spark(4);
        rightBack.setInverted(false);
        leftDrive = new SpeedControllerGroup (leftFront, leftBack);
        rightDrive = new SpeedControllerGroup(rightFront, rightBack);
        hatch = new Spark (13);
  
        //DIO
  
        int leftDriveEncoderPort = 0, rightDriveEncoderPort = 2;
        leftDriveEncoder = new Encoder(leftDriveEncoderPort, leftDriveEncoderPort+1);
        rightDriveEncoder = new Encoder(rightDriveEncoderPort, rightDriveEncoderPort+1);

      }

    else 
    {
      leftDrive = new CANSparkMax(4,MotorType.kBrushless);
      rightDrive = new CANSparkMax(6,MotorType.kBrushless);
      hatch = new CANSparkMax (2,MotorType.kBrushed);
    }
  }
  public static boolean isKimmie(){
    return ISKIMMIE;
  }
  public static boolean isVictoria(){
    return ISVICTORIA;
  }
}
