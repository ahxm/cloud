package com.ans.cloud.model;

/**
 * Created by anzhen on 2016/4/25.
 */
public class InnerCreateResponse extends Response {
    private String resourceId;

    public InnerCreateResponse() {
    }

    public InnerCreateResponse(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
