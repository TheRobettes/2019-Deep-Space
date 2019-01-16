/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Snapshot {
    private static final int FPS = 15; //frames per second 
    private static final int IMG_WIDTH = 160;
    private static final int IMG_HEIGHT = 120;
    private static final int TELEOP_EXPOSURE = 15;
    private static UsbCamera camera1 = null;
    
public static void cameraInit() {
    camera1 = CameraServer.getInstance().startAutomaticCapture(0);
    camera1.setResolution(IMG_WIDTH, IMG_HEIGHT);
    camera1.setFPS(FPS);
    camera1.setExposureManual(TELEOP_EXPOSURE);
    
    }
}
