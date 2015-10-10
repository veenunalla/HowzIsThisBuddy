package com.pivotaldesign.howzthisbuddy.bean;

public class OpinionResponderUserBO extends HowZThisBuddyBaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long phoneNumber;
	private boolean requestFlag;

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isRequestFlag() {
		return requestFlag;
	}

	public void setRequestFlag(boolean requestFlag) {
		this.requestFlag = requestFlag;
	}
}
