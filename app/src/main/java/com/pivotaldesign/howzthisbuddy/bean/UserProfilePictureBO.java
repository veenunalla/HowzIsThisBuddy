package com.pivotaldesign.howzthisbuddy.bean;

public class UserProfilePictureBO extends HowZThisBuddyBaseBO {
	private static final long serialVersionUID = 1L;
	private static final String className = UserProfilePictureBO.class
			.getName();

	private long phoneNumber;
	private byte[] profilePic;
	private String profilePicS3Url;
	private boolean downloadIndicator;

	public String getProfilePicS3Url() {
		return profilePicS3Url;
	}

	public void setProfilePicS3Url(String profilePicS3Url) {
		this.profilePicS3Url = profilePicS3Url;
	}

	public boolean isDownloadIndicator() {
		return downloadIndicator;
	}

	public void setDownloadIndicator(boolean downloadIndicator) {
		this.downloadIndicator = downloadIndicator;
	}

	public UserProfilePictureBO() {

	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public byte[] getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}

	public String toString() {
		return "className : " + className + "{\n " + "\n }";
	}
}
