package com.ans.cloud.model.v1;

import com.ans.cloud.model.Counter;
import com.ans.cloud.model.FeeRequest;

/**
 * Created by anzhen on 2016/6/8.
 */
public class CreateServerRequest extends FeeRequest implements Counter {

    /**
     * 云主机名称
     */
    private String serverName;
    /**
     * 镜像UUID
     */
    private String imageId;
    /**
     * 子网UUID
     */
    private String subnetId;
    /**
     * 云主机描述
     */
    private String description;
    /**
     * 用户名
     */
    private String userPassword;
    /**
     * cpu
     */
    private int cpu;
    /**
     * 内存 单位M
     */
    private int memory;
    /**
     * 磁盘
     */
    private int disk;
    /**
     * 批量
     */
    private int count;

    /**
     * 混淆器
     */
    @Override
    public void confuse() {
        super.confuse();
        this.userPassword = CONFUSE_CHARACTERS;

    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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
        if (memory > 0 && memory <= 256) {
            // 传入的是G
            this.memory = memory * 1024;
        } else {
            this.memory = memory;
        }
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
