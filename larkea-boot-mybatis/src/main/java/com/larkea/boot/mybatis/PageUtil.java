package com.larkea.boot.mybatis;

import java.util.List;
import java.util.regex.Pattern;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.larkea.boot.core.data.PageQueryParam;
import com.larkea.boot.core.data.TableAliasField;
import com.larkea.boot.core.data.TableAliasPageQueryParam;
import com.larkea.boot.core.exception.SystemException;
import com.larkea.boot.core.result.SystemResultCode;
import com.larkea.boot.core.util.CollectionUtil;
import com.larkea.boot.core.util.StringPool;
import com.larkea.boot.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageUtil {

	private static final int DEFAULT_OFFSET = 0;

	private static final int DEFAULT_LIMIT = 10;

	private static final Pattern PATTERN = Pattern.compile("^[A-Za-z0-9_]+$");

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
			String[] ascs = convertOrderByColumns(param.getAscs());
			final int length = ascs.length;
			if (length == 1) {
				page.addOrder(OrderItem.asc(ascs[0]));
			}
			else if (length > 1) {
				page.addOrder(OrderItem.ascs(ascs));
			}
		}
		if (null != param.getDescs()) {
			String[] descs = convertOrderByColumns(param.getDescs());
			final int length = descs.length;
			if (length == 1) {
				page.addOrder(OrderItem.desc(descs[0]));
			}
			else if (length > 1) {
				page.addOrder(OrderItem.descs(descs));
			}
		}
		return page;
	}

	/**
	 * Convert page query parameter to MyBatis Plus Page object
	 */
	public static <T> IPage<T> getPage(TableAliasPageQueryParam param) {
		int offset = (null == param.getOffset() || param.getOffset() < 0 ? DEFAULT_OFFSET
				: param.getOffset());
		int limit = (null == param.getLimit() || param.getLimit() < 0 ? DEFAULT_LIMIT
				: param.getLimit());
		int current = offset / limit + 1; // Current Page
		Page<T> page = new Page<>(current, limit);
		page.setSearchCount(param.isSearchCount());
		if (!CollectionUtil.isEmpty(param.getAscFields())) {
			String[] ascs = convertOrderByColumns(param.getAscFields());
			final int length = ascs.length;
			if (length == 1) {
				page.addOrder(OrderItem.asc(ascs[0]));
			}
			else if (length > 1) {
				page.addOrder(OrderItem.ascs(ascs));
			}
		}
		if (!CollectionUtil.isEmpty(param.getDescFields())) {
			String[] descs = convertOrderByColumns(param.getDescFields());
			final int length = descs.length;
			if (length == 1) {
				page.addOrder(OrderItem.desc(descs[0]));
			}
			else if (length > 1) {
				page.addOrder(OrderItem.descs(descs));
			}
		}
		return page;
	}

	public static String[] convertOrderByColumns(String[] columns) {
		if (columns == null) {
			return new String[] {};
		}

		List<String> columnList = Lists.newArrayList();
		for (String column : columns) {
			if (!PATTERN.matcher(column).matches()) {
				LOGGER.warn("Sql injection in order by found:{}", column);
				throw new SystemException(SystemResultCode.DATA_QUERY_FAILED);
			}

			columnList.add(String.format("`%s`", camelToSnake(column)));
		}

		return columnList.toArray(new String[] {});
	}

	public static String[] convertOrderByColumns(List<TableAliasField> fields) {
		if (CollectionUtil.isEmpty(fields)) {
			return new String[] {};
		}

		List<String> columnList = Lists.newArrayList();
		for (TableAliasField field : fields) {
			String column = field.getField();
			if (!PATTERN.matcher(column).matches()) {
				LOGGER.warn("Sql injection in order by found:{}", column);
				throw new SystemException(SystemResultCode.DATA_QUERY_FAILED);
			}

			String tableAlias = field.getTableAlias();
			if (StringUtil.isBlank(tableAlias)) {
				columnList.add(String.format("`%s`", camelToSnake(column)));
			}
			else {
				columnList.add(String.format("%s.`%s`", tableAlias, camelToSnake(column)));
			}
		}

		return columnList.toArray(new String[] {});
	}

	public static String camelToSnake(String fieldName) {
		StringBuilder stringBuilder = new StringBuilder();
		char[] chars = fieldName.toCharArray();
		for (char charactor : chars) {
			if (Character.isUpperCase(charactor)) {
				stringBuilder.append(StringPool.UNDERSCORE);
				charactor = Character.toLowerCase(charactor);
			}
			stringBuilder.append(charactor);
		}
		return stringBuilder.toString();
	}
}
