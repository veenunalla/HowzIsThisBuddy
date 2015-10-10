package com.pivotaldesign.howzthisbuddy.bean;


public class HowZBuddyProfileBO extends VendorBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String className = HowZBuddyProfileBO.class.getName();

	private AddressBO addressBO;
	private ContactDetailsBO contactDetailsBO;

	public HowZBuddyProfileBO() {

	}

	public AddressBO getAddress() {
		return addressBO;
	}

	public void setAddress(AddressBO addressBO) {
		this.addressBO = addressBO;
	}

	public ContactDetailsBO getContactDetails() {
		return contactDetailsBO;
	}

	public void setContactDetails(ContactDetailsBO contactDetailsBO) {
		this.contactDetailsBO = contactDetailsBO;
	}

	public String toString() {
		return "className : " + className + "{\n AddressVO:" + addressBO.toString()
				+ " contactDetailsBO:" + contactDetailsBO.toString() + "\n}";
	}

}
