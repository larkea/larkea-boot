package com.larkea.boot.core.data;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

@ApiModel(description = "通用树形选择器结构")
@Data
@Accessors(chain = true)
public class SelectData {

	@ApiModelProperty(value = "节点键，树内唯一")
	private String key;

	@ApiModelProperty(value = "父节点键")
	private String parentKey;

	@ApiModelProperty(value = "节点值")
	private String value;

	@ApiModelProperty(value = "节点标签/标题")
	private String label;

	public SelectData() {

	}

	public SelectData(String key, String parentKey, String value, String label) {
		this.key = key;
		this.parentKey = parentKey;
		this.value = value;
		this.label = label;
	}

	public SelectData(String key, String parentKey, String label) {
		this.key = key;
		this.parentKey = parentKey;
		this.value = String.valueOf(key);
		this.label = label;
	}

	/**
	 * 构造树形结构通用方法.
	 *
	 * @param collection     待构造的数组元素
	 * @param keyExtractor   数据的键值
	 * @param valueExtractor 数据本身
	 * @param labelExtractor 标题
	 * @param <T>            通用类型
	 * @return 构建的数据结构数据
	 */
	public static <T> TreeData<SelectData> build(@NonNull Collection<T> collection,
			@NonNull Function<T, String> keyExtractor,
			@NonNull Function<T, String> parentKeyExtractor,
			@NonNull Function<T, String> valueExtractor,
			@NonNull Function<T, String> labelExtractor
	) {
		List<SelectData> selectDataList = collection
				.stream()
				.map(data -> new SelectData()
						.setKey(keyExtractor.apply(data))
						.setParentKey(parentKeyExtractor.apply(data))
						.setValue(valueExtractor.apply(data))
						.setLabel(labelExtractor.apply(data)))
				.collect(Collectors.toList());

		return TreeData
				.build(selectDataList, SelectData::getKey, TreeData::new, SelectData::getParentKey);
	}

	/**
	 * 构造树形结构通用方法.
	 *
	 * @param collection     待构造的数组元素
	 * @param keyExtractor   数据的键值
	 * @param labelExtractor 标题
	 * @param <T>            通用类型
	 * @return 构建的数据结构数据
	 */
	public static <T> TreeData<SelectData> build(Collection<T> collection,
			Function<T, String> keyExtractor,
			Function<T, String> parentKeyExtractor,
			Function<T, String> labelExtractor
	) {
		return build(collection, keyExtractor, parentKeyExtractor, keyExtractor, labelExtractor);
	}

}
