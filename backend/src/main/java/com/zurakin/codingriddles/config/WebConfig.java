package com.zurakin.codingriddles.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Exclude static assets (js, css, png, etc.)
        registry.addViewController("/{path:^(?!assets|.*\\..*$).*$}")
                .setViewName("forward:/");
        registry.addViewController("/**/{path:^(?!assets|.*\\..*$).*$}")
                .setViewName("forward:/");
    }
}