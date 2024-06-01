package pucminas.todolist.config;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static java.util.Collections.singletonList;
import static pucminas.todolist.util.constants.ConfigConstants.ALLOWED_METHODS;
import static pucminas.todolist.util.constants.ConfigConstants.CORS_CONFIG;

@Slf4j
@Configuration
public class SecurityConfig {

    /**
     * Cria camada de segurança Filter Chain
     *
     * @param httpSecurity configuração para papéis de usuário
     * @return configuração do httpSecurity
     * @throws Exception lança exceção caso haja problema na configuração
     */
    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity httpSecurity) throws Exception {
        log.info(">>> filterChain: iniciando camada de segurança Filter Chain");
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request.anyRequest().permitAll())
                .build();
    }

    /**
     * Configuração de cors
     *
     * @return configuração de cors
     */
    @Bean
    public CorsFilter corsFilter() {
        log.info(">>> Iniciando configuração de Cors");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(singletonList("http://localhost:3000"));
        config.addAllowedHeader("*");
        config.setAllowedMethods(ALLOWED_METHODS);

        source.registerCorsConfiguration(CORS_CONFIG, config);
        return new CorsFilter(source);
    }
}
