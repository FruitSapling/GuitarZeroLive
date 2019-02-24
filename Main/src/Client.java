import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Client {
  public Socket socket = null;
  private String ip;
  private int port;
  private String dir;

  Client(String ip, int port) {
    this.ip = ip;
    this.port = port;
  }


  /*
  * A method to initiate the connection to the server if it hasn't already.
  * Implements the idea of lazy instantiation in terms of the Socket.
  */
  public void connect() {
    if (this.socket == null) {
      try {
        this.socket = new Socket(ip, port);
      } catch (IOException e) {
        System.out.println("Error connecting to server: " + e.getMessage());
      }
    }
    else {
      System.out.println("Client already connected");
    }
  }


  /*
  * A method to send a file over the connection to the server.
  */
  public void sendFile(File file) {
    try {
      OutputStream out = this.socket.getOutputStream();
      DataOutputStream dataOut = new DataOutputStream(out);

      // Initial byte is sent to tell the server what type of request it is (send or receive)
      out.write(1);


      // Sending of the file
      FileInputStream input = new FileInputStream(file);
      int bufferSize = 1024;

      for (int i = 0; i <= (file.length()/bufferSize); i++) {
        byte[] buffer = new byte[bufferSize];
        input.read(buffer,0,bufferSize);
        dataOut.write(buffer,0,bufferSize);
      }

      dataOut.close();
      out.close();

    }
    catch (IOException e) {
      System.out.println("Error sending file: " + e);
    }
  }



  public File[] getFile() {
    ArrayList<File> files = new ArrayList<>();
    try {
      OutputStream out = this.socket.getOutputStream();
      InputStream in = this.socket.getInputStream();

      // Send initial byte to tell the server that it is a request.
      out.write(0);



      // Read files

      ZipInputStream inStream = new ZipInputStream(in);
      ZipEntry zipfile;

      while ((zipfile = inStream.getNextEntry()) != null) {
        File file = new File(zipfile.getName());
      }


      //TODO: Finish reading in the bytes and convert them into File objects.

      in.close();
      out.close();

    }
    catch (IOException e) {
      System.out.println("Error getting files: " + e.getMessage());
    }

    return files.size() > 0 ? files.toArray(new File[files.size()]) : null;
  }


  public static void main(String[] args) {
    Client client = new Client("localhost",8888);
    client.connect();

    if (client.socket != null) {
      client.sendFile(new File("src.zip"));
    }

  }


}
