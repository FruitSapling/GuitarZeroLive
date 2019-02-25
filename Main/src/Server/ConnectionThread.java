package Server;

import java.io.BufferedInputStream;
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
import java.nio.file.Paths;

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

    int task = 2;
    try {
      InputStream inStream = client.getInputStream();
      task = inStream.read();
      //inStream.close();

      // TODO: Write methods to recieve and send files when necessary.
      switch (task) {
        case 0:
          System.out.println("Output");
          sendFiles();
          break;
        case 1:
          System.out.println("Input");
          recieveFile();
          break;
        default:
          System.out.println("Nothing");
      }
      //inStream.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }



  private File recieveFile() {
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



  private void sendFiles() {
    try {
      OutputStream out = client.getOutputStream();
      DataOutputStream outputStream = new DataOutputStream(out);

      File[] zippedFolders = getZippedFiles();
      FileZipper zipper = new FileZipper(zippedFolders,".","tempZip");
      File zippedFolder = zipper.zipFiles();

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

      outputStream.close();
      out.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

}
