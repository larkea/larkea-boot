package com.larkea.boot.core.data;

import java.util.List;

import com.google.common.collect.Lists;
import com.larkea.boot.core.util.ArrayUtil;
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
	private List<TableAliasField> ascFields;

	@ApiModelProperty(value = "Fields for Desc")
	private List<TableAliasField> descFields;

	@ApiModelProperty(value = "Query total count, Default value is true", example = "true")
	private boolean searchCount = true;

	public TableAliasPageQueryParam() {}

	public TableAliasPageQueryParam(PageQueryParam pageQueryParam) {
		this.offset = pageQueryParam.getOffset();
		this.limit = pageQueryParam.getLimit();
		this.searchCount = pageQueryParam.isSearchCount();

		if (!ArrayUtil.isEmpty(pageQueryParam.getAscs())) {
			List<TableAliasField> ascFields = Lists.newArrayList();
			for (String field: pageQueryParam.getAscs()) {
				ascFields.add(new TableAliasField(field));
			}
			this.ascFields = ascFields;
		}

		if (!ArrayUtil.isEmpty(pageQueryParam.getDescs())) {
			List<TableAliasField> descFields = Lists.newArrayList();
			for (String field: pageQueryParam.getDescs()) {
				descFields.add(new TableAliasField(field));
			}
			this.descFields = descFields;
		}
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

}
