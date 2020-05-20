package com.larkea.boot.mybatis;


import java.io.Serializable;
import java.util.Map;

import com.larkea.boot.core.data.Page;
import com.larkea.boot.core.data.PageQueryParam;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.NonNull;

/**
 * 重新定义 mybatisplus 的 IService 接口为 Dao 接口.
 *
 * @param <T> 实体
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
	 * 当前 id 是否存在
	 *
	 * @param id 实体的主键
	 * @return true 存在，false 不存在
	 */
	default boolean existsById(@NonNull Serializable id) {
		return 0 != count(new QueryWrapper<T>().eq("id", id));
	}

	/**
	 * 字段值是否存在
	 *
	 * @param column 字段
	 * @param val    字段值
	 * @return true 存在，false 不存在
	 */
	default boolean exists(SFunction<T, ?> column, Object val) {
		if (val == null) {
			return false;
		}

		return 0 != count(new QueryWrapper<T>().lambda().eq(column, val));
	}

	/**
	 * 除 id 外，字段值是否存在
	 *
	 * @param id     排除的主键
	 * @param column 字段
	 * @param val    字段值
	 * @return true 存在，false 不存在
	 */
	default boolean existsExcludesId(Serializable id, SFunction<T, ?> column, Object val) {
		if (val == null) {
			return false;
		}

		return 0 != count(new QueryWrapper<T>().ne("id", id).lambda().eq(column, val));
	}

	/**
	 * 记录是否存在
	 */
	default boolean exists(Wrapper<T> queryWrapper) {
		return 0 != count(queryWrapper);
	}

}
