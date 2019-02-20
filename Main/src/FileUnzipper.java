import java.io.File;
import java.io.FileFilter;

public class FileUnzipper {
    private File dir;
    private File[] zippedFolders;

    FileUnzipper(String directory) {
        this.dir = new File(directory);
    }

    public File[] getZippedFiles() {
        FileFilter filter = (pathname) -> pathname.toPath().endsWith(".zip");
        zippedFolders = dir.listFiles(filter);
        return zippedFolders;
    }


}
