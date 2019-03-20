
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUnzipper {
    private String dir;


    FileUnzipper(String saveDirectory) {
        this.dir = saveDirectory;
        Path path = Paths.get(saveDirectory);
        if (!Files.exists(path)) {
            File file = new File(saveDirectory);
            file.mkdir();
        }
    }



    /*
    * Method that takes a zip file and unzips it, placing the resulting files in the
    * dir specified in the FileUnzipper constructor (with there original names/extensions).
    */
    public File[] unzipFiles(File zippedFile) {
        ArrayList<File> files = new ArrayList<>();
        byte[] buffer = new byte[1024];

        try{

          //create output directory is not exists
          File folder = new File(Constants.STORE_FILE_PATH);
          if(!folder.exists()){
            folder.mkdir();
          }

          //get the zip file content
          ZipInputStream zis =
              new ZipInputStream(new FileInputStream(zippedFile));
          //get the zipped file list entry
          ZipEntry ze = zis.getNextEntry();

          while(ze!=null){

            String fileName = ze.getName();
            File newFile = new File(Constants.STORE_FILE_PATH + File.separator + fileName);
            files.add(newFile);

            System.out.println("file unzip : "+ newFile.getAbsoluteFile());

            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();

            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = zis.read(buffer)) > 0) {
              fos.write(buffer, 0, len);
            }

            fos.close();
            ze = zis.getNextEntry();
          }

          zis.closeEntry();
          zis.close();

          System.out.println("Done");

        }catch(IOException ex){
          ex.printStackTrace();
        }
        return files.toArray(new File[files.size()]);
      }


      public File unzipFilesToFolder(File zippedFolder) {
        File[] files = unzipFiles(zippedFolder);
        String name = zippedFolder.getName().split("[.]")[0];

        System.out.println(name);

        Path newFolder = Paths.get(Constants.STORE_FILE_PATH,name);

        File folder = new File(name);
        folder.mkdir();

        for (File file:files) {
          Path newPath = Paths.get(Constants.STORE_FILE_PATH,name,file.getName());
          try {
            Files.move(file.toPath(),newPath);
          }
          catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
          }

        }
      return folder;
    }

}
