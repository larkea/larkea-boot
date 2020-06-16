package com.larkea.boot.boot.config;

import com.larkea.boot.boot.context.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LarkeaBootContextAutoConfiguration {

    @Bean
    SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}
