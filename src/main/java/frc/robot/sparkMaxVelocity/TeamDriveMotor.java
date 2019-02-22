package frc.robot.sparkMaxVelocity;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeamDriveMotor extends CANSparkMax{
    private CANPIDController m_pidController;
    private CANEncoder m_encoder;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;
    public int ourCanID;

    public TeamDriveMotor(int portNumber) {
        super(portNumber, MotorType.kBrushless);
        ourCanID = portNumber;

        //we don't really know what it does nor why it is giving us angry red marks 
        // but we found this in VEX sample code so we just put it in
       // this.restoreFactoryDefaults(); 
        //retry this after we update the VEX API and if it breaks try something else
    /**
     * In order to use PID functionality for a controller, a CANPIDController object
     * is constructed by calling the getPIDController() method on an existing
     * CANSparkMax object
     */
    m_pidController = this.getPIDController();

    // Encoder object created to display position values
    m_encoder = this.getEncoder();

    // PID coefficients
    kP = 5e-5; 
    kI = 1e-6;
    kD = 0; 
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 5700;

    // set PID coefficients
    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);

    }

    @Override
    public void set(double speed){
        double velocitySetPoint = speed * maxRPM;
        m_pidController.setReference(velocitySetPoint, ControlType.kVelocity);
        SmartDashboard.putNumber("Spark " + ourCanID, velocitySetPoint );
        SmartDashboard.putNumber("Encoder " + ourCanID, m_encoder.getVelocity());
    }
}