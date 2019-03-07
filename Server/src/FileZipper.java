
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileZipper {
  private static File[] files;
  private static Path saveLocation;

  FileZipper(File[] files, String rootLocation, String fileName) {
    this.files = files;
    this.saveLocation = Paths.get(rootLocation,fileName);
  }

  /*
   * A method to create a zip file of note files, the fileList is all the
   * files to be contained with the new zip. Firstly the files are validated
   * then they are put into the zip file.
   */
  public static boolean createZipFile(ArrayList<File> fileList) {
    boolean valid = false;
    try {
      valid = validateFiles(fileList);
    }
    catch (FileSystemException e) {
      System.out.println(e.getMessage());
    }
    if (valid == true) {
      zipFiles(fileList);
      return true;
    }
    return false;
  }


  /*
   * Method to zip all the files from the 'files' ArrayList into a compressed format.
   */
  private static void zipFiles(ArrayList<File> files) {

    File[] filesArray = new File[3];
    files.toArray(filesArray);
    File firstFile;
    try {
      firstFile = new File(saveLocation.toString());
      String zipFileName = firstFile.getName().concat(".zip");

      FileOutputStream fileOut = new FileOutputStream(zipFileName);
      ZipOutputStream zipOut = new ZipOutputStream(fileOut);

      for (File file : filesArray) {
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

    System.out.println("YAY");
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
        if (file != null) {
          zipOut.putNextEntry(new ZipEntry(file.getName()));

          byte[] bytes = Files.readAllBytes(file.toPath());
          zipOut.write(bytes, 0, bytes.length);
          zipOut.closeEntry();
        }
      }

      zipOut.close();
      fileOut.close();

    } catch (IOException e) {
      System.err.println("I/O error: " + e);
    }

    return firstFile;
  }


  /*
   * A method to check the number of files and the extensions on each of them coincide with the specification:
   *   - The files array is of length 3.
   *   - The first file is PNG.
   *   - The second is a MIDI.
   *   - The first file is PNG.
   */
  public static boolean validateFiles(ArrayList<File> files) throws FileSystemException {
    int ARRAYLENGTH = 3;

    String[] extensions = new String[]{".png", ".mid", ".png"};

    if (files.size() == ARRAYLENGTH) {

      for (int i = 0; i < ARRAYLENGTH; i++) {

        File file = files.get(i);

        if (!file.getPath().endsWith(extensions[i])) {
          String errorMessage = String.format("%s is not a valid file format", file.getName());
          throw new FileSystemException(errorMessage);
        }
        System.out.println();
      }
    }
    return true;
  }


}
