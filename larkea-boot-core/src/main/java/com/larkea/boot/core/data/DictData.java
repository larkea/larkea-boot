package com.larkea.boot.core.data;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DictData implements BaseData {

	private final static long serialVersionUID = 2702186935367698988L;

	private Object value;

	private String description;

}
