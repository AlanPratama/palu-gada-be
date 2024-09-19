package com.palu_gada_be.palu_gada_be.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("kerjainaja-public")
                .pathsToMatch("/api/**")
                .build();
    }
}
