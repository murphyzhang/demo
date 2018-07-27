package com.immortal.core.platform;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class DefaultAppContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public void initialize(ConfigurableApplicationContext context) {
        try {
            //setIgnoreUnresolvableNestedPlaceholders遇到不可解析占位符时是否抛出异常, false抛出异常, true不抛异常继续传递
            context.getEnvironment().setIgnoreUnresolvableNestedPlaceholders(true);
            //读取当前项目resources/conf路径下所有properties配置文件
            //classpath仅读取一个properties文件, classpath*读取所有(包含项目引用的jar包)
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:conf/*.properties");
            MutablePropertySources propertySources = context.getEnvironment().getPropertySources();
            for (Resource resource : resources) {
                if (!propertySources.contains(resource.getFilename())) {
                    propertySources.addLast(new ResourcePropertySource(resource.getFilename(), resource));
                }
            }
        } catch (IOException e) {
            //配置文件加载失败, 抛出运行时异常
            throw new RuntimeException(e);
        }
    }
}
