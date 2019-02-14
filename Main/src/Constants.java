import java.util.HashMap;

public class Constants {
    public static String GUITAR_HERO = "Guitar Hero";
    public static int GUITAR_POLL_DELAY = 50;
    public static HashMap<Integer, GuitarButton> GUITAR_BUTTONS = new HashMap<Integer, GuitarButton>() {{
        put(0, GuitarButton.FRET_4);
        put(1, GuitarButton.FRET_1);
        put(2, GuitarButton.FRET_2);
        put(3, GuitarButton.FRET_3);
        put(4, GuitarButton.FRET_5);
        put(5, GuitarButton.FRET_6);

    }};
}
