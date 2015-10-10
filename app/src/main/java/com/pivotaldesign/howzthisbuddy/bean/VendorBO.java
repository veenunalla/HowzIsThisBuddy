package com.pivotaldesign.howzthisbuddy.bean;

public class VendorBO extends HowZThisBuddyBaseBO {

	private static final long serialVersionUID = 1L;
	private static final String className = VendorBO.class.getName();

	private long vendorId;
	private String vendorName;

	public VendorBO() {

	}

	public long getVendorId() {
		return vendorId;
	}

	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String toString() {
		return "className : " + className + "{\n vendorId:" + vendorId
				+ " vendorName:" + vendorName + "\n}";
	}
}
