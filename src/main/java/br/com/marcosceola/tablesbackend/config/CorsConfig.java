package br.com.marcosceola.tablesbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Value("${web-socket-server.allowed-cors-origins-pattern}")
    private String allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        var source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();

        config.addAllowedOriginPattern(allowedOrigins);
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
