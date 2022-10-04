package com.cts.eaution.exception;

/**
 * @author aadi
 *Exception class for api gateway
 */
public class GatewayException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2269011004935576283L;
	
	/**
	 * exception message
	 */
	private String message;
	/**
	 * exception details
	 */
	private String descrpition;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDescrpition() {
		return descrpition;
	}
	public void setDescrpition(String descrpition) {
		this.descrpition = descrpition;
	}
}

