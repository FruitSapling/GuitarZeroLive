import java.awt.Image;
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
    ImageIcon imageIcon = new ImageIcon(imageFileName); // load the image to a imageIcon
    Image image = imageIcon.getImage(); // transform it
    Image newimg = image.getScaledInstance(128, 168,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
    imageIcon = new ImageIcon(newimg);

    this.setIcon(imageIcon);
    this.buttonName = buttonName;
  }

  public CarouselButton(ImageIcon image, String buttonName) {
    this.setIcon(image);
    this.buttonName = buttonName;
  }

  public String getButtonName() {
    return buttonName;
  }

  public abstract void onClick(
  );
  public abstract void onHighlight();
}
