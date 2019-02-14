public class GuitarButtonPressedEvent extends java.util.EventObject {

    private GuitarButton guitarButton;

    public GuitarButtonPressedEvent(Object obj, GuitarButton btn) {
        super(obj);
        this.guitarButton = btn;
    }

    public GuitarButton getGuitarButton() {
        return guitarButton;
    }
}