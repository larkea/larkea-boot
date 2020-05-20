package com.larkea.boot.core.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import com.larkea.boot.core.util.BeanUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseConverter<D, M> {

	private static final int DATA_CLASS_TYPE_PARAM_IDX = 0;

	private static final int MODEL_CLASS_TYPE_PARAM_IDX = 1;

	@SuppressWarnings("unchecked")
	public D toData(M m) {
		try {
			D d = (D) getDataClass().newInstance();
			BeanUtil.copyProperties(m, d);
			return d;
		}
		catch (Exception e) {
			LOGGER.warn("Convert failed:{}", m);
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public M toModel(D d) {
		try {
			M m = (M) getModelClass().newInstance();
			BeanUtil.copyProperties(d, m);
			return m;
		}
		catch (Exception e) {
			LOGGER.warn("Convert failed:{}", d);
			return null;
		}

	}

	public List<D> toDataList(List<M> mList) {
		return mList.stream().map(this::toData).collect(Collectors.toList());
	}

	public List<M> toModelList(List<D> dList) {
		return dList.stream().map(this::toModel).collect(Collectors.toList());
	}

	private Type[] getTypes() {
		Type genType = this.getClass().getGenericSuperclass();
		return ((ParameterizedType) genType).getActualTypeArguments();
	}

	@SuppressWarnings("rawtypes")
	private Class getDataClass() {
		return (Class) (getTypes()[DATA_CLASS_TYPE_PARAM_IDX]);
	}

	@SuppressWarnings("rawtypes")
	private Class getModelClass() {
		return (Class) (getTypes()[MODEL_CLASS_TYPE_PARAM_IDX]);
	}

}
