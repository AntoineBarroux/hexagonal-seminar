package com.liksi.hexagonal.seminar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.liksi.hexagonal.seminar.adapters")
@PropertySource(value = "classpath:infra.yml", factory = YamlPropertySourceFactory.class)
public class JpaConfig {
}
