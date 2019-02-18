import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
  public void start(Stage primaryStage) {
    Thread t = new Thread(new GuitarPollingThread());
    t.start();
    ViewMain mainMenu = new ViewMain();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
