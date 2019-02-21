
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystemException;
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

    public static void fileBrowser(JPanel panel, int mode) {
        JFileChooser fb = new JFileChooser();
        fb.showOpenDialog(panel);
        FileNameExtensionFilter filter;
        switch (mode) {
            default:
                //TODO: None of this switch statement works so fuck knows what to do with it
                filter = null;
            case 1:
                filter = new FileNameExtensionFilter("MIDI File", "midi");
            case 2:
                filter = new FileNameExtensionFilter("PNG File", "png");
                //TODO: None of this switch statement works so fuck knows what to do with it

                fb.setFileFilter(filter);
                File file = fb.getSelectedFile();
                if(file != null) {
                    files[mode] = file;
                    }
                }
        }

    public static void createZipFile() {
        //TODO: This is called when the save button is hit, so validation and then zipping
    }

    /*
    * Method to zip all the files from the 'files' array into a compressed format and return the '.zip' file.
    */
    private File zipFiles() {
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
    * A method to check the number of files and the extensions on each of them coincide with the specification:
    *   - The files array is of length 3.
    *   - The first File is MIDI.
    *   - The second is a PNG.
    *   - The final is a proprietary format for holding the notes.
    */
    public static File[] validateFiles(File[] files) throws FileSystemException {
        int ARRAYLENGTH = 3;

        //TODO: add third file extension to the extensions array to validate our proprietary format.
        String[] extensions = new String[]{".midi", ".png", ""};

        if (files.length == ARRAYLENGTH) {

            //TODO: Remove -1 from (ARRAYLENGTH - 1) when third file extension has been added to extensions array.
            for (int i = 0; i < ARRAYLENGTH - 1; i++) {

                File file = files[i];

                if (!file.getPath().endsWith(extensions[i])) {
                    String errorMessage = String.format("%s is not a valid file format", file.getName());
                    throw new FileSystemException(errorMessage);
                }

                System.out.println();

            }

        }

        return files;
    }


    public static void main(String[] args) {

        File file1 = new File("testFileZipper1.midi");
        File file2 = new File("testFileZipper2.png");
        File file3 = new File("testFileZipper3.txt");

        File[] files  = new File[] {file1,file2,file3};

        File[] validatedFiles = null;

        try {
            validatedFiles = FileZipper.validateFiles(files);
        }
        catch (FileSystemException e) {
            System.out.println(e.getMessage());
        }
        finally {
            System.out.println(validatedFiles[0]);
        }


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
