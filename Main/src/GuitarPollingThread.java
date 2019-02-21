import java.util.ArrayList;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;


public class GuitarPollingThread implements Runnable {

    private float MIN_BTN_VALUE = 0.05f;

    private final ArrayList<GuitarButtonListener> listeners = new ArrayList<GuitarButtonListener>();

    /*
    Returns the guitar controller if it can be found,
    else it returns null.
    Code using this method must check for null and handle this appropriately.
     */
    public Controller getGuitarController() {
        ControllerEnvironment cenv  = ControllerEnvironment.getDefaultEnvironment();
        Controller[]          ctrls = cenv.getControllers();

        for ( Controller ctrl : ctrls ) {
            if ( ctrl.getName().contains( Constants.GUITAR_HERO ) ) {
                return ctrl;
            }
        }
        return null;
    }

    public void fireGuitarButtonPressedEvent(GuitarButton btn) {
        GuitarButtonPressedEvent e = new GuitarButtonPressedEvent(this, btn);
        for (GuitarButtonListener listener: listeners) {
            listener.guitarButtonPressReceived(e);
        }
    }

    public boolean beingPressed(float value) {
        if (value < MIN_BTN_VALUE && value > -MIN_BTN_VALUE) return true;
        else return false;
    }

    public void run() {

        Controller ctrl = getGuitarController();

        if (ctrl == null) {
            //TODO: handle case when guitar cannot be found.
            return;
        }

        Component[] cmps = ctrl.getComponents();
        float[]     vals = new float[  cmps.length ];

        while( true ) {
            if ( ctrl.poll() ) {

                int LAST_BINARY_BUTTON = 0;

                for (int i = 0; i < LAST_BINARY_BUTTON; i++) {
                    float value = cmps[ i ].getPollData();
                    vals[ i ] = value;

                    System.out.println("vals " + i + " is " + vals[i]);

                    if (value == 1.0) {
                        System.out.println(Constants.GUITAR_BUTTONS.get(i));
                        fireGuitarButtonPressedEvent(Constants.GUITAR_BUTTONS.get(i));
                    }
                }

                //TODO: check strum index value;
                int STRUM_INDEX = 0;
                float strumValue = vals[STRUM_INDEX];
                if (strumValue != 0.0) {
                    fireGuitarButtonPressedEvent(Constants.GUITAR_BUTTONS.get(STRUM_INDEX));
                }

                int WHAMMY_INDEX = 0;
                float whammy_value = vals[WHAMMY_INDEX];
                if (beingPressed(whammy_value)) {
                    fireGuitarButtonPressedEvent(Constants.GUITAR_BUTTONS.get(STRUM_INDEX));
                }


            }

            try { /* delay */
                Thread.sleep( Constants.GUITAR_POLL_DELAY );
            } catch ( Exception exn ) {
                System.out.println( exn ); System.exit( 1 );
            }
        }
    }
}
