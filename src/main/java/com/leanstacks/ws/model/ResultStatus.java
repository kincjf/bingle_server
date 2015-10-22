package com.leanstacks.ws.model;

/**
 * http, ftp status code를 기준으로 custom status code를 지정함
 * - references
 *   {@link https://en.wikipedia.org/wiki/List_of_HTTP_status_codes}
 *   {@link https://en.wikipedia.org/wiki/List_of_FTP_server_return_codes}
 *   
 * @author KIMSEONHO
 *
 */
public enum ResultStatus {
	/**
	 * Accepted and Transaction Success.
	 * See in HTTP STATUS CODE, 200
	 */
	OK(200),
	
	/**
	 * Requested file not found
	 * See in FTP STATUS CODE, 425 
	 */
	NOT_FOUND_FILE(425),
	
	/**
	 * Fail to upload file
	 * See in FTP STATUS CODE, 450
	 */
	FAIL_UPLOAD(450);
	
	private final int _value;

	private ResultStatus(int value) {
		this._value = value;
	}
	
	public int getValue() {
        return _value;
    }
}
