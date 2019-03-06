import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * An extension of JButton including a method 'onClick()' which should be the code to execute
 * when the button is selected ("clicked").
 *
 * @author Willem
 */
public abstract class CarouselButton extends JButton {
  public CarouselButton(String imageFileName) {
    this.setIcon(new ImageIcon(imageFileName));
  }
  public abstract void onClick();
}
