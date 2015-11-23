package com.leanstacks.ws.process;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.exec.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.leanstacks.ws.common.XmlSerializerUtil;
import com.leanstacks.ws.model.APS.APSConfig;

@Component
public class AutopanoProcess extends BaseProcess {
	private final String AUTOPANO_SERVER = "./tools/AutopanoServer/AutopanoServer";
	private final long jobTimeout = 15000;
	
	@Autowired
	private XmlSerializerUtil xmlSerializeUtil;
	
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
