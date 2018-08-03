package com.immortal.demo;

import com.immortal.core.platform.WebAbstractConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Properties;

@Configuration
@ComponentScan(basePackageClasses = {WebConfig.class}, basePackages = "com.immortal.demo")
public class WebConfig extends WebAbstractConfig {

    /**
     * 覆盖父类同名Bean
     * @return
     */
    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer config = new FreeMarkerConfigurer();
        //指定视图路径前缀
        config.setTemplateLoaderPath("/WEB-INF/template/");
        //设置freemarker解析器编码
        Properties properties = new Properties();
        properties.setProperty("defaultEncoding", "UTF-8");
        config.setFreemarkerSettings(properties);
        return config;
    }

    /**
     * 注册通用的请求到视图映射
     * ${domain}/直接返回${domain}/WEB-INF/freemarker/index.ftl
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }
}
