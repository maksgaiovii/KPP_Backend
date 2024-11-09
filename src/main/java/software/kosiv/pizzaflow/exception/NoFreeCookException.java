package software.kosiv.pizzaflow.exception;

import java.util.NoSuchElementException;

public class NoFreeCookException extends NoSuchElementException {
    public NoFreeCookException(String s) {
        super(s);
    }
    
    public NoFreeCookException(String s, Throwable cause) {
        super(s, cause);
    }
}
