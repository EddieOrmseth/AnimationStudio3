package AnimationStudio3.Display.Animations;

import AnimationStudio3.Animation;
import AnimationStudio3.Display.LazyMouseListener;
import AnimationStudio3.Display.Studio;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

public class AnimationButtonBoard extends JComponent {

    private final String directoryPath = "C:\\Users\\eddie\\Downloads\\Programming\\Java Programs\\AnimationStudio3\\src\\Animations\\";
    private final String extension = ".anim";

//    private Animation animation;

    private JButton playButton;
    private JButton writeButton;
    private JButton readButton;

//    private JLabel timeScaleLabel;
//    private double[] initialTimeData;
//    private String prevTSInput;
//    private JTextField timeScaleInput;

    private JFileChooser fileChooser;

    public AnimationButtonBoard(Studio studio, Animation animation) {
        playButton = new JButton("Play");
        writeButton = new JButton("Write");
        readButton = new JButton("Read");

        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(directoryPath));
        fileChooser.setFileFilter(new FileExtensionFilter(extension));

//        timeScaleLabel = new JLabel("TimeScale:");
//        initialTimeData = new double[animation.getNumFrames()];
//        for (int i = 0; i < initialTimeData.length; i++) {
//            initialTimeData[i] = animation.getTimeAtFrame(i).val;
//        }
//        prevTSInput = "1.0";
//        timeScaleInput = new JTextField(prevTSInput);

        playButton.addMouseListener(new LazyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = playButton.getText();
                if (text.equals("Play")) {
                    playButton.setText("Pause");
                    studio.setDisplayedThings(animation.getDisplayData(), null);
                    animation.play();
                } else if (text.equals("Pause")) {
                    playButton.setText("Play");
                    animation.pause();
                }
            }
        });

        writeButton.addMouseListener(new LazyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int retVal = fileChooser.showSaveDialog(null);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    file = new File(file.getAbsolutePath() + (file.getAbsolutePath().endsWith(extension) ? "" : extension));
                    try {
                        FileOutputStream outputStream = new FileOutputStream(file);
                        String string = animation.toString();
                        outputStream.write(string.getBytes());
                        outputStream.close();

                    } catch (Exception exception) {
                        return;
                    }
//                    System.out.println("Failed to open file");
                }
            }
        });

        readButton.addMouseListener(new LazyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int retVal = fileChooser.showOpenDialog(null);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        Scanner scanner = new Scanner(file);
                        String string = "";
                        while (scanner.hasNextLine()) {
                            string += scanner.nextLine() + "\n";
                        }

                        Animation newAnimation = Animation.fromString(string);
//                        newAnimation.timeScale(.1);
                        System.out.println("Current Animation: \n" + studio.getAnimation());
                        studio.reinitialize(newAnimation);
                    } catch (Exception exception) {
                        return;
                    }
                } else {
//                    System.out.println("Failed to open file");
                }
            }
        });

        add(playButton);
        add(writeButton);
        add(readButton);
//        add(timeScaleLabel);
//        add(timeScaleInput);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        int spacing = 20;
        int buttonHeight = height / 3 - 2 * spacing;
        playButton.setBounds(0, 0 * buttonHeight + 0 * spacing, width, buttonHeight);
        writeButton.setBounds(0, 1 * buttonHeight + 1 * spacing, width, buttonHeight);
        readButton.setBounds(0, 2 * buttonHeight + 2 * spacing, width, buttonHeight);
    }

//    @Override
//    public void paint(Graphics graphics) {
//        String currentInput = timeScaleInput.getText();
//        if (!currentInput.equals(prevTSInput)) {
//            try {
//                double timeScale = Double.parseDouble(currentInput);
//                prevTSInput = currentInput;
//                for (int i = 0; i < initialTimeData.length; i++) {
//
//                }
//            } catch (Exception e) {
//
//            }
//        }
//        super.paint(graphics);
//    }

}
