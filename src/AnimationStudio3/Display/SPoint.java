package AnimationStudio3.Display;

import AnimationStudio3.PDouble;

public class SPoint {

    private final PDouble x;
    private final PDouble y;

    public SPoint(double x, double y) {
        this.x = new PDouble(x);
        this.y = new PDouble(y);
    }

    public SPoint(PDouble x, PDouble y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x.val = x;
    }

    public void setY(double y) {
        this.y.val = y;
    }

    public PDouble getX() {
        return x;
    }

    public PDouble getY() {
        return y;
    }

    public SPoint clone() {
        return new SPoint(x.val, y.val);
    }

}
