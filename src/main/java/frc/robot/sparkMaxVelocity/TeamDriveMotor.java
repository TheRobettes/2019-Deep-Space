package frc.robot.sparkMaxVelocity;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class TeamDriveMotor extends CANSparkMax{

    public TeamDriveMotor(int portNumber) {
        super(portNumber, MotorType.kBrushless);

        /* ...Besides from vex-api documentations...
        *
        *   This method and its purpose and result is also described in 
        *    ChiefDelphi / Technical / Programming:
        *       "Spark Max Ramp Rate"
        *
        */
         this.setClosedLoopRampRate( 0.05 );
    }
}