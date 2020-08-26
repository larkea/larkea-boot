package com.larkea.boot.core.model;

import java.io.Serializable;

import com.larkea.boot.core.data.DataSupport;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseModel<D> extends DataSupport<D> implements Serializable {

}
