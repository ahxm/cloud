package com.ans.cloud.data.exception;

/**
 * 唯一约束异常
 * Created by anzhen on 15-7-15.
 */
public class DuplicateKeyException extends RepositoryException {

    public DuplicateKeyException() {
    }

    public DuplicateKeyException(String message) {
        super(message);
    }

    public DuplicateKeyException(int status, String message) {
        super(status, message);
    }

    public DuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateKeyException(int status, String message, Throwable cause) {
        super(status, message, cause);
    }
}
