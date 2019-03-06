import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarouselHandler implements ActionListener {

    private JFrame frame;
    private CarouselButton button;
    private ModelMain model1;
    private ControllerMain controller;
    private GuitarButtonController controller2;

    public CarouselHandler(CarouselButton button, JFrame frame){
        this.button = button;
        this.frame = frame;
    }

    public CarouselHandler(CarouselButton button, JFrame frame, ModelMain model1,
                           ControllerMain controller, GuitarButtonController controller2){
        this.button = button;
        this.frame = frame;
        this.model1 = model1;
        this.controller = controller;
        this.controller2 = controller2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (button.getButtonName() == "Exit") {
            System.exit(0);
        }
        if (button.getButtonName() == "Select") {
            frame.dispose();
            new ViewSelect(model1, controller, controller2);
        }
        if (button.getButtonName() == "Play") {
            //TODO: Tom pls check this one out
//            frame.dispose();
//            new ViewPlay(model1);
        }
        if (button.getButtonName() == "Store") {
            frame.dispose();
            new ViewStore(model1, controller, controller2);
        }
        if (button.getButtonName() == "Tutorial") {
            //TODO: Tutorial Mode Handler
        }
    }
}
