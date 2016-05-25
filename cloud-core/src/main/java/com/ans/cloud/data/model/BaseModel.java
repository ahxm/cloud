package com.ans.cloud.data.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Model的基类.
 *
 * @author lindeqiang
 * @since 2015/1/5 15:52
 */
public abstract class BaseModel implements Serializable, Cloneable {

    public static final int ENABLED = 1;

    public static final int DISABLED = 0;

    public static final int DELETED = -1;

    /**
     * 主键
     */
    protected long id;
    /**
     * 创建时间
     */
    protected Date createTime;
    /**
     * 创建人
     */
    protected long createBy;
    /**
     * 创建人代码
     */
    protected String createUser;

    /**
     * 修改时间
     */
    protected Date updateTime;
    /**
     * 修改人
     */
    protected long updateBy;
    /**
     * 修改人代码
     */
    protected String updateUser;
    /**
     * 状态(默认启用)
     */
    protected int status = ENABLED;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(long createBy) {
        this.createBy = createBy;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(long updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseModel baseModel = (BaseModel) o;

        return id == baseModel.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", createBy=" + createBy +
                ", createUser='" + createUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateBy=" + updateBy +
                ", updateUser='" + updateUser + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return null;
    }

    /**
     * 构建器
     *
     * @param <M>
     * @param <T>
     */
    public static abstract class Builder<M extends BaseModel, T> {
        protected M model;

        public Builder() {
        }

        public Builder(M model) {
            this.model = model;
        }

        public T id(long id) {
            model.setId(id);
            return (T) this;
        }

        public T createTime(Date createTime) {
            model.setCreateTime(createTime);
            return (T) this;
        }

        public T createBy(long createBy) {
            model.setCreateBy(createBy);
            return (T) this;
        }

        public T createUser(String createUser) {
            model.setCreateUser(createUser);
            return (T) this;
        }

        public T updateTime(Date updateTime) {
            model.setUpdateTime(updateTime);
            return (T) this;
        }

        public T updateBy(long updateBy) {
            model.setUpdateBy(updateBy);
            return (T) this;
        }

        public T updateUser(String updateUser) {
            model.setUpdateUser(updateUser);
            return (T) this;
        }

        public T status(int status) {
            model.setStatus(status);
            return (T) this;
        }

        /**
         * 构造对象
         *
         * @return 对象
         */
        public M create() {
            return model;
        }

    }
}