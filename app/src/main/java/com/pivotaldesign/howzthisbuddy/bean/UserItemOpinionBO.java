package com.pivotaldesign.howzthisbuddy.bean;

import java.util.List;


public class UserItemOpinionBO extends HowZThisBuddyBaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ItemBO itemBO;
	private OpinionBO opinionBO;
	private long requestPhoneNumber;
	private String responsePhoneNumber;
	private ItemSelfieDetailsBO itemSelfieDetailsBO;

	public ItemBO getItemBO() {
		return itemBO;
	}

	public void setItemBO(ItemBO itemBO) {
		this.itemBO = itemBO;
	}

	public OpinionBO getOpinionBO() {
		return opinionBO;
	}

	public void setOpinionBO(OpinionBO opinionBO) {
		this.opinionBO = opinionBO;
	}

	public long getRequestPhoneNumber() {
		return requestPhoneNumber;
	}

	public void setRequestPhoneNumber(long requestPhoneNumber) {
		this.requestPhoneNumber = requestPhoneNumber;
	}

public String getResponsePhoneNumber() {
		return responsePhoneNumber;
	}

	public void setResponsePhoneNumber(String responsePhoneNumber) {
		this.responsePhoneNumber = responsePhoneNumber;
	}

	/*	public long getResponsePhoneNumber() {
		return responsePhoneNumber;
	}

	public void setResponsePhoneNumber(long responsePhoneNumber) {
		this.responsePhoneNumber = responsePhoneNumber;
	}
*/
	public ItemSelfieDetailsBO getItemSelfieDetailsBO() {
		return itemSelfieDetailsBO;
	}

	public void setItemSelfieDetailsBO(ItemSelfieDetailsBO itemSelfieDetailsBO) {
		this.itemSelfieDetailsBO = itemSelfieDetailsBO;
	}

}
