package com.ans.cloud.data.model;

/**
 * 权限类型，包括读权限和写权限等
 * Created by anzhen on 2016/6/7.
 */
public enum PermissionType {

    READ(1,"R"),


    WRITE(2,"W");

    private int id;

    private String code;

    public static PermissionType of(String code){
        if(READ.getCode().equalsIgnoreCase(code)){
            return READ;
        }else if(WRITE.getCode().equalsIgnoreCase(code)){
            return WRITE;
        }

        return null;
    }

    /**
     * 检查必要的权限和实际的权限是否匹配
     *
     * @param required 必须满足的权限要求
     * @param input    实际被赋予的权限
     * @return true表示匹配，false表示不满足权限的配置要求
     */
    public static boolean check(PermissionType required, PermissionType input) {
        return !(required == WRITE && input == READ);
    }

    /**
     * 从中文翻译成code
     *
     * @param permission
     * @return
     */
    public static String translate(String permission) {
        if ("写".equals(permission)) {
            return WRITE.getCode();
        } else if ("读".equals(permission)) {
            return READ.getCode();
        }
        return permission;
    }

    PermissionType(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
