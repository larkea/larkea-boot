package com.larkea.boot.mybatis;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

/**
 * 自动填充.
 * <p>
 * 实现数据库插入更新操作自动填充更新时间gmt_created, gmt_updated 字段
 */
public class MybatisMetaObjectHandler implements MetaObjectHandler {

	private static final String FIELD_GMT_CREATED = "gmtCreated";

	private static final String FIELD_GMT_UPDATED = "gmtUpdated";

	@Override
	public void insertFill(MetaObject metaObject) {
		LocalDateTime now = LocalDateTime.now();
		this.strictInsertFill(metaObject, FIELD_GMT_CREATED, LocalDateTime.class, now);
		this.strictInsertFill(metaObject, FIELD_GMT_UPDATED, LocalDateTime.class, now);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, FIELD_GMT_UPDATED, LocalDateTime.class, LocalDateTime.now());
	}
}
