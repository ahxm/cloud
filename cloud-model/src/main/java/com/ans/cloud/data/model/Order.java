package com.ans.cloud.data.model;

/**
 * @author anzhen
 * @since 2016-02-01 15:33
 */
public class Order extends BaseModel {
    // 初始化
    public static final int INIT = 1;
    // 开始预扣费
    public static final int START_PRE_FEE = 2;
    // 开始生产
    public static final int START_CREATE = 3;
    // 开始检查
    public static final int START_CHECK = 4;
    // 开始扣费
    public static final int START_FEE = 5;
    // 成功
    public static final int SUCCESS = 6;
    // 失败
    public static final int FAIL = 7;
    // 生成失败，回滚
    public static final int ROLLBACK = 8;

    // 服务端创建唯一请求ID
    private String requestId;
    // 订单ID
    private String orderId;
    // 主账号ID
    private long userId;
    // 数据中心
    private String dataCenter;
    // 资源操作类型
    private int type;
    // 原有的计费类型
    private int feeType;
    // 资源ID
    private String resourceId;
    // 上下文
    private String context;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getFeeType() {
        return feeType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }
}
