import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipInputStream;

public class FileUnzipper {
    private File dir;
    private File[] zippedFolders;

    FileUnzipper(String directory) {
        this.dir = new File(directory);
    }



    public File[] unzipFiles(File zippedFile) {
      try {
        FileInputStream in = new FileInputStream(zippedFile);
        ZipInputStream zipIn = new ZipInputStream(in);

        byte[] bytes = Files.readAllBytes(zippedFile.toPath());
        zipIn.read(bytes,0,bytes.length);
        // TODO: Implement the unzipping of files
      }
      catch (IOException e) {
        System.out.println(e.getMessage());
      }
      return null;
    }


}
