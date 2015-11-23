package com.leanstacks.ws.util;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.leanstacks.ws.AppConfig;
import com.leanstacks.ws.model.APS.APSConfig;

public class XmlSerializerUtilTest {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
		
		XmlSerializerUtil xmlSerializeUtil = ctx.getBean(XmlSerializerUtil.class);
		
		String imageDir = "image";	
		String tempDir = "temp";
		
		String randomName = "201509241338057";
		String extractDir = Paths.get(tempDir, randomName).toString();
		
		APSConfig config = new APSConfig(extractDir, imageDir);
		String fullXmlPath = Paths.get(extractDir, randomName + ".xml").toString();
				
		try {
			xmlSerializeUtil.serialize(fullXmlPath, config);
			APSConfig deserialized = (APSConfig)xmlSerializeUtil.deserialize(fullXmlPath);
			System.out.println(deserialized);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
