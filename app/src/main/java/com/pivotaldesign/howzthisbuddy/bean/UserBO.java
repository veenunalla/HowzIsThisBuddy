package com.pivotaldesign.howzthisbuddy.bean;

import java.sql.Timestamp;

public class UserBO extends HowZThisBuddyBaseBO {

	private static final long serialVersionUID = 1L;
	private static final String className = UserBO.class.getName();

	private long phoneNumber;
	private String name;
	private String emailId;
	private Boolean registrationFlag;
	private long otpCode;
	private Timestamp otpTimeStamp;
	private long otpAttemptCount;
	private long otpRequestedCount;
	private String gcmRegistrationID;
	private Timestamp registrationRequestedDate;
	private Timestamp registrationSuccessfulDate;
	private Timestamp reRegistrationRequestedDate;
	private Timestamp reRegistrationSuccessfulDate;

	private UserProfilePictureBO userProfilePictureBO;

	public UserBO() {

	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Boolean isRegistrationFlag() {
		return registrationFlag;
	}

	public void setRegistrationFlag(Boolean registrationFlag) {
		this.registrationFlag = registrationFlag;
	}

	public long getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(long otpCode) {
		this.otpCode = otpCode;
	}

	public Timestamp getOtpTimeStamp() {
		return otpTimeStamp;
	}

	public void setOtpTimeStamp(Timestamp otpTimeStamp) {
		this.otpTimeStamp = otpTimeStamp;
	}

	public long getOtpAttemptCount() {
		return otpAttemptCount;
	}

	public void setOtpAttemptCount(long otpAttemptCount) {
		this.otpAttemptCount = otpAttemptCount;
	}

	public long getOtpRequestedCount() {
		return otpRequestedCount;
	}

	public void setOtpRequestedCount(long otpRequestedCount) {
		this.otpRequestedCount = otpRequestedCount;
	}

	public String getGcmRegistrationID() {
		return gcmRegistrationID;
	}

	public void setGcmRegistrationID(String gcmRegistrationID) {
		this.gcmRegistrationID = gcmRegistrationID;
	}

	public Timestamp getRegistrationRequestedDate() {
		return registrationRequestedDate;
	}

	public void setRegistrationRequestedDate(Timestamp registrationRequestedDate) {
		this.registrationRequestedDate = registrationRequestedDate;
	}

	public Timestamp getReRegistrationRequestedDate() {
		return reRegistrationRequestedDate;
	}

	public void setReRegistrationRequestedDate(
			Timestamp reRegistrationRequestedDate) {
		this.reRegistrationRequestedDate = reRegistrationRequestedDate;
	}

	public Timestamp getRegistrationSuccessfulDate() {
		return registrationSuccessfulDate;
	}

	public void setRegistrationSuccessfulDate(
			Timestamp registrationSuccessfulDate) {
		this.registrationSuccessfulDate = registrationSuccessfulDate;
	}

	public Timestamp getReRegistrationSuccessfulDate() {
		return reRegistrationSuccessfulDate;
	}

	public void setReRegistrationSuccessfulDate(
			Timestamp reRegistrationSuccessfulDate) {
		this.reRegistrationSuccessfulDate = reRegistrationSuccessfulDate;
	}

	public UserProfilePictureBO getUserProfilePictureBO() {
		return userProfilePictureBO;
	}

	public void setUserProfilePictureBO(
			UserProfilePictureBO userProfilePictureBO) {
		this.userProfilePictureBO = userProfilePictureBO;
	}

	public String toString() {
		return "className : " + className + "{\n phoneNumber:" + phoneNumber
				+ "\n name" + name + "\n emailId" + emailId
				+ "\n gcmRegistrationID :" + gcmRegistrationID
				+ "\n userProfilePictureBO :" + "\n }";
	}
}
