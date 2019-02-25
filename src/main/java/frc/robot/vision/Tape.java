package frc.robot.vision;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;

public class Tape extends Rect implements Comparable {
    public boolean isPositive;

    public Tape(Rect r, MatOfPoint mop) {
        super(r.x, r.y, r.width, r.height);
        this.isPositive = findSlope(mop);
    }

    @Override
    public int compareTo(Object otherObject) {
        Tape otherTape = (Tape) otherObject; // treat "otherObject" like a "Tape" type
        // Calculating negative whenever "otherTape" is to the left of "this"
        return otherTape.x - this.x;
    }
    private boolean findSlope(MatOfPoint mop) {
        int rightMostX = 0;
        int rightMostY = 0;
        Point[] points  = mop.toArray();
        for(int n = 0; n < points.length; n++) //n = "number"
        {
            Point currentPoint = points[n];
            if(currentPoint.x > rightMostX) {
                rightMostY = (int)currentPoint.y;
                rightMostX = (int)currentPoint.x;
            }
        }
        int verticalMidpoint = (this.height/2) + this.y;
        boolean calculatedSlope = rightMostY < verticalMidpoint;
        System.out.println("FindSlopeDataPoints: (" + rightMostX + " , " + rightMostY + ") "
         + verticalMidpoint + " " + calculatedSlope);
        return calculatedSlope;
    }
}
