package com.larkea.boot.mybatis;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.larkea.boot.core.data.EnumData;
import com.larkea.boot.core.data.EnumValue;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * Custom Enum Type Handler for larkea-core
 * <p>
 * Copied from mybatis-plus to use the code in larkea-core without adding MybatisPlus-Core dependency.
 * @author wangle
 * @since 2019-12-26
 */
public class MybatisEnumTypeHandler<E extends Enum<?>> extends BaseTypeHandler<Enum<?>> {

	private static final Map<String, String> TABLE_METHOD_OF_ENUM_TYPES = new ConcurrentHashMap<>();

	private final static ReflectorFactory reflectorFactory = new DefaultReflectorFactory();

	private final Class<E> type;

	private final Invoker invoker;

	public MybatisEnumTypeHandler(Class<E> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type = type;
		MetaClass metaClass = MetaClass.forClass(type, reflectorFactory);
		String name = "value";
		if (!EnumData.class.isAssignableFrom(type)) {
			name = TABLE_METHOD_OF_ENUM_TYPES.computeIfAbsent(type.getName(), k -> {
				Field field = dealEnumType(this.type).orElseThrow(() -> new IllegalArgumentException(
						String.format("Could not find @EnumValue in Class: %s.", type.getName())));
				return field.getName();
			});
		}
		this.invoker = metaClass.getGetInvoker(name);
	}

	public static Optional<Field> dealEnumType(Class<?> clazz) {
		return clazz.isEnum() ? Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(
						EnumValue.class)).findFirst() : Optional.empty();
	}

	@SuppressWarnings("Duplicates")
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Enum<?> parameter, JdbcType jdbcType)
			throws SQLException {
		if (jdbcType == null) {
			ps.setObject(i, this.getValue(parameter));
		}
		else {
			// see r3589
			ps.setObject(i, this.getValue(parameter), jdbcType.TYPE_CODE);
		}
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		if (null == rs.getObject(columnName) && rs.wasNull()) {
			return null;
		}
		return this.valueOf(this.type, rs.getObject(columnName));
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		if (null == rs.getObject(columnIndex) && rs.wasNull()) {
			return null;
		}
		return this.valueOf(this.type, rs.getObject(columnIndex));
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		if (null == cs.getObject(columnIndex) && cs.wasNull()) {
			return null;
		}
		return this.valueOf(this.type, cs.getObject(columnIndex));
	}

	private E valueOf(Class<E> enumClass, Object value) {
		E[] es = enumClass.getEnumConstants();
		return Arrays.stream(es).filter((e) -> equalsValue(value, getValue(e))).findAny().orElse(null);
	}

	protected boolean equalsValue(Object sourceValue, Object targetValue) {
		if (sourceValue instanceof Number && targetValue instanceof Number
				&& new BigDecimal(String.valueOf(sourceValue))
				.compareTo(new BigDecimal(String.valueOf(targetValue))) == 0) {
			return true;
		}
		return Objects.equals(sourceValue, targetValue);
	}

	private Object getValue(Object object) {
		try {
			return invoker.invoke(object, new Object[0]);
		}
		catch (ReflectiveOperationException e) {
			throw ExceptionUtils.mpe(e);
		}
	}
}
