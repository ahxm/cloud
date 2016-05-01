package registry.util;


import registry.annotation.NotNull;
import registry.exception.ValidateException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Created by anzhen on 2016/5/1.
 */
public class Validators {

    public static void validate(final Object target) throws IllegalAccessException,SecurityException,IllegalStateException{

        if(target == null){
            return;
        }

        Class<?> clazz = target.getClass();
        Field[] fields;
        Annotation[] annotations;
        while (clazz != null && clazz!= Objects.class){
            fields = clazz.getDeclaredFields();
            for(Field field:fields){
                annotations = field.getDeclaredAnnotations();
                if(annotations!=null){
                    for (Annotation annotation:annotations){
                        if(annotation instanceof NotNull){

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

    protected static void validateNotNull(Field field,NotNull annotation,Object target) throws IllegalAccessException , ValidateException {

    }

    /**
     * 获取字段值
     *
     * @param field  字段
     * @param target 目标
     * @return 字段值
     * @throws IllegalAccessException
     */

    protected static Object getField(final Field field,final )


}
