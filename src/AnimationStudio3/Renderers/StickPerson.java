package AnimationStudio3.Renderers;

import AnimationStudio3.AnimationData;
import AnimationStudio3.Display.Joints.*;
import AnimationStudio3.Display.Renderer;
import AnimationStudio3.Display.SPoint;
import AnimationStudio3.PDouble;
import AnimationStudio3.Vector;

import java.awt.*;

public class StickPerson extends Renderer {

    public enum BodyInfo {
        HipJointX,
        HipJointY,

        HipToLeftKneeAngle,
        HipToRightKneeAngle,
        LeftKneeToLeftFootAngle,
        RightKneeToRightFootAngle,

        HipToShouldAngle,

        ShoulderToLeftElbowAngle,
        ShoulderToRightElbowAngle,
        LeftElbowToLeftHandAngle,
        RightElbowToRightHandAngle,
    }

    private int HeadRadius = 30;
    private int NeckLength = 40;
    private int TorsoLength = 75;
    private int UpperArmLength = 55;
    private int LowerArmLength = 50;
    private int UpperLegLength = 65;
    private int LowerLegLength = 75;

    public static int BodyInfoToIndex(BodyInfo info) {
        int index = -1;

        switch (info) {
            case HipJointX:
                index = 0;
                break;
            case HipJointY:
                index = 1;
                break;
            case HipToLeftKneeAngle:
                index = 2;
                break;
            case HipToRightKneeAngle:
                index = 3;
                break;
            case LeftKneeToLeftFootAngle:
                index = 4;
                break;
            case RightKneeToRightFootAngle:
                index = 5;
                break;
            case HipToShouldAngle:
                index = 6;
                break;
            case ShoulderToLeftElbowAngle:
                index = 7;
                break;
            case ShoulderToRightElbowAngle:
                index = 8;
                break;
            case LeftElbowToLeftHandAngle:
                index = 9;
                break;
            case RightElbowToRightHandAngle:
                index = 10;
                break;
        }

        return index;
    }

    public static PDouble getPDoubleFromBodyInfo(AnimationData animationData, BodyInfo info) {
        return animationData.get(BodyInfoToIndex(info));
    }

    public StickPerson(AnimationData animationData, SPoint offset) {
        super(animationData, offset);

        this.offset.getX().val = 300;
        this.offset.getY().val = 300;
    }

    public void scale(double scalar) {
        HeadRadius *= scalar;
        NeckLength *= scalar;
        TorsoLength *= scalar;
        UpperArmLength *= scalar;
        LowerArmLength *= scalar;
        UpperLegLength *= scalar;
        LowerLegLength *= scalar;
    }

