package com.ans.cloud.client.exception;

/**
 * Created by anzhen on 2016/6/8.
 */
public class ClientException extends Exception {

    private boolean isRetry;

    public ClientException(){}

    public ClientException(String message,boolean isRetry){
        super(message);
        this.isRetry = isRetry;
    }

    public ClientException(String message,Throwable cause,boolean isRetry){
        super(message,cause);
        this.isRetry = isRetry;
    }

    public ClientException(Throwable cause,boolean isRetry){
        super(cause);
        this.isRetry = isRetry;
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }


    public boolean isRetry() {
        return isRetry;
    }

    public void setRetry(boolean isRetry) {
        this.isRetry = isRetry;
    }
}
