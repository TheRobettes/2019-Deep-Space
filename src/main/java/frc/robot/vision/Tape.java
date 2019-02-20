package frc.robot.vision;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;

public class Tape extends Rect implements Comparable {
    public boolean isPositive = false;

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
    private static boolean findSlope(MatOfPoint mop) {
        //for(int x = 0; x < mop.size(); x++) 
        {

        }
        return true;
    }
}
