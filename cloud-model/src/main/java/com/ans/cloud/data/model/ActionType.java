package com.ans.cloud.data.model;

/**
 * Created by anzhen on 2016/5/29.
 */
public enum ActionType {

    CREATE(1,"create"),

    DELETE(2,"delete"),

    MODIFY(3,"update"),

    STOP(4,"stop"),

    RENEW(5,"renew");

    private int id;

    private String code;

    ActionType(int id, String code) {
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


    public static ActionType valueOf(int type){
        switch (type){
            case 1:
                return CREATE;
            case 2:
                return DELETE;
            case 3:
                return MODIFY;
            case 4:
                return STOP;
            case 5:
                return RENEW;
        }

        return null;
    }
}
