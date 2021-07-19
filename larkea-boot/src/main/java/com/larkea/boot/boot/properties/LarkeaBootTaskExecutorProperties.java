package com.larkea.boot.boot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Async Settings
 */
@Getter
@Setter
@ConfigurationProperties("larkea.boot.task-executor")
public class LarkeaBootTaskExecutorProperties {

	private int corePoolSize = 4;

	private int maxPoolSize = 100;

	private int queueCapacity = 20000;

	private int keepAliveSeconds = 300;
}
