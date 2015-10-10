/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.model;

/**
 * @author Satish Kolawale
 *
 */
public class HBGivenResponse {

	private String productUrl = null;
	private String productName = null;
	private String price = null;
	private String selfieUrl = null;
	private int givenStatus = 0;
	/**
	 * @return the productUrl
	 */
	public String getProductUrl() {
		return productUrl;
	}
	/**
	 * @param productUrl the productUrl to set
	 */
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the selfieUrl
	 */
	public String getSelfieUrl() {
		return selfieUrl;
	}
	/**
	 * @param selfieUrl the selfieUrl to set
	 */
	public void setSelfieUrl(String selfieUrl) {
		this.selfieUrl = selfieUrl;
	}
	/**
	 * @return the givenStatus
	 */
	public int getGivenStatus() {
		return givenStatus;
	}
	/**
	 * @param givenStatus the givenStatus to set
	 */
	public void setGivenStatus(int givenStatus) {
		this.givenStatus = givenStatus;
	}
}