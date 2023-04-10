package AnimationStudio3.Display.Joints;

import AnimationStudio3.Display.SPoint;
import AnimationStudio3.PDouble;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JointDisplay extends JComponent {

    private ArrayList<Joint> joints;
    private ArrayList<AngleBind> angleBinds;
    private FreeJoint offsetJoint;

    public JointDisplay(FreeJoint offsetJoint) {
        joints = new ArrayList<>();
        angleBinds = new ArrayList<>();

        this.offsetJoint = offsetJoint;
    }

    public ArrayList<Joint> getJoints() {
        return joints;
    }

    @Override
    public Component add(Component component) {
        super.add(component);
        if (component instanceof Joint) {
            joints.add((Joint) component);
//            if (component instanceof FreeJoint) {
//                ((FreeJoint) component).setOffset(offset);
//            }
        }

        return component;
    }

    public void add(Component... components) {
        for (int i = 0; i < components.length; i++) {
            add(components[i]);
        }
    }

    public void add(AngleBind angleBind) {
        angleBinds.add(angleBind);
    }

    public void add(AngleBind... angleBinds) {
        for (int i = 0; i < angleBinds.length; i++) {
            add(angleBinds[i]);
        }
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        for (int i = 0; i < angleBinds.size(); i++) {
            angleBinds.get(i).update();
        }
    }

    @Override
    public void remove(Component component) {
        super.remove(component);
        if (component instanceof Joint) {
            joints.remove((Joint) component);
        }
    }

    public PDouble getOffsetX() {
        return offsetJoint.getPX();
    }

    public PDouble getOffsetY() {
        return offsetJoint.getPY();
    }

    public FreeJoint getOffsetJoint() {
        return offsetJoint;
    }

    public void autoFit() {
        if (joints.size() == 0) {
            return;
        }

        int buffer = 20;
        Point most = joints.get(0).getLocation();

        for (int i = 1; i < joints.size(); i++) {
            Joint joint = joints.get(i);

            if (joint.getCenterX() > most.x) {
                most.x = joint.getCenterX();
            }

            if (joint.getCenterY() > most.y) {
                most.y = joint.getCenterY();
            }
        }

        setSize(most.x + buffer, most.y + buffer);
    }

}
