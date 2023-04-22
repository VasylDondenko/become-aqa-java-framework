package exceptions;

public class AccessTokenNullException extends AccessTokenException {
    public AccessTokenNullException(String message) {
        super(message);
    }
    public AccessTokenNullException(String message, Throwable cause) {
        super(message, cause);
    }
}
