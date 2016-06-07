package com.ans.cloud.data.model;

/**
 * Created by anzhen on 2016/1/24.
 * 服务代码
 */
public enum Resource {
    /**
     * 云主机
     */
    SERVER(0, Constants.CODE_SERVER, Constants.CODE_SERVER_NAME, "vm", "compute", "ecs", 20, 1),
    /**
     * 私有镜像
     */
    IMAGE(1, Constants.CODE_IMAGE, Constants.CODE_IMAGE_NAME, null, "compute", "ecs", 5, 2),
    /**
     * 云硬盘
     */
    VOLUME(2, Constants.CODE_VOLUME, Constants.CODE_VOLUME_NAME, "disk", "storage", "ecs", 15, 7),
    /**
     * 子网
     */
    SUBNET(10, Constants.CODE_SUBNET, Constants.CODE_SUBNET_NAME, null, "network", "ecs", 10, 3),
    /**
     * 路由器
     */
    ROUTER(11, Constants.CODE_ROUTER, Constants.CODE_ROUTER_NAME, null, "network", "ecs", 10, 4),
    /**
     * 公网IP
     */
    FLOATINGIP(12, Constants.CODE_FLOATING_IP, Constants.CODE_FLOATING_IP_NAME, "ip", "network", "ecs", 10, 5),
    /**
     * 防火墙
     */
    FIREWALL(13, Constants.CODE_FIREWALL, Constants.CODE_FIREWALL_NAME, null, "network", "ecs", 10, 11),
    /**
     * 负载均衡
     */
    LOADBALANCE(14, Constants.CODE_LOADBALANCE, Constants.CODE_LOADBALANCE_NAME, "balance", "network", "ecs", 5, 6),
    /**
     * vpn
     */
    VPN(15, Constants.CODE_VPN, Constants.CODE_VPN_NAME, "vpn", "network", "ecs", 0, 13),
    /**
     * 数据库
     */
    DATABASE(20, Constants.CODE_DATABASE, Constants.CODE_DATABASE_NAME, "database", "database", "rds", 5, 9),
    /**
     * 监控报警
     */
    ALARM(30, Constants.CODE_ALARM, Constants.CODE_ALARM_NAME, null, "monitor", "ecs", 20, 12),
    /**
     * 通知
     */
    NOTIFICATION(31, Constants.CODE_NOTIFICATION, Constants.CODE_NOTIFICATION_NAME, null, "monitor", "ecs", 0, 100),
    /**
     * 云盘备份
     */
    SNAPSHOT(32, Constants.CODE_SNAPSHOT, Constants.CODE_SNAPSHOT_NAME, null, "storage", "ecs", 15, 8),
    /**
     * 数据库备份
     */
    DB_SNAPSHOT(33, Constants.CODE_DB_SNAPSHOT, Constants.CODE_DB_SNAPSHOT_NAME, null, "database", "rds", 15, 10),
    /**
     * 其他类型
     */
    OTHER(100, Constants.CODE_OTHER, Constants.CODE_OTHER_NAME, null, null, null, 0, 1000);

    // ID
    private int id;
    // 代码
    private String code;
    // 名称
    private String name;
    // 计费项目
    private String fee;
    // 所属模块(url的path)
    private String module;
    // 所属集群
    private String cluster;
    // 默认阀值
    private int quota;
    // 显示顺序
    private int index;

    Resource(int id, String code, String name, String fee, String module, String cluster, int quota, int index) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.fee = fee;
        this.module = module;
        this.cluster = cluster;
        this.quota = quota;
        this.index = index;
    }

    /**
     * 根据ID查找资源
     *
     * @param id
     * @return 资源
     */
    public static Resource of(final int id) {
        Resource[] resources = Resource.values();
        for (Resource resource : resources) {
            if (resource.getId() == id) {
                return resource;
            }
        }

        return null;
    }

    /**
     * 根据代码或计费代码查找资源
     *
     * @param code 代码或计费代码
     * @return 资源
     */
    public static Resource of(final String code) {
        Resource[] resources = Resource.values();
        for (Resource resource : resources) {
            if (resource.getCode().equalsIgnoreCase(code)) {
                return resource;
            } else if (resource.getFee() != null && resource.getFee().equalsIgnoreCase(code)) {
                return resource;
            }
        }
        return null;
    }

    /**
     * 中文支持
     *
     * @param name 中文名
     * @return 翻译成code，不匹配直接返回原值
     */
    public static String translate(String name) {
        for (Resource resource : Resource.values()) {
            if (resource.getName().equalsIgnoreCase(name)) {
                return resource.getCode();
            }
        }
        return name;
    }

    /**
     * 目前在系统正在使用的有配额的资源类型的数量
     *
     * @return 有配额的资源类型的数量
     */
    public static final int getQuotaResourceCount() {
        int count = 0;
        for (Resource resource : Resource.values()) {
            if (resource.getQuota() > 0) {
                count++;
            }
        }
        return count;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getFee() {
        return fee;
    }

    public String getModule() {
        return module;
    }

    public String getCluster() {
        return cluster;
    }

    public int getQuota() {
        return quota;
    }

    public static class Constants {
        public static final String CODE_SERVER = "server";
        public static final String CODE_IMAGE = "image";
        public static final String CODE_VOLUME = "volume";
        public static final String CODE_SUBNET = "subnet";
        public static final String CODE_ROUTER = "router";
        public static final String CODE_FLOATING_IP = "floatingIp";
        public static final String CODE_FIREWALL = "firewall";
        public static final String CODE_LOADBALANCE = "loadbalance";
        public static final String CODE_VPN = "vpn";
        public static final String CODE_DATABASE = "database";
        public static final String CODE_ALARM = "alarm";
        public static final String CODE_NOTIFICATION = "notification";
        public static final String CODE_SNAPSHOT = "snapshot";
        public static final String CODE_DB_SNAPSHOT = "dbSnapshot";
        public static final String CODE_OTHER = "other";

        public static final String CODE_SERVER_NAME = "云主机";
        public static final String CODE_IMAGE_NAME = "私有镜像";
        public static final String CODE_VOLUME_NAME = "云硬盘";
        public static final String CODE_SUBNET_NAME = "子网";
        public static final String CODE_ROUTER_NAME = "路由器";
        public static final String CODE_FLOATING_IP_NAME = "公网IP";
        public static final String CODE_FIREWALL_NAME = "防火墙";
        public static final String CODE_LOADBALANCE_NAME = "负载均衡";
        public static final String CODE_VPN_NAME = "VPN";
        public static final String CODE_DATABASE_NAME = "云数据库";
        public static final String CODE_ALARM_NAME = "监控报警";
        public static final String CODE_NOTIFICATION_NAME = "通知列表";
        public static final String CODE_SNAPSHOT_NAME = "云硬盘备份";
        public static final String CODE_DB_SNAPSHOT_NAME = "云数据库备份";
        public static final String CODE_OTHER_NAME = "其他";
    }
}
