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
import edu.wpi.first.wpilibj.Solenoid;
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

  public static final boolean IS_PRACTICE_ROBO = false;
  
  public static final String KIMMIE = "Kimmie";
  public static final String VICTORIA = "Victoria";
  
  private static boolean ISKIMMIE = false;
  private static boolean ISVICTORIA = false;

  //currently speedcontrollers
  public static SpeedController leftDrive;
  public static SpeedController rightDrive;
  public static SpeedController hatch;
  
  //VRM
  public static Solenoid gaston = null;
  public static Solenoid brake = null;
  public static Solenoid gastonUpAndDown = null;
  
  
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

    int leftDriveEncoderPort = 0, rightDriveEncoderPort = 2;

    if (robotID.equals (KIMMIE)){
      ISKIMMIE = true;

      leftDrive = new Spark(2);
      rightDrive = new Spark(8);
      hatch = new Spark (3);

      //DIO
      leftLightSensor = new DigitalInput(7);
      middleLightSensor = new DigitalInput(8);
      rightLightSensor = new DigitalInput(9);

      leftDriveEncoder = new Encoder(leftDriveEncoderPort, leftDriveEncoderPort+1);
      rightDriveEncoder = new Encoder(rightDriveEncoderPort, rightDriveEncoderPort+1);

    }
    else if (robotID.equals (VICTORIA)){
      ISVICTORIA = true;

      leftDriveEncoderPort = 4;
      rightDriveEncoderPort = 6;

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
        leftDriveEncoder = new Encoder(leftDriveEncoderPort, leftDriveEncoderPort+1);
        rightDriveEncoder = new Encoder(rightDriveEncoderPort, rightDriveEncoderPort+1);

      }

    else 
    {
      leftDrive = new CANSparkMax(4,MotorType.kBrushless);
      //rightDrive = new CANSparkMax(6,MotorType.kBrushless);
      //hatch = new CANSparkMax (2,MotorType.kBrushed);
      

      //  .... NEW WAY !! ...
      leftDrive = new SpeedControllerGroup(
        tryNewSparkMax(4,MotorType.kBrushless, IS_PRACTICE_ROBO ),
        tryNewSparkMax(5,MotorType.kBrushless, IS_PRACTICE_ROBO )
          );

      rightDrive = new SpeedControllerGroup(
        tryNewSparkMax(6,MotorType.kBrushless, !IS_PRACTICE_ROBO ),
        tryNewSparkMax(7,MotorType.kBrushless, !IS_PRACTICE_ROBO )
           );
      
      hatch = tryNewSparkMax (3,MotorType.kBrushed, false);

      gaston = new Solenoid(1); 
      brake = new Solenoid (2);
      gastonUpAndDown = new Solenoid(3);
    
    }

    //DIO
    leftDriveEncoder = new Encoder(leftDriveEncoderPort, leftDriveEncoderPort+1);
    rightDriveEncoder = new Encoder(rightDriveEncoderPort, rightDriveEncoderPort+1);
    leftDriveEncoder.reset();
    rightDriveEncoder.reset();
    
    if(isVictoria()){
      rightDriveEncoder.setReverseDirection(true);
    }
  }
  public static boolean isKimmie(){
    return ISKIMMIE;
  }
  public static boolean isVictoria(){
    return ISVICTORIA;
  }

  private SpeedController tryNewSparkMax(int port, MotorType motorType, boolean invert) {
    SpeedController motor = tryNewSparkMax(port, motorType);
    motor.setInverted(invert);
    return motor;
  }

  private SpeedController tryNewSparkMax(int port, MotorType motorType) {
    SpeedController motor;
   
    // First, attempt connecting to an expected CANID...
    try {
        motor = new CANSparkMax(port, motorType);
    }

    //  ... if that failed for any reason, do next-new statement as an fault-correction.
    catch (Exception ex) {
      ex.printStackTrace();
      motor = new Spark(port);
    }

    return motor;
  }
}
