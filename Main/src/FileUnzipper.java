
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUnzipper {
    private String dir;


    FileUnzipper(String saveDirectory) {
        this.dir = saveDirectory;
    }



    /*
    * Method that takes a zip file and unzips it, placing the resulting files in the
    * dir specified in the FileUnzipper constructor (with there original names/extensions).
    */
    public File[] unzipFiles(File zippedFile) {
      try {
        FileInputStream in = new FileInputStream(zippedFile);
        ZipInputStream zipIn = new ZipInputStream(in);

        int bufferSize = 1024;
        ZipEntry next;

        while ((next = zipIn.getNextEntry()) != null) {
          long size = next.getSize();
          Path path = Paths.get(dir,next.getName().split("/")[1]);
          File file = new File(path.toString());
          FileOutputStream out = new FileOutputStream(file);

          for (int i = 0; i < (size/bufferSize)+1; i++) {
            byte[] buffer = new byte[bufferSize];
            zipIn.read(buffer,0,bufferSize);
            out.write(buffer);
          }
          out.close();
        }
        zipIn.close();
        in.close();
      }
      catch (IOException e) {
        System.out.println(e.getMessage());
        System.exit(1);
      }
      return null;
    }


    public static void main(String[] args) {
      FileUnzipper zip = new FileUnzipper("");
      zip.unzipFiles(new File("temp1.zip"));
    }


}
