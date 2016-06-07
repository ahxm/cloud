package com.ans.cloud.data.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 异步任务类型
 */
public class Task extends BaseModel implements Serializable {
    static final long serialVersionUID = 1L;

    /**
     * 新增
     */
    public static final int NEW = 0;
    /**
     * 派发
     */
    public static final int DISPATCHED = 1;
    /**
     * 执行中
     */
    public static final int RUNNING = 2;
    /**
     * 执行成功
     */
    public static final int SUCCESSED = 3;
    /**
     * 失败，不需要重试
     */
    public static final int FAILED_NO_RETRY = 4;
    /**
     * 审核
     */
    public static final int DISABLED = 5;
    /**
     * 失败,需要重试
     */
    public static final int FAILED_RETRY = 6;
    /**
     * 任意的执行器
     */
    public static final int OWNER_ANY = 0;
    /**
     * 原有的执行器者优先
     */
    public static final int OWNER_FIRST = 1;
    /**
     * 必须派发给原有的执行器
     */
    public static final int OWNER_MUST = 2;

    // 类型
    private String type;
    // 派发类型
    private int dispatchType = OWNER_ANY;
    // 优先级
    private int priority;
    // 所有者
    private String owner;
    // 参数
    private String url;
    // cron表达式
    private String cron;
    // 关联ID
    private long referId;
    // 守护任务
    private boolean daemons;
    // 异常是否要重试
    private boolean retry;
    // 重试次数
    private int retryCount;
    // 最大重试次数
    private int maxRetryCount;
    // 调度时间
    private Date retryTime;
    // 异常
    private String exception;

    public Task() {
    }

    public Task(long id) {
        this.id = id;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(int dispatchType) {
        this.dispatchType = dispatchType;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getReferId() {
        return referId;
    }

    public void setReferId(long referId) {
        this.referId = referId;
    }

    public boolean isDaemons() {
        return daemons;
    }

    public void setDaemons(boolean daemons) {
        this.daemons = daemons;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRetry() {
        if (!retry && daemons) {
            // 守护进程要重试
            return true;
        }
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public Date getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(Date retryTime) {
        this.retryTime = retryTime;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    @Override
    public String toString() {
        return "Task{" +
                "type='" + type + '\'' +
                ", dispatchType=" + dispatchType +
                ", priority=" + priority +
                ", owner='" + owner + '\'' +
                ", url='" + url + '\'' +
                ", cron='" + cron + '\'' +
                ", referId=" + referId +
                ", daemons=" + daemons +
                ", retry=" + retry +
                ", retryCount=" + retryCount +
                ", maxRetryCount=" + maxRetryCount +
                ", retryTime=" + retryTime +
                //", exception='" + exception + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Task task = (Task) o;

        if (id != task.id) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }


    public static class Builder extends BaseModel.Builder<Task, Builder> {

        public Builder() {
            this(new Task());
        }

        public Builder(Task task) {
            super(task);
        }

        public Builder cron(String cron) {
            model.setCron(cron);
            return this;
        }

        public Builder type(String type) {
            model.setType(type);
            return this;
        }

        public Builder dispatchType(int dispatchType) {
            model.setDispatchType(dispatchType);
            return this;
        }

        public Builder priority(int priority) {
            model.setPriority(priority);
            return this;
        }

        public Builder referId(long referId) {
            model.setReferId(referId);
            return this;
        }

        public Builder daemons(boolean daemons) {
            model.setDaemons(daemons);
            return this;
        }

        public Builder owner(String owner) {
            model.setOwner(owner);
            return this;
        }

        public Builder url(String url) {
            model.setUrl(url);
            return this;
        }

        public Builder retry(boolean retry) {
            model.setRetry(retry);
            return this;
        }

        public Builder retryTime(Date retryTime) {
            model.setRetryTime(retryTime);
            return this;
        }

        public Builder exception(String exception) {
            model.setException(exception);
            return this;
        }

        public Builder maxRetryCount(int maxRetryCount) {
            model.setMaxRetryCount(maxRetryCount);
            return this;
        }

        public Builder retryCount(int retryCount) {
            model.setRetryCount(retryCount);
            return this;
        }

        public static Builder build() {
            return new Builder();
        }

        public static Builder build(Task task) {
            return new Builder(task);
        }

    }


}


