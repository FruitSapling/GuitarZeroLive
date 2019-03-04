import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class CarouselButton extends JButton {
  public CarouselButton(String imageFileName) {
    this.setIcon(new ImageIcon(imageFileName));
  }
  public abstract void onClick();
}
