package com.larkea.boot.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 重新定义 mybatisplus 的 ServiceImpl 为 DaoImpl.
 *
 * @param <M> Mapper
 * @param <T> Entity
 */
public class DaoImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements Dao<T> {

}
