package com.ans.cloud.model;

import com.ans.cloud.data.model.Cluster;
import com.ans.cloud.data.model.ClusterUser;
import com.ans.cloud.data.model.User;

import java.io.Serializable;

/**
 * @author anzhen
 * @since 2015-12-29 15:12
 */
public class RequestContext implements Serializable {
    private transient static ThreadLocal<RequestContext> local = new ThreadLocal<RequestContext>();
    // 请求的服务
    private String path;
    // 客户端地址
    private String clientAddress;
    // 服务端地址
    private String serverAddress;
    // 主账号
    private User account;
    // 用户
    private User user;
    // 用于签名的字符串
    private String signSource;
    // 集群
    private Cluster cluster;
    // 集群用户信息
    private ClusterUser clusterUser;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public User getAccount() {
        return account;
    }

    public void setAccount(User account) {
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSignSource() {
        return signSource;
    }

    public void setSignSource(String signSource) {
        this.signSource = signSource;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public ClusterUser getClusterUser() {
        return clusterUser;
    }

    public void setClusterUser(ClusterUser clusterUser) {
        this.clusterUser = clusterUser;
    }

    /**
     * 获取请求上下文
     *
     * @return 请求上下文
     */
    public static RequestContext get() {
        return local.get();
    }

    /**
     * 获取数据中心
     *
     * @return
     */
    public static String getDataCenter() {
        return get().getCluster().getDataCenter();
    }

    /**
     * 获取集群类型
     *
     * @return
     */
    public static String getClusterType() {
        return get().getCluster().getType();
    }

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    public static String getTenantId() {
        return get().getClusterUser().getTenantId();
    }

    /**
     * 获取用户PIN
     *
     * @return 用户PIN
     */
    public static String getPin() {
        return get().getClusterUser().getPin();
    }

    /**
     * 获取集群用户ID
     *
     * @return 用户ID
     */
    public static String getUserId() {
        return get().getClusterUser().getUserId();
    }

    /**
     * 获取集群ID
     *
     * @return 集群ID
     */
    public static long getClusterId() {
        return get().getClusterUser().getClusterId();
    }

    /**
     * 设置上下文
     *
     * @param context 上下文
     */
    public static void binding(RequestContext context) {
        local.set(context);
    }


}
