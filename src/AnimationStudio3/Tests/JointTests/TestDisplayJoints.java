package AnimationStudio3.Tests.JointTests;

import AnimationStudio3.AnimationData;
import AnimationStudio3.Display.Joints.JointDisplay;
import AnimationStudio3.Renderers.Polygon;
import AnimationStudio3.Tests.TestFrame;

public class TestDisplayJoints {

    public static void main(String... args) {
        TestFrame testFrame = new TestFrame();

        Polygon polygon = new Polygon(null, null);
        AnimationData d0 = new AnimationData(new double[]{0, 0, 0, 0, 0, 0, 0, 0});
        d0.scale(100);
        AnimationData d1 = new AnimationData(new double[]{1, 1, 1, 1, 1, 1, 1, 1});
        d1.scale(100);
        AnimationData d2 = new AnimationData(new double[]{2, 2, 2, 2, 2, 2, 2, 2});
        d2.scale(100);

        JointDisplay jointDisplay = polygon.getJointDisplayFor(d2);
        jointDisplay.setLocation(100, 100);
        testFrame.add(jointDisplay);

        while (true) {
            testFrame.repaint();
            jointDisplay.autoFit();
        }
    }

}
