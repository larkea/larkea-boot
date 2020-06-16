package com.larkea.boot.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("larkea.boot.swagger")
public class SwaggerProperties {

    private boolean enabled = true;

    private boolean autoScan = true;

    private String basePackage;

    private String title;

    private String description;

    private String version = "1.0.0";

    private String contactName;

    private String contactUrl;

    private String contactEmail;

    private String groupName;

    private String host;

    private String basePath;

}



