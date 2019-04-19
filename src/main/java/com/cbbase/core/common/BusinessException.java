package com.cbbase.core.common;

public class BusinessException extends RuntimeException{

	private int errorCode = 1;
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException() {
        super();
    }

    public BusinessException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

	public BusinessException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
