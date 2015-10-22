package com.leanstacks.ws.model;

/**
 * APS를 이용하여 변환한 결과 status, 변환된 image의 url 저장.
 * @author KIMSEONHO
 *
 */
public class StitchResult {

	private int status;
	private String imagePath;
	
	public StitchResult(ResultStatus status, String imageFilePath) {
		this.status = status.getValue();
		this.imagePath = imageFilePath;
	}

	public int getStatus() {
		return status;
	}
	
	public void setStatus(ResultStatus status) {
		this.status = status.getValue();
	}

	public String getImage() {
		return imagePath;
	}
	
	public void setImage(String imagePath) {
		this.imagePath = imagePath;
	}
}
