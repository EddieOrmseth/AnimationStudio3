package AnimationStudio3.Tests.JointTests;

import AnimationStudio3.Display.Joints.BetweenAngleBind;
import AnimationStudio3.Display.Joints.FreeJoint;
import AnimationStudio3.Display.Joints.LengthJoint;
import AnimationStudio3.Display.Joints.ToAngleBind;
import AnimationStudio3.PDouble;
import AnimationStudio3.Tests.TestFrame;

import javax.swing.*;
import java.awt.*;

public class TestLRAJoints {

    public static void main(String... args) {
        TestFrame testFrame = new TestFrame();

        PDouble to = new PDouble(0);
        PDouble between = new PDouble(0);

        FreeJoint joint = new FreeJoint(new PDouble(100), new PDouble(100));
        LengthJoint lengthJoint1 = new LengthJoint(joint, new Point(100, 100));
        LengthJoint lengthJoint2 = new LengthJoint(lengthJoint1, new Point(100, 100));

        ToAngleBind toAngleBind = new ToAngleBind(to, joint, lengthJoint1);
        BetweenAngleBind betweenAngleBind = new BetweenAngleBind(between, joint, lengthJoint1, lengthJoint2);

        testFrame.add(joint);
        testFrame.add(lengthJoint1);
        testFrame.add(lengthJoint2);

        JLabel label = new JLabel();
        label.setBounds(20, 20, 250, 30);
        testFrame.add(label);

        System.out.println(Math.toDegrees(Math.atan2(0, 10)));
        System.out.println(Math.toDegrees(Math.atan2(10, 10)));
        System.out.println(Math.toDegrees(Math.atan2(10, 0)));

        while (true) {
            toAngleBind.update();
            betweenAngleBind.update();

            label.setText("To Angle: " + (int) Math.toDegrees(to.val) + "    Between Angle: " + (int) Math.toDegrees(between.val));
            testFrame.repaint();
        }
    }

}
