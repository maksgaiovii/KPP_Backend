package software.kosiv.pizzaflow.exception;

public class PausedCookException extends CookStateException {
    
    public PausedCookException(String message) {
        super(message);
    }
}
