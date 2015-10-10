package com.pivotaldesign.howzthisbuddy.bean;

import java.sql.Timestamp;

public class OpinionBO extends HowZThisBuddyBaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long opinionId;
	private String opinionRequestDate;
	private long opinionTypeCode;
	private String opinionResponseDate;
	private String comments;
	private long responsePhoneNumber;
	
	public String getOpinionRequestDate() {
		return opinionRequestDate;
	}

	public void setOpinionRequestDate(String opinionRequestDate) {
		this.opinionRequestDate = opinionRequestDate;
	}

	public String getOpinionResponseDate() {
		return opinionResponseDate;
	}

	public void setOpinionResponseDate(String opinionResponseDate) {
		this.opinionResponseDate = opinionResponseDate;
	}

	

	public long getResponsePhoneNumber() {
		return responsePhoneNumber;
	}

	public void setResponsePhoneNumber(long responsePhoneNumber) {
		this.responsePhoneNumber = responsePhoneNumber;
	}

	public long getOpinionTypeCode() {
		return opinionTypeCode;
	}

	public void setOpinionTypeCode(long opinionTypeCode) {
		this.opinionTypeCode = opinionTypeCode;
	}

	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public long getOpinionId() {
		return opinionId;
	}

	public void setOpinionId(long opinionId) {
		this.opinionId = opinionId;
	}

}
