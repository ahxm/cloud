package registry.util;


import registry.annotation.*;
import registry.exception.ValidateException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Created by anzhen on 2016/5/1.
 */
public class Validators {

    public static void validate(final Object target) throws IllegalAccessException, SecurityException, IllegalStateException {

        if (target == null) {
            return;
        }

        Class<?> clazz = target.getClass();
        Field[] fields;
        Annotation[] annotations;
        while (clazz != null && clazz != Objects.class) {
            fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                annotations = field.getDeclaredAnnotations();
                if (annotations != null) {
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof NotNull) {
                            validateNotNull(field, (NotNull) annotation, target);
                        } else if (annotation instanceof NotEmpty) {
                            validateNotEmpty(field, (NotEmpty) annotation, target);
                        } else if (annotation instanceof IntRange) {
                            validateIntRange(field, (IntRange) annotation, target);
                        } else if (annotation instanceof LongRange) {
                            validateLongRange(field, (LongRange) annotation, target);
                        } else if (annotation instanceof ShortRange) {
                            validateShortRange(field, (ShortRange) annotation, target);
                        } else if (annotation instanceof ByteRange) {
                            validateByteRange(field, (ByteRange) annotation, target);
                        }
                    }
                }
            }
        }

    }

    /**
     * 验证不能为空
     *
     * @param field      字段
     * @param annotation 声明
     * @param target     目标对象
     * @throws IllegalAccessException
     * @throws ValidateException
     */

    protected static void validateNotNull(Field field, NotNull annotation, Object target) throws IllegalAccessException, ValidateException {

        Object value = getField(field, target);
        if (value == null) {
            if (annotation.message() == null || annotation.message().isEmpty()) {
                throw new ValidateException(field.getName() + "can not be null");
            }
            throw new ValidateException(String.format(annotation.message(), field.getName()));
        }

    }


    /**
     * 验证字符串不能为空
     *
     * @param field      字段
     * @param annotation 声明
     * @param target     目标对象
     * @throws IllegalAccessException
     * @throws ValidateException
     */

    protected static void validateNotEmpty(Field field, NotEmpty annotation, Object target) throws IllegalAccessException, ValidateException {
        Class<?> type = field.getType();
        if (type.isAssignableFrom(String.class)) {
            Object value = getField(field, target);
            if (value == null || ((String) value).isEmpty()) {
                if (annotation.message() == null || annotation.message().isEmpty()) {
                    throw new ValidateException(field.getName() + "can not be empty");

                }
                throw new ValidateException(String.format(annotation.message(), field.getName()));
            }
        }
    }

    /**
     * 验证整数范围
     *
     * @param field      字段
     * @param annotation 声明
     * @param target     目标对象
     * @throws IllegalAccessException
     * @throws ValidateException
     */
    protected static void validateIntRange(Field field, IntRange annotation, Object target) throws
            IllegalAccessException, ValidateException {
        boolean flag = true;
        Class<?> type = field.getType();
        if (type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class)) {
            Object value = getField(field, target);
            if (value == null) {
                return;
            }
            Integer v = (Integer) value;
            if (v > annotation.max() || v < annotation.min()) {
                flag = false;
            }
        } else {
            flag = false;
        }
        if (!flag) {
            if (annotation.message() == null || annotation.message().isEmpty()) {
                throw new ValidateException(
                        String.format("%s is not in range[%d,%d]", field.getName(), annotation.min(),
                                annotation.max()));
            }
            throw new ValidateException(
                    String.format(annotation.message(), field.getName(), annotation.min(), annotation.max()));
        }
    }

    /**
     * 验证长整数范围
     *
     * @param field      字段
     * @param annotation 声明
     * @param target     目标对象
     * @throws IllegalAccessException
     * @throws ValidateException
     */
    protected static void validateLongRange(Field field, LongRange annotation, Object target) throws
            IllegalAccessException, ValidateException {
        boolean flag = true;
        Class<?> type = field.getType();
        if (type.isAssignableFrom(long.class) || type.isAssignableFrom(Long.class)) {
            Object value = getField(field, target);
            if (value == null) {
                return;
            }
            Long v = (Long) value;
            if (v > annotation.max() || v < annotation.min()) {
                flag = false;
            }
        } else {
            flag = false;
        }
        if (!flag) {
            if (annotation.message() == null || annotation.message().isEmpty()) {
                throw new ValidateException(
                        String.format("%s is not in range[%d,%d]", field.getName(), annotation.min(),
                                annotation.max()));
            }
            throw new ValidateException(
                    String.format(annotation.message(), field.getName(), annotation.min(), annotation.max()));
        }
    }

    /**
     * 验证短整数范围
     *
     * @param field      字段
     * @param annotation 声明
     * @param target     目标对象
     * @throws IllegalAccessException
     * @throws ValidateException
     */
    protected static void validateShortRange(Field field, ShortRange annotation, Object target) throws
            IllegalAccessException, ValidateException {
        boolean flag = true;
        Class<?> type = field.getType();
        if (type.isAssignableFrom(short.class) || type.isAssignableFrom(Short.class)) {
            Object value = getField(field, target);
            if (value == null) {
                return;
            }
            Short v = (Short) value;
            if (v > annotation.max() || v < annotation.min()) {
                flag = false;
            }
        } else {
            flag = false;
        }
        if (!flag) {
            if (annotation.message() == null || annotation.message().isEmpty()) {
                throw new ValidateException(
                        String.format("%s is not in range[%d,%d]", field.getName(), annotation.min(),
                                annotation.max()));
            }
            throw new ValidateException(
                    String.format(annotation.message(), field.getName(), annotation.min(), annotation.max()));
        }
    }

    /**
     * 验证字节范围
     *
     * @param field      字段
     * @param annotation 声明
     * @param target     目标对象
     * @throws IllegalAccessException
     * @throws ValidateException
     */
    protected static void validateByteRange(Field field, ByteRange annotation, Object target) throws
            IllegalAccessException, ValidateException {
        boolean flag = true;
        Class<?> type = field.getType();
        if (type.isAssignableFrom(byte.class) || type.isAssignableFrom(Byte.class)) {
            Object value = getField(field, target);
            if (value == null) {
                return;
            }
            Byte v = (Byte) value;
            if (v > annotation.max() || v < annotation.min()) {
                flag = false;
            }
        } else {
            flag = false;
        }
        if (!flag) {
            if (annotation.message() == null || annotation.message().isEmpty()) {
                throw new ValidateException(
                        String.format("%s is not in range[%d,%d]", field.getName(), annotation.min(),
                                annotation.max()));
            }
            throw new ValidateException(
                    String.format(annotation.message(), field.getName(), annotation.min(), annotation.max()));
        }
    }


    /**
     * 获取字段值
     *
     * @param field  字段
     * @param target 目标
     * @return 字段值
     * @throws IllegalAccessException
     */

    protected static Object getField(final Field field, final Object target) throws IllegalAccessException {
        if (field.isAccessible()) {
            return field.get(target);
        } else {
            field.setAccessible(true);
            try {
                return field.get(target);
            } finally {
                field.setAccessible(false);
            }
        }
    }


}
