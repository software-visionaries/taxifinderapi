package app.taxifinderapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter CorsFilter(){
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://192.168.8.13:8081","http://localhost:3000","http://172.20.10.2:8081"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "UPDATE", "PUT"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);
    }
}
