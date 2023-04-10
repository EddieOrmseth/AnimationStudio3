package AnimationStudio3.Display.Joints;

import AnimationStudio3.PDouble;
import AnimationStudio3.Display.SPoint;

import java.awt.*;

public class FreeJoint extends Joint {

    private PDouble xBind;
    private PDouble yBind;

    private SPoint offset = new SPoint(0.0, 0.0);

    public FreeJoint(PDouble xBind, PDouble yBind) {
        this.xBind = xBind;
        this.yBind = yBind;
        setLocation((int) (xBind.val - getWidth() / 2.0), (int) (yBind.val - getHeight() / 2.0));
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        if (isSelected()) {
            xBind.val = (getX() + (getWidth() / 2.0)) - offset.getX().val;
            yBind.val = (getY() + (getHeight() / 2.0)) - offset.getY().val;
        } else {
            setLocation((int) (xBind.val + offset.getX().val - getWidth() / 2.0), (int) (yBind.val + offset.getY().val - getHeight() / 2.0));
        }
    }

    @Override
    protected Point calcNewPosition() {
        return getLocation();
    }

    @Override
    protected Point calcClickedNewPosition(Point newMousePos) {
        return newMousePos;
    }

    public void setOffset(SPoint offset) {
        this.offset = offset;
        setLocation((int) (xBind.val + offset.getX().val - getWidth() / 2.0), (int) (yBind.val + offset.getY().val - getHeight() / 2.0));
    }

    public PDouble getPX() {
        return xBind;
    }

    public PDouble getPY() {
        return yBind;
    }

}
