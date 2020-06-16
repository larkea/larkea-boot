package com.larkea.boot.core.validator;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@ApiModelProperty("Class Name")
    @JsonIgnore
	private String object;

	@ApiModelProperty("filed name")
	private String field;

	@ApiModelProperty("reject value")
	private Object rejectedValue;

	@ApiModelProperty("validation message")
	private String message;

	public ValidationResult(String object, String message) {
		this.object = object;
		this.message = message;
	}

}
