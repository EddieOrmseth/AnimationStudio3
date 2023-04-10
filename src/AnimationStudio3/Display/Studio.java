package AnimationStudio3.Display;

import AnimationStudio3.Animation;
import AnimationStudio3.AnimationData;
import AnimationStudio3.Display.Animations.AnimationButtonBoard;
import AnimationStudio3.Display.Frames.FrameButtonBoard;
import AnimationStudio3.Display.Joints.JointDisplay;
import AnimationStudio3.Display.Joints.JointDisplayManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Studio extends JFrame {

    private Animation animation;
    private Renderer renderer;
    private FrameButtonBoard frameButtonBoard;
    private JointDisplayManager jointDisplayManager;
    private AnimationButtonBoard animationButtonBoard;

    public Studio(Animation animation, Renderer renderer) {
        super("Animation Studio 3");
        this.animation = animation;
        this.renderer = renderer;
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(30, 20, 1300, 750);
        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Final Animation: \n" + animation);
            }
        });

        initialize(animation, renderer);
    }

    private void initialize(Animation animation, Renderer renderer) {

        frameButtonBoard = new FrameButtonBoard(animation, renderer, this);
        frameButtonBoard.setLocation(20, 20);
        add(frameButtonBoard);

        jointDisplayManager = new JointDisplayManager(animation, renderer, renderer.getOffsetJoint());
        jointDisplayManager.setBounds(0, 150, 1180, 583);
        jointDisplayManager.setBorder(new LineBorder(Color.BLACK, 2));
        add(jointDisplayManager);

        animationButtonBoard = new AnimationButtonBoard(this, animation);
        int endBandWidth = getWidth() - 1180;
        animationButtonBoard.setBounds((getWidth() - endBandWidth + ((endBandWidth - 75) / 2) - 7), 160, 75, 300);
        add(animationButtonBoard);
    }

    public void reinitialize(Animation animation) {
        remove(frameButtonBoard);
        remove(jointDisplayManager);
        remove(animationButtonBoard);
        initialize(animation, renderer);
    }

    public void setDisplayedThings(AnimationData animationData, JointDisplay jointDisplay) {
        jointDisplayManager.setDisplayedThings(animationData, jointDisplay);
    }

    public void update() {
        jointDisplayManager.update();
        repaint();
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
//        System.out.println("Paint");
    }

}
