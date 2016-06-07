package com.ans.cloud.model;

/**
 * 计费类型
 * Created by anzhen on 16-4-12.
 */
public enum FeeType {
    /**
     * 规格配制型
     */
    SPECIFICATION,
    /**
     * 包年包月
     */
    PERIOD,
    /**
     * 容量型
     */
    CAPACITY;

    public static FeeType valueOf(int feeType) {
        if (feeType == 1) {
            return SPECIFICATION;
        }
        if (feeType == 2 || feeType == 3 || feeType == 4 || feeType == 6) {
            return PERIOD;
        }
        if (feeType == 5) {
            return CAPACITY;
        }
        if (feeType > 600) {
            return PERIOD;
        }
        return null;
    }
}
