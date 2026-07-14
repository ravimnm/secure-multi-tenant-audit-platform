package com.ivar.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @value("${auth.service.url}")
    private String authServiceUrl;
    
    @Bean
    public WebClient webClient() {

        return WebClient.builder()
                .baseUrl(authServiceUrl)
                .build();
    }
}
