package com.intellective.foia.csapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("forward:/index.html");
        registry.addViewController("/error").setViewName("forward:/index.html");
        registry.addViewController("/app").setViewName("forward:/index.html");
    }
}
