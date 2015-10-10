package com.pivotaldesign.howzthisbuddy.bean;

public class ResponseContactInfBO {

	private String phonenum;
	private String name;
	private boolean ischecked;
	private String profilePicS3Url;
	private int downloadIndicator;
	
	public String getProfilePicS3Url() {
		return profilePicS3Url;
	}

	public void setProfilePicS3Url(String profilePicS3Url) {
		this.profilePicS3Url = profilePicS3Url;
	}

	public int isDownloadIndicator() {
		return downloadIndicator;
	}

	public void setDownloadIndicator(int downloadIndicator) {
		this.downloadIndicator = downloadIndicator;
	}

	public boolean isIschecked() {
		return ischecked;
	}

	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
