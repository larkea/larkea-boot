package com.larkea.boot.core.data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.larkea.boot.core.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DataSupport<D> implements ToData<D>, FromData<D> {

	@SuppressWarnings("unchecked")
	public D toData() {
		try {
			D data = (D) getDataClass().newInstance();
			BeanUtil.copyProperties(this, data);
			return data;
		}
		catch (Exception e) {
			LOGGER.warn("Convert failed:{}", this);
			return null;
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public <R> R fromData(D data) {
		BeanUtil.copyProperties(data, this);
		return (R) this;
	}

	private Type[] getTypes() {
		Type genType = this.getClass().getGenericSuperclass();
		return ((ParameterizedType) genType).getActualTypeArguments();
	}

	private Class getDataClass() {
		int typeParamIdx = 0;
		return (Class) (getTypes()[typeParamIdx]);
	}

}
