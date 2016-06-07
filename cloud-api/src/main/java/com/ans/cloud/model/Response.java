package com.ans.cloud.model;

import java.io.Serializable;

/**
 * @author anzhen
 * @since 2015-12-29 15:11
 */
public class Response implements Serializable {
    protected String requestId;

    public Response() {
    }

    public Response(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public static class SimpleInfo {
        private long id;
        private String name;

        public SimpleInfo() {
        }

        public SimpleInfo(long id, String name) {
            this.setId(id);
            this.setName(name);
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
