package com.larkea.boot.boot.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import com.larkea.boot.boot.properties.LarkeaBootTaskExecutorProperties;
import lombok.AllArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
@Import({ LarkeaBootTaskExecutorProperties.class })
@AllArgsConstructor
public class LarkeaBootTaskExecutorAutoConfiguration extends AsyncConfigurerSupport {

	private final LarkeaBootTaskExecutorProperties properties;

	@Override
	@Bean(name = "larkeaBootTaskExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(properties.getCorePoolSize());
		executor.setMaxPoolSize(properties.getMaxPoolSize());
		executor.setQueueCapacity(properties.getQueueCapacity());
		executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
		executor.setThreadNamePrefix("larkea-boot-task-executor-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

}
