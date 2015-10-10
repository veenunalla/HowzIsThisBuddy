package com.pivotaldesign.howzthisbuddy.bean;

import java.util.List;

public class OpinionsGivenDetailsBO extends HowZThisBuddyBaseBO {

	/**
	 * This BO is response to GetOpinionsGivenDetails
	 */
	private static final long serialVersionUID = 1L;
	
	private long requestPhoneNumber;
	private long responsePhoneNumber;
	private List<UserItemOpinionBO> opinionsGiven;
	private List<UserItemOpinionBO> opinionsPending;
	
	
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
	
	public List<UserItemOpinionBO> getOpinionsGiven() {
		return opinionsGiven;
	}
	public void setOpinionsGiven(List<UserItemOpinionBO> opinionsGiven) {
		this.opinionsGiven = opinionsGiven;
	}
	
	public List<UserItemOpinionBO> getOpinionsPending() {
		return opinionsPending;
	}
	public void setOpinionsPending(List<UserItemOpinionBO> opinionsPending) {
		this.opinionsPending = opinionsPending;
	}
}
