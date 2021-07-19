package com.larkea.boot.mybatis;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.larkea.boot.core.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "larkea.boot.mybatis")
public class MybatisProperties implements MybatisPlusPropertiesCustomizer {

	private Class<? extends TypeHandler> defaultEnumTypeHandler = MybatisEnumTypeHandler.class;

	private Boolean banner = false;

	private String logicDeleteValue = "NOW()";

	private String logicNotDeleteValue = "NULL";

	@Override
	public void customize(MybatisPlusProperties properties) {
		if (defaultEnumTypeHandler != null) {
			if (properties.getConfiguration() == null) {
				MybatisConfiguration configuration = new MybatisConfiguration();
				properties.setConfiguration(configuration);
			}
			properties.getConfiguration().setDefaultEnumTypeHandler(defaultEnumTypeHandler);
		}

		if (banner != null) {
			properties.getGlobalConfig().setBanner(banner);
		}

		if (StringUtil.isNotBlank(logicDeleteValue)) {
			properties.getGlobalConfig().getDbConfig().setLogicDeleteValue(logicDeleteValue);
		}

		if (StringUtil.isNotBlank(logicNotDeleteValue)) {
			properties.getGlobalConfig().getDbConfig().setLogicNotDeleteValue(logicNotDeleteValue);
		}
	}
}
