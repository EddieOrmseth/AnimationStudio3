package AnimationStudio3.Display.Joints;

import java.awt.*;

public class LengthJoint extends Joint {

    protected Joint relativeJoint;
    protected Point relativeVector;
    protected double distance;

    public LengthJoint(Joint relativeJoint, Point relativeVector) {
        this.relativeJoint = relativeJoint;
        this.relativeVector = relativeVector;
        this.distance = getMagnitude(relativeVector);
        setLocation(relativeJoint.getX() + relativeVector.x, relativeJoint.getY() + relativeVector.y);
    }

    @Override
    protected Point calcNewPosition() {
        Point newLoc = relativeJoint.getLocation();
        newLoc.x += relativeVector.x;
        newLoc.y += relativeVector.y;

        return newLoc;
    }

    @Override
    protected Point calcClickedNewPosition(Point newMousePos) {
        Point relativeLoc = relativeJoint.getLocation();
        Point difference = new Point(newMousePos.x - relativeLoc.x, newMousePos.y - relativeLoc.y);
        double magChange = distance / getMagnitude(difference);

        relativeVector = new Point((int) (difference.x * magChange), (int) (difference.y * magChange));
        Point newLoc = new Point(relativeLoc);
        newLoc.x += relativeVector.x;
        newLoc.y += relativeVector.y;

        return newLoc;
    }

    public double getMagnitude(Point point) {
        return Math.pow((point.x * point.x) + (point.y * point.y), .5);
    }

}
