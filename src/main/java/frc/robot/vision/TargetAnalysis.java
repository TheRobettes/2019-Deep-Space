/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import java.util.ArrayList;

import org.opencv.core.Rect;

/**
 * Add your docs here.
 */
public class TargetAnalysis {
    public static boolean foundTarget;

    public static double targetXOffset;
    public static double  targetWidthPct;

    public static final long cameraCenterX = Snapshot.IMG_WIDTH/2;

    public static void updateValues() {
        Rect targetTheFirst = null;
        Rect targetTheSecond = null;

        ArrayList<Rect> rectangles = Snapshot.getContours();
        int count = rectangles.size();

        foundTarget = false;
        for(int x = 0; x < count; x++) {
            Rect r = rectangles.get(x);

            double aspectRatio = (double) r.width/r.height;
            if(!foundTarget && aspectRatio > 1.75) {
                foundTarget = true;
                targetWidthPct = 100.0 * r.width / Snapshot.IMG_WIDTH;
                long rectCenter = r.x + (r.width/2);
                targetXOffset = (long)( 100.0 * (cameraCenterX - rectCenter)) / Snapshot.IMG_WIDTH;
            }
        }


    }




}
