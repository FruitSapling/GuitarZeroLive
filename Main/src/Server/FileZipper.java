package Server;


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
      firstFile = new File(saveLocation.toString());
      String zipFileName = firstFile.getName().concat(".zip");

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



    /*
    EXAMPLE USAGE OF ZIPPING

    public static void main(String[] args) {

        // Create files in the project root folder '\GuitarZeroLive\'.
        File file1 = new File("testFileZipper1.txt");
        File file2 = new File("testFileZipper2.txt");
        File file3 = new File("testFileZipper3.txt");

        File[] files = new File[]{file1, file2, file3};

        // Specify the save location of the new zip file '\GuitarZeroLive\'.
        String zipLocation = ".";


        FileZipper zipper = new FileZipper(files, zipLocation, "zipper");

        zipper.zipFiles(files);

    }
    */

}
