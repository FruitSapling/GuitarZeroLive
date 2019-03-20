import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import javax.imageio.ImageIO;

public class TutorialModel {

  public BufferedImage currentStep;
  public int currentStepIndex = 0;
  private PropertyChangeSupport support;
  private BufferedImage[] steps;

  public TutorialModel() {
    this.support      = new PropertyChangeSupport(this);
    this.steps        = new BufferedImage[Constants.tutorialStepsPaths.length];

    for (int i = 0; i < Constants.tutorialStepsPaths.length; i++) {
      try {
        steps[i] = ImageIO.read(new File(Constants.tutorialStepsPaths[i]));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    // Initialise Tutorial mode with the first step
    this.currentStep = steps[0];
  }

  // Call this method when the model should move to the next tutorial step.
  public void nextStep() {
    if (currentStepIndex != Constants.tutorialStepsPaths.length-1) {
      currentStepIndex += 1;
      currentStep = steps[currentStepIndex];
      support.firePropertyChange("nextStep", null, null);
    } else { // We have reached the last tutorial step
      support.firePropertyChange("endTutorial", null, null);
    }

  }

  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    this.support.addPropertyChangeListener(pcl);
  }

}
