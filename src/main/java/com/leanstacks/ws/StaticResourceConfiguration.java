package com.leanstacks.ws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * By default Spring Boot will serve static content from a directory called
 * /static (or /public or /resources or /META-INF/resources) in the classpath or
 * from the root of the ServletContext. You can customize the static resource
 * 
 * locations using spring.resources.staticLocations (replacing the default
 * values with a list of directory locations).
 * {@link http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#using-boot-devtools-restart-exclude}
 * 
 * @author KIMSEONHO
 */
@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
	
	@Value("${stitch.path.imagedir}")
	private String imageDirName;
	
	private final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
			"classpath:/resources/", "classpath:/static/", "classpath:/public/"	};

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
		registry.addResourceHandler("/image/**").addResourceLocations("file:" + imageDirName + "/");
		
		super.addResourceHandlers(registry);
	}
}
