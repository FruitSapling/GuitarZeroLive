package Server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ConnectionThread implements Runnable {
  private Socket client;

  ConnectionThread(Socket client) {
    this.client = client;
  }

  @Override
  public void run() {
    System.out.println("Connected");

    while(!client.isClosed()) {
      int task = 2;
      try {
        InputStream inStream = client.getInputStream();
        task = inStream.read();

        // TODO: Write methods to recieve and send files when necessary.
        switch (task) {
          case 0:
            System.out.println("Output");
            break;
          case 1:
            System.out.println("Input");
            break;
          default:
            System.out.println("Nothing");
        }

      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  /*
   * To be used on the server to send the files back to the client
   */
  public File[] getZippedFiles() {
    //FileFilter filter = (pathname) -> pathname.toPath().endsWith(".zip");
    //zippedFolders = dir.listFiles(filter);
    //return zippedFolders;
    return null;
  }

}
