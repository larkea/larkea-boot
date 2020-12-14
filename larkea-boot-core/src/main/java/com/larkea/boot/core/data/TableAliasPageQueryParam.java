package com.larkea.boot.core.data;

import java.util.List;

import com.google.common.collect.Lists;
import com.larkea.boot.core.util.CollectionUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Page query parameter data structure
 */
@ApiModel(description = "Page query parameter data structure")
@Data
public class TableAliasPageQueryParam implements QueryParam {

	@ApiModelProperty(value = "Current offset", notes = "Default value:0", example = "0")
	private Integer offset = 0;

	@ApiModelProperty(value = "Current page size", notes = "Default value:10", example = "10")
	private Integer limit = 10;

	@ApiModelProperty(value = "Fields for Asc")
	private List<TableAliasField> ascFields = Lists.newArrayList();

	@ApiModelProperty(value = "Fields for Desc")
	private List<TableAliasField> descFields = Lists.newArrayList();

	@ApiModelProperty(value = "Query total count, Default value is true", example = "true")
	private boolean searchCount = true;

	public TableAliasPageQueryParam() {
	}

	public TableAliasPageQueryParam(Integer offset, Integer limit, boolean searchCount) {
		this.offset = offset;
		this.limit = limit;
		this.searchCount = searchCount;
	}

	public boolean hasAscOrDesc() {
		return !hasNoAscOrDesc();
	}

	/**
	 * Whether has failed to sort.
	 */
	public boolean hasNoAscOrDesc() {
		return CollectionUtil.isEmpty(ascFields) && CollectionUtil.isEmpty(descFields);
	}

	public void addAsc(TableAliasField field) {
		this.ascFields.add(field);
	}

	public void addDesc(TableAliasField field) {
		this.descFields.add(field);
	}

}
