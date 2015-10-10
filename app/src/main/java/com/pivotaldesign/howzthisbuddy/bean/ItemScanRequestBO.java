package com.pivotaldesign.howzthisbuddy.bean;

public class ItemScanRequestBO extends HowZThisBuddyBaseBO {

	private static final long serialVersionUID = 1L;
	private static final String className = ItemScanRequestBO.class.getName();

	public ItemScanRequestBO() {

	}

	private String searchKey;

	
	public String getSearchKey() {
		return searchKey;
	}


	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}


	public String toString() {
		return "className : " + className + "{ " + "\n }";
	}
}
