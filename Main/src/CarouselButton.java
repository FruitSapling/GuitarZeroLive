import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * An extension of JButton including a method 'onClick()' which should be the code to execute
 * when the button is selected ("clicked").
 *
 * @author Willem
 */
public abstract class CarouselButton extends JButton {

  private String buttonName;

  public CarouselButton(String imageFileName, String buttonName) {
    this.setIcon(new ImageIcon(imageFileName));
    this.buttonName = buttonName;
  }

  public String getButtonName() {
    return buttonName;
  }

  public abstract void onClick(
  );
}
