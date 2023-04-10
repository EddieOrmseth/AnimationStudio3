package AnimationStudio3;

import AnimationStudio3.Display.SPoint;

public class Vector {

    private double x = 0;
    private double y = 0;

    public static Vector fromCartesian(double x, double y) {
        Vector vector = new Vector();
        vector.x = x;
        vector.y = y;

        return vector;
    }

    public static Vector fromPolar(double theta, double magnitude) {
        Vector vector = new Vector();
        vector.x = magnitude * Math.cos(theta);
        vector.y = magnitude * Math.sin(theta);

        return vector;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getTheta() {
        return Math.atan2(y, x);
    }

    public void setTheta(double theta) {
        double magnitude = getMagnitude();
        x = magnitude * Math.cos(theta);
        y = magnitude * Math.sin(theta);
    }

    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector setMagnitude(double magnitude) {
        double fraction = magnitude / getMagnitude();
        x *= fraction;
        y *= fraction;

        return this;
    }

    public Vector add(Vector vector) {
        x += vector.getX();
        y += vector.getY();

        return this;
    }

    public Vector add(SPoint point) {
        x += point.getX().val;
        y += point.getY().val;

        return this;
    }

    public Vector addTheta(double addedTheta) {
        setTheta(addedTheta + getTheta());

        return this;
    }

    public Vector mult(double scalar) {
        x *= scalar;
        y *= scalar;

        return this;
    }

    public Vector clone() {
        return Vector.fromCartesian(x, y);
    }

}
