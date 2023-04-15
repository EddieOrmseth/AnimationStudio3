package AnimationStudio3;

import AnimationStudio3.Display.Studio;
import AnimationStudio3.Renderers.StickPerson;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        StickPerson stickPerson = new StickPerson(null, null);
//        stickPerson.scale(.2);

        AnimationData d0 = new AnimationData(new double[]{25.0, -144.0, 0.2990324071290624, -1.13941630009, 0.45642546726574684, 1.005094858138719, -1.4364748848419282, 3.8402524783112564, 2.5795362992505826, 4.80670293171187, 4.742249535153762});
        AnimationData d1 = new AnimationData(new double[]{25.0, -144.0, 0.2990324071290624, -1.13941630009, 0.45642546726574684, 1.005094858138719, -1.4364748848419282, 3.8402524783112564, 2.5795362992505826, 4.80670293171187, 4.742249535153762});
        AnimationData d2 = new AnimationData(new double[]{25.0, -144.0, 0.2990324071290624, -1.13941630009, 0.45642546726574684, 1.005094858138719, -1.4364748848419282, 3.8402524783112564, 2.5795362992505826, 4.80670293171187, 4.742249535153762});

        AnimationData data = new AnimationData(new double[]{25.0, -144.0, 0.2990324071290624, -1.13941630009, 0.45642546726574684, 1.005094858138719, -1.4364748848419282, 3.8402524783112564, 2.5795362992505826, 4.80670293171187, 4.742249535153762});

        Animation animation = new Animation(new ArrayList<>(List.of(new AnimationData[]{d0, d1, d2})), new double[]{1.0, 1.0, 1.0}, data);

        Studio studio = new Studio(animation, stickPerson);

        while (true) {
            studio.update();
        }
    }

}
