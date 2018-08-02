package com.immortal.demo;

import com.immortal.core.platform.WebAbstractConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@ComponentScan(basePackageClasses = {WebConfig.class}, basePackages = "com.immortal.demo")
public class WebConfig extends WebAbstractConfig {

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("/WEB-INF/template");
        resolver.setSuffix(".ftl");
        return resolver;
    }

    /**
     * 注册通用的请求到视图映射
     * ${domain}/直接返回${domain}/WEB-INF/template/index.ftl
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
    }
}
