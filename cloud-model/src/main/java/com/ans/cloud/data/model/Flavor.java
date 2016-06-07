package com.ans.cloud.data.model;

/**
 * Created by anzhen on 2016/4/5.
 */
public class Flavor extends BaseModel {
    public static final String TYPE_VM_LINUX = "linux";
    public static final String TYPE_VM_WINDOWS = "windows";
    public static final String TYPE_DB_MYSQL = "mysql";

    private long userId;
    private int cpu;
    private int memory;
    private int disk;
    private String type;
    private String description;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
