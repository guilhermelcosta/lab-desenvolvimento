package pucminas.todolist.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static java.util.Collections.singletonList;
import static pucminas.todolist.util.constants.ConfigConstants.ALLOWED_METHODS;
import static pucminas.todolist.util.constants.ConfigConstants.CORS_CONFIG;

@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    public CorsFilter corsFilter() {
        log.info(">>> Iniciando configuração de Cors");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(singletonList("*"));
        config.addAllowedHeader("*");
        config.setAllowedMethods(ALLOWED_METHODS);

        source.registerCorsConfiguration(CORS_CONFIG, config);
        return new CorsFilter(source);
    }
}
