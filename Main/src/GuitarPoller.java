import java.util.ArrayList;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 * A class to generate Guitar button press events.
 * This class polls the Guitar with a while-loop, and when it detects a button press
 * in the Guitar, it generates this event and tells controller to fire an event.
 *
 * @author Willem
 *
 */
public class GuitarPoller implements Runnable {

    private float MIN_BTN_VALUE = 0.05f;
    private GuitarController controller;

    public GuitarPoller(GuitarController controller) {
        this.controller = controller;
    }

    /*
    Returns the Guitar controller if it can be found,
    else it returns null.
    Code using this method must check for null and handle this appropriately.
     */
    public Controller getGuitarController() {
        ControllerEnvironment cenv  = ControllerEnvironment.getDefaultEnvironment();
        Controller[]          ctrls = cenv.getControllers();

        for ( Controller ctrl : ctrls ) {
            if ( ctrl.getName().contains( Constants.GUITAR_HERO ) ) {
                System.out.println("Connected to Guitar");
                return ctrl;
            }
        }
        return null;
    }


//    public synchronized void addListener(GuitarButtonListener listener){
//        controller.listeners.add(listener);
//    }
//
//    public synchronized void removeListener(GuitarButtonListener listener){
//        controller.listeners.remove(listener);
//    }

    public boolean beingPressed(float value) {
        return value < (-1 * MIN_BTN_VALUE) || value > MIN_BTN_VALUE;
    }

    public void run() {

        Controller ctrl = getGuitarController();

        if (ctrl == null) {
            //TODO: make user-friendly GUI to ask user to connect Guitar.
            return; //end the thread,
        }

        //TODO:
        // find which buttons are being pressed
        // send them to controller

        ArrayList<GuitarButton> buttonsPressed = new ArrayList<>();

        Component[] cmps        = ctrl.getComponents();
        float[]     vals        = new float[  cmps.length ];
        //values from the previous loop iteration
        float[]     prevVals   = new float[  cmps.length ];

        while( true ) {
            if ( ctrl.poll() ) {
                int LAST_BINARY_BUTTON = 0;
                buttonsPressed.clear();

                //loop to populate the 'vals' array
                for (int i = 0; i < cmps.length; i++) {
                    float value = cmps[ i ].getPollData();
                    vals[i] = value;
                }
                //generate events for any binary buttons being pressed
                for (int i: Constants.binaryButtonsIndices) {
                    if (vals[i] == 1.0) {
                        buttonsPressed.add(Constants.INDEX_TO_BUTTON.get(i));
//                        controller.fireGuitarButtonPressedEvent(Constants.INDEX_TO_BUTTON.get(i));
                    }
                }

                int STRUM_INDEX = Constants.BUTTON_TO_INDEX.get(GuitarButton.STRUM);
                float strumValue = vals[STRUM_INDEX];
                float prevStrumValue = prevVals[STRUM_INDEX];

                int ZERO_POWER_INDEX = Constants.BUTTON_TO_INDEX.get(GuitarButton.ZERO_POWER);
                if (!beingPressed(prevVals[ZERO_POWER_INDEX]) && beingPressed(vals[ZERO_POWER_INDEX])) {
                    controller.zeroPowerPressed();
                }

                int BENDER_INDEX = Constants.BUTTON_TO_INDEX.get(GuitarButton.BENDER_JOYSTICK);
                float prevValue = vals[BENDER_INDEX];
                float benderValue = vals[BENDER_INDEX];

                if ((prevVals[BENDER_INDEX]==0.0) && (vals[BENDER_INDEX]>0.0)) {
                    controller.guitarStrummed(buttonsPressed);
                }

                int WHAMMY_INDEX = Constants.BUTTON_TO_INDEX.get(GuitarButton.WHAMMY);
                float whammyValue = vals[WHAMMY_INDEX];

                // If either the whammy, bender joystick or strum bar was pressed
                if (beingPressed(strumValue)
                    || vals[BENDER_INDEX]>0.0
                    || whammyValue > Constants.WHAMMY_RESTING_VALUE) {
                    controller.zeroPowerWhammyOrBenderPressed();
                }

                // If the Guitar was just strummed...
                if ((!beingPressed(strumValue)) && (beingPressed(prevStrumValue))) {

                    if (prevStrumValue==1.0) {
                        controller.strumUp();
                    } else if (prevStrumValue==-1.0) {
                        controller.strumDown();
                    }

                    controller.guitarStrummed(buttonsPressed);
//                    controller.fireGuitarButtonPressedEvent(Constants.INDEX_TO_BUTTON.get(STRUM_INDEX), prevStrumValue);
                }

            }

            //set prevVals to current vals
            for (int i = 0; i < vals.length; i++) {
                prevVals[i] = vals[i];
            }

            try { /* delay */
                Thread.sleep( Constants.GUITAR_POLL_DELAY );
            } catch ( Exception exn ) {
                System.out.println( exn );
                System.exit( 1 );
            }
        }
    }
}
