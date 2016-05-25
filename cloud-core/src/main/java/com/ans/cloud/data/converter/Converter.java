package com.ans.cloud.data.converter;

/**
 * 模型对象转换器
 *
 * @param <S> 原始对象
 * @param <T> 目标对象
 */
public interface Converter<S, T> {

    /**
     * 从原始对象转换成目标对象
     *
     * @param model 原始对象
     * @return 目标对象
     */
    T convert(S model);

}
