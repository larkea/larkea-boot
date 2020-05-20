package com.larkea.boot.core.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询参数
 */
@ApiModel(description = "分页查询参数")
@Data
public class PageQueryParam implements QueryParam {

	private static final long serialVersionUID = 5712709049320944991L;

	/**
	 * 当前页
	 */
	@ApiModelProperty(value = "当前记录数", notes = "默认值:0", example = "0")
	private Integer offset = 0;

	/**
	 * 每页多少条数据
	 */
	@ApiModelProperty(value = "每页多少条数据", notes = "默认值:10", example = "10")
	private Integer limit = 10;

	/**
	 * 升序字段
	 */
	@ApiModelProperty(value = "升序字段")
	private String[] ascs = {};

	/**
	 * 降序字段
	 */
	@ApiModelProperty(value = "降序字段")
	private String[] descs = {};

	/**
	 * 是否查询总数
	 */
	@ApiModelProperty(value = "是否查询总数, 默认是", example = "1")
	private boolean searchCount = true;

	/**
	 * 是否有排序的字段.
	 */
	public boolean hasAscOrDesc() {
		return !hasNoAscOrDesc();
	}

	/**
	 * 是否没有排序的字段.
	 */
	public boolean hasNoAscOrDesc() {
		return this.ascs.length == 0 && this.descs.length == 0;
	}

	/**
	 * 添加 Order By 字段.
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
	 * 添加升序 Order By 字段.
	 */
	public String[] extendsAscs(String... orderBy) {
		ascs = extendsOrderBy(ascs, orderBy);
		return ascs;
	}

	/**
	 * 添加降序 Order By 字段.
	 */
	public String[] extendsDescs(String... orderBy) {
		descs = extendsOrderBy(descs, orderBy);
		return descs;
	}
}
