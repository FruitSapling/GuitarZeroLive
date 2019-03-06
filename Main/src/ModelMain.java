import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModelMain {

  public boolean menuOpen;
  private PropertyChangeSupport support;
  public CarouselMenu carouselMenu;

  public ModelMain() {
    this.menuOpen = false;
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
    menuOpen = true;
    System.out.println("Showing Menu");
    this.support.firePropertyChange(null,null,null);
  }
  public void hideMenu() {
    menuOpen = false;
    System.out.println("Hiding Menu");
    this.support.firePropertyChange(null,null,null);
  }
  public void strummed() {
    System.out.println("Strum Me Daddy");
  }
  public void down() {

  }
}

