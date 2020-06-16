package com.larkea.boot.mybatis;

import com.larkea.boot.core.data.PageQueryParam;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.experimental.UtilityClass;

public class PageUtil {

	private static final int DEFAULT_OFFSET = 0;

	private static final int DEFAULT_LIMIT = 10;

    private PageUtil() {
    }

    /**
     * Convert page query parameter to MyBatis Plus Page object
	 */
	public static <T> IPage<T> getPage(PageQueryParam param) {
		int offset = (null == param.getOffset() || param.getOffset() < 0 ? DEFAULT_OFFSET
				: param.getOffset());
		int limit = (null == param.getLimit() || param.getLimit() < 0 ? DEFAULT_LIMIT
				: param.getLimit());
		int current = offset / limit + 1; // Current Page
		Page<T> page = new Page<>(current, limit);
		page.setSearchCount(param.isSearchCount());
		if (null != param.getAscs()) {
			final int length = param.getAscs().length;
			if (length == 1) {
				page.addOrder(OrderItem.asc(param.getAscs()[0]));
			}
			else if (length > 1) {
				page.addOrder(OrderItem.ascs(param.getAscs()));
			}
		}
		if (null != param.getDescs()) {
			final int length = param.getDescs().length;
			if (length == 1) {
				page.addOrder(OrderItem.desc(param.getDescs()[0]));
			}
			else if (length > 1) {
				page.addOrder(OrderItem.descs(param.getDescs()));
			}
		}
		return page;
	}
}
