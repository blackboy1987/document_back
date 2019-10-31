package com.igomall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 配置自己的国际化语言解析器
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new LocaleResolverConfig();
    }


}
