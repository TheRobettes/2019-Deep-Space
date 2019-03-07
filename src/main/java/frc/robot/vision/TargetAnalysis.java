/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import java.util.ArrayList;
import java.util.Collections;
import org.opencv.core.Rect;

/**
 * Add your docs here.
 */
public class TargetAnalysis {

    private static final double FIELD_OF_VIEW_ADJUSTMENT = 16.0/29; //result taken from "LifeCam" camera

    public static boolean foundTarget;

    public static double targetAngleFromVision;
    public static double  targetWidthPct;

    public static final long cameraCenterX = Snapshot.IMG_WIDTH / 2;

    public static void updateValues() {

        Rect targetTheLeft = null;
        Rect targetTheRight = null;

        ArrayList<Tape> rectangles = Snapshot.getContours();
        int count = rectangles.size();

        if(count == 1){
            Tape r = rectangles.get(0);
            targetWidthPct = 100.0 * (2 * r.width) / Snapshot.IMG_WIDTH;
            double gapCenter = (r.isPositive) ?r.x + (r.width * 2): r.x - (r.width * 2);
            
            double targetXOffset = (long)( 100.0 * (gapCenter - cameraCenterX)) / Snapshot.IMG_WIDTH;
            targetAngleFromVision = FIELD_OF_VIEW_ADJUSTMENT*targetXOffset;
            foundTarget = true;
            return;
        }

        foundTarget = false;
        Collections.sort(rectangles);
        for (int x = 0; x < count; x++) {
            Tape r = rectangles.get(x);

            if (targetTheLeft == null && r.isPositive) {
                targetTheLeft = r;
                //System.out.println("LeftX " + r.x);
            }

            else if (targetTheRight == null && !r.isPositive && targetTheLeft != null) {
                targetTheRight = r;
                //System.out.println("RightX " + r.x);
            }

            else {
                //System.out.println("Skipped Contours " + r.x + " " + r.isPositive);
            }

        }

        if (targetTheRight != null) {
            foundTarget = true;
            double innerEdgeLeftContour = targetTheLeft.x + targetTheLeft.width;
            double innerEdgeRightContour = targetTheRight.x;

            // System.out.println("INNER LEFT EDGE: " + innerEdgeLeftContour + " INNER RIGHT
            // EDGE: " + innerEdgeRightContour);

            // inner right - inner left = space between contours
            targetWidthPct = 100.0 * (innerEdgeRightContour - innerEdgeLeftContour) / Snapshot.IMG_WIDTH;

            double gapCenter = (innerEdgeRightContour + innerEdgeLeftContour)/2;
            double targetXOffset = (long)( 100.0 * (gapCenter - cameraCenterX)) / Snapshot.IMG_WIDTH;
            targetAngleFromVision = FIELD_OF_VIEW_ADJUSTMENT*targetXOffset;
        }
    }

}
