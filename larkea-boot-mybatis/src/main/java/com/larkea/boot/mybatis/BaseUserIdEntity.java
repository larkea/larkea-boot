package com.larkea.boot.mybatis;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

/**
 * Base entity with user-related fields.
 * <p>
 * <ul>
 * <li>userIdCreated</li>
 * <li>userIdUpdated</li>
 * </ul>
 * </p>
 */
@Getter
@Setter
public abstract class BaseUserIdEntity<T> extends BaseEntity<T> {

    @TableField(fill = FieldFill.INSERT)
    private Long userIdCreated;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long userIdUpdated;

}
