package registry.util;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by anzhen on 2016/5/24.
 */
public class ClazzUtil {

    private static ConcurrentHashMap<Method, String> names = new ConcurrentHashMap<Method, String>();

    private static ConcurrentHashMap<String, Class> clazzes = new ConcurrentHashMap<String, Class>();

    /**
     * 获取方法全路径名称
     *
     * @param method 方法
     * @return 方法全路径名称
     */

    public static String getFullName(final Method method) {
        if (method == null) {
            return null;
        }
        String name = names.get(method);
        if (name == null) {
            String clazz = method.getDeclaringClass().getName();
            String mname = method.getName();
            StringBuilder builder = new StringBuilder(clazz.length() + mname.length() + 1);
            builder.append(clazz).append(".").append(mname);
            name = builder.toString();
            names.putIfAbsent(method, name);
        }
        return name;
    }

    /**
     * 加载类
     *
     * @param className 类名
     * @return 类
     */
    public static Class getClass(final String className) {
        if (className == null || className.isEmpty()) {
            return null;
        }

        Class clazz = clazzes.get(className);
        if (clazz == null) {
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                return null;
            }
            if (clazz != null) {
                Class old = clazzes.putIfAbsent(className, clazz);
                if (old != null) {
                    clazz = old;
                }
            }
        }

        return clazz;
    }

    public static void main(String[] args) {
        Class clazz = getClass("registry.util.Context");
        Method[] methods =  clazz.getDeclaredMethods();
        for(Method method:methods){
            System.out.println(getFullName(method));
        }
    }

}
