package com.liksi.hexagonal.seminar.http.config;

import com.liksi.hexagonal.seminar.http.adapters.AirportHttpClient;
import com.liksi.hexagonal.seminar.http.adapters.RouteHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class AirlabsApiConfig {

    final int size = 16 * 1024 * 1024;
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
            .build();

    @Bean
    WebClient airlabsWebClient(AirlabsApiProperties airlabsApiProperties) {
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory(uriComponentsBuilder(airlabsApiProperties));
        return WebClient.builder()
                .uriBuilderFactory(defaultUriBuilderFactory)
                .exchangeStrategies(strategies)
                .build();
    }

    @Bean
    AirportHttpClient airportHttpClient(WebClient airlabsWebClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(airlabsWebClient))
                        .build();
        return httpServiceProxyFactory.createClient(AirportHttpClient.class);
    }

    @Bean
    RouteHttpClient routeHttpClient(WebClient airlabsWebClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(airlabsWebClient))
                        .build();
        return httpServiceProxyFactory.createClient(RouteHttpClient.class);
    }

    private UriComponentsBuilder uriComponentsBuilder(final AirlabsApiProperties airlabsApiProperties) {
        return UriComponentsBuilder.fromHttpUrl(airlabsApiProperties.url())
                .queryParam("api_key", airlabsApiProperties.key());
    }
}
