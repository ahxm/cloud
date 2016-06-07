package com.ans.cloud.model;

/**
 * 费用请求,用于记录包年包月等信息
 * Created by anzhen on 16-1-18.
 */
public abstract class FeeRequest extends Request {
    // 计费类型
    protected int feeType;

    public int getFeeType() {
        return feeType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }
}
