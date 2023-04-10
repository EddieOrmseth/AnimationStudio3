package AnimationStudio3.Display.Joints;

import AnimationStudio3.Animation;
import AnimationStudio3.AnimationData;
import AnimationStudio3.Display.LazyMouseListener;
import AnimationStudio3.Display.Renderer;
import AnimationStudio3.Display.SPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class JointDisplayManager extends JComponent {

    private Animation animation;
    private boolean running = false;
    private Renderer renderer;
//    private JButton reflectButton;

    private AnimationData displayedData;
    private JointDisplay displayedJointDisplay;

    private SPoint offset;
    private FreeJoint offsetJoint;

    private SPoint renderOffset;

    public JointDisplayManager(Animation animation, Renderer renderer, FreeJoint offsetJoint) {
        this.animation = animation;
        this.renderer = renderer;

        this.offsetJoint = offsetJoint;

        offset = new SPoint(0.0, 0.0);
        renderOffset = new SPoint(0.0, 0.0);

//        reflectButton = new JButton("Reflect");
//        reflectButton.setBounds(getWidth() - 50, getHeight() - 50, 50, 50);
//        reflectButton.addMouseListener(new LazyMouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//
//            }
//        });
//        add(reflectButton);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        renderOffset.setX(offset.getX().val);
        renderOffset.setY(offset.getY().val);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        renderer.setBounds(0, 0, width, height);
//        reflectButton.setBounds(getWidth() - (75 + 1), 1, 75, 30);
    }

    public void setDisplayedThings(AnimationData animationData, JointDisplay jointDisplay) {
        if (displayedJointDisplay != null) {
            remove(displayedJointDisplay);
            displayedJointDisplay.remove(renderer);
            displayedJointDisplay.remove(offsetJoint);

//            for (int i = 0; i < displayedJointDisplay.getJoints().size(); i++) {
//                Joint joint = displayedJointDisplay.getJoints().get(i);
//
//                if (joint instanceof FreeJoint) {
//                    ((FreeJoint) joint).setOffset(offset.clone());
//                }
//            }
        } else {
            remove(renderer);
            running = false;
        }

        if (jointDisplay != null) {
            jointDisplay.setBounds(0, 0, getWidth(), getHeight());
            add(jointDisplay);
            jointDisplay.add(renderer);
            renderer.setBounds(0, 0, getWidth(), getHeight());
            jointDisplay.add(offsetJoint);

//            for (int i = 0; i < jointDisplay.getJoints().size(); i++) {
//                Joint joint = jointDisplay.getJoints().get(i);
//
//                if (joint instanceof FreeJoint) {
//                    ((FreeJoint) joint).setOffset(offset);
//                }
//            }
        } else {
            add(renderer);
            running = true;
        }

        renderer.animationData = animationData;
        this.displayedData = animationData;
        displayedJointDisplay = jointDisplay;
    }

    public JointDisplay getDisplayedJointDisplay() {
        return displayedJointDisplay;
    }

    public void update() {
        if (running) {
            animation.update();
        }
    }

}
