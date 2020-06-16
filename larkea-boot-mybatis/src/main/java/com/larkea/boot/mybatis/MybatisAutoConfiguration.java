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
 * Mybatis Plus Auto Configuration
 */
@Configuration(proxyBeanMethods = false)
@Import({MybatisMetaObjectHandler.class, MybatisProperties.class})
@EnableTransactionManagement
public class MybatisAutoConfiguration {

    /**
     * Enable pagination plugin
     */
    @Bean
    @ConditionalOnMissingBean({PaginationInterceptor.class})
    public PaginationInterceptor paginationInterceptor() {
        // Enable optimize for count when using left join
        return new PaginationInterceptor().setCountSqlParser(new JsqlParserCountOptimize(true));
    }

    /**
     * Prevent full-table update or delete
     */
    @Bean
    @ConditionalOnMissingBean({SqlExplainInterceptor.class})
    public SqlExplainInterceptor sqlExplainInterceptor() {
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>(1);
        sqlParserList.add(new BlockAttackSqlParser());
        sqlExplainInterceptor.setSqlParserList(sqlParserList);
        return sqlExplainInterceptor;
    }

    /**
     * Enable optimistic lock
     */
    @Bean
    @ConditionalOnMissingBean({OptimisticLockerInterceptor.class})
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
