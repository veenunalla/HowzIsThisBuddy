package com.pivotaldesign.howzthisbuddy.bean;


public class UserMessageParamsBO extends HowZThisBuddyBaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long messageId;
	private String paramName;
	private String paramValue;

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

}
