package com.pivotaldesign.howzthisbuddy.bean;

public class MessageDefinition {
	private int messageCode;
	private String messageText;
	private int hoursValid;
	private int messageCategory;

	public int getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(int messageCode) {
		this.messageCode = messageCode;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public int getHoursValid() {
		return hoursValid;
	}

	public void setHoursValid(int hoursValid) {
		this.hoursValid = hoursValid;
	}

	public int getMessageCategory() {
		return messageCategory;
	}

	public void setMessageCategory(int messageCategory) {
		this.messageCategory = messageCategory;
	}

}
