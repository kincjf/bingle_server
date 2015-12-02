package com.leanstacks.ws.process;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.exec.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.leanstacks.ws.model.APS.APSConfig;
import com.leanstacks.ws.util.XmlSerializerUtil;

/**
 * 이미지 변환 엔진을 child process로 실행함
 * @author KIMSEONHO
 */
@Component
public class AutopanoProcess extends BaseProcess {

	private String toolDir;
	private long jobTimeout;
	
	private final String AUTOPANO_SERVER;
	
	@Autowired
	private XmlSerializerUtil xmlSerializeUtil;
	
	/**
	 * must sets value after Bean initialize, it may be null if not.
	 * @param dir
	 * @param jobTimeout
	 */
	@Autowired
	public AutopanoProcess(
			@Value("${stitch.path.tools}") String dir,
			@Value("${stitch.aps.jobtimeout}") long jobTimeout ) {
		this.toolDir = dir;
		this.jobTimeout = jobTimeout;
		
		this.AUTOPANO_SERVER = Paths.get(toolDir, "AutopanoServer", "AutopanoServer").toString();
		
		logger.debug("toolDir : " + this.toolDir + ", jobTimeout : " + this.jobTimeout);
	}
	
	/**
	 * 
	 * @param storeFolderPath
	 * @param storeFileName
	 * @param outputFolderPath
	 * @return
	 */
	public String getAPSXmlPath(final String storeFolderPath,
			final String storeFileName, final String outputFolderPath) {
				
		APSConfig config = new APSConfig(storeFolderPath, outputFolderPath);
		String fullXmlPath = Paths.get(storeFolderPath, storeFileName + ".xml").toString();
		
		try {
			xmlSerializeUtil.serialize(fullXmlPath, config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fullXmlPath = "";		// NO_PATH
		}
		
		return fullXmlPath;
	}

	/**
	 * 
	 * @param fullXmlPath
	 * @return status 0(Success) | 1(Error) 
	 */
	public int run(String fullXmlPath) {
		
		Path path = Paths.get(fullXmlPath);
		int resultValue = 1;		// initialize as error code(1)
		
		if (!Files.exists(path)) {
			logger.error("File not exist");
			return resultValue;
		}
		
		final CommandLine commandLine = new CommandLine(this.AUTOPANO_SERVER);
		commandLine.addArgument("xml=" + fullXmlPath);
		
		ResultHandler resultHandler = super.run(commandLine, jobTimeout, 0, false);
		
		try {
			resultHandler.waitFor();
			if (resultHandler.hasResult()) {
				resultValue = resultHandler.getExitValue();
			}
		} catch (InterruptedException e) {
			logger.error("AutopanoProcess Error : " + e.getMessage());
			e.printStackTrace();
		}
		
		return resultValue;
	}
	
}
