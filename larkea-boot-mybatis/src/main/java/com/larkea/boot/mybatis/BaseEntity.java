package com.larkea.boot.mybatis;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.larkea.boot.core.data.DataSupport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEntity<T> extends DataSupport<T> implements Serializable {

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime gmtCreated;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime gmtUpdated;

}
