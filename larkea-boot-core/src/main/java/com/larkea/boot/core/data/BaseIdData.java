package com.larkea.boot.core.data;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseIdData implements BaseData {

	private final static long serialVersionUID = -1741347060973497629L;

	private Long id;

}
