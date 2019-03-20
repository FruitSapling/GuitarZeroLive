/**
 * @author Tom
 * Refactored for Store Mode from Slash Mode by @Morgan
 */
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class StoreModel {
    public boolean menuOpen;

    private PropertyChangeSupport support;
    public CarouselMenu carouselMenu;

    public StoreModel() {
        //getFilesFromServer(0);
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

    public CarouselButton[] getFilesFromServer(int page) {
      CarouselButton[] buttons = new CarouselButton[5];
        Client client = new Client("localhost",Constants.STORE_FILE_PATH,Constants.CLIENT_PORT_NUMBER);

        client.connect();

        if (client.socket != null) {
            File zippedFiles = client.receiveFiles("zipped",page);
            FileUnzipper unzipper = new FileUnzipper(Constants.STORE_FILE_PATH);

            File[] unzippedFiles = unzipper.unzipFiles(zippedFiles);

            buttons = getButtonsFromFiles(unzippedFiles);

            setCarouselMenu(buttons);
        }
        else {
            System.out.println("Cannot connect to server");
            System.exit(0);
        }

        return buttons;
    }

    private CarouselButton[] getButtonsFromFiles(File[] files) {
        ArrayList<CarouselButton> buttons = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {

            File file = files[i];
            FileUnzipper unzipper = new FileUnzipper(Constants.STORE_FILE_PATH);

            FileFilter filter = (pathname) ->
                pathname.getName().endsWith(".jpg")
                || pathname.getName().endsWith(".png");

            File folder = unzipper.unzipFiles(file)[0].getParentFile();

            File imageFile = folder.listFiles(filter)[0];

            String filePath = Constants.STORE_FILE_PATH + "/song" + i + imageFile.getName();

            CarouselButton button = new CarouselButton(filePath,imageFile.getName()) {
                @Override
                public void onClick() {
                    System.out.println(getButtonName());
                }
            };

            buttons.add(button);

        }

        return buttons.toArray(new CarouselButton[buttons.size()]);

    }

}