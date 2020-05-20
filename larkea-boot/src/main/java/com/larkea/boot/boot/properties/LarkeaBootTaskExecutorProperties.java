package com.larkea.boot.boot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 异步配置
 */
@Getter
@Setter
@ConfigurationProperties("larkea.boot.task-executor")
public class LarkeaBootTaskExecutorProperties {

	/**
	 * 异步核心线程数，默认：4
	 */
	private int corePoolSize = 4;

	/**
	 * 异步最大线程数，默认：100
	 */
	private int maxPoolSize = 100;

	/**
	 * 队列容量，默认：10000
	 */
	private int queueCapacity = 20000;

	/**
	 * 线程存活时间，默认：300
	 */
	private int keepAliveSeconds = 300;
}
