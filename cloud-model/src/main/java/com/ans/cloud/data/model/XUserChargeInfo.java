package com.ans.cloud.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by anzhen on 2016/1/4.
 */
public class XUserChargeInfo implements Serializable {
    public enum Status {
        //正常
        NORMAL,
        // 欠费
        ARREARAGE,
        // 欠费停机
        ARREARAGE_STOP,
        // 欠费销毁
        ARREARAGE_DESTROY;
    }
    // 用户pin
    private String pin;
    // 状态
    private Status status;

    private List<Resource> resources;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public static class Resource implements Serializable{
        // 资源id
        private String resourceId;
        // 服务code
        private String serverCode;

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        public String getServerCode() {
            return serverCode;
        }

        public void setServerCode(String serverCode) {
            this.serverCode = serverCode;
        }
    }

}
