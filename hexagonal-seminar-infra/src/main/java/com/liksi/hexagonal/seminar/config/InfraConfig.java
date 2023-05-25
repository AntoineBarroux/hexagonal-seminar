package com.liksi.hexagonal.seminar.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = "com.liksi.hexagonal.seminar")
public class InfraConfig {
}
