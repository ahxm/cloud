package com.ans.cloud.data.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by anzhen on 2016/5/29.
 */
public class DataCenter extends BaseModel{

    private static Pattern P_RANGE1 = Pattern.compile("^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$");
    private static Pattern P_RANGE2 = Pattern.compile("^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\-([0-9]{1,3})$");
    private static Pattern P_RANGE3 = Pattern.compile(
            "^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\-([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})"
                    + "\\.([0-9]{1,3})$");
    private static Pattern P_RANGE4 = Pattern.compile("^([0-9]{1,3})\\.([0-9]{1,3})\\.\\*$");

    // 代码
    private String code;
    // 名称
    private String name;
    // IP段
    private String ips;
    //
    private String region;
    // 数据中心URL
    private String url;
    //
    @Deprecated
    private String mongoIp;
    // 自动部署数据中心编码
    private String deployCode;

    // 数据中心关联的部署环境
    private List<Long> envIds;

    public DataCenter() {
    }

    public DataCenter(long id) {
        this.id = id;
    }

    public DataCenter(String code, String name, String ips) {
        this.code = code;
        this.name = name;
        this.ips = ips;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMongoIp() {
        return mongoIp;
    }

    public void setMongoIp(String mongoIp) {
        this.mongoIp = mongoIp;
    }

    public String getDeployCode() {
        return deployCode;
    }

    public void setDeployCode(String deployCode) {
        this.deployCode = deployCode;
    }

    public List<Long> getEnvIds() {
        return envIds;
    }

    public void setEnvIds(List<Long> envIds) {
        this.envIds = envIds;
    }

    /**
     * IP匹配
     *
     * @param clientIp 客户端IP
     * @return 是否匹配
     */
    public boolean match(final String clientIp) {
        if (clientIp == null) {
            return false;
        }
        if (ips == null || ips.isEmpty()) {
            return false;
        }
        String[] parts = clientIp.split("[\\.]");
        String[] ips = this.ips.split("[;,]");
        Matcher matcher;
        long beginIp;
        long endIp;
        for (String ip : ips) {
            ip = ip.trim();
            if (ip.isEmpty()) {
                continue;
            }
            matcher = P_RANGE3.matcher(ip);
            if (matcher.find()) {
                beginIp = toLong(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
                endIp = toLong(matcher.group(5), matcher.group(6), matcher.group(7), matcher.group(8));
            } else {
                matcher = P_RANGE1.matcher(ip);
                if (matcher.find()) {
                    beginIp = toLong(matcher.group(1), matcher.group(2), matcher.group(3), "0");
                    endIp = toLong(matcher.group(1), matcher.group(2), matcher.group(3), "255");
                } else {
                    matcher = P_RANGE2.matcher(ip);
                    if (matcher.find()) {
                        beginIp = toLong(matcher.group(1), matcher.group(2), matcher.group(3), "0");
                        endIp = toLong(matcher.group(1), matcher.group(2), matcher.group(4), "255");
                    } else {
                        matcher = P_RANGE4.matcher(ip);
                        if (matcher.find()) {
                            beginIp = toLong(matcher.group(1), matcher.group(2), "0", "0");
                            endIp = toLong(matcher.group(1), matcher.group(2), "255", "255");
                        } else {
                            continue;
                        }
                    }
                }
            }
            long value = toLong(parts[0], parts[1], parts[2], parts[3]);
            if (value >= beginIp && value <= endIp) {
                return true;
            }
        }
        return false;
    }

    private static long toLong(final String p1, final String p2, final String p3, final String p4) {
        long[] ip = new long[4];
        ip[0] = Long.parseLong(p1);
        ip[1] = Long.parseLong(p2);
        ip[2] = Long.parseLong(p3);
        ip[3] = Long.parseLong(p4);
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }


}
