package com.larkea.boot.mybatis;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis Plus 自动化配置
 */
@Configuration(proxyBeanMethods = false)
@Import({ MybatisMetaObjectHandler.class, MybatisProperties.class })
@EnableTransactionManagement
public class MybatisAutoConfiguration {

	/**
	 * 分页插件
	 */
	@Bean
	@ConditionalOnMissingBean({ PaginationInterceptor.class })
	public PaginationInterceptor paginationInterceptor() {
		// 开启 count 的 join 优化,只针对 left join !!!
		return new PaginationInterceptor().setCountSqlParser(new JsqlParserCountOptimize(true));
	}

	/**
	 * 防止全表更新/删除
	 */
	@Bean
	@ConditionalOnMissingBean({ SqlExplainInterceptor.class })
	public SqlExplainInterceptor sqlExplainInterceptor() {
		SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
		List<ISqlParser> sqlParserList = new ArrayList<>(1);
		sqlParserList.add(new BlockAttackSqlParser());
		sqlExplainInterceptor.setSqlParserList(sqlParserList);
		return sqlExplainInterceptor;
	}

	/**
	 * 乐观锁
	 */
	@Bean
	@ConditionalOnMissingBean({ OptimisticLockerInterceptor.class })
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInterceptor();
	}
}
