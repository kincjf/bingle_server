package com.leanstacks.ws;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * {@link http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-application-events-and-listeners}
 * @author KIMSEONHO
 *
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	@Value("${stitch.path.tempdir}")
	private String tempDir;

	@Value("${stitch.path.imagedir}")
	private String imageDir;
    
	/*
	 * This method is called during Spring's startup.
	 * 
	 * @param event Event raised when an ApplicationContext gets initialized or
	 * refreshed.
	 */
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
//		String appPath = this.getClass().getResource("/").getPath();
		
		final String tempDirPath = this.tempDir;
		final String imageDirPath = this.imageDir;
		
		File tempDir = new File(tempDirPath);
		File imageDir = new File(imageDirPath);
		
		if (!tempDir.exists()) {
			tempDir.mkdir();
		}
		
		if (!imageDir.exists()) {
			imageDir.mkdir();
		}
		
		return;
	}
}