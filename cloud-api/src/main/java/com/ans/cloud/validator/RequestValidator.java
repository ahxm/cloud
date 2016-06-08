package com.ans.cloud.validator;

import com.ans.cloud.model.Request;

import java.util.List;
import java.util.regex.Pattern;


/**
 * Created by anzhen on 2016/6/8.
 */
public class RequestValidator<R extends Request> implements Validator<R> {

    public static final int UUID_LENGTH = 50;

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^1[3|4|5|7|8]\\d{9}$";

    /**
     * 判断是否为空
     *
     * @param value 值
     * @return 为空标示
     */
    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * 判断字符串长度
     *
     * @param value   值
     * @param minSize 最小值
     * @param maxSize 最大值
     * @return 长度在区间里面
     */
    public static boolean isBetween(String value, int minSize, int maxSize) {
        int size = value == null ? 0 : value.length();
        return size >= minSize && size <= maxSize;
    }

    /**
     * 判断值是否在区间内
     *
     * @param value 值
     * @param min   最小值
     * @param max   最大值
     * @return 值在区间里面
     */
    public static boolean isBetween(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * 判断是否相等
     *
     * @param source 原字符串
     * @param target 目标字符串
     * @return 相等标示
     */
    public static boolean isEqual(final String source, final String target) {
        if (source == null && target == null) {
            return true;
        }
        if (source == null) {
            return false;
        }
        if (target == null) {
            return false;
        }
        return target.equals(source);
    }

    /**
     * 是否是数字
     *
     * @param ch 字符
     * @return 数字标示
     */
    public static boolean isDigit(final char ch) {
        //[0-9]
        return ch >= 48 && ch <= 57;
    }

    /**
     * 是否是可见的字母
     *
     * @param ch 字符
     * @return 可见的字母标示
     */
    public static boolean isAlphabet(final char ch) {
        //[A-Za-z]
        return ch >= 65 && ch <= 90 || ch >= 97 && ch <= 122;
    }

    /**
     * 是否是有效的密码
     *
     * @param password 名称
     * @return 有效标示
     */
    public static boolean isPassword(final String password) {
        if (password == null || password.length() > 16 && password.length() < 8) {
            return false;
        }
        char[] chars = password.toCharArray();
        int flag = 0;
        for (char ch : chars) {
            // 必须是打印的ASCII码表
            if (ch < 33 || ch > 126) {
                return false;
            }
            // 判断是否是字母
            if (isAlphabet(ch)) {
                if (Character.isUpperCase(ch)) {
                    // 大小字母
                    flag |= 0x1;
                } else if (Character.isLowerCase(ch)) {
                    // 小写字母
                    flag |= 0x2;
                }
            } else if (isDigit(ch)) {
                // 数字
                flag |= 0x4;
            }
        }
        return flag == 7;
    }

    /**
     * 是否是UUID
     *
     * @param value
     * @return
     */
    public static boolean isUUID(final String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        //标准的UUID格式为：xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx (8-4-4-4-12)
        //其中每个 x 是 0-9 或 a-f 范围内的一个十六进制的数字
        int[] lengths = new int[]{8, 4, 4, 4, 12};
        String[] parts = new String[5];
        int index = 0;
        int start = -1;
        int end = -1;
        char[] chars = value.toCharArray();
        char ch = 0;
        // 遍历字符串
        for (int i = 0; i < chars.length; i++) {
            if (index > 4) {
                // 超过了5个部分
                return false;
            }
            ch = chars[i];
            if (ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'z') {
                // 字母
                if (start == -1) {
                    start = i;
                }
                end = i;
            } else if (ch == '-') {
                // 分隔符
                if (start == -1) {
                    // 前面必须有字符
                    return false;
                }
                // 该段字符串
                parts[index] = new String(chars, start, end - start + 1);
                if (parts[index].length() != lengths[index]) {
                    // 比较长度
                    return false;
                }
                index++;
                start = -1;
                end = -1;
            } else {
                // 其它字符
                return false;
            }
        }
        // 剩余最后一段
        if (start > -1) {
            parts[index] = new String(chars, start, end - start + 1);
            if (parts[index].length() != lengths[index]) {
                return false;
            }
        }
        if (index != 4) {
            // 不是5个部分
            return false;
        }
        return true;
    }

    /**
     * 是否是有效的ip
     *
     * @param ip ip地址
     * @return 有效标示
     */
    public static boolean isIp(final String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        int[] parts = new int[4];
        int index = 0;
        int start = -1;
        int end = -1;
        int part;
        char[] chars = ip.toCharArray();
        char ch = 0;
        for (int i = 0; i < chars.length; i++) {
            if (index > 3) {
                // 超过了4个数字
                return false;
            }
            ch = chars[i];
            switch (ch) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    if (start == -1) {
                        start = i;
                    }
                    end = i;
                    if (end - start > 2) {
                        // 超长了，最多3个数字
                        return false;
                    }
                    break;
                case '.':
                    // 分隔符
                    if (start == -1) {
                        // 前面必须有字符
                        return false;
                    }
                    part = Integer.parseInt(new String(chars, start, end - start + 1));
                    if (part > 255) {
                        return false;
                    }
                    parts[index++] = part;
                    start = -1;
                    end = -1;
                    break;
                default:
                    return false;
            }
        }
        if (start > -1) {
            part = Integer.parseInt(new String(chars, start, end - start + 1));
            if (part > 255) {
                return false;
            }
            parts[index] = part;
            return index == 3;
        } else {
            // 以.结尾
            return false;
        }
    }

