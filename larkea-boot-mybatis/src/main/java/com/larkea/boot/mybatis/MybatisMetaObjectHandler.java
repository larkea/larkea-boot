package com.larkea.boot.mybatis;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

/**
 * Auto-filling.
 * <p>
 * Update gmt_created, gmt_updated when insert/update data
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
