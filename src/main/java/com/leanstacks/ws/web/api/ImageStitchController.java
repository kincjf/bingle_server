package com.leanstacks.ws.web.api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.leanstacks.ws.model.ResultStatus;
import com.leanstacks.ws.model.StitchResult;

/**
 * The ImageStitchController class is a RESTful web service controller. The
 * <code>@RestController</code> annotation informs Spring that each
 * <code>@RequestMapping</code> method returns a <code>@ResponseBody</code>.
 * 
 * @author KIMSEONHO
 */
@RestController
public class ImageStitchController extends BaseController {
//	private String appPath = this.getClass().getResource("/").getPath();
	
    @Value("${stitch.path.tempdir}")
	private String tempDir;

	@Value("${stitch.path.imagedir}")
	private String imageDir;
	
	/**
	 * 1. 파일 업로드 후 APS를 이용한 변환 수행
	 * 2. 변환 완료시 해당 image path 반환
	 * 
	 * {@link https://spring.io/guides/gs/uploading-files/}
	 * @param file
	 * @return
	 */
    @RequestMapping(
    		value = "/upload", method = RequestMethod.POST,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StitchResult> handleFileUpload(
    		@RequestParam("file") MultipartFile file){

        if (!file.isEmpty()) {
        	
        	try {
                byte[] bytes = file.getBytes();
                // @link http://joda-time.sourceforge.net/apidocs/org/joda/time/format/DateTimeFormat.html
                String randomName = DateTime.now().toString("yyyyMMddHHmmssSS");
                
        		final String tempFilePath = tempDir + "/" + randomName + ".zip";
        		final String imageFilePath = imageDir + "/" + randomName + ".jpg";
                		
                BufferedOutputStream stream =
                		new BufferedOutputStream(new FileOutputStream(new File(
                				tempFilePath)));
                stream.write(bytes);
                stream.close();
                
                String testImageFilePath
                	= imageDir + "/" + "201509241338057.jpg";
                
                return new ResponseEntity<StitchResult>(
                		new StitchResult(ResultStatus.OK, testImageFilePath), HttpStatus.OK);
            } catch (Exception e) {
            	return new ResponseEntity<StitchResult>(
            			new StitchResult(ResultStatus.FAIL_UPLOAD, null), HttpStatus.OK);
            }
        } else {        	
        	return new ResponseEntity<StitchResult>(
        			new StitchResult(ResultStatus.NOT_FOUND_FILE, null), HttpStatus.OK);
        }
    }
}
