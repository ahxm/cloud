package com.ans.cloud.model;

import com.ans.cloud.data.model.Query;

import java.util.Date;

/**
 * 任务查询
 * Created by anzhen on 15-2-27.
 */
public class QTask implements Query {
    //关联主键
    private long referId;
    //任务类型
    private String type;
    //执行者
    private String owner;
    //执行开始时间
    private Date beginTime;
    //执行结束时间
    private Date endTime;
    //状态
    private int status = -1;

    public long getReferId() {
        return referId;
    }

    public void setReferId(long referId) {
        this.referId = referId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
