package com.immortal.core.platform;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.HashMap;
import java.util.Map;

public class WebAbstractConfig extends WebMvcConfigurerAdapter implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", DispatcherServlet.class);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("contextConfigLocation", this.getClass().getName());
        parameters.put("applicationContext", AnnotationConfigApplicationContext.class.getName());
        parameters.put("contextInitializer", DefaultAppContextInitializer.class.getName());
        dispatcher.setInitParameters(parameters);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