    /**
     * 判断是否是IP列表
     *
     * @param ips IP列表
     * @return
     */
    public static boolean isIps(final List<String> ips) {
        if (ips == null || ips.isEmpty()) {
            return false;
        } else {
            for (String ip : ips) {
                if (!isIp(ip)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否是Cidr字符串<p/>
     * 两种格式：10.10.12.0/10 | 10.10.12.0
     *
     * @param value
     * @return
     */
    public static boolean isCidr(final String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        // 分割符号
        int pos = value.indexOf('/');

        String ip = value;
        String v = null;
        if (pos > -1 && pos != value.length() - 1) {
            ip = value.substring(0, pos);
            v = value.substring(pos + 1);
        }

        if (!isIp(ip)) {
            return false;
        }

        if (v == null) {
            return true;
        }
        try {
            int length = Integer.parseInt(v);
            return length >= 0 && length <= 32;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 有效的端口号
     *
     * @param port 端口
     * @return
     */
    public static boolean isPort(final int port) {
        return isBetween(port, 0, 65535);
    }

    /**
     * 有效的端口号范围，用:分割
     *
     * @param port 端口范围字符串
     * @return
     */
    public static boolean isPorts(final String port) {
        if (port == null || port.isEmpty()) {
            return false;
        }
        int[] parts = new int[2];
        int index = 0;
        int start = -1;
        int end = -1;
        int part;
        char[] chars = port.toCharArray();
        char ch = 0;
        for (int i = 0; i < chars.length; i++) {
            if (index > 1) {
                // 超过了2个数字
                return false;
            }
            ch = chars[i];
            switch (ch) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    if (start == -1) {
                        start = i;
                    }
                    end = i;
                    break;
                case ':':
                    // 分隔符
                    if (start == -1) {
                        // 前面必须有字符
                        return false;
                    }
                    part = Integer.parseInt(new String(chars, start, end - start + 1));
                    if (!isPort(part)) {
                        return false;
                    }
                    parts[index++] = part;
                    start = -1;
                    end = -1;
                    break;
                default:
                    return false;
            }
        }
        if (start > -1) {
            part = Integer.parseInt(new String(chars, start, end - start + 1));
            if (!isPort(part)) {
                return false;
            }
            parts[index] = part;
            return true;
        } else {
            // 以-结尾
            return false;
        }
    }


    /**
     * 有效的email
     *
     * @param email 邮件
     * @return
     */
    public static boolean isEmail(final String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        boolean flag = false;
        int start = -1;
        int end = -1;
        char[] chars = email.toCharArray();
        char ch = 0;
        for (int i = 0; i < chars.length; i++) {
            ch = chars[i];
            if (ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') {
                if (start == -1) {
                    start = i;
                }
                end = i;
            } else if (ch == '_' || ch == '-') {
                if (start == -1) {
                    // 前面必须有字母或数字
                    return false;
                }
                end = i;
            } else if (ch == '.') {
                if (start == -1) {
                    // 前面必须有标识符
                    return false;
                }
                // 不能以'_'和'-'结束
                if (chars[end] == '_' || chars[end] == '-') {
                    return false;
                }
                start = -1;
                end = -1;
            } else if (ch == '@') {
                if (start == -1) {
                    // 前面必须有标识符
                    return false;
                }
                if (flag) {
                    // 不能出现多次
                    return false;
                }
                // 不能以'_'和'-'结束
                if (chars[end] == '_' || chars[end] == '-') {
                    return false;
                }
                flag = true;
                start = -1;
                end = -1;
            }
        }
        if (start > -1) {
            // 不能以'_'和'-'结束
            if (chars[end] == '_' || chars[end] == '-') {
                return false;
            }
            // 必须出现'@'
            return flag;
        } else {
            // 以'.'或者'@'结尾
            return false;
        }
    }

    /**
     * 有效的phone
     *
     * @param phone 端口
     * @return
     */
    public static boolean isPhone(final String phone) {
        return Pattern.matches(REGEX_MOBILE, phone);
    }

    /**
     * 是否是有效的Protocol
     *
     * @param protocol 状态
     * @return 有效标示
     */
    public static boolean isProtocol(final String protocol) {
        if (protocol == null || protocol.isEmpty()) {
            return false;
        }
        for (Protocol p : Protocol.values()) {
            if (protocol.equalsIgnoreCase(p.name())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检验请求参数是否合法
     *
     * @param request 请求
     * @return
     */
    @Override
    public String validate(R request) {
        if (!isUUID(request.getRequestId())) {
            return "requestId is invalid.";
        } else if (!isBetween(request.getSignature(), 1, UUID_LENGTH)) {
            return "signature is invalid.";
        } else if (!isBetween(request.getAccessKey(), 1, UUID_LENGTH)) {
            return "accessKey is invalid.";
        } else if (!isBetween(request.getDataCenter(), 1, UUID_LENGTH)) {
            return "dataCenter is invalid.";
        } else if (!isBetween(request.getAction(), 1, UUID_LENGTH)) {
            return "action is invalid.";
        } else if (!isBetween(request.getSignatureMethod(), 1, UUID_LENGTH)) {
            return "signatureMethod is invalid.";
        } else if (!isBetween(request.getSignatureVersion(), 1, UUID_LENGTH)) {
            return "signatureVersion is invalid.";
        } else if (!isBetween(request.getApiVersion(), 1, UUID_LENGTH)) {
            return "apiVersion is invalid.";
        } else if (!isBetween(request.getAcceptType(), 1, UUID_LENGTH)) {
            return "acceptType is invalid.";
        } else if (!isBetween(request.getAccount(), 0, UUID_LENGTH)) {
            // account, user在API调用时为空, 之后会在验证签名后补全
            return "account is invalid.";
        } else if (!isBetween(request.getUser(), 0, UUID_LENGTH)) {
            return "user is invalid.";
        } else if (request.getTimestamp() == null) {
            return "timestamp is invalid.";
        }

        return null;
    }

    /**
     * 是否是有效的数据库名称
     *
     * @param name 名称
     * @return 有效标示
     */
    protected boolean isClusterName(final String name) {
        if (name == null || name.length() > 32 || name.length() < 2) {
            return false;
        }
        int flag = 0;
        char[] chars = name.toCharArray();
        for (char ch : chars) {
            if (ch == '_') {
                flag |= 0x1;
                continue;
            } else if (isDigit(ch)) {
                // 数字
                flag |= 0x2;
                continue;
            } else if (isAlphabet(ch)) {
                // 字母
                flag |= 0x4;
                continue;
            }
            return false;
        }
        // 数据库名称可以都是下划线
        return true;
    }

    /**
     * 是否是有效的英文名称
     *
     * @param name 名称
     * @return 有效标示
     */
    public static boolean isEnglishName(final String name) {
        if (name == null || name.length() > 32) {
            return false;
        }
        int flag = 0;
        char[] chars = name.toCharArray();
        for (char ch : chars) {
            if (ch == '_') {
                flag |= 0x1;
                continue;
            } else if (isDigit(ch)) {
                // 数字
                flag |= 0x2;
                continue;
            } else if (isAlphabet(ch)) {
                // 字母
                flag |= 0x4;
                continue;
            }
            return false;
        }
        // 不能都是下划线
        return flag != 1;
    }

    /**
     * 是否是有效的中文名称
     *
     * @param name 名称
     * @return 有效标示
     */
    protected boolean isChineseName(final String name) {
        if (name == null || name.length() > 32) {
            return false;
        }
        Character.UnicodeBlock ub;
        char[] chars = name.toCharArray();
        int flag = 0;
        for (char ch : chars) {
            if (ch == '_') {
                flag |= 0x1;
                continue;
            } else if (ch == '-') {
                flag |= 0x5;
                continue;
            } else if (isDigit(ch)) {
                flag |= 0x2;
                continue;
            } else if (isAlphabet(ch)) {
                flag |= 0x4;
                continue;
            } else {
                ub = Character.UnicodeBlock.of(ch);
                if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock
                        .CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock
                        .CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub
                        == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock
                        .HALFWIDTH_AND_FULLWIDTH_FORMS) {
                    flag |= 0x4;
                    continue;
                }
            }
            return false;
        }
        // 不能都是下划线
        return flag != 1;
    }

    /**
     * 是否是有效的描述
     *
     * @param name 名称
     * @return 有效标示
     */
    protected boolean isDescription(final String name) {
        if (name == null || name.isEmpty()) {
            return true;
        }
        if (name.length() > 32) {
            return false;
        }
        Character.UnicodeBlock ub;
        char[] chars = name.toCharArray();
        for (char ch : chars) {
            if (ch == '_') {
                continue;
            } else if (isDigit(ch)) {
                continue;
            } else if (isAlphabet(ch)) {
                continue;
            } else {
                ub = Character.UnicodeBlock.of(ch);
                if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock
                        .CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock
                        .CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub
                        == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock
                        .HALFWIDTH_AND_FULLWIDTH_FORMS) {
                    continue;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * 协议
     */
    public enum Protocol {
        HTTP,
        HTTPS,
        TCP;
    }
}
