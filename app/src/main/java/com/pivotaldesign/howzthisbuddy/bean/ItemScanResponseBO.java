package com.pivotaldesign.howzthisbuddy.bean;

public class ItemScanResponseBO extends HowZThisBuddyBaseBO {

	private static final long serialVersionUID = 1L;
	private static final String className = ItemScanResponseBO.class.getName();

	private String searchKey;
	private String itemDesc;
	private String itemTitle;
	private long vendorId;
	private String vendorProductId;
	private String productUrl;
	private double price;

	public ItemScanResponseBO() {

	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public long getVendorId() {
		return vendorId;
	}

	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorProductId() {
		return vendorProductId;
	}

	public void setVendorProductId(String vendorProductId) {
		this.vendorProductId = vendorProductId;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String toString() {
		return "className : " + className + "{ " + "\n }";
	}
}
