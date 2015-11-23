package com.leanstacks.ws.process;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Based on Apache Commons Exec
 * {@link http://commons.apache.org/proper/commons-exec/xref-test/org/apache/commons/exec/TutorialTest.html}
 * @author KIMSEONHO
 *
 */
public class BaseProcess {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
	/**
     * 
     * @param commandLine
     * @param jobTimeout ms
     * @param exitValue
     * @param inBackground
     * @return
     */
    protected ResultHandler run(final CommandLine commandLine, final long jobTimeout,
			final int exitValue, final boolean inBackground) {
		
		int resultExitValue;
		ExecuteWatchdog watchdog = null;
		ResultHandler resultHandler = null;
		
		final Executor executor = new DefaultExecutor();
		executor.setExitValue(exitValue);

		// 테스트를 위한 Stream redirect, 나중에는 파일에 하는게 좋을 것 같음.
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler( System.out, System.err, System.in );
        executor.setStreamHandler(pumpStreamHandler);
        
		if (jobTimeout > 0) {
			watchdog = new ExecuteWatchdog(jobTimeout);
			executor.setWatchdog(watchdog);
		}
		
		try {
			if (inBackground) {
	            logger.info("Executing non-blocking print job...");
	            resultHandler = new ResultHandler(watchdog);
	            executor.execute(commandLine, resultHandler);
	        } else {
	        	logger.info("Executing blocking print job  ...");
	             resultExitValue = executor.execute(commandLine);
	             resultHandler = new ResultHandler(resultExitValue);
	        }
		} catch (ExecuteException e) {
			logger.error("BaseProcess Execute Error : " + commandLine.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("BaseProcess IO Error : " + commandLine.toString());
			e.printStackTrace();
		}
		
		return resultHandler;
	}
	
	protected class ResultHandler extends DefaultExecuteResultHandler {
        private ExecuteWatchdog watchdog;
        public ResultHandler(final ExecuteWatchdog watchdog) {
            this.watchdog = watchdog;
        }
        
        public ResultHandler(final int exitValue) {
            super.onProcessComplete(exitValue);
        }
        
        @Override
        public void onProcessComplete(final int exitValue) {
            super.onProcessComplete(exitValue);
            logger.info("[resultHandler] The process was successful...");
        }
        
        @Override
        public void onProcessFailed(final ExecuteException e) {
            super.onProcessFailed(e);
            if (watchdog != null && watchdog.killedProcess()) {
            	logger.error("[resultHandler] The process timed out");
            }
            else {
            	logger.error("[resultHandler] The process failed to do : " + e.getMessage());
            }
        }
    }
}
