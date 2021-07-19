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

@ApiModel(description = "Tree data structure")
@Data
public class TreeData<T> {

	@JsonUnwrapped
	private T data;

	@ApiModelProperty(value = "Children nodes")
	private List<TreeData<T>> children;

	private TreeData() {
	}

	public TreeData(T data) {
		this.data = data;
	}

	/**
	 * Build a tree data structure for a one-d collection and keep origin order.
	 *
	 * @param collection  A one-d collection with data,
	 * @param keyMapper   Key mapper function
	 * @param valueMapper Value mapper function
	 * @param <T>         Data type of value
	 * @param <K>         Data type of key
	 * @return Tree data
	 */
	public static <T, K> TreeData<T> build(@NonNull Collection<T> collection,
			@NonNull Function<T, K> keyMapper,
			@NonNull Function<T, TreeData<T>> valueMapper,
			@NonNull Function<T, K> parentKeyExtractor) {

		TreeData<T> root = valueMapper.apply(null);

		// Use LinkedHashMap to keep origin order in the collection
		final Map<K, TreeData<T>> map = collection.stream()
				.collect(Collectors.toMap(keyMapper, valueMapper, throwingMerger(), LinkedHashMap::new));

		// Need to check whether a circle in collection
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
