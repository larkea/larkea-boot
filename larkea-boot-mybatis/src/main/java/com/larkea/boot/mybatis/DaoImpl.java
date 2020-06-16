package com.larkea.boot.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * Rewrite mybatisplus's ServiceImpl to DaoImpl.
 *
 * @param <M> Mapper
 * @param <T> Entity
 */
public class DaoImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements Dao<T> {

}
