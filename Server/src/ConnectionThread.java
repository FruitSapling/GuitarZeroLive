/*
* Class that is run as a thread to create a connection to a client.
* @author Mark Newell
*/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ConnectionThread implements Runnable {
  private Socket client;
  private File dir;

  ConnectionThread(Socket client, String sourceDirectory) {
    this.client = client;
    this.dir = new File(sourceDirectory);
  }

  @Override
  public void run() {
    System.out.println("Connected");

    int task;
    try {
      InputStream inStream = client.getInputStream();
      task = inStream.read();
      //inStream.close();

      switch (task) {
        case 0:
          System.out.println("Output");
          int page = inStream.read();
          sendFiles(page);
          break;
        case 1:
          System.out.println("Input");
          receiveFile();
          break;
        default:
          System.out.println("Nothing");
      }
      inStream.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }



  /*
  * Method to retrieve the next files from the client,
  * Deprecated so maybe not needed.
  */
  @Deprecated
  private File receiveFile() {
    File outputFile = new File(Paths.get(dir.getName(),"src.zip").toString());

    try {
      InputStream in = client.getInputStream();
      DataInputStream inStream = new DataInputStream(in);

      outputFile.createNewFile();
      FileOutputStream out = new FileOutputStream(outputFile);

      byte[] buffer = new byte[1024];

      while (inStream.read(buffer,0,buffer.length) != -1) {
        out.write(buffer);
      }
      out.close();
      inStream.close();
      in.close();

    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return outputFile;
  }



  /*
   * To be used on the server to send the files back to the client
   */
  public File[] getZippedFiles() {
    FileFilter filter = (pathname) -> pathname.getPath().endsWith(".zip");
    File[] zippedFolders = dir.listFiles(filter);
    return zippedFolders;
  }


  /*
  * A method to send a page (5) folders to the client at a time.
  * This page integer should start at 0 for page 1.
  */
  private void sendFiles(int page) {
    try {
      OutputStream out = client.getOutputStream();
      DataOutputStream outputStream = new DataOutputStream(out);

      File[] zippedFolders = Arrays.copyOfRange(getZippedFiles(),(page*5),((page+1)*5));
      FileZipper zipper = new FileZipper(zippedFolders,".","tempZip");

      File zippedFolder = zipper.zipFiles();
      zippedFolder.createNewFile();

      FileInputStream input = new FileInputStream(zippedFolder);
      int bufferSize = 1024;

      for (int i = 0; i <= (zippedFolder.length()/bufferSize); i++) {
        byte[] buffer = new byte[bufferSize];
        input.read(buffer,0,bufferSize);
        outputStream.write(buffer,0,bufferSize);
      }

      outputStream.close();
      out.close();
      input.close();

      Files.delete(Paths.get("tempZip.zip"));

    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

}
