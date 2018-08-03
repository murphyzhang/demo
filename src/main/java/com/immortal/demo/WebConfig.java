package com.immortal.demo;

import com.immortal.core.platform.WebAbstractConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.Properties;

@Configuration
@ComponentScan(basePackageClasses = {WebConfig.class}, basePackages = "com.immortal.demo")
public class WebConfig extends WebAbstractConfig {

    /**
     * 注入freemarker配置类
     * FreeMarkerConfigurer父类FreeMarkerConfigurationFactory依赖于spring-context-support.jar
     * 缺少spring-context-support.jar依赖将无法设置freemarker启动参数
     * @return
     */
    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer config = new FreeMarkerConfigurer();
        //指定视图路径前缀
        config.setTemplateLoaderPath("/WEB-INF/freemarker/");
        //设置freemarker解析器编码
        Properties properties = new Properties();
        properties.setProperty("defaultEncoding", "UTF-8");
        config.setFreemarkerSettings(properties);
        return config;
    }

    /**
     * 注入freemarker视图解析器
     * @return
     */
    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        //配置视图内容编码类型
        resolver.setContentType("text/html;charset=UTF-8");
        //开启视图缓存
        resolver.setCache(true);
        //设置视图后缀
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
