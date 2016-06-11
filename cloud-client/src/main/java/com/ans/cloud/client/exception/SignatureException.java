package com.ans.cloud.client.exception;

/**
 * Created by anzhen on 2016/6/11.
 */
public class SignatureException extends ClientException {

    public SignatureException() {
    }

    public SignatureException(boolean isRetry) {
        super(isRetry);
    }

    public SignatureException(String message, boolean isRetry) {
        super(message, isRetry);
    }

    public SignatureException(String message, Throwable cause, boolean isRetry) {
        super(message, cause, isRetry);
    }

    public SignatureException(Throwable cause, boolean isRetry) {
        super(cause, isRetry);
    }

    public SignatureException(String message) {
        super(message);
    }

    public SignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignatureException(Throwable cause) {
        super(cause);
    }
}
