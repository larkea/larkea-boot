package com.larkea.boot.boot.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext context;

	public static <T> T getBean(Class<T> clazz) {
		if (context == null) {
			return null;
		}
		return context.getBean(clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		if (context == null) {
			return null;
		}
		return (T) context.getBean(beanName);
	}

	public static <T> T getBean(String beanName, Class<T> clazz) {
		if (context == null) {
			return null;
		}
		return context.getBean(beanName, clazz);
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static void publishEvent(ApplicationEvent event) {
		if (context == null) {
			return;
		}
		context.publishEvent(event);
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringContextHolder.context = context;
	}

}
