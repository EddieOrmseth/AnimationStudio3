package AnimationStudio3.Display.Frames;

import AnimationStudio3.AnimationData;
import AnimationStudio3.Display.Joints.JointDisplay;
import AnimationStudio3.Display.LazyMouseListener;
import AnimationStudio3.Display.Studio;
import AnimationStudio3.PDouble;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class FrameButton extends JComponent {

    private AnimationData frame;
    private JointDisplay jointDisplay;
    private Studio studio;

    private PDouble timeToFrame;
    private String timeInputStr;
    private String nameStr;

    private JButton selectFrame;
    private JButton shiftLeft;
    private JButton shiftRight;
    private JButton setLeft;
    private JButton setRight;
    private JButton flip;
    private JButton deleteButton;
    private JLabel timeLabel;
    private JTextField timeInput;

    public FrameButton(AnimationData frame, JointDisplay jointDisplay, PDouble timeToFrame, String nameStr, FrameButtonBoard frameButtonBoard, Studio studio) {
        this.frame = frame;
        this.jointDisplay = jointDisplay;
        this.studio = studio;
        this.timeToFrame = timeToFrame;
        timeInputStr = "" + timeToFrame;
        this.nameStr = nameStr;

        selectFrame = new JButton(nameStr);
        shiftLeft = new JButton("<=");
        shiftRight = new JButton("=>");
        setLeft = new JButton("L");
        setRight = new JButton("R");
        flip = new JButton("Flip LR");
        deleteButton = new JButton("delete");
        timeLabel = new JLabel("Time: ");
        timeInput = new JTextField("" + timeToFrame.val);

        setSize(100, 120);
        setupUIElements(frameButtonBoard);
        setVisible(true);
    }

    private void setupUIElements(FrameButtonBoard frameButtonBoard) {
        final int Width = getWidth();
        final int Height = getHeight() / 6;
        FrameButton frameButton = this;

        int yVal = 0;

        selectFrame.setBounds(0, yVal, Width, Height);
        selectFrame.addMouseListener(new LazyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                studio.setDisplayedThings(getFrame(), getJointDisplay());
            }
        });
        add(selectFrame);

        yVal += Height;

        shiftLeft.setBounds(0, yVal, Width / 2, Height);
        shiftRight.setBounds(Width / 2, yVal, Width / 2, Height);
        shiftLeft.addMouseListener(new LazyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frameButtonBoard.reorderFromFrameButton(frameButton, -1);
            }
        });
        shiftRight.addMouseListener(new LazyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frameButtonBoard.reorderFromFrameButton(frameButton, 1);
            }
        });
        add(shiftLeft);
        add(shiftRight);

        yVal += Height;

        setLeft.setBounds(0, yVal, Width / 2, Height);
        setRight.setBounds(Width / 2, yVal, Width / 2, Height);
        setLeft.addMouseListener(new LazyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frameButtonBoard.setDataFromFrameButton(frameButton, -1);
            }
        });
        setRight.addMouseListener(new LazyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frameButtonBoard.setDataFromFrameButton(frameButton, 1);
            }
        });
        add(setLeft);
        add(setRight);

        yVal += Height;

        flip.setBounds(0, yVal, Width, Height);
        flip.addMouseListener(new LazyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frameButtonBoard.reflectFromFrameButton(frameButton);
            }
        });
        add(flip);

        yVal += Height;

        deleteButton.setBounds(0, yVal, Width, Height);
        deleteButton.setBackground(Color.RED);
        deleteButton.addMouseListener(new LazyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frameButtonBoard.deleteFromFrameButton(frameButton);
            }
        });
        add(deleteButton);

        yVal += Height;

        timeLabel.setBounds(0, yVal, Width / 2, Height);
        timeInput.setBounds(Width / 2, yVal, Width / 2, Height);
        add(timeLabel);
        add(timeInput);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        if (!timeInput.getText().equals(timeInputStr) && !timeInput.hasFocus()) {
            try {
                double newTime = Double.parseDouble(timeInput.getText());
                timeToFrame.val = newTime;
                String newTimeStr = "" + newTime;
                timeInput.setText(newTimeStr);
                timeInputStr = newTimeStr;
            } catch (Exception ignored) {

            }
        }
    }

    public AnimationData getFrame() {
        return frame;
    }

    public void setFrame(AnimationData frame) {
        this.frame = frame;
    }

    public JointDisplay getJointDisplay() {
        return jointDisplay;
    }

    public void setJointDisplay(JointDisplay jointDisplay) {
        this.jointDisplay = jointDisplay;
    }

    public PDouble getTimeToFrame() {
        return timeToFrame;
    }

    public void setTimeToFrame(PDouble timeToFrame) {
        this.timeToFrame = timeToFrame;
    }

}
