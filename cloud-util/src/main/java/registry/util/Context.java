package registry.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by anzhen on 2016/5/2.
 */
public class Context {

    protected ConcurrentHashMap<String,Object> parameters = new ConcurrentHashMap<String, Object>();

    public Context() {
    }

    public Context(ConcurrentHashMap<String, Object> parameters) {
        if(parameters !=null){
            this.parameters.putAll(parameters);
        }

    }

    /**
     * 获取参数
     *
     * @param name  参数名称
     * @param clazz 类型
     * @return 参数对象
     */

    public <T> T getParameter(final String name,final Class<T> clazz){
        return (T)parameters.get(name);
    }

    /**
     * 获取参数
     *
     * @param name 参数名称
     * @return 参数对象
     */
    public Object getParameter(final String name) {
        return parameters.get(name);
    }

    /**
     * 获取字符串参数
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 字符串参数
     */
    public String getParameter(final String name, final String defaultValue) {
        Object result = parameters.get(name);
        if (result == null) {
            return defaultValue;
        }
        String value = result.toString();
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 获取字节参数
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 浮点数参数
     */
    public byte getParameter(final String name, final byte defaultValue) {
        Object result = parameters.get(name);
        if (result == null) {
            return defaultValue;
        }
        if (result instanceof Number) {
            return ((Number) result).byteValue();
        }
        String text = result.toString();
        if (text == null || text.isEmpty()) {
            return defaultValue;
        }
        try {
            return Byte.parseByte(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 获取短整数参数
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 浮点数参数
     */
    public short getParameter(final String name, final short defaultValue) {
        Object result = parameters.get(name);
        if (result == null) {
            return defaultValue;
        }
        if (result instanceof Number) {
            return ((Number) result).shortValue();
        }
        String text = result.toString();
        if (text == null || text.isEmpty()) {
            return defaultValue;
        }
        try {
            return Short.parseShort(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 获取整数参数
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 整数
     */
    public int getParameter(final String name, final int defaultValue) {
        Object result = parameters.get(name);
        if (result == null) {
            return defaultValue;
        }
        if (result instanceof Number) {
            return ((Number) result).intValue();
        }
        String text = result.toString();
        if (text == null || text.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    /**
     * 获取长整形参数
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 长整形参数
     */
    public long getParameter(final String name, final long defaultValue) {
        Object result = parameters.get(name);
        if (result == null) {
            return defaultValue;
        }
        if (result instanceof Number) {
            return ((Number) result).longValue();
        }
        String text = result.toString();
        if (text == null || text.isEmpty()) {
            return defaultValue;
        }
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 获取浮点数参数
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 浮点数参数
     */
    public double getParameter(final String name, final double defaultValue) {
        Object result = parameters.get(name);
        if (result == null) {
            return defaultValue;
        }
        if (result instanceof Number) {
            return ((Number) result).doubleValue();
        }
        String text = result.toString();
        if (text == null || text.isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 获取布尔值
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 布尔值
     */
    public boolean getParameter(final String name, final boolean defaultValue) {
        Object result = parameters.get(name);
        if (result == null) {
            return defaultValue;
        }
        if (result instanceof Number) {
            return ((Number) result).longValue() != 0;
        }
        String value = result.toString();
        if ("true".equalsIgnoreCase(value)) {
            return true;
        }
        if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        try {
            return Long.parseLong(value) != 0;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 获取正整数参数
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 正整数
     */
    public byte getPositive(final String name, final byte defaultValue) {
        byte result = getParameter(name, defaultValue);
        if (result <= 0 && defaultValue <= 0) {
            throw new IllegalArgumentException("defaultValue <= 0");
        }
        return result <= 0 ? defaultValue : result;
    }

    /**
     * 获取正整数参数
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 正整数
     */
    public short getPositive(final String name, final short defaultValue) {
        short result = getParameter(name, defaultValue);
        if (result <= 0 && defaultValue <= 0) {
            throw new IllegalArgumentException("defaultValue <= 0");
        }
        return result <= 0 ? defaultValue : result;
    }

    /**
     * 获取正整数参数
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 正整数
     */
    public int getPositive(final String name, final int defaultValue) {
        int result = getParameter(name, defaultValue);
        if (result <= 0 && defaultValue <= 0) {
            throw new IllegalArgumentException("defaultValue <= 0");
        }
        return result <= 0 ? defaultValue : result;
    }

    /**
     * 获取长正整数参数
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 长正整数
     */
    public long getPositive(final String name, final long defaultValue) {
        long result = getParameter(name, defaultValue);
        if (result <= 0 && defaultValue <= 0) {
            throw new IllegalArgumentException("defaultValue <= 0");
        }
        return result <= 0 ? defaultValue : result;
    }

    /**
     * 获取自然数参数
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 自然数
     */
    public short getNatural(final String name, final short defaultValue) {
        short result = getParameter(name, defaultValue);
        if (result < 0 && defaultValue < 0) {
            throw new IllegalArgumentException("defaultValue < 0");
        }
        return result < 0 ? defaultValue : result;
    }

    /**
     * 获取自然数参数
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 自然数
     */
    public byte getNatural(final String name, final byte defaultValue) {
        byte result = getParameter(name, defaultValue);
        if (result < 0 && defaultValue < 0) {
            throw new IllegalArgumentException("defaultValue < 0");
        }
        return result < 0 ? defaultValue : result;
    }

    /**
     * 获取自然数参数
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 自然数
     */
    public int getNatural(final String name, final int defaultValue) {
        int result = getParameter(name, defaultValue);
        if (result < 0 && defaultValue < 0) {
            throw new IllegalArgumentException("defaultValue < 0");
        }
        return result < 0 ? defaultValue : result;
    }

    /**
     * 获取自然数参数
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return 自然数
     */
    public long getNatural(final String name, final long defaultValue) {
        long result = getParameter(name, defaultValue);
        if (result < 0 && defaultValue < 0) {
            throw new IllegalArgumentException("defaultValue < 0");
        }
        return result < 0 ? defaultValue : result;
    }


    /**
     * 存放键值对
     *
     * @param key   键
     * @param value 值
     * @return 先前的对象
     */
    public Object put(final String key, final Object value) {
        return parameters.put(key, value);
    }

    /**
     * 存放键值对
     *
     * @param key   键
     * @param value 值
     * @return 先前的对象
     */
    public Object putIfAbsent(final String key, final Object value) {
        return parameters.putIfAbsent(key, value);
    }

    /**
     * 存放键值
     *
     * @param map
     */
    public void putAll(final Map<String, ?> map) {
        if (map != null) {
            parameters.putAll(map);
        }
    }

    /**
     * 删除对象
     *
     * @param key
     * @return
     */
    public Object remove(final String key) {
        return parameters.remove(key);
    }

    /**
     * 清理环境变量
     */
    public void clear() {
        parameters.clear();
    }

    /**
     * 转换成Map对象
     *
     * @return Map对象
     */
    public Map<String, Object> toMap() {
        return new HashMap<String, Object>(parameters);
    }

    /**
     * 获取迭代器
     *
     * @return 迭代器
     */
    public Iterator<Map.Entry<String, Object>> iterator() {
        return parameters.entrySet().iterator();
    }
}
