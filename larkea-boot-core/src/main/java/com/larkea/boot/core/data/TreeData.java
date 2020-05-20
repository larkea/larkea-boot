package com.larkea.boot.core.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

@ApiModel(description = "通用树形结构")
@Data
public class TreeData<T> {

	@JsonUnwrapped
	private T data;

	@ApiModelProperty(value = "子节点")
	private List<TreeData<T>> children;

	private TreeData() {
	}

	public TreeData(T data) {
		this.data = data;
	}

	/**
	 * 构造树形结构通用方法.
	 *
	 * @param collection  待构造的数组元素，属性结构不会改变数据元素的顺序
	 * @param keyMapper   数据的键值
	 * @param valueMapper 数据本身
	 * @param <T>         通用类型
	 * @param <K>         返回的键类型
	 * @return 构建的数据结构数据
	 */
	public static <T, K> TreeData<T> build(@NonNull Collection<T> collection,
			@NonNull Function<T, K> keyMapper,
			@NonNull Function<T, TreeData<T>> valueMapper,
			@NonNull Function<T, K> parentKeyExtractor) {

		TreeData<T> root = valueMapper.apply(null);

		// 使用 LinkedHashMap 来保持原始的顺序
		final Map<K, TreeData<T>> map = collection.stream()
				.collect(Collectors.toMap(keyMapper, valueMapper, throwingMerger(), LinkedHashMap::new));

		// 如果数据存在闭环, 序列化会有问题
		map.values().forEach(treeData -> {
			if (treeData.getData() == null) {
				return;
			}
			K parentKey = parentKeyExtractor.apply(treeData.getData());
			map.getOrDefault(parentKey, root).addChild(treeData);
		});
		return root;
	}

	private static <T> BinaryOperator<T> throwingMerger() {
		return (u, v) -> {
			throw new IllegalStateException(String.format("Duplicate key %s", u));
		};
	}

	private void addChild(TreeData<T> child) {
		if (child == null) {
			return;
		}
		if (children == null) {
			this.children = new ArrayList<>();
		}
		this.children.add(child);
	}
}
