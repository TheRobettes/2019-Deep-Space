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

    private static final double FIELD_OF_VIEW_ADJUSTMENT = 16.0/29; //result taken from "LifeCam" camera

    public static boolean foundTarget;

    public static double targetAngleFromVision;
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

            if(targetTheFirst == null) {
                targetTheFirst = r;
            }

            else if(targetTheSecond == null) {
                targetTheSecond = r;
            }
        }


        if(targetTheSecond != null) {
            double innerEdgeLeftContour = targetTheFirst.x + targetTheFirst.width; 
            double innerEdgeRightContour = targetTheSecond.x;

            //System.out.println("INNER LEFT EDGE: " + innerEdgeLeftContour + " INNER RIGHT EDGE: " + innerEdgeRightContour);


            if (innerEdgeRightContour < innerEdgeLeftContour) {
                innerEdgeLeftContour = targetTheSecond.x + targetTheSecond.width;
                innerEdgeRightContour = targetTheFirst.x;
                //System.out.println("     INNER LEFT EDGE: " + innerEdgeLeftContour + " INNER RIGHT EDGE: " + innerEdgeRightContour);

            }


            //inner right - inner left = space between contours
            targetWidthPct = 100.0 * (innerEdgeRightContour - innerEdgeLeftContour) / Snapshot.IMG_WIDTH;

            double gapCenter = (innerEdgeRightContour + innerEdgeLeftContour)/2;
            double targetXOffset = (long)( 100.0 * (gapCenter - cameraCenterX)) / Snapshot.IMG_WIDTH;
            targetAngleFromVision = FIELD_OF_VIEW_ADJUSTMENT*targetXOffset;
        }

    }




}
