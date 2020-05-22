package com.larkea.boot.core.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * A json util based on jackson.
 */
@Slf4j
@UtilityClass
public class JsonUtil {

	private static ObjectMapper mapper = new ObjectMapper();

	static {
		SimpleModule module = new SimpleModule(Version.unknownVersion());
		module.addSerializer(LocalDateTime.class,
				new LocalDateTimeSerializer(DateUtil.DATETIME_FORMATTER));
		module.addSerializer(LocalDate.class, new LocalDateSerializer(DateUtil.DATE_FORMATTER));
		module.addSerializer(LocalTime.class, new LocalTimeSerializer(DateUtil.TIME_FORMATTER));
		module.addDeserializer(LocalDateTime.class,
				new LocalDateTimeDeserializer(DateUtil.DATETIME_FORMATTER));
		module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateUtil.DATE_FORMATTER));
		module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateUtil.TIME_FORMATTER));

		// Date 序列化和反序列化
		module.addSerializer(Date.class, new JsonSerializer<Date>() {
			@Override
			public void serialize(Date date, JsonGenerator jsonGenerator,
					SerializerProvider serializerProvider) throws IOException {
				SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.PATTERN_DATETIME);
				String formattedDate = formatter.format(date);
				jsonGenerator.writeString(formattedDate);
			}
		});
		module.addDeserializer(Date.class, new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonParser jsonParser,
					DeserializationContext deserializationContext)
					throws IOException, JsonProcessingException {
				SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_DATETIME);
				String date = jsonParser.getText();
				try {
					return format.parse(date);
				}
				catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		});
		mapper.registerModule(module)
				.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
				.setDateFormat(new SimpleDateFormat(DateUtil.PATTERN_DATETIME))
				.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.setSerializationInclusion(JsonInclude.Include.NON_NULL)
				.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
	}

	public static String toJson(Object value) {
		try {
			return mapper.writeValueAsString(value);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 将一个 object 转成键值字典.
	 */
	public static Map<String, String> toMap(Object value) {
		return mapper.convertValue(value, new TypeReference<Map<String, String>>() {
		});
	}

	/**
	 * 讲一个json字符串转成键值字典
	 *
	 * @param json 字符串
	 * @return 字典Map
	 */
	public static Map<String, Object> toMap(String json) {
		TypeReference<Map<String, Object>> typeRef
				= new TypeReference<Map<String, Object>>() {
		};

		try {
			return mapper.readValue(json, typeRef);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 将一个 object 转成键值数组, 一对键值放在相邻的位置. [name1, value1, name2, value2, ..., nameN, valueN]
	 */
	public static List<String> toNamesAndValues(Object value) {
		Map<String, String> map = toMap(value);
		List<String> namesAndValues = new ArrayList<>();
		map.forEach((k, v) -> {
			namesAndValues.add(k);
			namesAndValues.add(v);
		});

		return namesAndValues;
	}

	/**
	 * 处理指定 Class<T> 的 JSON 结构.
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 处理任意 JSON 结构.
	 */
	public static <T> T fromJson(String json, TypeReference<T> typeReference) {
		try {
			return (T) mapper.readValue(json, typeReference);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Create a new ObjectMapper.
	 *
	 * @return a new ObjectMapper object
	 */
	public static ObjectMapper copy() {
		return mapper.copy();
	}
}