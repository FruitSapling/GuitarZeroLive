import java.util.HashMap;
import java.lang.Math;

/**
 * The Constants class keeps all constants used throughout the code.
 *
 * @author Willem
 */
public class Constants {
    public static String    EXIT_IMAGE_PATH = "Main/src/resources/exit.png";
    public static String    SELECT_IMAGE_PATH = "Main/src/resources/select.png";
    public static String    PLAY_IMAGE_PATH = "Main/src/resources/play.png";
    public static String    STORE_IMAGE_PATH = "Main/src/resources/store.png";
    public static String    TUTORIAL_IMAGE_PATH = "Main/src/resources/tutorial.png";
    public static String    AMERICAN_IDIOT_IMAGE_PATH = "Main/src/resources/american_idiot.jpg";
    public static String    CHAMPAGNE_SUPERNOVA_IMAGE_PATH = "Main/src/resources/champagne_supernova.jpg";
    public static String    MR_BRIGHTSIDE_IMAGE_PATH = "Main/src/resources/mr_brightside.jpg";
    public static String    SEPTEMBER_IMAGE_PATH = "Main/src/resources/september.jpg";
    public static String    WONDERWALL_IMAGE_PATH = "Main/src/resources/wonderwall.jpg";
    public static String    DEFAULT_WHITE_IMAGE_PATH = "Main/src/resources/exit.png";
    public static int       BUTTON_WIDTH = 128;
    public static int       BUTTON_HEIGHT = 168;
    public static int       CAROUSEL_MENU_HEIGHT = 192;
    public static String    GUITAR_HERO = "Guitar Hero";

    public static String    ZIP_FILE_PATH = "Main/src/Music";
    public static String    STORE_FILE_PATH = "Main/src/Store";
    public static int       CLIENT_PORT_NUMBER = 8888;

    public static int       FirstGuitar = 25;
    public static int       LastGuitar = 38;
    public static int       scoreMultiplier = 2;
    public static int       streakMultiple = 10;
    public static int       scoreMultiple = 500;
    public static int       maxInGameCurrency = 5;
    public static int       noteValue = 50;

    public static int       w = 750;
    public static int       h = 1000;

    public static double    tanTheta = 240/750;

    public static int GUITAR_POLL_DELAY = 50;
    public static HashMap<Integer, GuitarButton> INDEX_TO_BUTTON = new HashMap<Integer, GuitarButton>() {{
        put(0, GuitarButton.WHITE_1);
        put(1, GuitarButton.BLACK_1);
        put(2, GuitarButton.BLACK_2);
        put(3, GuitarButton.BLACK_3);
        put(4, GuitarButton.WHITE_2);
        put(5, GuitarButton.WHITE_3);
        put(8, GuitarButton.ZERO_POWER);
        put(10, GuitarButton.ESCAPE);
        put(12, GuitarButton.BENDER_BUTTON);
        put(13, GuitarButton.BENDER_JOYSTICK);
        put(15, GuitarButton.STRUM);
        put(17, GuitarButton.WHAMMY);
    }};
    public static HashMap<GuitarButton, Integer> BUTTON_TO_INDEX = new HashMap<GuitarButton, Integer>() {{
        put(GuitarButton.BLACK_1, 0);
        put(GuitarButton.BLACK_1, 1);
        put(GuitarButton.BLACK_2, 2);
        put(GuitarButton.BLACK_3, 3);
        put(GuitarButton.BLACK_2, 5);
        put(GuitarButton.BLACK_3, 6);
        put(GuitarButton.ZERO_POWER, 8);
        put(GuitarButton.ESCAPE, 10);
        put(GuitarButton.BENDER_BUTTON, 12);
        put(GuitarButton.BENDER_JOYSTICK, 13);
        put(GuitarButton.STRUM, 15);
        put(GuitarButton.WHAMMY, 17);
    }};

    //The indices of the binary buttons on the Guitar controller
    public static int[] binaryButtonsIndices = new int[] {0,1,2,3,4,5,8,10,12};
}
