/**
 * 
 */
package tw.tpe.com.nggf.common.exception;

/**
 * @author Hyweb
 *
 */
public class NggfCommonException extends Exception {
	private static final long serialVersionUID = System.currentTimeMillis();

	/**
	 * 錯誤代碼
	 */
	protected String errCode = "1001";

	/**
	 * 錯誤內容訊息
	 */
	protected String errMessage = "NGGF 系統共同錯誤，子功能系統未覆寫錯誤原因";

	/**
	 * 
	 */
	public NggfCommonException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public NggfCommonException(String message) {
		super(message);
		this.errMessage = message;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errCode
	 * @param errMessage
	 */
	public NggfCommonException(String errCode, String errMessage) {
		super(errMessage);

		this.errCode = errCode;
		this.errMessage = errMessage;
	}

	/**
	 * @param errCode
	 * @param errMessage
	 * @param cause
	 */
	public NggfCommonException(String errCode, String errMessage, Throwable cause) {
		super(errMessage, cause);

		this.errCode = errCode;
		this.errMessage = errMessage;
	}

	/**
	 * @param cause
	 */
	public NggfCommonException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NggfCommonException(String message, Throwable cause) {
		super(message, cause);
		this.errMessage = message;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the errCode
	 */
	public String getErrCode() {
		return errCode;
	}

	/**
	 * @return the errmessage
	 */
	public String getErrMessage() {
		return errMessage;
	}

	/**
	 * @param errCode the errCode to set
	 */
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	/**
	 * @param errmessage the errmessage to set
	 */
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
}
