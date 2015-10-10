package com.pivotaldesign.howzthisbuddy.bean;

public class DeviceDetailsBO extends HowZThisBuddyBaseBO {
	private static final long serialVersionUID = 1L;
	private static final String className = DeviceDetailsBO.class.getName();

	private String deviceType;
	private String gcmRegistrationID;

	public String getGcmRegistrationID() {
		return gcmRegistrationID;
	}

	public void setGcmRegistrationID(String gcmRegistrationID) {
		this.gcmRegistrationID = gcmRegistrationID;
	}

	public DeviceDetailsBO() {

	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String toString() {
		return "className : " + className + "{\n deviceType:" + deviceType
				+ "\n gcmRegistrationID:" + gcmRegistrationID + "\n }";
	}
}
