package com.ambientese.grupo5.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.beans.factory.annotation.Autowired;
import com.ambientese.grupo5.Filters.AuthFilter;
import com.ambientese.grupo5.Services.UsuarioService.JWTUtil;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JWTUtil jwtUtil;

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter(jwtUtil));
        registrationBean.addUrlPatterns("/auth/*", "/funcionarios", "/empresas", "/start-avaliacao", "/avaliacao", "/result-avaliacao");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    // Configuração de CORS
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")  // Aplica CORS a todos os endpoints
            .allowedOriginPatterns("http://localhost:*")  // Permite localhost em qualquer porta
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Métodos permitidos
            .allowedHeaders("*")  // Todos os headers permitidos
            .allowCredentials(true);  // Permite envio de cookies/autenticação
    }
}