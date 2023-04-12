package AnimationStudio3.Display.Frames;

import AnimationStudio3.Animation;
import AnimationStudio3.AnimationData;
import AnimationStudio3.Display.Joints.JointDisplay;
import AnimationStudio3.Display.LazyMouseListener;
import AnimationStudio3.PDouble;
import AnimationStudio3.Display.Renderer;
import AnimationStudio3.Display.Studio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FrameButtonBoard extends JComponent {

    private Studio studio;
    private Animation animation;
    private Renderer renderer;
    private ArrayList<FrameButton> frameButtons;
    private int frameNameCounter = 0;
    private JButton newButton;

    public FrameButtonBoard(Animation animation, Renderer renderer, Studio studio) {
        this.animation = animation;
        this.renderer = renderer;
        this.studio = studio;

        frameButtons = new ArrayList<>();
        for (int i = 0; i < animation.getNumFrames(); i++) {
            JointDisplay newJointDisplay = renderer.getJointDisplayFor(animation.getDataAtFrame(i));
            frameButtons.add(new FrameButton(animation.getDataAtFrame(i), newJointDisplay, animation.getTimeAtFrame(i), "" + frameNameCounter++, this, studio));
            add(frameButtons.get(i));
        }

        newButton = new JButton("+");
        newButton.setBackground(Color.GREEN);
        newButton.setSize(100, 80);
        newButton.addMouseListener(new LazyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addFrame();
            }
        });
        add(newButton);

        setButtonPositions();
    }

    public void setButtonPositions() {
        for (int i = 0; i < animation.getNumFrames(); i++) {
            frameButtons.get(i).setLocation((20 + frameButtons.get(i).getWidth()) * i, 0);
        }

        newButton.setLocation((20 + (frameButtons.size() > 0 ? frameButtons.get(0).getWidth() : 0)) * (frameButtons.size()), 0);
        setSize(((20 + (frameButtons.size() > 0 ? frameButtons.get(0).getWidth() : 0)) * (frameButtons.size())) + newButton.getWidth(), 120);
    }

    public int getFrameButtonIndex(FrameButton frameButton) {
        for (int i = 0; i < frameButtons.size(); i++) {
            if (frameButtons.get(i) == frameButton) {
                return i;
            }
        }

        return -1;
    }

    public void reorderFromFrameButton(FrameButton frameButton, int deltaIndex) {
        int frameButtonIndex = getFrameButtonIndex(frameButton);
        int newIdx = frameButtonIndex + deltaIndex;
        if (frameButtonIndex == -1 || newIdx < 0 || newIdx >= frameButtons.size()) {
            return;
        }

        FrameButton savedButton = frameButtons.get(newIdx);
        AnimationData savedData = animation.getDataAtFrame(newIdx);
        PDouble savedTime = animation.getTimeAtFrame(newIdx);

        frameButtons.set(newIdx, frameButtons.get(frameButtonIndex));
        animation.setDataAtFrame(newIdx, animation.getDataAtFrame(frameButtonIndex));
        animation.setTimeAtFrame(newIdx, animation.getTimeAtFrame(frameButtonIndex));

        frameButtons.set(frameButtonIndex, savedButton);
        animation.setDataAtFrame(frameButtonIndex, savedData);
        animation.setTimeAtFrame(frameButtonIndex, savedTime);

        setButtonPositions();
    }

    public void setDataFromFrameButton(FrameButton frameButton, int deltaIndex) {
        int frameButtonIndex = getFrameButtonIndex(frameButton);
        int toCopyIndex = frameButtonIndex + deltaIndex;
        if (frameButtonIndex == -1 || toCopyIndex < 0 || toCopyIndex >= frameButtons.size()) {
            return;
        }

        AnimationData newData = animation.getDataAtFrame(toCopyIndex).clone();
        PDouble newTime = new PDouble(1.0);
        JointDisplay newJointDisplay = renderer.getJointDisplayFor(newData);

        setNewFrameButtonInfo(frameButtonIndex, newData, newTime, newJointDisplay);
    }

    public void reflectFromFrameButton(FrameButton frameButton) {
        int frameButtonIndex = getFrameButtonIndex(frameButton);
        PDouble newTime = frameButton.getTimeToFrame();
        AnimationData newData = renderer.getFlipRightAndLeftData(frameButton.getFrame());
        JointDisplay newJointDisplay = renderer.getJointDisplayFor(newData);

        setNewFrameButtonInfo(frameButtonIndex, newData, newTime, newJointDisplay);
    }

    public void setNewFrameButtonInfo(int frameButtonIndex, AnimationData newData, PDouble newTime, JointDisplay newJointDisplay) {
        FrameButton setFrame = frameButtons.get(frameButtonIndex);
        setFrame.setFrame(newData);
        setFrame.setTimeToFrame(newTime);
        setFrame.setJointDisplay(newJointDisplay);
        animation.setDataAtFrame(frameButtonIndex, newData);
        animation.setTimeAtFrame(frameButtonIndex, newTime);

        studio.setDisplayedThings(newData, newJointDisplay);
    }

    public void deleteFromFrameButton(FrameButton frameButton) {
        int frameButtonIndex = getFrameButtonIndex(frameButton);
        if (frameButtonIndex == -1) {
            return;
        }

        FrameButton removed = frameButtons.remove(frameButtonIndex);
        remove(removed);
        animation.removeFrame(frameButtonIndex);

        setButtonPositions();
    }

    public void addFrame() {
        AnimationData data = new AnimationData(animation.getDisplayData().size());
        PDouble time = new PDouble(1.0);
        FrameButton button = new FrameButton(data, renderer.getJointDisplayFor(data), time, "" + frameNameCounter++, this, studio);
        add(button);

        animation.setDataAtFrame(animation.getNumFrames(), data);
        animation.setTimeAtFrame(animation.getNumFrames(), time);
        frameButtons.add(frameButtons.size(), button);

        setButtonPositions();
    }

}
