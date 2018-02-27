package com.demo.core.web;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class BaseWebMvcConfig extends WebMvcConfigurerAdapter {
    public BaseWebMvcConfig() {
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver alr = new AcceptHeaderLocaleResolver();
        alr.setDefaultLocale(Locale.CHINA);
        return alr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        return new LocaleChangeInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(this.localeChangeInterceptor());
    }
}
