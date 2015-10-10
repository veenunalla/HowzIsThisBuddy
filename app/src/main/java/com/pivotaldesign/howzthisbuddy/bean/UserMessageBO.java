package com.pivotaldesign.howzthisbuddy.bean;

import java.sql.Timestamp;

public class UserMessageBO extends HowZThisBuddyBaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long messageId;
	private long phoneNumber;
	private int messageCode;
	private Timestamp messageTimeStamp;
	private Timestamp messageExpTimeStamp;

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(int messageCode) {
		this.messageCode = messageCode;
	}

	public Timestamp getMessageTimeStamp() {
		return messageTimeStamp;
	}

	public void setMessageTimeStamp(Timestamp messageTimeStamp) {
		this.messageTimeStamp = messageTimeStamp;
	}

	public Timestamp getMessageExpTimeStamp() {
		return messageExpTimeStamp;
	}

	public void setMessageExpTimeStamp(Timestamp messageExpTimeStamp) {
		this.messageExpTimeStamp = messageExpTimeStamp;
	}

}
