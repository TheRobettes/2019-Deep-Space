/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.sparkMaxVelocity.TeamDriveMotor;
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
  public static final String DEEPSPACE = "Aisha";
  
  private static boolean ISKIMMIE = false;
  private static boolean ISVICTORIA = false;

  //currently speedcontrollers
  public static SpeedController leftDrive;
  public static SpeedController rightDrive;
  public static SpeedController hatch;
  public static SpeedController cargoHandler;
  
  //VRM
  public static Solenoid gaston = null;
  public static Solenoid brake = null;
  public static Solenoid gastonUpAndDown = null;
  

  //DIGITAL Sensors
  public static DigitalInput leftLightSensor = null;
  public static DigitalInput middleLightSensor = null;
  public static DigitalInput rightLightSensor = null;
  public static Encoder leftDriveEncoder = null;
  public static Encoder rightDriveEncoder = null;
  public static CANEncoder hatchEncoder = null;
    
  //current gyroscope 
  public static Gyro gyro = new ADXRS450_Gyro();

  public RobotMap(String robotID){

    if (robotID.equals(DEEPSPACE))
      robotID += (IS_PRACTICE_ROBO) ? " (practice)" : " (COMP!)" ; 
    System.out.println("starting RobotMap for " + robotID);
    
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
  
      }

    else 
    // for Aisha 
    {

      /*
      OLD WAY

      leftDrive = new CANSparkMax(4,MotorType.kBrushless);
      rightDrive = new CANSparkMax(6,MotorType.kBrushless);
      hatch = new CANSparkMax (2,MotorType.kBrushed);
      */ 

      //  .... NEW WAY !! ...
      leftDrive = new SpeedControllerGroup(
        tryNewSparkMax(4,MotorType.kBrushless, !IS_PRACTICE_ROBO ), 
        tryNewSparkMax(5,MotorType.kBrushless, !IS_PRACTICE_ROBO)
          );

      rightDrive = new SpeedControllerGroup(
        tryNewSparkMax(6,MotorType.kBrushless, (IS_PRACTICE_ROBO)? IS_PRACTICE_ROBO: !IS_PRACTICE_ROBO ), //Aisha = !IS_PRACTICE_ROBO
        tryNewSparkMax(7,MotorType.kBrushless, (IS_PRACTICE_ROBO)? IS_PRACTICE_ROBO: !IS_PRACTICE_ROBO )
           );
      
      hatch = tryNewSparkMax (2,MotorType.kBrushed);
      

      //left and right intake motors combined as one
      this.cargoHandler = new SpeedControllerGroup(
          tryNewSparkMax(3,MotorType.kBrushed),
          tryNewSparkMax(1,MotorType.kBrushed)
          );

      //set scale values for the hatchEncoder
      hatchEncoder.setPositionConversionFactor(4000);

      gaston = new Solenoid(1) {
        @Override
        public void set(boolean on){
          super.set( !on );
        }
      }; 

      brake = new Solenoid (2);
      gastonUpAndDown = new Solenoid(3);

      

    
    }

    //this is for all robots 

    //DIO
    leftDriveEncoder = new Encoder(leftDriveEncoderPort, leftDriveEncoderPort+1);
    rightDriveEncoder = new Encoder(rightDriveEncoderPort, rightDriveEncoderPort+1);
    leftDriveEncoder.reset();
    rightDriveEncoder.reset();
    
    if(isVictoria()){
      rightDriveEncoder.setReverseDirection(true);
      leftDriveEncoder.setReverseDirection(true);

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
    CANSparkMax motor = null;
   
    // First, attempt connecting to an expected CANID...
    try {
     
      /*
      * Check for Optional call-values to signal missing CAN motors...
      *          (to review what happens with try/catch statements)
      *
      if (port > 10)
        throw new Exception("Skipping " + port);
      */

      //initializing team drive motors versus hatch arm motor 
      motor = 
        ( motorType == MotorType.kBrushless)  
            ? new TeamDriveMotor(port) //if brushed- Use Spark-MAX for velocity control
            : new CANSparkMax(port, motorType); // else - regular for hatch

      //initialize the hatch encoder to the correct motor
      if (port == 3){
        this.hatchEncoder = motor.getEncoder(EncoderType.kQuadrature, 0);
      }      
    }

    //  ... if that failed for any reason, do next-new statement as an fault-correction.
    catch (Exception ex) {
      ex.printStackTrace();
      //motor = new SparkMax(port);
    }

    return motor;
  }
}
