package com.leanstacks.ws.process;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.exec.CommandLine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class NadircapProcess extends BaseProcess {
	@Value("${stitch.path.tools}")
	private String toolDir;
	
	@Value("${stitch.nadircap.jobtimeout}")
	private long jobTimeout;

	private final String BSHELL = "sh";
	private final String NADIR_CAP = Paths.get(toolDir, "nadircap.sh").toString();
	
	/**
	 * 
	 * @param imagePath
	 * @return status 0(Success) | 1(Error) 
	 */
	@Bean
	public int run(String imagePath) {
		
		Path path = Paths.get(imagePath);
		int resultValue = 1;		// initialize as error code(1)
		
		if (!Files.exists(path)) {
			logger.error("File not exist");
			return resultValue;
		}
		
		final CommandLine commandLine = new CommandLine(this.BSHELL);
		commandLine.addArgument(this.NADIR_CAP);
		commandLine.addArgument(imagePath);
		
		ResultHandler resultHandler = super.run(commandLine, jobTimeout, 0, false);
		
		try {
			resultHandler.waitFor();
			if (resultHandler.hasResult()) {
				resultValue = resultHandler.getExitValue();
			}
		} catch (InterruptedException e) {
			logger.error("NadircapProcess Error : " + e.getMessage());
			e.printStackTrace();
		}
		
		return resultValue;
	}	
}
