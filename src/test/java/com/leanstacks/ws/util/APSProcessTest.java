package com.leanstacks.ws.util;

import java.nio.file.Paths;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.leanstacks.ws.AppConfig;
import com.leanstacks.ws.process.AutopanoProcess;
import com.leanstacks.ws.process.NadircapProcess;

@ContextConfiguration
@ActiveProfiles("stitch")
/**
 * Due to unable to set system variable(LD_LIBRARY_PATH)
 * It cannot execute external process
 * Please just see for reference information.
 * @author pastelplus
 *
 */
public class APSProcessTest {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.register(AutopanoProcess.class);
		ctx.register(NadircapProcess.class);
		ctx.refresh();
		
		AutopanoProcess autopanoProcess = ctx.getBean(AutopanoProcess.class);
		NadircapProcess nadircapProcess = ctx.getBean(NadircapProcess.class);
		
		String imageDir = "image";	
		String tempDir = "temp";
		
		String randomName = "201509241338057";
		String extractDir = Paths.get(tempDir, randomName).toString();
		
		String s = System.getProperty("user.dir");
		String path = System.getenv("LD_LIBRARY_PATH");
		System.out.println("Current Dir : " + s);
		System.out.println("Path : " + path);
		
		String imageName = null;
		String fullImageFilePath = null;
		final String fullXmlPath = autopanoProcess.getAPSXmlPath(extractDir, randomName, imageDir);
		int resultStatus = 1;
		
		if (fullXmlPath != "") {
			resultStatus = 0;
		} else {
			resultStatus = 1;	
			System.err.println("getAPSXmlPath Error");
		}
		
		if ((autopanoProcess.run(fullXmlPath) == 0) && (resultStatus == 0)) {
			resultStatus = 0;
			imageName = randomName + ".jpg";
			fullImageFilePath = Paths.get(imageDir, imageName).toString();
		} else {
			resultStatus = 1;
			System.err.println("autopanoProcess Error");
		}
		
		if ((nadircapProcess.run(fullImageFilePath) == 0) && (resultStatus == 0)) {
			resultStatus = 0;
		} else {
			resultStatus = 1;
			System.err.println("nadircapProcess Error");
		}
				
		System.out.println("resultStatus : " + resultStatus);
	}
}
