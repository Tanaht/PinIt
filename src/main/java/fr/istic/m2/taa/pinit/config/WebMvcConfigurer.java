package fr.istic.m2.taa.pinit.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("fr.istic.m2.taa.pinit.config")
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/target/www/**")) {
            registry.addResourceHandler("/target/www/**").addResourceLocations(
                    "classpath:/target/www/");
        }
    }

}
