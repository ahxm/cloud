package com.ans.cloud.data.model;

import java.util.List;

/**
 * Created by anzhen on 2016/2/19.
 */
public class PermissionContent {
    private String version;
    private List<PermissionContentDetail> content;

    public List<PermissionContentDetail> getContent() {
        return content;
    }

    public void setContent(List<PermissionContentDetail> content) {
        this.content = content;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static class PermissionContentDetail{
        private String permission;
        private List<String> resource;

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public List<String> getResource() {
            return resource;
        }

        public void setResource(List<String> resource) {
            this.resource = resource;
        }
    }
}
