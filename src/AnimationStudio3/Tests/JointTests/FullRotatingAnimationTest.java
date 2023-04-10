package AnimationStudio3.Tests.JointTests;

import AnimationStudio3.Animation;
import AnimationStudio3.AnimationData;
import AnimationStudio3.Display.Studio;
import AnimationStudio3.Renderers.ThreeJointRotator;

import java.util.ArrayList;
import java.util.List;

public class FullRotatingAnimationTest {

    public static void main(String... args) {
        ThreeJointRotator rotator = new ThreeJointRotator(null, null);

        AnimationData d0 = new AnimationData(new double[]{0, 0, 0, 0});
//        d0.scale(150);
        AnimationData d1 = new AnimationData(new double[]{0, 0, 0, 0});
//        d1.scale(150);
        AnimationData d2 = new AnimationData(new double[]{0, 0, 0, 0});
//        d2.scale(150);

        AnimationData data = new AnimationData(4);
        Animation animation = new Animation(new ArrayList<>(List.of(new AnimationData[]{d0, d1, d2})), new double[]{1.0, 1.0, 1.0}, data);

        Studio studio = new Studio(animation, rotator);

        while (true) {
            studio.update();
        }

    }

}
