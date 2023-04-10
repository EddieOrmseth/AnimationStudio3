package AnimationStudio3.Display.Joints;

import AnimationStudio3.Display.LazyMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class Joint extends JRadioButton {

    boolean isClicked;
    protected int mouseXOffset = 0;
    protected int mouseYOffset = 0;

    public Joint() {
        addMouseListener(new MovementMouseListener());
        setSize(16, 14);
    }

    public Point getCenter() {
        return new Point(getCenterX(), getCenterY());
    }

    public int getCenterX() {
        return getX() + getWidth() / 2;
    }

    public int getCenterY() {
        return getY() + getHeight() / 2;
    }

//    @Override
//    public void setLocation(Point point) {
//        setLocation(point.x, point.y);
//    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        if (isClicked) {
            Point rawMousePos = MouseInfo.getPointerInfo().getLocation();
            Point adjustedMousePos = new Point(rawMousePos.x + mouseXOffset, rawMousePos.y + mouseYOffset);
            setLocation(calcClickedNewPosition(adjustedMousePos));
        } else {
            setLocation(calcNewPosition());
        }
    }

    protected abstract Point calcNewPosition();

    protected abstract Point calcClickedNewPosition(Point newMousePos);

    protected class MovementMouseListener implements LazyMouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
            isClicked = true;
            setSelected(true);
            mouseXOffset = getX() - e.getXOnScreen();
            mouseYOffset = getY() - e.getYOnScreen();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            isClicked = false;
            setSelected(false);
        }
    }

}
