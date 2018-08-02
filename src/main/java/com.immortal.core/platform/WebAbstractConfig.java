package com.immortal.core.platform;

import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.HashMap;
import java.util.Map;

/**
 * SpringServletContainerInitializer
 * @HandlesTypes({WebApplicationInitializer.class}) 表示继承或者实现WebApplicationInitializer的类都会被加载
 * 继承WebApplicationInitializer的接口以及抽象类将不会被实例化
 */
@EnableWebMvc
public abstract class WebAbstractConfig extends WebMvcConfigurerAdapter implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        //动态配置servlet, 通过addServlet方法将类注册为可用的servlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", DispatcherServlet.class);
        //添加到servlet的参数名在spring-web的ContextLoader类中定义
        Map<String, String> parameters = new HashMap<>();
        parameters.put(ContextLoader.CONFIG_LOCATION_PARAM, this.getClass().getName());
        parameters.put(ContextLoader.CONTEXT_CLASS_PARAM, AnnotationConfigWebApplicationContext.class.getName());
        parameters.put(ContextLoader.CONTEXT_INITIALIZER_CLASSES_PARAM, DefaultAppContextInitializer.class.getName());
        dispatcher.setInitParameters(parameters);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/", "*.html");
    }

    /**
     * 静态资源处理
     * 把"/**"请求注册到SimpleUrlHandlerMapping的urlMap中
     * 等同于xml配置<mvc:default-servlet-handler/>
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer config = new FreeMarkerConfigurer();
        return config;
    }

    /*public void configureViewResolvers(ViewResolverRegistry registry) {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setPrefix("/WEB-INF/template/");
        freeMarkerViewResolver.setSuffix(".ftl");
        registry.viewResolver(freeMarkerViewResolver);
    }*/
}
