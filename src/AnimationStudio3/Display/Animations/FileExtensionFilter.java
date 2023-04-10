package AnimationStudio3.Display.Animations;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileExtensionFilter extends FileFilter {

    private String fileExtension;

    public FileExtensionFilter(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public boolean accept(File f) {
        return f.getName().endsWith(fileExtension);
    }

    @Override
    public String getDescription() {
        return null;
    }

}
