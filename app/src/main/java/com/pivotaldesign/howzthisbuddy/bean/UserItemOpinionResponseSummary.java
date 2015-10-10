package com.pivotaldesign.howzthisbuddy.bean;

public class UserItemOpinionResponseSummary {

	private long requestPhoneNumber;
	private long opinionGivenCount;
	private long opinionPendingCount;

	public long getRequestPhoneNumber() {
		return requestPhoneNumber;
	}

	public void setRequestPhoneNumber(long requestPhoneNumber) {
		this.requestPhoneNumber = requestPhoneNumber;
	}

	public long getOpinionGivenCount() {
		return opinionGivenCount;
	}

	public void setOpinionGivenCount(long opinionGivenCount) {
		this.opinionGivenCount = opinionGivenCount;
	}

	public long getOpinionPendingCount() {
		return opinionPendingCount;
	}

	public void setOpinionPendingCount(long opinionPendingCount) {
		this.opinionPendingCount = opinionPendingCount;
	}

}
