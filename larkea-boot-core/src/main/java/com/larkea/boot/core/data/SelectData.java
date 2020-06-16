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

@ApiModel(description = "Tree select data structure")
@Data
@Accessors(chain = true)
public class SelectData {

    @ApiModelProperty(value = "key of node, must be unique in a tree")
    private String key;

    @ApiModelProperty(value = "key of parent node")
    private String parentKey;

    @ApiModelProperty(value = "value of node")
    private String value;

    @ApiModelProperty(value = "name of node")
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
     * Build a tree data structure for a one-d collection.
     *
     * @param collection     a one-d collection with data
     * @param keyExtractor   key extractor
     * @param valueExtractor value extractor
     * @param labelExtractor name extractor
     * @param <T>            Data type
     * @return Tree data
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
     * Build a tree data structure for a one-d collection.
     *
     * @param collection     a one-d collection with data
     * @param keyExtractor   key extractor
     * @param labelExtractor name extractor
     * @param <T>            Data type
     * @return Tree data
     */
    public static <T> TreeData<SelectData> build(Collection<T> collection,
                                                 Function<T, String> keyExtractor,
                                                 Function<T, String> parentKeyExtractor,
                                                 Function<T, String> labelExtractor
    ) {
        return build(collection, keyExtractor, parentKeyExtractor, keyExtractor, labelExtractor);
    }

}
