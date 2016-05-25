package com.ans.cloud.data.util;

import com.ans.cloud.data.Pageable;
import com.ans.cloud.data.model.*;


import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询工具
 * Created by anzhen on 15-7-7.
 */
public class Datum {

    /**
     * 判断Page对象是否有数据
     *
     * @param page Page
     * @return true:有数据
     */
    public static boolean hasData(Page page) {
        return page != null && page.getResult() != null && !page.getResult().isEmpty();
    }

    /**
     * 分页查询
     *
     * @param pageable 分页服务
     * @param query    查询条件
     * @param pageSize 每页大小
     * @param <E>      结果类型
     * @param <Q>      查询条件类型
     * @return 结果数据
     */
    public static <E, Q extends Query> List<E> findAll(final Pageable<E, Q> pageable, final Q query,
                                                       final int pageSize) {
        List<E> result = new ArrayList<E>();
        findAll(pageable, query, pageSize, result, null);
        return result;
    }


    /**
     * 分页查询
     *
     * @param pageable 分页服务
     * @param query    查询条件
     * @param pageSize 每页大小
     * @param callback 回调函数
     * @param <E>      结果类型
     * @param <Q>      查询条件类型
     * @return 结果数据
     */
    public static <E, Q extends Query> List<E> findAll(final Pageable<E, Q> pageable, final Q query, final int pageSize,
                                                       final Callback callback) {
        List<E> result = new ArrayList<E>();
        findAll(pageable, query, pageSize, result, callback);
        return result;
    }

    /**
     * 分页查询
     *
     * @param pageable 分页服务
     * @param query    查询条件
     * @param pageSize 每页大小
     * @param result   结果集
     * @param callback 回调函数
     * @param <E>      结果类型
     * @param <Q>      查询条件类型
     * @return 结果数据
     */
    public static <E, Q extends Query> void findAll(final Pageable<E, Q> pageable, final Q query, final int pageSize,
                                                    List<E> result, final Callback callback) {
        if (pageable == null) {
            throw new IllegalArgumentException("pageable can not be null");
        }
        if (query == null) {
            throw new IllegalArgumentException("pageable can not be null");
        }
        if (result == null) {
            throw new IllegalArgumentException("result can not be null");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize must be greater than 0");
        }

        int pageNo = 1;
        Pagination pagination;
        Page<E> page;
        while (true) {
            // 判断是否要继续
            if (callback != null && !callback.continuous()) {
                break;
            }
            // 构造分页条件
            pagination = new Pagination(pageNo++, pageSize);
            // 查询数据
            page = pageable.findByQuery(new QPageQuery<Q>(pagination, query));
            if (page == null || page.getResult() == null) {
                break;
            }
            if (callback == null) {
                // 没有过滤器
                result.addAll(page.getResult());
            } else {
                // 有过滤器
                for (E e : page.getResult()) {
                    // 遍历元素是否要过滤
                    if (!callback.filter(e)) {
                        result.add(e);
                    }
                }
            }
            if (!page.hasNext()) {
                break;
            }
        }
    }

    /**
     * 查找指定状态的数据
     *
     * @param datum 数据列表
     * @param state 状态
     * @param <E>
     * @return 符合条件的数据列表
     */
    public static <E extends BaseModel> List<E> with(final List<E> datum, final int state) {
        if (datum == null) {
            return new ArrayList<E>();
        }
        List<E> result = new ArrayList<E>(datum.size());
        for (E data : datum) {
            if (data.getStatus() == state) {
                result.add(data);
            }
        }
        return result;
    }


    /**
     * 查找满足过滤条件的数据
     *
     * @param datum    数据列表
     * @param callback 回调
     * @param <E>
     * @return 符合条件的数据列表
     */
    public static <E extends BaseModel> List<E> include(
            final List<E> datum, Callback<E> callback) {
        if (datum == null) {
            return new ArrayList<E>();
        }

        List<E> result = new ArrayList<E>(datum.size());
        for (E data : datum) {
            if (callback == null || callback.filter(data)) {
                result.add(data);
            }
        }

        return result;
    }


