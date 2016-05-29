package registry.security;

/**
 * Created by anzhen on 2016/5/27.
 */

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 加解密
 * Created by anzhen on 16-1-14.
 */
public class Encrypts {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String CIPHER_DES = "DES/ECB/PKCS5Padding";
    private static final String DES = "DES";
    private static final int DES_KEY_SIZE = 8;

    /**
     * 加密
     *
     * @param source 原字符串
     * @param key    秘钥
     * @return
     * @throws EncryptException
     */
    public static String encryptByDes(final String source, final String key) throws EncryptException {
        try {
            byte[] sourceBytes = source.getBytes(UTF8);
            //实例化
            Cipher cipher = Cipher.getInstance(CIPHER_DES);
            //初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, createSecretKey(key));
            //执行操作
            byte[] data = cipher.doFinal(sourceBytes);
            return new String(Hex.encodeHex(data));
        } catch (Exception e) {
            throw new EncryptException(e.getMessage(), e);
        }
    }

    /**
     * 创建密匙
     *
     * @param key 密匙字符串
     * @return 密匙对象
     * @throws java.security.InvalidKeyException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    private static SecretKey createSecretKey(String key) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        String desKey = paddingKey(key, DES_KEY_SIZE);
        byte[] keyBytes = desKey.getBytes(UTF8);
        //实例化Des密钥
        DESKeySpec dks = new DESKeySpec(keyBytes);
        //实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        //生成密钥
        return keyFactory.generateSecret(dks);
    }

    /**
     * 补充字符串
     *
     * @param key  密匙
     * @param size 大小
     * @return
     */
    private static String paddingKey(String key, int size) {
        String desKey = key;
        if (desKey.length() < size) {
            StringBuffer buffer = new StringBuffer(key);
            for (int i = 0; i < size - desKey.length(); i++) {
                buffer.append('@');
            }
            desKey = buffer.toString();
        }
        return desKey;
    }

    /**
     * 解密
     *
     * @param source 加密并编码的字符串
     * @param key    秘钥
     * @return 字符串
     * @throws EncryptException
     */
    public static String decryptByDes(final String source, final String key) throws EncryptException {
        try {
            byte[] sourceBytes = Hex.decodeHex(source.toCharArray());
            //实例化
            Cipher cipher = Cipher.getInstance(CIPHER_DES);
            //初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, createSecretKey(key));
            //执行操作
            byte[] data = cipher.doFinal(sourceBytes);
            return new String(data, UTF8);
        } catch (Exception e) {
            throw new EncryptException(e.getMessage(), e);
        }
    }
}

