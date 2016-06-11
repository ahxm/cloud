package com.ans.cloud.client.exception;

/**
 * Created by anzhen on 2016/6/11.
 */
public class ServerException  extends Exception{

    private String requestId;

    private String errCode;

    private String errMsg;

    public String getRequestId() {
        return requestId;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public ServerException(String requestId, String errCode, String errMsg) {
        super((errMsg == null || errMsg.isEmpty()) ? errCode : errCode + " -> " + errMsg);
        this.requestId = requestId;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
