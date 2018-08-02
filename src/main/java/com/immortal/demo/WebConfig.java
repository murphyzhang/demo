package com.immortal.demo;

import com.immortal.core.platform.WebAbstractConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {WebConfig.class}, basePackages = "com.immortal.demo")
public class WebConfig extends WebAbstractConfig {

}
