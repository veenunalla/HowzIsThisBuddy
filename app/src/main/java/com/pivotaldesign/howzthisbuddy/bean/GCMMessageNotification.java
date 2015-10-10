package com.pivotaldesign.howzthisbuddy.bean;

public class GCMMessageNotification {

	private String gcmRegistrationID;
	private String message;
	
	public GCMMessageNotification(String gcmRegistrationID, String message) {
		this.gcmRegistrationID = gcmRegistrationID;
		this.message = message;
	}

	public String getGcmRegistrationID() {
		return gcmRegistrationID;
	}

	public void setGcmRegistrationID(String gcmRegistrationID) {
		this.gcmRegistrationID = gcmRegistrationID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
