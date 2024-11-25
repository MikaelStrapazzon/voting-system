package com.example.votingsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${application.title}")
    private String title;
    
    @Value("${application.version}")
    private String version;
    
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title(title).version(version));
    }
}
