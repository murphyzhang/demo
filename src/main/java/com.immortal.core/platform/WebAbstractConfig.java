package com.immortal.core.platform;

import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
        dispatcher.addMapping("/");
    }

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
     * setContentType: 配置视图内容编码类型
     * setViewClass: 配置解析器类型
     * setCache: 开启视图缓存
     * setSuffix: 设置视图后缀
     * @return
     */
    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setViewClass(FreeMarkerView.class);
        resolver.setCache(true);
        resolver.setSuffix(".ftl");
        return resolver;
    }

    /**
     * 静态资源处理
     * 把"/**"请求注册到SimpleUrlHandlerMapping的urlMap中
     * 等同于xml配置<mvc:default-servlet-handler/>
     *
     * DispatcherServlet设置mapping("/")几乎拦截所有请求, 破坏了servlet一个特性(根目录下的文件可以直接访问), DefaultServletHandlerConfigurer帮助回归该特性
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 注册Handler处理静态资源重定向
     * eg:
     * 当请求http://localhost:5200/demo/html/vue/index.html时, 返回http://localhost:5200/demo/WEB-INF/html/vue/index.html
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/html/**").addResourceLocations("/WEB-INF/html/");
    }
}
