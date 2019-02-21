public class GuitarButtonPressedEvent extends java.util.EventObject {

    private GuitarButton guitarButton;
    private float value;

    public GuitarButtonPressedEvent(Object obj, GuitarButton btn) {
        super(obj);
        this.guitarButton = btn;
    }

    public GuitarButtonPressedEvent(Object obj, GuitarButton btn, float value) {
        super(obj);
        this.guitarButton = btn;
        this.value = value;
    }

    public GuitarButton getGuitarButton() {
        return guitarButton;
    }
}