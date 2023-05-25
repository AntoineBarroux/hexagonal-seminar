package com.liksi.hexagonal.seminar.jpa.config;

import com.liksi.hexagonal.seminar.config.YamlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.liksi.hexagonal.seminar.jpa.adapters")
@PropertySource(value = "classpath:infra.yml", factory = YamlPropertySourceFactory.class)
public class JpaConfig {
}
