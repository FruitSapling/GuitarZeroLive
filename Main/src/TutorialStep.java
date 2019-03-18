import java.awt.Point;
import java.awt.image.BufferedImage;

public class TutorialStep {

  BufferedImage image;
  Point p;

  public TutorialStep(BufferedImage image, Point p) {
    this.image = image;
    this.p = p;
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  public void setPosition(int x, int y) {
    this.p = p;
  }


}
