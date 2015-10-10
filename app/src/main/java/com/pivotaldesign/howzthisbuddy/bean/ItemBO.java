package com.pivotaldesign.howzthisbuddy.bean;

import java.math.BigDecimal;

public class ItemBO extends HowZThisBuddyBaseBO {

	private static final long serialVersionUID = 1L;
	private static final String className = ItemBO.class.getName();

	private long itemId;
	private String itemDesc;
	private String itemTitle;
	private long vendorId;
	private String vendorProductId;
	private String productUrl;
	//private BigDecimal price;
	private String price;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public ItemBO() {

	}

	public String getVendorProductId() {
		return vendorProductId;
	}

	public void setVendorProductId(String vendorProductId) {
		this.vendorProductId = vendorProductId;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public long getVendorId() {
		return vendorId;
	}

	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String toString() {
		return "className : " + className + "{\n itemDetailId:" + itemId
				+ "\n productId" + vendorProductId + "\n itemDesc" + itemDesc
				+ "\n vendorId" + vendorId + "\n productUrl" + productUrl
				+ "\n price" + price + "\n }";
	}
}
