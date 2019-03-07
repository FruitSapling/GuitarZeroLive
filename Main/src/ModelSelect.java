/**
 * @author Morgan
 * Skeleton code of ModelMain used to create class
 */

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModelSelect {
    public boolean menuOpen;

    private PropertyChangeSupport support;
    public CarouselMenu carouselMenu;

    public ModelSelect() {
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
}
