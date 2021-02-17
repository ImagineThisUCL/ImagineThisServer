package com.ucl.imaginethisserver.Conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {
    // get value from application.properties
    @Value("${imaginethis.cors_origin}")
    private String corsString;

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1/**")
                        .allowedOrigins(corsString.equals("") ? "*" : corsString)
                        .allowedMethods("GET", "POST", "PATCH", "DELETE");
            }
        };
    }
}
