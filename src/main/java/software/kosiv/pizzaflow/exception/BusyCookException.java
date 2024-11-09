package software.kosiv.pizzaflow.exception;

public class BusyCookException extends CookStateException {
    public BusyCookException(String message) {
        super(message);
    }
}
