package com.larkea.boot.mybatis;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseLogicDeletableEntity<T> extends BaseEntity<T> {

	@TableLogic
	private LocalDateTime gmtDeleted;

}
