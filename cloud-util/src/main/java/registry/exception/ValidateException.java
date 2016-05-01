package registry.exception;

/**
 * Created by anzhen on 2016/5/1.
 */
public class ValidateException extends RuntimeException {

    public ValidateException() {
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }
}
