package com.pivotaldesign.howzthisbuddy.bean;
public class AddressBO extends HowZThisBuddyBaseBO {

	private static final long serialVersionUID = 1L;
	private static final String className = AddressBO.class.getName();

	
	private String addrLine1;
	private String addrLine2;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private String zipExtn;

	public AddressBO() {
	}

	public String getAddrLine1() {
		return addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public String getAddrLine2() {
		return addrLine2;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getZipExtn() {
		return zipExtn;
	}

	public void setZipExtn(String zipExtn) {
		this.zipExtn = zipExtn;
	}

	public String toString() {
		return "className :  " + className + "{\n addrLine1:" + addrLine1
				+ " addrLine2:" + addrLine2 + " city:" + city + " state:"
				+ state + " country:" + country + " zipCode:" + zipCode
				+ " zipExtn:" + zipExtn + "\n }";
	}
}