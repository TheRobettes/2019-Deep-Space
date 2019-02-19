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

  private static final boolean IS_TEST_ROBOT = true ;


  public static final String KIMMIE = "Kimmie";
  public static final String VICTORIA = "Victoria";
  public static final String DEEPSPACE = "DeepSpace";
  
  private static boolean ISKIMMIE = false;
  private static boolean ISVICTORIA = false;

  //currently speedcontrollers
  public static SpeedController leftDrive;
  public static SpeedController rightDrive;
  public static SpeedController hatch;
  
  //VRM
  public static Solenoid gaston = null;
  //public static DoubleSolenoid brake = null;
  public static Solenoid gastonUpAndDown = null;
  
  
  //Analog
  private static int hatchPotentialPort = 0;

  //DIO Sensors
  public static DigitalInput leftLightSensor = null;
  public static DigitalInput middleLightSensor = null;
  public static DigitalInput rightLightSensor = null;
  public static Encoder leftDriveEncoder = null;
  public static Encoder rightDriveEncoder = null;

  
  
  //current gyroscope 
  public static Gyro gyro = new ADXRS450_Gyro();

  public RobotMap(String robotID){

    int leftDriveEncoderPort = 0, rightDriveEncoderPort = 2;

    if (robotID.equals (KIMMIE)){
      ISKIMMIE = false;

      leftDrive = new Spark(2);
      rightDrive = new Spark(8);
      hatch = new Spark (3);

      //DIO
      leftLightSensor = new DigitalInput(7);
      middleLightSensor = new DigitalInput(8);
      rightLightSensor = new DigitalInput(9);

     //pneumatics
      gaston = new Solenoid(1); 
      //brake = new Solenoid(4, 5);
      gastonUpAndDown = new Solenoid(3);
    }

    else if (robotID.equals (VICTORIA)){
      ISVICTORIA = true;

      //TODO: Victoria may have different encoders than the current testing robots
       leftDriveEncoderPort = 6;
       rightDriveEncoderPort = 4;

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
  
      }

    else //DEEP SPACE
    {

      String robotName = (IS_TEST_ROBOT) ? "PRACTICE" : "COMPETITION";
      System.out.println(" Creating adapters for: " + robotName);

      /* ...old way...
      leftDrive = new Spark(0);
      hatch = new Spark (2);
      rightDrive = new Spark (1);
      leftDrive = new CANSparkMax(4,MotorType.kBrushless);
      //rightDrive = new CANSparkMax(6,MotorType.kBrushless);
      //hatch = new CANSparkMax (2,MotorType.kBrushed);
      */

      //  .... NEW WAY !! ...
      leftDrive = new SpeedControllerGroup(
        tryNewSparkMax(4,MotorType.kBrushless, false ),
        tryNewSparkMax(5,MotorType.kBrushless, false)
          );

      rightDrive = new SpeedControllerGroup(
        tryNewSparkMax(6,MotorType.kBrushless, true),
        tryNewSparkMax(7,MotorType.kBrushless, true)
           );
      
      hatch = tryNewSparkMax (3,MotorType.kBrushed, false);

      gaston = new Solenoid(1); 
     // brake = new DoubleSolenoid(4, 5);
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
  private SpeedController tryNewSparkMax(int port, MotorType sparkMaxType, boolean invert) {
    SpeedController newMotor = tryNewSparkMax(port, sparkMaxType);
    newMotor.setInverted(invert);
    return newMotor;
  }
  private SpeedController tryNewSparkMax(int port, MotorType sparkMaxType) {
    SpeedController newMotor;
    try {
      if(port > 10) {
        throw new Exception("Skipping CAN motor: " + port);
      }

      // try this step, knowing it might fail.
      newMotor = new CANSparkMax(port, sparkMaxType);

    } catch(Exception myCANException) {

      // record the failure
      myCANException.printStackTrace();

      // also continue to make a motor-instance anyway!
      newMotor = new Spark(port);
    }

    return newMotor;
  }
  public static boolean isKimmie(){
    return ISKIMMIE;
  }
  public static boolean isVictoria(){
    return ISVICTORIA;
  }
}
