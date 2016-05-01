package registry.util;

/**
 * Created by anzhen on 2016/5/1.
 */
public class RegistryException extends Exception{

    public RegistryException() {
        super();
    }

    public RegistryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistryException(String message) {
        super(message);
    }

    public RegistryException(Throwable cause) {
        super(cause);
    }

    public static class ConnectionLossException extends RegistryException {
        public ConnectionLossException() {
            super("CONNECTIONLOSS");
        }
    }

    public static class OperationTimeoutException extends RegistryException {
        public OperationTimeoutException() {
            super("OPERATIONTIMEOUT");
        }
    }

    public static class SessionExpiredException extends RegistryException {
        public SessionExpiredException() {
            super("SESSIONEXPIRED");
        }
    }

    public static class RegistryOpenFailedException extends RegistryException {
        public RegistryOpenFailedException(String message, Throwable cause) {
            super(message, cause);
        }

        public RegistryOpenFailedException(String message) {
            super(message);
        }
    }

    /**
     * 版本号冲突异常
     */
    public static class BadVersionException extends RegistryException {
        public BadVersionException(String message, Throwable cause) {
            super(message, cause);
        }

        public BadVersionException(String message) {
            super(message);
        }
    }
}
