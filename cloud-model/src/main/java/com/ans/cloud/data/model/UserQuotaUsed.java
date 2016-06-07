package com.ans.cloud.data.model;

import java.io.Serializable;

/**
 * Created by wanglitao on 2016/5/17.
 */
public class UserQuotaUsed implements Serializable {
    private long userId;

    private int serverUsed;

    private int imageUsed;

    private int volumeUsed;

    private int subnetUsed;

    private int routerUsed;

    private int floatingIpUsed;

    private int firewallUsed;

    private int loadbalanceUsed;

    private int databaseUsed;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getServerUsed() {
        return serverUsed;
    }

    public void setServerUsed(int serverUsed) {
        this.serverUsed = serverUsed;
    }

    public int getImageUsed() {
        return imageUsed;
    }

    public void setImageUsed(int imageUsed) {
        this.imageUsed = imageUsed;
    }

    public int getVolumeUsed() {
        return volumeUsed;
    }

    public void setVolumeUsed(int volumeUsed) {
        this.volumeUsed = volumeUsed;
    }

    public int getSubnetUsed() {
        return subnetUsed;
    }

    public void setSubnetUsed(int subnetUsed) {
        this.subnetUsed = subnetUsed;
    }

    public int getRouterUsed() {
        return routerUsed;
    }

    public void setRouterUsed(int routerUsed) {
        this.routerUsed = routerUsed;
    }

    public int getFloatingIpUsed() {
        return floatingIpUsed;
    }

    public void setFloatingIpUsed(int floatingIpUsed) {
        this.floatingIpUsed = floatingIpUsed;
    }

    public int getFirewallUsed() {
        return firewallUsed;
    }

    public void setFirewallUsed(int firewallUsed) {
        this.firewallUsed = firewallUsed;
    }

    public int getLoadbalanceUsed() {
        return loadbalanceUsed;
    }

    public void setLoadbalanceUsed(int loadbalanceUsed) {
        this.loadbalanceUsed = loadbalanceUsed;
    }

    public int getDatabaseUsed() {
        return databaseUsed;
    }

    public void setDatabaseUsed(int databaseUsed) {
        this.databaseUsed = databaseUsed;
    }

    @Override
    public String toString() {
        return "UserQuotaUsed{" +
                "userId=" + userId +
                ", serverUsed=" + serverUsed +
                ", imageUsed=" + imageUsed +
                ", volumeUsed=" + volumeUsed +
                ", subnetUsed=" + subnetUsed +
                ", routerUsed=" + routerUsed +
                ", floatingIpUsed=" + floatingIpUsed +
                ", firewallUsed=" + firewallUsed +
                ", loadbalanceUsed=" + loadbalanceUsed +
                ", databaseUsed=" + databaseUsed +
                '}';
    }
}
