package com.ans.cloud.data.converter;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表转换器
 * Created by anzhen on 15-3-4.
 */
public final class ListConverter {

    /**
     * 从原始对象转换成目标对象
     *
     * @param models    原始对象集合
     * @param converter 转换器
     * @param <S>       原始类型
     * @param <T>       目标类型
     * @return 目标对象集合
     */

    public static <S, T> List<T> convert(final List<S> models, final Converter<S, T> converter) {
        if (models == null || converter == null) {
            return null;
        }
        List<T> result = new ArrayList<T>(models.size());
        for (S model : models) {
            result.add(converter.convert(model));
        }
        return result;
    }

    /**
     * 从目标对象转换成原始对象
     *
     * @param models   目标对象集合
     * @param reverter 转换器
     * @param <S>      原始类型
     * @param <T>      目标类型
     * @return 原始对象集合
     */
    public static <S, T> List<S> revert(final List<T> models, final Reverter<S, T> reverter) {
        if (models == null || reverter == null) {
            return null;
        }
        List<S> result = new ArrayList<S>(models.size());
        for (T model : models) {
            result.add(reverter.revert(model));
        }
        return result;
    }
}
