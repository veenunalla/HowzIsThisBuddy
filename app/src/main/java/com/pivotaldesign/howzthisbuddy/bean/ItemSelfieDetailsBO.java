package com.pivotaldesign.howzthisbuddy.bean;

public class ItemSelfieDetailsBO extends HowZThisBuddyBaseBO {

	private static final long serialVersionUID = 1L;
	private static final String className = ItemSelfieDetailsBO.class.getName();

	public ItemSelfieDetailsBO() {

	}

	private long itemSelfieDetailsId;
	private boolean itemSelfieDetailsFlag;
	private String selfiePic;

	public String getSelfiePic() {
		return selfiePic;
	}

	public void setSelfiePic(String selfiePic) {
		this.selfiePic = selfiePic;
	}

	public long getItemSelfieDetailsId() {
		return itemSelfieDetailsId;
	}

	public void setItemSelfieDetailsId(long itemSelfieDetailsId) {
		this.itemSelfieDetailsId = itemSelfieDetailsId;
	}

	public boolean isItemSelfieDetailsFlag() {
		return itemSelfieDetailsFlag;
	}

	public void setItemSelfieDetailsFlag(boolean itemSelfieDetailsFlag) {
		this.itemSelfieDetailsFlag = itemSelfieDetailsFlag;
	}

	
}
