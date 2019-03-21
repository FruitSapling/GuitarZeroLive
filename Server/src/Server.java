/**
 * @author Mark Newell
 *
 * A basic TCP server that allows multiple clients (game clients) to connect.
 * It then takes commands from the client machines as to when to send the zip folders of songs for the store mode
 * */


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Server must be run separately outside of intellij to allow the client to connect to it.
 */
public class Server {
  ServerSocket socket;

  Server() {
    try {
      this.socket = new ServerSocket(Constants.PORT_NUMBER);
    }
    catch (IOException e) {
      System.out.println("Error setting up server: " + e.getMessage());
    }
  }


  /*
  * Make a new thread for each connection so the server can take many connections
  * */
  public void acceptConnections() {
    try {
      Socket socket = this.socket.accept();

      Thread thread = new Thread(new ConnectionThread(socket,Constants.RESOURCES_FOLDER));
      thread.start();
    }
    catch (IOException e) {
      System.out.println("Error accepting connections: " + e.getMessage());
    }
  }


  public static void main(String[] args) {
    Server server = new Server();
    while (true) {
      server.acceptConnections();
    }
  }


}
