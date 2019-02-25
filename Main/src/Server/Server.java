package Server;

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
      this.socket = new ServerSocket(8888);
    }
    catch (IOException e) {
      System.out.println("Error setting up server: " + e.getMessage());
    }
  }

  /*
  Server(int port) {
    try {
      this.socket = new ServerSocket(port);
    }
    catch (IOException e) {
      System.out.println("Error setting up server: " + e.getMessage());
    }
  }
  */

  public void acceptConnections() {
    try {
      Socket socket = this.socket.accept();

      Thread thread = new Thread(new ConnectionThread(socket,"."));
      thread.start();
    }
    catch (IOException e) {
      System.out.println("Error accepting connections: " + e.getMessage());
    }
  }


  /* TEST USE OF SERVER

  public static void main(String[] args) {
    Server server = new Server();
    while (true) {
      server.acceptConnections();
    }
  }
  */

}
