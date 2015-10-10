package com.pivotaldesign.howzthisbuddy.bean;

import java.sql.Timestamp;
import java.util.List;

public class UserItemOpinionRequestSummary {

	private long phoneNumber;
	private ItemBO itemBO;
	private long likeCount;
	private long disLikeCount;
	private long mayBeCount;
	private long opinionRequestCount;
	private long opinionResponseCount;
	private ItemSelfieDetailsBO itemSelfieDetailsBO;
	private String opinionRequestDate;
	private List<OpinionBO> opinionRespondedList;
	private List<OpinionBO> opinionRespondPendingList;

	
	public List<OpinionBO> getOpinionRespondedList() {
		return opinionRespondedList;
	}

	public void setOpinionRespondedList(List<OpinionBO> opinionRespondedList) {
		this.opinionRespondedList = opinionRespondedList;
	}

	public List<OpinionBO> getOpinionRespondPendingList() {
		return opinionRespondPendingList;
	}

	public void setOpinionRespondPendingList(
			List<OpinionBO> opinionRespondPendingList) {
		this.opinionRespondPendingList = opinionRespondPendingList;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public ItemBO getItemBO() {
		return itemBO;
	}

	public void setItemBO(ItemBO itemBO) {
		this.itemBO = itemBO;
	}

	public long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}

	public long getDisLikeCount() {
		return disLikeCount;
	}

	public void setDisLikeCount(long disLikeCount) {
		this.disLikeCount = disLikeCount;
	}

	public long getMayBeCount() {
		return mayBeCount;
	}

	public void setMayBeCount(long mayBeCount) {
		this.mayBeCount = mayBeCount;
	}

	public long getOpinionRequestCount() {
		return opinionRequestCount;
	}

	public void setOpinionRequestCount(long opinionRequestCount) {
		this.opinionRequestCount = opinionRequestCount;
	}

	public long getOpinionResponseCount() {
		return opinionResponseCount;
	}

	public void setOpinionResponseCount(long opinionResponseCount) {
		this.opinionResponseCount = opinionResponseCount;
	}

	public String getOpinionRequestDate() {
		return opinionRequestDate;
	}

	public void setOpinionRequestDate(String opinionRequestDate) {
		this.opinionRequestDate = opinionRequestDate;
	}

	public ItemSelfieDetailsBO getItemSelfieDetailsBO() {
		return itemSelfieDetailsBO;
	}

	public void setItemSelfieDetailsBO(ItemSelfieDetailsBO itemSelfieDetailsBO) {
		this.itemSelfieDetailsBO = itemSelfieDetailsBO;
	}
}
