package com.larkea.boot.mybatis;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.larkea.boot.core.data.DataSupport;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据库操作实体基类
 */
@Getter
@Setter
public abstract class BaseEntity<T> extends DataSupport<T> implements Serializable {

	/**
	 * 数据创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime gmtCreated;

	/**
	 * 数据修改时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime gmtUpdated;

}
