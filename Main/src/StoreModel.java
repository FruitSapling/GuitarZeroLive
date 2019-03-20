/**
 * @author Tom
 * Refactored for Store Mode from Slash Mode by @Morgan
 * Wrote functionality to get files from server @Mark
 */
import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class StoreModel {
    public boolean menuOpen;

    private PropertyChangeSupport support;
    public CarouselMenu carouselMenu;

    public StoreModel() {
        this.support = new PropertyChangeSupport(this);
        support.addPropertyChangeListener(carouselMenu);
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

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.support.addPropertyChangeListener(pcl);
    }

    public void showMenu() {
        this.support.firePropertyChange(null,null,null);
    }
    public void hideMenu() {
        this.support.firePropertyChange(null,null,null);
    }

    public CarouselButton[] getFilesFromServer(int page, JFrame frame) {
      CarouselButton[] buttons = new CarouselButton[5];
        Client client = new Client("localhost",Constants.STORE_FILE_PATH,Constants.CLIENT_PORT_NUMBER);

        client.connect();

        if (client.socket != null) {
            File zippedFiles = client.receiveFiles("zipped",page);
            FileUnzipper unzipper = new FileUnzipper(Constants.STORE_FILE_PATH);

            unzipper.unzipFiles(zippedFiles);

            for(File file: getFolders(Constants.STORE_FILE_PATH,".zip")) {
                unzipper.unzipFiles(file);
            }

            buttons = getButtonsFromFiles(frame);

            setCarouselMenu(buttons);
            cleanUpStoreFolder();
        }
        else {
            System.out.println("Cannot connect to server");
            System.exit(0);
        }

        return buttons;
    }

    private CarouselButton[] getButtonsFromFiles(JFrame frame) {
        ArrayList<CarouselButton> buttons = new ArrayList<>();

        File[] files = getFolders(Constants.STORE_FILE_PATH,"dir");

        for (int i = 0; i < files.length; i++) {

            File file = files[i];

            FileFilter filter = (pathname) ->
                pathname.getName().endsWith(".jpg")
                || pathname.getName().endsWith(".png");


            File imageFile = file.listFiles(filter)[0];

            String filePath = imageFile.getPath();

            CarouselButton button = new CarouselButton(filePath,imageFile.getName().split("[.]")[0]) {
                @Override
                public void onClick() {
                    JOptionPane.showMessageDialog(null, "Selected track has become: " + getButtonName(),
                            "Selection Info", JOptionPane.INFORMATION_MESSAGE);

                    //SelectedTrack(getButtonName());

                }
            };

            buttons.add(button);

        }

        for (int i = buttons.size(); i < 5; i++) {
            CarouselButton button = new CarouselButton(Constants.DEFAULT_WHITE_IMAGE_PATH,"none") {
                @Override
                public void onClick() {
                    System.out.println(getButtonName());
                }
            };
            buttons.add(button);
        }

        return buttons.toArray(new CarouselButton[buttons.size()]);

    }



    public File[] getFolders(String parentPath, String extension) {
        File file = new File(parentPath);
        FileFilter filter = null;
        if (extension.equals("dir")) {
            filter = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            };
        }
        else {
            filter = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith(extension);
                }
            };
        }


        return file.listFiles(filter);
    }


    public void cleanUpStoreFolder() {
        File[] zipFolders = getFolders(Constants.STORE_FILE_PATH,".zip");

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

}