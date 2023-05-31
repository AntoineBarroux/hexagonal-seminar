package com.liksi.hexagonal.seminar.http.config;

import com.liksi.hexagonal.seminar.http.adapters.ClimatiqHttpClient;
import com.liksi.hexagonal.seminar.http.exception.AirportNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClimatiqApiConfig {

    @Bean
    WebClient climatiqWebClient(ClimatiqApiProperties climatiqApiProperties) {
        return WebClient.builder()
                .baseUrl(climatiqApiProperties.url())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + climatiqApiProperties.key())
                .defaultStatusHandler(HttpStatusCode::isError, response -> {
                    throw new AirportNotFoundException("Departure or arrival airport not found");
                })
                .build();
    }

    @Bean
    ClimatiqHttpClient climatiqHttpClient(WebClient climatiqWebClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(climatiqWebClient))
                        .build();
        return httpServiceProxyFactory.createClient(ClimatiqHttpClient.class);
    }
}
