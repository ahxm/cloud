package com.ans.cloud.data.converter;

/**
 * 模型对象转换器
 *
 * @param <S> 原始对象
 * @param <T> 目标对象
 */
public interface Reverter<S, T> {
    /**
     * 从目标对象转换成原始对象
     *
     * @param model 目标对象
     * @return 原始对象
     */
    S revert(T model);
}