    @Override
    public void renderAt(Graphics graphics, AnimationData data, SPoint point) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(3));

        // Paint Torso
        Vector hip = Vector.fromCartesian((getPDoubleFromBodyInfo(data, BodyInfo.HipJointX).val + point.getX().val), (getPDoubleFromBodyInfo(data, BodyInfo.HipJointY).val + point.getY().val));
        Vector hipToShoulder = Vector.fromPolar(getPDoubleFromBodyInfo(data, BodyInfo.HipToShouldAngle).val, TorsoLength);
        Vector shoulder = hip.clone().add(hipToShoulder);
        paintAtAndTo(graphics, hip, hipToShoulder);

        // Paint Left Leg
        Vector hipToLeftKnee = hipToShoulder.clone().mult(-1).addTheta(getPDoubleFromBodyInfo(data, BodyInfo.HipToLeftKneeAngle).val).setMagnitude(UpperLegLength);
        paintAtAndTo(graphics, hip, hipToLeftKnee);

        Vector leftKneeToLeftFoot = hipToLeftKnee.clone().addTheta(getPDoubleFromBodyInfo(data, BodyInfo.LeftKneeToLeftFootAngle).val).setMagnitude(LowerLegLength);
        paintAtAndTo(graphics, hip.clone().add(hipToLeftKnee), leftKneeToLeftFoot);

        // Paint Right Leg
        Vector hipToRightKnee = hipToShoulder.clone().mult(-1).addTheta(getPDoubleFromBodyInfo(data, BodyInfo.HipToRightKneeAngle).val).setMagnitude(UpperLegLength);
        paintAtAndTo(graphics, hip, hipToRightKnee);

        Vector rightKneeToRightFoot = hipToRightKnee.clone().addTheta(getPDoubleFromBodyInfo(data, BodyInfo.RightKneeToRightFootAngle).val).setMagnitude(LowerLegLength);
        paintAtAndTo(graphics, hip.clone().add(hipToRightKnee), rightKneeToRightFoot);

        // Paint Left Arm
        Vector shoulderToLeftElbow = hipToShoulder.clone().addTheta(getPDoubleFromBodyInfo(data, BodyInfo.ShoulderToLeftElbowAngle).val).setMagnitude(UpperArmLength);
        paintAtAndTo(graphics, shoulder, shoulderToLeftElbow);

        Vector leftElbowToLeftHand = shoulderToLeftElbow.clone().addTheta(getPDoubleFromBodyInfo(data, BodyInfo.LeftElbowToLeftHandAngle).val).setMagnitude(LowerArmLength);
        paintAtAndTo(graphics, shoulder.clone().add(shoulderToLeftElbow), leftElbowToLeftHand);

        // Paint Right Arm
        Vector shoulderToRightElbow = hipToShoulder.clone().addTheta(getPDoubleFromBodyInfo(data, BodyInfo.ShoulderToRightElbowAngle).val).setMagnitude(UpperArmLength);
        paintAtAndTo(graphics, shoulder, shoulderToRightElbow);

        Vector rightElbowToRightHand = shoulderToRightElbow.clone().addTheta(getPDoubleFromBodyInfo(data, BodyInfo.RightElbowToRightHandAngle).val).setMagnitude(LowerArmLength);
        paintAtAndTo(graphics, shoulder.clone().add(shoulderToRightElbow), rightElbowToRightHand);

        // Paint Head And Neck
        Vector neck = shoulder.add(hipToShoulder.clone().setMagnitude(NeckLength));
        paintAtAndAt(graphics, shoulder, neck);
        graphics.fillOval((int) neck.getX() - HeadRadius, (int) neck.getY() - HeadRadius, 2 * HeadRadius, 2 * HeadRadius);
    }

    @Override
    public JointDisplay getJointDisplayFor(AnimationData data) {
        JointDisplay jointDisplay = new JointDisplay(offsetJoint);

        FreeJoint hip = new FreeJoint(getPDoubleFromBodyInfo(data, BodyInfo.HipJointX), getPDoubleFromBodyInfo(data, BodyInfo.HipJointY));
        hip.setOffset(offset);
        jointDisplay.add(hip);

        double thetaHtoS = getPDoubleFromBodyInfo(data, BodyInfo.HipToShouldAngle).val;
        double thetaStoH = getPDoubleFromBodyInfo(data, BodyInfo.HipToShouldAngle).val + Math.PI;

        LengthJoint shoulder = new LengthJoint(hip, fromPolar(TorsoLength, thetaHtoS));
        jointDisplay.add(shoulder);

        double thetaHtoLK = thetaStoH + getPDoubleFromBodyInfo(data, BodyInfo.HipToLeftKneeAngle).val;
        double thetaHtoRK = thetaStoH + getPDoubleFromBodyInfo(data, BodyInfo.HipToRightKneeAngle).val;
        double thetaLKtoLF = thetaHtoLK + getPDoubleFromBodyInfo(data, BodyInfo.LeftKneeToLeftFootAngle).val;
        double thetaRKtoRF = thetaHtoRK + getPDoubleFromBodyInfo(data, BodyInfo.RightKneeToRightFootAngle).val;

        LengthJoint leftKnee = new LengthJoint(hip, fromPolar(UpperLegLength, thetaHtoLK));
        LengthJoint rightKnee = new LengthJoint(hip, fromPolar(UpperLegLength, thetaHtoRK));
        LengthJoint leftFoot = new LengthJoint(leftKnee, fromPolar(LowerLegLength, thetaLKtoLF));
        LengthJoint rightFoot = new LengthJoint(rightKnee, fromPolar(LowerLegLength, thetaRKtoRF));
        jointDisplay.add(leftKnee, rightKnee, leftFoot, rightFoot);

        double thetaStoLE = thetaHtoS + getPDoubleFromBodyInfo(data, BodyInfo.ShoulderToLeftElbowAngle).val;
        double thetaStoRE = thetaHtoS + getPDoubleFromBodyInfo(data, BodyInfo.ShoulderToRightElbowAngle).val;
        double thetaLEtoLH = thetaStoLE + getPDoubleFromBodyInfo(data, BodyInfo.LeftElbowToLeftHandAngle).val;
        double thetaREtoRH = thetaStoRE + getPDoubleFromBodyInfo(data, BodyInfo.RightElbowToRightHandAngle).val;

        LengthJoint leftElbow = new LengthJoint(shoulder, fromPolar(UpperArmLength, thetaStoLE));
        LengthJoint rightElbow = new LengthJoint(shoulder, fromPolar(UpperArmLength, thetaStoRE));
        LengthJoint leftHand = new LengthJoint(leftElbow, fromPolar(LowerArmLength, thetaLEtoLH));
        LengthJoint rightHand = new LengthJoint(rightElbow, fromPolar(LowerArmLength, thetaREtoRH));
        jointDisplay.add(leftElbow, rightElbow, leftHand, rightHand);


        ToAngleBind hipToShoulder = new ToAngleBind(getPDoubleFromBodyInfo(data, BodyInfo.HipToShouldAngle), hip, shoulder);
        jointDisplay.add(hipToShoulder);

        BetweenAngleBind hipToLeftKnee = new BetweenAngleBind(getPDoubleFromBodyInfo(data, BodyInfo.HipToLeftKneeAngle), shoulder, hip, leftKnee);
        BetweenAngleBind hipToRightKnee = new BetweenAngleBind(getPDoubleFromBodyInfo(data, BodyInfo.HipToRightKneeAngle), shoulder, hip, rightKnee);
        BetweenAngleBind leftKneeToLeftFoot = new BetweenAngleBind(getPDoubleFromBodyInfo(data, BodyInfo.LeftKneeToLeftFootAngle), hip, leftKnee, leftFoot);
        BetweenAngleBind rightKneeToRightFoot = new BetweenAngleBind(getPDoubleFromBodyInfo(data, BodyInfo.RightKneeToRightFootAngle), hip, rightKnee, rightFoot);
        jointDisplay.add(hipToLeftKnee, hipToRightKnee, leftKneeToLeftFoot, rightKneeToRightFoot);

        BetweenAngleBind shoulderToLeftElbow = new BetweenAngleBind(getPDoubleFromBodyInfo(data, BodyInfo.ShoulderToLeftElbowAngle), hip, shoulder, leftElbow);
        BetweenAngleBind shoulderToRightElbow = new BetweenAngleBind(getPDoubleFromBodyInfo(data, BodyInfo.ShoulderToRightElbowAngle), hip, shoulder, rightElbow);
        BetweenAngleBind leftElbowToLeftHand = new BetweenAngleBind(getPDoubleFromBodyInfo(data, BodyInfo.LeftElbowToLeftHandAngle), shoulder, leftElbow, leftHand);
        BetweenAngleBind rightElbowToRightHand = new BetweenAngleBind(getPDoubleFromBodyInfo(data, BodyInfo.RightElbowToRightHandAngle), shoulder, rightElbow, rightHand);
        jointDisplay.add(shoulderToLeftElbow, shoulderToRightElbow, leftElbowToLeftHand, rightElbowToRightHand);

        return jointDisplay;
    }

    public AnimationData reflectData(AnimationData data) {
        swap(data, BodyInfoToIndex(BodyInfo.HipToLeftKneeAngle), BodyInfoToIndex(BodyInfo.HipToRightKneeAngle));
        swap(data, BodyInfoToIndex(BodyInfo.LeftKneeToLeftFootAngle), BodyInfoToIndex(BodyInfo.RightKneeToRightFootAngle));
        swap(data, BodyInfoToIndex(BodyInfo.ShoulderToLeftElbowAngle), BodyInfoToIndex(BodyInfo.ShoulderToRightElbowAngle));
        swap(data, BodyInfoToIndex(BodyInfo.LeftElbowToLeftHandAngle), BodyInfoToIndex(BodyInfo.RightElbowToRightHandAngle));

        return data;
    }

}