    /**
     * 查找排除满足过滤条件的数据
     *
     * @param datum    数据列表
     * @param callback 回调
     * @param <E>
     * @return 符合条件的数据列表
     */
    public static <E extends BaseModel> List<E> exclude(
            final List<E> datum, Callback<E> callback) {
        if (datum == null) {
            return new ArrayList<E>();
        }

        List<E> result = new ArrayList<E>(datum.size());
        for (E data : datum) {
            if (callback == null || !callback.filter(data)) {
                result.add(data);
            }
        }

        return result;
    }


    /**
     * 查找指定状态的数据
     *
     * @param datum  数据列表
     * @param states 状态
     * @param <E>
     * @return 符合条件的数据列表
     */
    public static <E extends BaseModel> List<E> with(final List<E> datum, final int... states) {
        if (datum == null || states == null || states.length == 0) {
            return new ArrayList<E>();
        }
        List<E> result = new ArrayList<E>(datum.size());
        for (E data : datum) {
            for (int state : states) {
                if (data.getStatus() == state) {
                    result.add(data);
                    break;
                }
            }
        }
        return result;
    }


    /**
     * 查找不包括指定状态的数据
     *
     * @param datum 数据列表
     * @param state 状态
     * @param <E>
     * @return 符合条件的数据列表
     */
    public static <E extends BaseModel> List<E> without(final List<E> datum, final int state) {
        if (datum == null) {
            return new ArrayList<E>();
        }
        List<E> result = new ArrayList<E>(datum.size());
        for (E data : datum) {
            if (data.getStatus() != state) {
                result.add(data);
            }
        }
        return result;
    }


    /**
     * 查找不包括指定状态的数据
     *
     * @param datum  数据列表
     * @param states 状态
     * @param <E>
     * @return 符合条件的数据列表
     */
    public static <E extends BaseModel> List<E> without(final List<E> datum, final int... states) {
        if (datum == null || states == null || states.length == 0) {
            return new ArrayList<E>();
        }
        List<E> result = new ArrayList<E>(datum.size());
        boolean flag;
        for (E data : datum) {
            flag = true;
            for (int state : states) {
                if (data.getStatus() == state) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result.add(data);
            }
        }
        return result;
    }

    /**
     * 回调函数
     */
    public interface Callback<E> {
        /**
         * 是否继续
         *
         * @return 继续标示
         */
        boolean continuous();

        /**
         * 是否过滤
         *
         * @return 过滤标示
         */
        boolean filter(E target);
    }

    /**
     * 默认回调实现
     *
     * @param <E>
     */
    public static class PageCallback<E> implements Callback<E> {

        @Override
        public boolean continuous() {
            return true;
        }

        @Override
        public boolean filter(E target) {
            return false;
        }
    }

    /**
     * 必须是指定状态
     *
     * @param <E>
     */
    public static class WithState<E extends BaseModel> implements Callback<E> {

        private int[] states;

        public WithState(int... states) {
            this.states = states;
        }

        @Override
        public boolean continuous() {
            return true;
        }

        @Override
        public boolean filter(E target) {
            boolean filterd = true;
            for (int state : states) {
                if (target.getStatus() == state) {
                    filterd = false;
                    break;
                }
            }
            return filterd;
        }
    }


    /**
     * 不能有状态
     *
     * @param <E>
     */
    public static class WithoutState<E extends BaseModel> implements Callback<E> {

        private int[] states;

        public WithoutState(int... states) {
            this.states = states;
        }

        @Override
        public boolean continuous() {
            return true;
        }

        @Override
        public boolean filter(E target) {
            boolean filterd = false;
            for (int state : states) {
                if (target.getStatus() == state) {
                    filterd = true;
                    break;
                }
            }
            return filterd;
        }
    }


    /**
     * 必须是启用状态
     *
     * @param <E>
     */
    public static class EnableState<E extends BaseModel> implements Callback<E> {

        @Override
        public boolean continuous() {
            return true;
        }

        @Override
        public boolean filter(E target) {
            return target.getStatus() != BaseModel.ENABLED;
        }
    }

}
