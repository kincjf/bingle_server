package com.leanstacks.ws.util;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * @author KIMSEONHO
 *
 */
public class ZipUtilTest {
	
	public ZipUtilTest() {
		
		try {
			// Initiate ZipFile object with the path/name of the zip file.
			// 중복 파일이 있을 경우 덮어 씌우고, 폴더가 없으면 자동으로 생성한다. 
			ZipFile zipFile = new ZipFile("./temp/201509241338057.zip");
			
			// Extracts all files to the path specified
			zipFile.extractAll("./temp/201509241338057");
			
		} catch (ZipException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ZipUtilTest();
	}
}
