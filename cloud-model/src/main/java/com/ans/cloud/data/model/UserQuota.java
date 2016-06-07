package com.ans.cloud.data.model;

/**
 * @author anzhen
 * @since 2016-02-01 15:39
 */
public class UserQuota extends BaseModel implements OptimisticLock {
    // 资源类型
    private int type;
    // 资源配额
    private int quota;
    // 已使用量
    private int used;
    // 版本
    private long version;
    // 用户ID
    private long userId;
    // 数据中心 （机房）
    private String dataCenter;

    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "UserQuota{" +
                "type=" + type +
                ", quota=" + quota +
                ", used=" + used +
                ", version=" + version +
                ", userId=" + userId +
                ", dataCenter='" + dataCenter + '\'' +
                '}';
    }
}
