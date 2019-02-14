import java.util.ArrayList;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;


public class GuitarPollingThread implements Runnable {

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
    }

    public void run() {

        Controller ctrl = getGuitarController();

        if (ctrl == null) {
            System.out.println("yalla");
            //TODO: handle case when guitar cannot be found.
            return;
        }
        System.out.println("WOO GOT THRU");

        Component[] cmps = ctrl.getComponents();
        float[]     vals = new float[  cmps.length ];

        while( true ) {
            if ( ctrl.poll() ) {

                System.out.println("vals[0] is: " + vals[0]);

                if (vals[ 0 ] == 1.0) {

                }

                for ( int i = 0; i < cmps.length; i = i + 1 ) { /* store */
                    float value = cmps[ i ].getPollData();
                    vals[ i ] = value;

                    System.out.println("vals " + i + " is " + vals[i]);

                    if (value == 1.0) {
                        System.out.println(Constants.GUITAR_BUTTONS.get(i));
                        fireGuitarButtonPressedEvent(Constants.GUITAR_BUTTONS.get(i));
                    }

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
