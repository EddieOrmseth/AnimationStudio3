package AnimationStudio3.Display.Joints;

import AnimationStudio3.PDouble;

public abstract class AngleBind {

    private PDouble bound;

    private final double PIOverTwo = Math.PI / 2.0;
    private final double PI = Math.PI;
    private final double TwoPI = 2.0 * Math.PI;


    public AngleBind(PDouble bound) {
        this.bound = bound;
    }

    public void update() {
        double oldVal = domainZeroToTwoPI(bound.val);
        double newVal = domainZeroToTwoPI(getAngle());
        double deltaVal;

        if (Math.abs(oldVal - newVal) < PI) {
            deltaVal = newVal - oldVal;
        } else {
            oldVal = domainNegPIToPI(oldVal);
            newVal = domainNegPIToPI(newVal);

            deltaVal = newVal - oldVal;
        }

        bound.val += deltaVal;
    }

    public double domainZeroToTwoPI(double radians) {
        double PI2 = 2 * Math.PI;
        return (((radians % PI2) + PI2) % PI2);
    }

    public double domainNegPIToPI(double radians) {
        radians = domainZeroToTwoPI(radians);
        if (radians <= Math.PI) {
            return radians;
        } else {
            return -(PI - (radians % Math.PI));
        }
    }

    public double getVal() {
        return bound.val;
    }

    public abstract double getAngle();

}
