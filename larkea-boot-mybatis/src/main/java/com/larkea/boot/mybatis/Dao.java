package com.larkea.boot.mybatis;


import java.io.Serializable;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.larkea.boot.core.data.Page;
import com.larkea.boot.core.data.PageQueryParam;
import lombok.NonNull;

/**
 * Rewrite mybatisplus's IService to Dao .
 */
public interface Dao<T> extends IService<T> {

    default Page<T> page(PageQueryParam pageQueryParam) {
        return page(pageQueryParam, Wrappers.emptyWrapper());
    }

    default Page<T> page(PageQueryParam pageQueryParam, Wrapper<T> queryWrapper) {
        IPage<T> iPage = PageUtil.getPage(pageQueryParam);
        iPage = page(iPage, queryWrapper);
        return new Page<>(iPage.getTotal(), iPage.getRecords());
    }

    default Page<Map<String, Object>> pageMaps(PageQueryParam pageQueryParam) {
        return pageMaps(pageQueryParam, Wrappers.emptyWrapper());
    }

    default Page<Map<String, Object>> pageMaps(PageQueryParam pageQueryParam,
                                               Wrapper<T> queryWrapper) {
        IPage<Map<String, Object>> iPage = PageUtil.getPage(pageQueryParam);
        IPage<Map<String, Object>> mapIPage = pageMaps(iPage, queryWrapper);
        return new Page<>(mapIPage.getTotal(), mapIPage.getRecords());
    }

    /**
     * Check id whether existed or not
     *
     * @param id primary key
     * @return true when existed，false when not existed
     */
    default boolean existsById(@NonNull Serializable id) {
        return 0 != count(new QueryWrapper<T>().eq("id", id));
    }

    /**
     * Check the value of the column whether existed or not
     *
     * @param column field
     * @param val    the value of field
     * @return true when existed，false when not existed
     */
    default boolean exists(SFunction<T, ?> column, Object val) {
        if (val == null) {
            return false;
        }

        return 0 != count(new QueryWrapper<T>().lambda().eq(column, val));
    }

    /**
     * Check the value of the column whether existed or not, excludes id
     *
     * @param id     the primary key excludes
     * @param column field
     * @param val    the value of field
     * @return true when existed，false when not existed
     */
    default boolean existsExcludesId(Serializable id, SFunction<T, ?> column, Object val) {
        if (val == null) {
            return false;
        }

        return 0 != count(new QueryWrapper<T>().ne("id", id).lambda().eq(column, val));
    }

    /**
     * Check whether existed or not using query wrapper
     * @return true when existed，false when not existed
     */
    default boolean exists(Wrapper<T> queryWrapper) {
        return 0 != count(queryWrapper);
    }

}
