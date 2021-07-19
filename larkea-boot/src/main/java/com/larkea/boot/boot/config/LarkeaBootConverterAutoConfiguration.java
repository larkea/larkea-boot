package com.larkea.boot.boot.config;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.larkea.boot.core.util.DateUtil;
import com.larkea.boot.core.util.StringUtil;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Default converter for converting date string to date types in RequestParam and PathVariable.
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class LarkeaBootConverterAutoConfiguration {

	/**
	 * LocalDate from string
	 */
	@Bean
	public Converter<String, LocalDate> localDateConverter() {
		return new Converter<String, LocalDate>() {
			@Override
			public LocalDate convert(String source) {
				if (StringUtil.isBlank(source)) {
					return null;
				}
				return LocalDate.parse(source, DateTimeFormatter.ofPattern(DateUtil.PATTERN_DATE));
			}
		};
	}

	/**
	 * LocalDateTime from string
	 */
	@Bean
	public Converter<String, LocalDateTime> localDateTimeConverter() {
		return new Converter<String, LocalDateTime>() {
			@Override
			public LocalDateTime convert(String source) {
				if (StringUtil.isBlank(source)) {
					return null;
				}
				return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DateUtil.PATTERN_DATETIME));
			}
		};
	}

	/**
	 * LocalTime from string
	 */
	@Bean
	public Converter<String, LocalTime> localTimeConverter() {
		return new Converter<String, LocalTime>() {
			@Override
			public LocalTime convert(String source) {
				if (StringUtil.isBlank(source)) {
					return null;
				}
				return LocalTime.parse(source, DateTimeFormatter.ofPattern(DateUtil.PATTERN_TIME));
			}
		};
	}

	/**
	 * Date from string
	 */
	@Bean
	public Converter<String, Date> dateConverter() {
		return new Converter<String, Date>() {
			@Override
			public Date convert(String source) {
				if (StringUtil.isBlank(source)) {
					return null;
				}
				SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_DATETIME);
				try {
					return format.parse(source);
				}
				catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer Jackson2ObjectMapperBuilderCustomizer() {
		return new Jackson2ObjectMapperBuilderCustomizer() {
			@Override
			public void customize(
					Jackson2ObjectMapperBuilder builder) {
				builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
				builder.simpleDateFormat(DateUtil.PATTERN_DATETIME);
				builder.serializationInclusion(JsonInclude.Include.NON_NULL);
				builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
				builder.featuresToDisable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

				// Config jackson how to serialize and deserialize LocalDateTime/LocalDate/LocalTime
				JavaTimeModule javaTimeModule = new JavaTimeModule();
				javaTimeModule.addSerializer(LocalDateTime.class,
						new LocalDateTimeSerializer(DateUtil.DATETIME_FORMATTER));
				javaTimeModule
						.addSerializer(LocalDate.class, new LocalDateSerializer(DateUtil.DATE_FORMATTER));
				javaTimeModule
						.addSerializer(LocalTime.class, new LocalTimeSerializer(DateUtil.TIME_FORMATTER));
				javaTimeModule.addDeserializer(LocalDateTime.class,
						new LocalDateTimeDeserializer(DateUtil.DATETIME_FORMATTER));
				javaTimeModule
						.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateUtil.DATE_FORMATTER));
				javaTimeModule
						.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateUtil.TIME_FORMATTER));

				// Config jackson how to serialize and deserialize Date
				javaTimeModule.addSerializer(Date.class, new JsonSerializer<Date>() {
					@Override
					public void serialize(Date date, JsonGenerator jsonGenerator,
							SerializerProvider serializerProvider) throws IOException {
						SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.PATTERN_DATETIME);
						String formattedDate = formatter.format(date);
						jsonGenerator.writeString(formattedDate);
					}
				});

				javaTimeModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {
					@Override
					public Date deserialize(JsonParser jsonParser,
							DeserializationContext deserializationContext)
							throws IOException, JsonProcessingException {
						SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_DATETIME);
						String date = jsonParser.getText();

						if (StringUtil.isBlank(date)) {
							return null;
						}

						try {
							return format.parse(date);
						}
						catch (ParseException e) {
							throw new RuntimeException(e);
						}
					}
				});
				builder.modules(javaTimeModule);
			}
		};
	}

}
