package com.ans.cloud.data.exception;

/**
 * 乐观锁异常
 * Created by anzhen on 15-7-15.
 */
public class OptimisticLockException extends RepositoryException {

    public OptimisticLockException() {
    }

    public OptimisticLockException(String message) {
        super(message);
    }

    public OptimisticLockException(int status, String message) {
        super(status, message);
    }

    public OptimisticLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public OptimisticLockException(int status, String message, Throwable cause) {
        super(status, message, cause);
    }
}
