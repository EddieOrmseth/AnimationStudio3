package AnimationStudio3.Renderers;

import AnimationStudio3.AnimationData;
import AnimationStudio3.Display.Joints.FreeJoint;
import AnimationStudio3.Display.Joints.JointDisplay;
import AnimationStudio3.Display.Renderer;
import AnimationStudio3.Display.SPoint;

import java.awt.*;

public class Polygon extends Renderer {

    public Polygon(AnimationData animationData, SPoint offset) {
        super(animationData, offset);
    }

    @Override
    public void renderAt(Graphics graphics, AnimationData data, SPoint point) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        int numPoints = data.size() / 2;
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(3));

        for (int i = 0; i < numPoints - 1; i++) {
            int idx = 2 * i;
            graphics.drawLine((int) (data.get(idx).val + point.getX().val), (int) (data.get(idx + 1).val + point.getY().val), (int) (data.get(idx + 2).val + point.getX().val), (int) (data.get(idx + 3).val + point.getY().val));
        }

        graphics.drawLine((int) (data.get(0).val + point.getX().val), (int) (data.get(1).val + point.getY().val), (int) (data.get(data.size() - 2).val + point.getX().val), (int) (data.get(data.size() - 1).val + point.getY().val));
    }

    @Override
    public JointDisplay getJointDisplayFor(AnimationData data) {
        JointDisplay jointDisplay = new JointDisplay(offsetJoint);
        int numPoints = data.size() / 2;
        for (int i = 0; i < numPoints; i++) {
            int idx = 2 * i;
            jointDisplay.add(new FreeJoint(data.get(idx), data.get(idx + 1)));
        }

        jointDisplay.autoFit();
        jointDisplay.setLocation(0, 0);
        return jointDisplay;
    }

}
