/* 
 * (版權及授權描述)
 *
 * Copyright 2012 (C) Hyweb Technology. All Rights Reserved.
 *
 * Id: A1002 
 * Date: 2012/10/11 上午11:43:27
 ****************************************
 * Date: 2012/10/11 上午11:43:27  User: Terry　Lee
 * Desc: 新加註解
 * --------------------------------------
 */
package tw.tpe.com.nggf.common.action.vo;

/**
 * JSON Result VO
 * 
 * @author <a href="mailto:terry.lee@hyweb.com.tw">Terry Lee</a>
 * @version Revision: 1 Date: 2012/10/11 上午11:43:27
 * @see
 * $Author: A1002 $ $Revision: 13096 $ $Date: 2012-10-11 12:13:53 +0800 (Thu, 11 Oct 2012) $
 */
public class JSONResultVo {
	
	private Boolean success;
	
	private String message;
	
	private Object result;

	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

}
