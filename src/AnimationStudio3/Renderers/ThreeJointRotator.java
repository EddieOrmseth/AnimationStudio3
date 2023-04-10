package AnimationStudio3.Renderers;

import AnimationStudio3.AnimationData;
import AnimationStudio3.Display.AutoUpdateJLabel;
import AnimationStudio3.Display.Joints.*;
import AnimationStudio3.Display.Renderer;
import AnimationStudio3.Display.SPoint;
import AnimationStudio3.PDouble;
import AnimationStudio3.Vector;

import java.awt.*;

public class ThreeJointRotator extends Renderer {

    private final int Length1 = 50;
    private final int Length2 = 100;

    public ThreeJointRotator(AnimationData animationData, SPoint offset) {
        super(animationData, offset);

        this.offset.getX().val = 300;
        this.offset.getY().val = 300;
    }

    private String getAngleDisplayString(PDouble toAngleBind, PDouble betweenAngleBind) {
        String toAngleBindString = toAngleBind == null ? "" : "ToAngle: " + (int) toDegrees(toAngleBind.val);
        String betweenAngleBindString = betweenAngleBind == null ? "" : "BetweenAngle: " + (int) toDegrees(betweenAngleBind.val);
        return toAngleBindString + "    " + betweenAngleBindString;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
    }

    @Override
    public void renderAt(Graphics graphics, AnimationData data, SPoint point) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(3));

        Vector freeJointPos = Vector.fromCartesian(data.get(0).val, data.get(1).val).add(point);
        Vector freeToL1 = Vector.fromPolar(data.get(2).val, Length1);
        paintAtAndTo(graphics, freeJointPos, freeToL1);

        Vector l1ToL2 = freeToL1.clone().addTheta(data.get(3).val).setMagnitude(Length2);
        paintAtAndTo(graphics, freeJointPos.clone().add(freeToL1), l1ToL2);
    }

    @Override
    public JointDisplay getJointDisplayFor(AnimationData data) {
        JointDisplay jointDisplay = new JointDisplay(offsetJoint);
//        System.out.println("here");

        AutoUpdateJLabel label = new AutoUpdateJLabel(() -> getAngleDisplayString(data.get(2), data.get(3)));
        label.setBounds(20, 20, 250, 30);
        jointDisplay.add(label);

        FreeJoint freeJoint = new FreeJoint(data.get(0), data.get(1));
        freeJoint.setOffset(offset);

        double theta1 = data.get(2).val;
        double theta2 = data.get(2).val + data.get(3).val;
        LengthJoint l1 = new LengthJoint(freeJoint, fromPolar(Length1, theta1));
        LengthJoint l2 = new LengthJoint(l1, fromPolar(Length2, theta2));
        jointDisplay.add(freeJoint, l1, l2);

        ToAngleBind toL1 = new ToAngleBind(data.get(2), freeJoint, l1);
        BetweenAngleBind toL2 = new BetweenAngleBind(data.get(3), freeJoint, l1, l2);
        jointDisplay.add(toL1, toL2);

        return jointDisplay;
    }

    public double toDegrees(double radians) {
        return radians * (180.0 / Math.PI);
    }

}
