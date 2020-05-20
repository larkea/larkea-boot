package com.larkea.boot.core.data;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页结构.
 *
 * @param <T> 分页数组中的数据项类型
 */
@Data
@ApiModel(description = "分页")
public class Page<T> implements BaseData {

	// 总记录数　
	@ApiModelProperty(value = "总记录数", required = true)
	private Long total;

	// 当前内容
	@ApiModelProperty(value = "当前记录", required = true)
	private List<T> rows;

	public Page() {
		this(0L, Lists.newArrayList());
	}

	public Page(Long total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}

	public static <T> Page craft(Long total, List<T> rows) {
		return new Page<>(total, rows);
	}

	public static <T> Page<T> empty() {
		return new Page<>();
	}

	public <R> Page<R> map(Function<? super T, ? extends R> mapFunction) {
		return new Page<>(this.total,
				this.rows.stream().map(mapFunction).collect(Collectors.toList()));
	}
}
