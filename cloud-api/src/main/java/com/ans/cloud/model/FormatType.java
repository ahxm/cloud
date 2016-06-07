package com.ans.cloud.model;

/**
 * 格式类型
 * Created by anzhen on 16-1-7.
 */
public enum FormatType {
    XML("xml", "application/xml"),
    JSON("json", "application/json");

    // 格式
    private String format;
    // 类型
    private String mediaType;

    FormatType(String format, String mediaType) {
        this.format = format;
        this.mediaType = mediaType;
    }

    public String getFormat() {
        return format;
    }

    public String getMediaType() {
        return mediaType;
    }

}
