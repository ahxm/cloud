package registry.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by anzhen on 2016/5/24.
 */
public class EncryptUtil {

    public static final String DEFAULT_KEY = "www.ans.com";
    protected static final String KEY_ALGORITHM = "DES";
    protected static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

    /**
     * Encrypt string using MD5 algorithm
     *
     * @param source
     * @return
     */
    public final static String encryptMD5(String source) {
        if (source == null) {
            source = "";
        }
        try {
            return encrypt(source, "MD5");
        } catch (NoSuchAlgorithmException e) {
            //this should not happen
        }
        return null;
    }

    /**
     * Encrypt string using SHA algorithm
     *
     * @param source
     * @return
     */
    public final static String encryptSHA(String source) {
        if (source == null) {
            source = "";
        }
        try {
            return encrypt(source, "SHA");
        } catch (NoSuchAlgorithmException e) {
            //this should not happen
        }
        return null;
    }

    /**
     * Encrypt string
     */
    private final static String encrypt(String source, String algorithm) throws NoSuchAlgorithmException {
        byte[] resByteArray = encrypt(source.getBytes(), algorithm);
        return toHexString(resByteArray);
    }

    /**
     * Encrypt byte array.
     */
    private final static byte[] encrypt(byte[] source, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.reset();
        md.update(source);
        return md.digest();
    }

    /**
     * Get hex string from byte array
     */
    private final static String toHexString(byte[] res) {
        StringBuffer sb = new StringBuffer(res.length << 1);
        for (int i = 0; i < res.length; i++) {
            String digit = Integer.toHexString(0xFF & res[i]);
            if (digit.length() == 1) {
                digit = '0' + digit;
            }
            sb.append(digit);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     */
    protected static Key toDESKey(byte[] key) throws Exception {
        //实例化Des密钥
        DESKeySpec dks = new DESKeySpec(key);
        //实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        //生成密钥
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密后的数据
     */
    public static byte[] encryptDES(byte[] data, byte[] key) throws Exception {
        //还原密钥
        Key k = toDESKey(key);
        //实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @return byte[] 加密后的数据
     */
    public static byte[] encryptDES(byte[] data) throws Exception {
        return encryptDES(data, DEFAULT_KEY.getBytes());
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密后的数据
     */
    public static byte[] decryptDES(byte[] data, byte[] key) throws Exception {
        //欢迎密钥
        Key k = toDESKey(key);
        //实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @return byte[] 解密后的数据
     */
    public static byte[] decryptDES(byte[] data) throws Exception {
        return decryptDES(data, DEFAULT_KEY.getBytes());
    }

}
