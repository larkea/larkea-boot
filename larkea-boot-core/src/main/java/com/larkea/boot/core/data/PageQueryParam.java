package com.larkea.boot.core.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Page query parameter data structure
 */
@ApiModel(description = "Page query parameter data structure")
@Data
public class PageQueryParam implements QueryParam {

    @ApiModelProperty(value = "Current offset", notes = "Default value:0", example = "0")
    private Integer offset = 0;

    @ApiModelProperty(value = "Current page size", notes = "Default value:10", example = "10")
    private Integer limit = 10;

    @ApiModelProperty(value = "Fields for Asc")
    private String[] ascs = {};

    @ApiModelProperty(value = "Fields for Desc")
    private String[] descs = {};

    @ApiModelProperty(value = "Query total count, Default value is true", example = "true")
    private boolean searchCount = true;

    public boolean hasAscOrDesc() {
        return !hasNoAscOrDesc();
    }

    /**
     * Whether has failed to sort.
     */
    public boolean hasNoAscOrDesc() {
        return this.ascs.length == 0 && this.descs.length == 0;
    }

    /**
     * Add fields for Order By.
     */
    public String[] extendsOrderBy(String[] origin, String... orderBy) {
        List<String> orderByList = Optional.ofNullable(origin)
                .map(Lists::newArrayList)
                .orElse(Lists.newArrayList());

        if (orderBy != null && orderBy.length > 0) {
            orderByList.addAll(Arrays.asList(orderBy));
        }

        return orderByList.toArray(new String[0]);
    }

    /**
     * Add asc Order By fields.
     */
    public String[] extendsAscs(String... orderBy) {
        ascs = extendsOrderBy(ascs, orderBy);
        return ascs;
    }

    /**
     * Add desc Order By fields.
     */
    public String[] extendsDescs(String... orderBy) {
        descs = extendsOrderBy(descs, orderBy);
        return descs;
    }
}
