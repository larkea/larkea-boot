package com.larkea.boot.mybatis.generator;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisGeneratorAutoConfiguration {

	private final static String PREFIX = "larkea.boot.mybatis.generator.";

	@Bean
	@ConfigurationProperties(prefix = PREFIX + "global")
	GlobalConfig globalConfig() {
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setOpen(false);
		globalConfig.setEntityName("%sEntity");
		globalConfig.setServiceName("%sDao");
		globalConfig.setServiceImplName("%sDaoImpl");
		globalConfig.setMapperName("%sMapper");
		globalConfig.setXmlName("%sMapper");
		globalConfig.setBaseResultMap(true);
		globalConfig.setBaseColumnList(true);
		globalConfig.setFileOverride(true); // 覆盖
		return globalConfig;
	}

	@Bean
	@ConfigurationProperties(prefix = PREFIX + "data-source", ignoreInvalidFields = true)
	DataSourceConfig dataSourceConfig() {
		return new DataSourceConfig();
	}

	@Bean
	@ConfigurationProperties(prefix = PREFIX + "package")
	PackageConfig packageConfig() {
		PackageConfig packageConfig = new PackageConfig();
		//packageConfig.setParent("com.huitongio.delos");
		packageConfig.setEntity("dao.entity");
		packageConfig.setService("dao");
		packageConfig.setServiceImpl("dao.impl");
		packageConfig.setMapper("dao.mapper");
		packageConfig.setXml("dao.mapper");
		packageConfig.setController("web.controller");
		return packageConfig;
	}

	@Bean
	InjectionConfig injectionConfig(GlobalConfig globalConfig, PackageConfig packageConfig) {
		String outputDirPrefix = String
				.format("%s/%s", globalConfig.getOutputDir(), packageConfig.getParent().replace('.', '/'));
		InjectionConfig injectionConfig = new InjectionConfig() {
			@Override
			public void initMap() {

			}
		};
		List<FileOutConfig> fileOutConfigList = new ArrayList<>();
		fileOutConfigList.add(new FileOutConfig("/templates/data.java.ftl") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				String fileName = tableInfo.getEntityName().replaceAll("Entity$", "");
				return outputDirPrefix + "/data/"
						+ fileName + StringPool.DOT_JAVA;
			}
		});
		fileOutConfigList.add(new FileOutConfig("/templates/dataResult.java.ftl") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				String fileName = tableInfo.getEntityName().replaceAll("Entity$", "");
				return outputDirPrefix + "/data/"
						+ fileName + "Result" + StringPool.DOT_JAVA;
			}
		});
		fileOutConfigList.add(new FileOutConfig("/templates/dataParam.java.ftl") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				String fileName = tableInfo.getEntityName().replaceAll("Entity$", "");
				return outputDirPrefix + "/data/"
						+ fileName + "Param" + StringPool.DOT_JAVA;
			}
		});
		fileOutConfigList.add(new FileOutConfig("/templates/service.java.ftl") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				String fileName = tableInfo.getEntityName().replaceAll("Entity$", "");
				return outputDirPrefix + "/service/"
						+ String.format("%sService", fileName) + StringPool.DOT_JAVA;
			}
		});
		fileOutConfigList.add(new FileOutConfig("/templates/serviceImpl.java.ftl") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				String fileName = tableInfo.getEntityName().replaceAll("Entity$", "");
				return outputDirPrefix + "/service/impl/"
						+ String.format("%sServiceImpl", fileName) + StringPool.DOT_JAVA;
			}
		});
		injectionConfig.setFileOutConfigList(fileOutConfigList);
		return injectionConfig;
	}

	@Bean
	@ConfigurationProperties(prefix = PREFIX + "template")
	TemplateConfig templateConfig() {
		return new TemplateConfig()
				.setService("templates/dao.java")
				.setServiceImpl("templates/daoImpl.java");
	}

	@Bean
	@ConfigurationProperties(prefix = PREFIX + "strategy")
	StrategyConfig strategyConfig() {
		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig.setRestControllerStyle(true);
		strategyConfig.setNaming(NamingStrategy.underline_to_camel);
		strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
		strategyConfig.setSuperEntityClass("com.larkea.boot.mybatis.BaseEntity");
		strategyConfig.setEntityLombokModel(true);
		strategyConfig.setSuperControllerClass("com.larkea.boot.boot.controller.Controller");
		strategyConfig.setSuperServiceClass("com.larkea.boot.mybatis.Dao");
		strategyConfig.setSuperServiceImplClass("com.larkea.boot.mybatis.DaoImpl");
		strategyConfig.setLogicDeleteFieldName("gmt_deleted");
		strategyConfig.setSuperEntityColumns("gmt_created", "gmt_updated");
		strategyConfig.setControllerMappingHyphenStyle(true);
		strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true);
		return strategyConfig;
	}

	@Bean
	MybatisGeneratorTemplateEngine templateEngine(PackageConfig packageConfig) {
		return new MybatisGeneratorTemplateEngine(packageConfig);
	}

	@Bean
	AutoGenerator autoGenerator(GlobalConfig globalConfig,
			DataSourceConfig dataSourceConfig, PackageConfig packageConfig,
			InjectionConfig injectionConfig,
			StrategyConfig strategyConfig, TemplateConfig templateConfig,
			MybatisGeneratorTemplateEngine templateEngine) {
		AutoGenerator generator = new AutoGenerator();
		generator.setGlobalConfig(globalConfig);
		generator.setDataSource(dataSourceConfig);
		generator.setPackageInfo(packageConfig);
		generator.setCfg(injectionConfig);
		generator.setStrategy(strategyConfig);
		generator.setTemplate(templateConfig);
		generator.setTemplateEngine(templateEngine);
		return generator;
	}

}
