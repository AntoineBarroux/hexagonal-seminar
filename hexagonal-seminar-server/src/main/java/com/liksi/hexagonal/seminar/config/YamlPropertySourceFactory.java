package com.liksi.hexagonal.seminar.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Objects;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(final String name, final EncodedResource resource) {
        final var factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource.getResource());
        final var properties = factory.getObject();
        return new PropertiesPropertySource(
                Objects.requireNonNull(resource.getResource().getFilename()),
                Objects.requireNonNull(properties)
        );
    }
}
