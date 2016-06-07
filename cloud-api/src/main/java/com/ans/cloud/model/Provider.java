package com.ans.cloud.model;

/**
 * Created by anzhen on 16-4-13.
 */
public enum Provider {
    SINGLE(1, "非BGP"),
    BGP(2, "BGP");

    // 值
    private int value;
    // 名称
    private String name;

    Provider(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static Provider of(int value) {
        switch (value) {
            case 0:
                return null;
            case 1:
                return SINGLE;
            case 2:
                return BGP;
            default:
                return SINGLE;
        }
    }

    /**
     * 名称转换
     *
     * @param name 名称
     * @return 运营商
     */
    public static Provider of(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        } else if ("单线".equalsIgnoreCase(name) || "非BGP".equalsIgnoreCase(name)) {
            return SINGLE;
        } else if ("BGP".equalsIgnoreCase(name)) {
            return BGP;
        } else {
            return SINGLE;
        }
    }

    /**
     * 是否存在
     *
     * @param name 名称
     * @return
     */
    public static Provider exist(String name) {
        if ("单线".equalsIgnoreCase(name) || "非BGP".equalsIgnoreCase(name)) {
            return SINGLE;
        } else if ("BGP".equalsIgnoreCase(name)) {
            return BGP;
        } else {
            return null;
        }
    }
}
