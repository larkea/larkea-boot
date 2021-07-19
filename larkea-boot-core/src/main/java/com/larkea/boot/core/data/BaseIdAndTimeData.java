package com.larkea.boot.core.data;


import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseIdAndTimeData extends BaseIdData {

	private final static long serialVersionUID = -8132575073846562918L;

	private LocalDateTime gmtCreated;

	private LocalDateTime gmtUpdated;

}
