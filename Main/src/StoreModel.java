/**
 * @author Tom & Mark
 * Refactored for Store Mode from Slash Mode by @Morgan
 * Wrote functionality to get files from server @Mark
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class StoreModel {
    public boolean menuOpen;
    public boolean anotherPage;

    private PropertyChangeSupport support;
    public CarouselMenu carouselMenu;
    public int page;

    public StoreModel() {
        this.support = new PropertyChangeSupport(this);
        support.addPropertyChangeListener(carouselMenu);
        this.page = 0;
    }

    public void setCarouselMenu(CarouselButton[] buttons) {
        this.carouselMenu = new CarouselMenu(buttons, 0, 400);
        carouselMenu.revalidate();
        carouselMenu.repaint();
    }

    public void cycleCarouselLeft() {
        this.support.firePropertyChange("cycleCarousel", null, "left");
    }

    public void cycleCarouselRight() {
        this.support.firePropertyChange("cycleCarousel", null, "right");
    }

    public void selectMode() {
        this.support.firePropertyChange("selectMode", null, null);
    }

    public void backMode() {
      this.support.firePropertyChange("backMode", null, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.support.addPropertyChangeListener(pcl);
    }

    public void showMenu() {
        this.support.firePropertyChange(null,null,null);
    }
    public void hideMenu() {
        this.support.firePropertyChange(null,null,null);
    }

    /**
     * @author Mark
     * Method to use Client to connect to the server,
     * recieve the zipped files,
     * unzip them and create buttons for the carousel respectivly.
     * */
    public CarouselButton[] getFilesFromServer(JFrame frame) {
      CarouselButton[] buttons = new CarouselButton[5];
        Client client = new Client(Constants.SERVER_IP_ADDRESS,Constants.STORE_FILE_PATH,Constants.CLIENT_PORT_NUMBER);

        try {
          client.connect();
        }
        catch (IOException e) {
          JOptionPane.showMessageDialog(null, "Error connecting to the server",
              "Error Info", JOptionPane.INFORMATION_MESSAGE);
          backToMain(frame);
          return buttons;
        }


        if (client.socket != null) {
            File zippedFiles = client.receiveFiles("zipped",page);
            FileUnzipper unzipper = new FileUnzipper(Constants.STORE_FILE_PATH);

            // Unzip received zip file
            unzipper.unzipFiles(zippedFiles);

            // If there are less than 3 files sent there wont be any more so can't have another page.
            File[] files = getFolders(Constants.STORE_FILE_PATH,".zip");
            this.anotherPage = files.length >= 3;

            for(File file: files) {
                unzipper.unzipFiles(file);
            }

            buttons = getButtonsFromFiles(frame);

            setCarouselMenu(buttons);

            // Get rid of useless zip folders
            // In future would look into keeping zip folders to store more files locally
            // with only a small disk space usage. Ran out of time to implement this.
            cleanUpStoreFolder(".zip");
        }
        else {
            System.out.println("Cannot connect to server");
            System.exit(0);
        }

        return buttons;
    }

    /**
     * @author Mark
     * A method to create the buttons from the extracted files using their images.
     * */
    private CarouselButton[] getButtonsFromFiles(JFrame frame) {
        ArrayList<CarouselButton> buttons = new ArrayList<>();

        File[] files = getFolders(Constants.STORE_FILE_PATH,"dir");

        // Make only 3 buttons with actual images even if too many are in the folder
        int limiter = files.length > 5 ? 5 : files.length;

        for (int i = 0; i < limiter; i++) {

            File file = files[i];

            // Accept both image types just in case
            FileFilter filter = (pathname) ->
                pathname.getName().endsWith(".jpg")
                || pathname.getName().endsWith(".png");


            File imageFile = file.listFiles(filter)[0];

            String filePath = imageFile.getPath();

            CarouselButton button = new CarouselButton(filePath,imageFile.getName().split("[.]")[0]) {
                @Override public void onHighlight() {}
                @Override
                public void onClick() {
                    JOptionPane.showMessageDialog(null, "You have bought: " + getButtonName(),
                            "Selection Info", JOptionPane.INFORMATION_MESSAGE);

                    // Move the track's folder to the Music folder and remove it from the Store folder.
                    moveSelectedTrack(getButtonName());
                    backToMain(frame);

                }
            };

            buttons.add(button);

        }

        for (int i = buttons.size(); i < 5; i++) {
            CarouselButton button = new CarouselButton(Constants.DEFAULT_WHITE_IMAGE_PATH,"none") {
                @Override public void onHighlight() {}
                @Override
                public void onClick() {}
            };
            buttons.add(button);
        }

        return buttons.toArray(new CarouselButton[buttons.size()]);

    }



    /**
     * @author Mark
     * A simple method to get an array of child files at a given location
     * of the given extension.
     * */
    public File[] getFolders(String parentPath, String extension) {
        File file = new File(parentPath);
        FileFilter filter;
        if (extension.equals("dir")) {
            filter = (pathname) -> pathname.isDirectory();
        }
        else {
            filter = (pathname) -> pathname.getName().endsWith(extension);
        }


        return file.listFiles(filter);
    }


    /**
     * @author Mark
     * A simple method to remove files/folders from the Store folder.
     * */
    public void cleanUpStoreFolder(String extension) {
        File[] zipFolders = getFolders(Constants.STORE_FILE_PATH,extension);

        for (File folder: zipFolders) {
            try {
                Files.delete(folder.toPath());
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }

        }
    }

    /**
     * @author Mark
     * Simple method to end the current frame and create a new Main frame.
     * */
    public void backToMain(JFrame frame) {
      frame.dispose();
      MainModel model = new MainModel();
      MainController controller1 = new MainController(model);
      MainGuitarController controller2 = new MainGuitarController(model);
      new MainView(model, controller1, controller2);
    }



    /**
     * @author Mark
     * Move a folder of the given folderName (in the Store folder) to the Music folder.
     * */
    private void moveSelectedTrack(String folderName) {

      Path source = Paths.get(Constants.STORE_FILE_PATH,folderName);
      Path destination = Paths.get(Constants.ZIP_FILE_PATH,folderName);

      if (!Files.exists(destination)) {
        try {
          Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
          System.out.println(e.getMessage());
          System.exit(0);
        }
      }

    }


}