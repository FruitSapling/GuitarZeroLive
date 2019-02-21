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

public class Client {
  private Socket socket = null;
  private String ip;
  private int port;

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
      byte[] buffer = new byte[4096];

      while(input.read(buffer) > 0) {
        dataOut.write(buffer);
      }

      dataOut.close();
      out.close();

    }
    catch (IOException e) {
      System.out.println("Error sending file: " + e);
    }
  }



  public File[] getFile() {
    ArrayList<File> files = null;
    try {
      OutputStream out = this.socket.getOutputStream();

      // Send initial byte to tell the server that it is a request.
      out.write(0);

      InputStream in = this.socket.getInputStream();
      DataInputStream inStream = new DataInputStream(in);

      FileOutputStream fileOut = new FileOutputStream("temp.zip");

      byte[] buffer = new byte[4096];

      //TODO: Finish reading in the bytes and convert them into File objects.

    }
    catch (IOException e) {
      System.out.println("Error getting files: " + e.getMessage());
    }

    return files.size() > 0 ? files.toArray(new File[files.size()]) : null;
  }


  public static void main(String[] args) {
    Client client = new Client("localhost",8888);
    client.connect();
    try {
      client.getFile();
    }
    catch (NullPointerException e) {
      System.out.println("Error null pointer: " + e.getMessage());
    }

  }


}
