package com.pivotaldesign.howzthisbuddy.bean;

public class ContactDetailsBO extends HowZThisBuddyBaseBO {

	private static final long serialVersionUID = 1L;
	private static final String className = ContactDetailsBO.class.getName();

	private String emailAddress;
	private long phoneNumber;

	public ContactDetailsBO() {
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String toString() {
		return "className : " + className + "{ \n emailAddress:" + emailAddress
				+ " phoneNumber:" + phoneNumber + "\n }";
	}
}