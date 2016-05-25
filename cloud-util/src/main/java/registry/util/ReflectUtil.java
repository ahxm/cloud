package registry.util;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Created by anzhen on 2016/5/25.
 */
public class ReflectUtil {

    /**
     * 动态调用方法
     *
     * @param target
     * @param methodName
     * @param args
     * @return
     */
    public static Object invoke(final Object target, final String methodName, final Class<?>... args) {
        return AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                try {
                    Method method = method(target, methodName, args);
                    method.setAccessible(true);
                    return method.invoke(target);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        });
    }


    /**
     * 查找方法对象
     *
     * @param target
     * @param methodName
     * @param args
     * @return
     * @throws NoSuchMethodException
     */
    public static Method method(Object target, String methodName, Class<?>[] args) throws NoSuchMethodException {
        try {
            return target.getClass().getMethod(methodName, args);
        } catch (NoSuchMethodException e) {
            return target.getClass().getDeclaredMethod(methodName, args);
        }
    }
}
