package AnimationStudio3.Display.Joints;

import AnimationStudio3.PDouble;

import java.awt.*;

public class BetweenAngleBind extends AngleBind {

    private Joint firstJoint;
    private Joint middleJoint;
    private Joint lastJoint;

    public BetweenAngleBind(PDouble bound, Joint firstJoint, Joint middleJoint, Joint lastJoint) {
        super(bound);
        this.firstJoint = firstJoint;
        this.middleJoint = middleJoint;
        this.lastJoint = lastJoint;
    }

    @Override
    public double getAngle() {
        Point firstCenter = firstJoint.getCenter();
        Point middleCenter = middleJoint.getCenter();
        Point lastCenter = lastJoint.getCenter();

        Point toFirst = new Point(firstCenter.x - middleCenter.x, firstCenter.y - middleCenter.y);
        Point toLast = new Point(lastCenter.x - middleCenter.x, lastCenter.y - middleCenter.y);
//        return (2 * Math.PI) - ((Math.PI) - Math.atan2(toFirst.x * toLast.y - toFirst.y * toLast.x, toFirst.x * toLast.x + toFirst.y * toLast.y));

        double angle = (2 * Math.PI) - ((Math.PI) - Math.atan2(toFirst.x * toLast.y - toFirst.y * toLast.x, toFirst.x * toLast.x + toFirst.y * toLast.y));
        if (angle > Math.PI) {
            return -((2 * Math.PI) - angle);
        } else {
            return angle;
        }

//        return (Math.PI + Math.atan2(toFirst.x * toLast.y - toFirst.y * toLast.x, toFirst.x * toLast.x + toFirst.y * toLast.y));
    }

}
