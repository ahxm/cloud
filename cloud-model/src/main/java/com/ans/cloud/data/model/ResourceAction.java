package com.ans.cloud.data.model;

import static com.ans.cloud.data.model.Resource.*;
import static com.ans.cloud.data.model.ActionType.*;


/**
 * @author anzhen
 * @since 2016-02-01 17:33
 */
public enum ResourceAction {
    // 云主机
    CREATE_SERVER(SERVER.getId(), SERVER.getId() * 10 + CREATE.getId(), "CreateServer", Order.START_PRE_FEE),
    DELETE_SERVER(SERVER.getId(), SERVER.getId() * 10 + DELETE.getId(), "DeleteServer", Order.START_CREATE),
    RENEW_SERVER(SERVER.getId(), SERVER.getId() * 10 + RENEW.getId(), "RenewServer", Order.START_PRE_FEE),

    // DB
    CREATE_DATABASE(DATABASE.getId(), DATABASE.getId() * 10 + CREATE.getId(), "CreateDatabase", Order.START_PRE_FEE),
    DELETE_DATABASE(DATABASE.getId(), DATABASE.getId() * 10 + DELETE.getId(), "DeleteDatabase", Order.START_CREATE),
    RENEW_DATABASE(DATABASE.getId(), DATABASE.getId() * 10 + RENEW.getId(), "RenewDatabase", Order.START_PRE_FEE),
    //恢复一个备份到新的数据库集群
    RESTORE_DATABASE_TO_NEW(DATABASE.getId(), DATABASE.getId() * 10 + CREATE.getId(), "RestoreDatabaseToNew",
            Order.START_PRE_FEE),
    //按时间点恢复到新的数据库集群
    RESTORE_DATABASE_TIME_TO_NEW(DATABASE.getId(), DATABASE.getId() * 10 + CREATE.getId(), "RestoreDatabaseTimeToNew",
            Order.START_PRE_FEE),

    // 公网IP
    CREATE_FLOATING_IP(FLOATINGIP.getId(), FLOATINGIP.getId() * 10 + CREATE.getId(), "CreateFloatingIp",
            Order.START_PRE_FEE),
    DELETE_FLOATING_IP(FLOATINGIP.getId(), FLOATINGIP.getId() * 10 + DELETE.getId(), "DeleteFloatingIp",
            Order.START_CREATE),
    RENEW_FLOATING_IP(FLOATINGIP.getId(), FLOATINGIP.getId() * 10 + RENEW.getId(), "RenewFloatingIp",
            Order.START_PRE_FEE),

    // 负载均衡
    CREATE_LOADBALANCE(LOADBALANCE.getId(), LOADBALANCE.getId() * 10 + CREATE.getId(), "CreateLoadbalance",
            Order.START_PRE_FEE),
    DELETE_LOADBALANCE(LOADBALANCE.getId(), LOADBALANCE.getId() * 10 + DELETE.getId(), "DeleteLoadbalance",
            Order.START_CREATE),
    RENEW_LOADBALANCE(LOADBALANCE.getId(), LOADBALANCE.getId() * 10 + RENEW.getId(), "RenewLoadbalance",
            Order.START_PRE_FEE),

    // 路由器
    CREATE_VPN(VPN.getId(), VPN.getId() * 10 + CREATE.getId(), "CreateVpn", Order.START_PRE_FEE),
    DELETE_VPN(VPN.getId(), VPN.getId() * 10 + DELETE.getId(), "DeleteVpn", Order.START_CREATE),
    RENEW_VPN(VPN.getId(), VPN.getId() * 10 + RENEW.getId(), "RenewVpn", Order.START_PRE_FEE),

    // 云硬盘
    CREATE_VOLUME(VOLUME.getId(), VOLUME.getId() * 10 + CREATE.getId(), "CreateVolume", Order.START_PRE_FEE),
    DELETE_VOLUME(VOLUME.getId(), VOLUME.getId() * 10 + DELETE.getId(), "DeleteVolume", Order.START_CREATE),
    RENEW_VOLUME(VOLUME.getId(), VOLUME.getId() * 10 + RENEW.getId(), "RenewVolume", Order.START_PRE_FEE),

    // 欠费销毁
    ARREARAGE(OTHER.getId(), OTHER.getId() * 10 + 1, "arrearage", Order.INIT),
    // 停止虚机
    STOP_SERVER(SERVER.getId(), SERVER.getId() * 10 + STOP.getId(), "stop.server", Order.INIT),
    // 停止lb
    STOP_LOADBALANCE(LOADBALANCE.getId(), LOADBALANCE.getId() * 10 + STOP.getId(), "stop.loadbalance", Order.INIT);

    // 资源类型
    private int type;
    // 资源操作类型
    private int action;
    // 对应任务
    private String task;
    // 订单初始化状态
    private int status;

    ResourceAction(int type, int action, String task, int status) {
        this.type = type;
        this.action = action;
        this.task = task;
        this.status = status;
    }

    public static ResourceAction of(int actionType) {
        for (ResourceAction action : values()) {
            if (action.getAction() == actionType) {
                return action;
            }
        }
        return null;
    }

    public static ResourceAction of(Resource resource, ActionType actionType) {
        if (resource == null || actionType == null) {
            return null;
        }
        return of(resource.getId() * 10 + actionType.getId());
    }

    public int getType() {
        return type;
    }

    public int getAction() {
        return action;
    }

    public String getTask() {
        return task;
    }

    public int getStatus() {
        return status;
    }

    public ActionType getActionType() {
        return ActionType.valueOf(action % 10);
    }

}
