package com.larkea.boot.mybatis;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据库操作实体逻辑删除基类
 */
@Getter
@Setter
public abstract class BaseLogicDeletableEntity<T> extends BaseEntity<T> {

	/**
	 * 逻辑删除时间
	 */
	@TableLogic
	private LocalDateTime gmtDeleted;

}
