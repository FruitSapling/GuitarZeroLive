
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Paths;



public class Client {
  public Socket socket = null;
  private String ip;
  private int port;
  private String dir;

  Client(String ip, String rootDir, int port) {
    this.ip = ip;
    this.port = port;
    this.dir = rootDir;
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
  * A method to send a file (inc zipped folder) over the connection to the server.
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



  /*
  * A method to receive a file (inc zipped folder) over the connection with the server.
  * The resulting created file is stored in the new zip folder filename + '.zip' in the
  * root directory for the connection specified in the constructor as 'dir'.
  * The file returned is this newly created zip file.
  */
  private File receiveFile(String fileName) {
    File outputFile = new File(Paths.get(dir,fileName + ".zip").toString());

    try {
      OutputStream out = this.socket.getOutputStream();
      InputStream in = this.socket.getInputStream();
      DataInputStream inStream = new DataInputStream(in);

      out.write(0);
      out.write(0);

      FileOutputStream fileOut = new FileOutputStream(outputFile);

      byte[] buffer = new byte[1024];

      while (inStream.read(buffer,0,buffer.length) != -1) {
        fileOut.write(buffer);
      }
      fileOut.close();
      inStream.close();
      in.close();

    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return outputFile;
  }



  public static void main(String[] args) {
    Client client = new Client("localhost",".",8888);
    client.connect();

    if (client.socket != null) {
      client.receiveFile("srcc");
      //client.sendFile(new File("src.zip"));
    }

  }

}
