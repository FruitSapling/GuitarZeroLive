
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileZipper {
  private static File[] files;
  private Path saveLocation;

  FileZipper(File[] files, String rootLocation, String fileName) {
    this.files = files;
    this.saveLocation = Paths.get(rootLocation,fileName);
  }


  /*
   * Method to zip all the files from the 'files' array into a compressed format and return the '.zip' file.
   */
  public File zipFiles() {
    File firstFile = null;
    try {
      firstFile = new File(saveLocation.toString().concat(".zip"));
      String zipFileName = firstFile.getName();

      FileOutputStream fileOut = new FileOutputStream(zipFileName);
      ZipOutputStream zipOut = new ZipOutputStream(fileOut);

      for (File file : files) {
        zipOut.putNextEntry(new ZipEntry(file.getName()));

        byte[] bytes = Files.readAllBytes(file.toPath());
        zipOut.write(bytes, 0, bytes.length);
        zipOut.closeEntry();
      }

      zipOut.close();
      fileOut.close();

    } catch (IOException e) {
      System.err.println("I/O error: " + e);
    }

    return firstFile;
  }


}
