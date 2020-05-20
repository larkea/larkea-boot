package com.larkea.boot.mybatis;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据库操作实体基类
 * <p>
 * 增加以下字段:
 *
 * <ul>
 * <li>userIdCreated</li>
 * <li>userIdUpdated</li>
 * </ul>
 */
@Getter
@Setter
public abstract class BaseUserIdEntity<T> extends BaseEntity<T> {

	/**
	 * 创建人主键
	 */
	@TableField(fill = FieldFill.INSERT)
	private Long userIdCreated;

	/**
	 * 修改人主键
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long userIdUpdated;

}
