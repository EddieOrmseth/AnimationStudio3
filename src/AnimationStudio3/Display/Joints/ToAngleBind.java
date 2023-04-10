package AnimationStudio3.Display.Joints;

import AnimationStudio3.PDouble;

import java.awt.*;

public class ToAngleBind extends AngleBind {

    private Joint firstJoint;
    private Joint nextJoint;

    public ToAngleBind(PDouble bound, Joint firstJoint, Joint nextJoint) {
        super(bound);
        this.firstJoint = firstJoint;
        this.nextJoint = nextJoint;
    }

    @Override
    public double getAngle() {
        Point firstCenter = firstJoint.getCenter();
        Point nextCenter = nextJoint.getCenter();

        return Math.atan2(nextCenter.y - firstCenter.y, nextCenter.x - firstCenter.x);
    }

}
