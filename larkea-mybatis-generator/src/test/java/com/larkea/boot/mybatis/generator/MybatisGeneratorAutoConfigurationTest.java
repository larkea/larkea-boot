package com.larkea.boot.mybatis.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@EnableConfigurationProperties
@SpringBootTest(classes = { MybatisGeneratorAutoConfiguration.class })
class MybatisGeneratorAutoConfigurationTest {
	@Autowired
	private AutoGenerator autoGenerator;

	@Test
	void autoGenerator() {
		autoGenerator.execute();
	}
}
