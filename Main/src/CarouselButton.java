import javax.swing.ImageIcon;
import javax.swing.JButton;

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
