/**
 * @author Mark Newell
 *
 * A class to perform different types of copression and validation.
 */


import java.io.File;
import java.io.FileFilter;
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

  /*
   * A method to create a zip file of note files, the fileList is all the
   * files to be contained with the new zip. Firstly the files are validated
   * then a notes file is extracted before all four files are put into the zip file.
   */
  public static boolean createZipFile(ArrayList<File> fileList) {
    boolean valid = false;
    try {
      valid = validateFiles(fileList);
    } catch (FileSystemException e) {
      System.out.println(e.getMessage());
    }
    if (valid == true) {
      // Make the notes file
      ExtractNotes.makeNotesFile(fileList.get(1));
      File notesFile = new File(
          Constants.RESOURCES_FOLDER + "/" + fileList.get(1).getName() + "notes");
      fileList.add(notesFile);

      // Zip the files
      zipFiles(fileList);

      // Clean up resources folder
      try {
        Files.delete(notesFile.toPath());
      } catch (IOException e) {
        System.out.println(e.getMessage());
        System.exit(0);
      }

      return true;
    }
    return false;
  }


  /*
   * Method to retrieve all the zip files in  the folder location.
   */
  public static File[] getZippedFiles(String location) {
    FileFilter filter = (pathname) -> pathname.getPath().endsWith(".zip");
    File[] zippedFolders = new File(location).listFiles(filter);
    return zippedFolders;
  }


  /*
   * Method to zip all the files from the 'files' ArrayList into a compressed format.
   */
  private static void zipFiles(ArrayList<File> files) {

    File[] filesArray = new File[4];
    files.toArray(filesArray);

    int fileCount = getZippedFiles(Constants.RESOURCES_FOLDER).length;

    File firstFile;
    try {
      firstFile = new File(Constants.RESOURCES_FOLDER + "/song" + fileCount);
      String zipFileName = firstFile.getPath().concat(".zip");

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

    System.out.println("Success");
  }


  public File zipFiles(File[] files) {

    File zipFolder = null;
    try {
      zipFolder = new File(Constants.RESOURCES_FOLDER + "/tempZip.zip");

      FileOutputStream fileOut = new FileOutputStream(zipFolder.getPath());
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

    return zipFolder;
  }


  /*
   * A method to check the number of files and the extensions on each of them coincide with the specification:
   *   - The files array is of length 3.
   *   - The first file is PNG.
   *   - The second is a MIDI.
   *   - The first file is TXT.
   */
  public static boolean validateFiles(ArrayList<File> files) throws FileSystemException {
    int ARRAYLENGTH = 3;

    String[] extensions = new String[]{".png", ".mid", ".txt"};

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
