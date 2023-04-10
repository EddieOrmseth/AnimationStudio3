package AnimationStudio3.Tests.AnimationTests;

import AnimationStudio3.Animation;
import AnimationStudio3.AnimationData;

import java.util.ArrayList;
import java.util.List;

public class TestReadWriteData {

    public static void main(String... args) {

        AnimationData d0 = new AnimationData(new double[] {1, 1, 1, 1, 1, 1, 1, 1});
        d0.scale(150);
        AnimationData d1 = new AnimationData(new double[] {2, 2, 2, 2, 2, 2, 2, 2});
        d1.scale(150);
        AnimationData d2 = new AnimationData(new double[] {3, 3, 3, 3, 3, 3, 3, 3});
        d2.scale(150);

        AnimationData data = new AnimationData(8);
        Animation animation = new Animation(new ArrayList<>(List.of(new AnimationData[]{d0, d1, d2})), new double[]{1.0, 1.0, 1.0}, data);

        String animationString = animation.toString();

        Animation animationParsed = Animation.fromString(animationString);
        String animParsedString = animationParsed.toString();

        if (animationString.equals(animParsedString)) {
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed!");
        }

    }

}
