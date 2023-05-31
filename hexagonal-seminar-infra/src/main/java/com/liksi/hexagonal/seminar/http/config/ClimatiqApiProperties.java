package com.liksi.hexagonal.seminar.http.config;

import com.liksi.hexagonal.seminar.config.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "climatiq.api")
@EnableConfigurationProperties
@PropertySource(value = "classpath:http.yml", factory = YamlPropertySourceFactory.class)
public record ClimatiqApiProperties(
        String url,
        String key
){ }

