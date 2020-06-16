package com.larkea.boot.swagger;

import java.util.Optional;

import javax.servlet.ServletContext;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicate;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import springfox.documentation.PathProvider;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

/**
 * Swagger
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import({SwaggerProperties.class})
@ConditionalOnClass({Docket.class, Swagger2DocumentationConfiguration.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(value = "larkea.boot.swagger.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class SwaggerAutoConfiguration {

    private final Environment environment;

    private ServletContext servletContext;

    @Bean
    public Docket docket(SwaggerProperties properties) {
        String appName = environment.getProperty("spring.application.name");
        Predicate<RequestHandler> selector;

        if (properties.isAutoScan()) {
            selector = RequestHandlerSelectors.withClassAnnotation(Api.class);
        } else {
            Assert.notNull(properties.getBasePackage(), "Error: larkea.boot.swagger.basePackage is null");
            selector = RequestHandlerSelectors.basePackage(properties.getBasePackage());
        }

        PathProvider pathProvider = new RelativePathProvider(servletContext) {
            @Override
            public String getApplicationBasePath() {
                return properties.getBasePath();
            }
        };

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(properties.getGroupName())
                .host(properties.getHost())
                .pathProvider(pathProvider)
                .apiInfo(apiInfo(appName, properties))
                .select()
                .apis(selector)
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(String appName, SwaggerProperties properties) {
        String title = Optional.ofNullable(properties.getTitle())
                .orElse(appName);
        String description = Optional.ofNullable(properties.getDescription())
                .orElse(appName);
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(properties.getVersion())
                .contact(new Contact(properties.getContactName(), properties.getContactUrl(),
                        properties.getContactEmail()))
                .build();
    }

}
