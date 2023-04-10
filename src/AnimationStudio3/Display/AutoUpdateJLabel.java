package AnimationStudio3.Display;

import AnimationStudio3.Display.Joints.GetText;

import javax.swing.*;
import java.awt.*;

public class AutoUpdateJLabel extends JLabel {

    private GetText textGetter;

    public AutoUpdateJLabel(GetText textGetter) {
        this.textGetter = textGetter;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        setText(textGetter.getText());
    }

}
