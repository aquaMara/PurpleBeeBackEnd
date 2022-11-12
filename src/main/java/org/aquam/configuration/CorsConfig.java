package org.aquam.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Component
public class CorsConfig {

    public void corsConfiguration(HttpSecurity http) throws Exception {
        http.cors(c -> {
            CorsConfigurationSource corsConfigurationSource = s -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000", "exp://192.168.100.105:19000", "http://10.0.2.2:3000"));
                corsConfiguration.setAllowedHeaders(List.of("*"));
                corsConfiguration.setAllowedMethods(List.of("*"));
                return corsConfiguration;
            };
            c.configurationSource(corsConfigurationSource);
        });
    }
}
