package AnimationStudio3.Display;

import AnimationStudio3.AnimationData;
import AnimationStudio3.Display.Joints.FreeJoint;
import AnimationStudio3.Display.Joints.JointDisplay;
import AnimationStudio3.Vector;

import javax.swing.*;
import java.awt.*;

public abstract class Renderer extends JComponent {

    public AnimationData animationData;
    public SPoint offset;

    protected FreeJoint offsetJoint;

    public Renderer(AnimationData animationData, SPoint offset) {
        this.animationData = animationData;
        this.offset = offset == null ? new SPoint(0.0, 0.0) : offset;
        setLayout(null);
        offsetJoint = new FreeJoint(this.offset.getX(), this.offset.getY());
    }

    @Override
    public void paint(Graphics graphics) {
        if (animationData != null) {
            renderAt(graphics, animationData, offset);
        }
    }

    public FreeJoint getOffsetJoint() {
        return offsetJoint;
    }

    public abstract void renderAt(Graphics graphics, AnimationData data, SPoint point);

    public abstract JointDisplay getJointDisplayFor(AnimationData data);

    public void paintAtAndTo(Graphics graphics, Vector p, Vector v) {
        graphics.drawLine((int) p.getX(), (int) p.getY(), (int) (p.getX() + v.getX()), (int) (p.getY() + v.getY()));
    }

    public void paintAtAndAt(Graphics graphics, Vector p1, Vector p2) {
        graphics.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
    }

    protected Point fromPolar(double magnitude, double theta) {
        return new Point((int) (magnitude * Math.cos(theta)), (int) (magnitude * Math.sin(theta)));
    }

    public AnimationData getFlipRightAndLeftData(AnimationData data) {
        return flipRightAndLeft(data.clone());
    }

    protected AnimationData flipRightAndLeft(AnimationData data) {
        return null;
    }

    protected void swap(AnimationData data, int idx1, int idx2) {
        double tmp = data.get(idx1).val;
        data.set(idx1, data.get(idx2).val);
        data.set(idx2, tmp);
    }

}
