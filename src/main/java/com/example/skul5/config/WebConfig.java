package com.example.skul5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class WebConfig {

    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver tr = new ClassLoaderTemplateResolver();
        tr.setPrefix("templates/");
        tr.setSuffix(".html");
        tr.setTemplateMode("HTML");
        tr.setCharacterEncoding("UTF-8");
        return tr;
    }

    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine st = new SpringTemplateEngine();
        st.setTemplateResolver(templateResolver());
        return st;
    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver vr = new ThymeleafViewResolver();
        vr.setTemplateEngine(templateEngine());
        vr.setCharacterEncoding("UTF-8");
        return vr;
    }

    public void addResourceHandler(ResourceHandlerRegistry reg) {
        reg.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");

        reg.addResourceHandler("webjars/**")
                .addResourceLocations("/webjars/");
    }

}
