package pucminas.listatarefas.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pucminas.listatarefas.interceptor.LoggingInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Adiciona interceptadores nos controllers
     *
     * @param registry registro do interceptador
     */
    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor());
    }
}
