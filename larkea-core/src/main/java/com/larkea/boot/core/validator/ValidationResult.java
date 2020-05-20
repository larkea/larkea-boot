package com.larkea.boot.core.validator;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ValidationResult {

	@ApiModelProperty("对象")
	private String object;

	@ApiModelProperty("字段")
	private String field;

	@ApiModelProperty("非法值")
	private Object rejectedValue;

	@ApiModelProperty("验证消息")
	private String message;

	public ValidationResult(String object, String message) {
		this.object = object;
		this.message = message;
	}

}
