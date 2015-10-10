package com.pivotaldesign.howzthisbuddy.bean;

public class UserItemOpinionDataBaseBO {

	private long itemId;
	private long opinionId;
	private long requestPhoneNumber;
	private long responsePhoneNumber;
	private long itemSelfieDetailId;

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getOpinionId() {
		return opinionId;
	}

	public void setOpinionId(long opinionId) {
		this.opinionId = opinionId;
	}

	public long getRequestPhoneNumber() {
		return requestPhoneNumber;
	}

	public void setRequestPhoneNumber(long requestPhoneNumber) {
		this.requestPhoneNumber = requestPhoneNumber;
	}

	public long getResponsePhoneNumber() {
		return responsePhoneNumber;
	}

	public void setResponsePhoneNumber(long responsePhoneNumber) {
		this.responsePhoneNumber = responsePhoneNumber;
	}

	public long getItemSelfieDetailId() {
		return itemSelfieDetailId;
	}

	public void setItemSelfieDetailId(long itemSelfieDetailId) {
		this.itemSelfieDetailId = itemSelfieDetailId;
	}
}
