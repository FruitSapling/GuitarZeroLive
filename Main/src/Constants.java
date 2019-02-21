import java.util.HashMap;

public class Constants {
    public static String GUITAR_HERO = "Guitar Hero";
    public static int GUITAR_POLL_DELAY = 50;
    public static HashMap<Integer, GuitarButton> INDEX_TO_BUTTON = new HashMap<Integer, GuitarButton>() {{
        put(0, GuitarButton.FRET_4);
        put(1, GuitarButton.FRET_1);
        put(2, GuitarButton.FRET_2);
        put(3, GuitarButton.FRET_3);
        put(4, GuitarButton.FRET_5);
        put(5, GuitarButton.FRET_6);
        put(8, GuitarButton.ZERO_POWER);
        put(10, GuitarButton.ESCAPE);
        put(12, GuitarButton.BENDER_BUTTON);
        put(13, GuitarButton.BENDER_JOYSTICK);
        put(15, GuitarButton.STRUM);
        put(17, GuitarButton.WHAMMY);
    }};
    public static HashMap<GuitarButton, Integer> BUTTON_TO_INDEX = new HashMap<GuitarButton, Integer>() {{
        put(GuitarButton.FRET_4, 0);
        put(GuitarButton.FRET_1, 1);
        put(GuitarButton.FRET_2, 2);
        put(GuitarButton.FRET_3, 3);
        put(GuitarButton.FRET_5, 5);
        put(GuitarButton.FRET_6, 6);
        put(GuitarButton.ZERO_POWER, 8);
        put(GuitarButton.ESCAPE, 10);
        put(GuitarButton.BENDER_BUTTON, 12);
        put(GuitarButton.BENDER_JOYSTICK, 13);
        put(GuitarButton.STRUM, 15);
        put(GuitarButton.WHAMMY, 17);
    }};

    //The indices of the binary buttons on the guitar controller
    public static int[] binaryButtonsIndices = new int[] {0,1,2,3,4,5,8,10,12};
}
