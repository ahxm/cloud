package registry.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;

/**
 * Created by anzhen on 2016/5/25.
 */
public class Digests {

    /**
     * HMAC-SHA1算法
     *
     * @param source 原字符串
     * @param key    密匙
     * @return
     */
    public static byte[] hmacSha1(String source, String key) {
        return HmacUtils.hmacSha1(key, source);
    }

    /**
     * BASE64 编码
     *
     * @param data 数据
     * @return
     */
    public static String encodeBase64(byte[] data) {
        return Base64.encodeBase64String(data);
    }

    /**
     * BASE64 解码
     *
     * @param source 编码字符串
     * @return
     */
    public static byte[] decodeBase64(String source) {
        return Base64.decodeBase64(source);
    }
}
