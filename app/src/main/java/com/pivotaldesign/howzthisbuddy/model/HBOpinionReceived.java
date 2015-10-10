/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.model;

import java.util.Date;

/**
 * @author Satish Kolawale
 *
 */
public class HBOpinionReceived extends HBGivenResponse{
	
	private String productDescription = null;
	private float currentPrice = 0;
	private Date date = null;
	private int likeCount = 0;
	private int notSureCount = 0;
	private int disLikeCount = 0;
	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}
	/**
	 * @param productDescription the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	/**
	 * @return the currentPrice
	 */
	public float getCurrentPrice() {
		return currentPrice;
	}
	/**
	 * @param currentPrice the currentPrice to set
	 */
	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the likeCount
	 */
	public int getLikeCount() {
		return likeCount;
	}
	/**
	 * @param likeCount the likeCount to set
	 */
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	/**
	 * @return the notSureCount
	 */
	public int getNotSureCount() {
		return notSureCount;
	}
	/**
	 * @param notSureCount the notSureCount to set
	 */
	public void setNotSureCount(int notSureCount) {
		this.notSureCount = notSureCount;
	}
	/**
	 * @return the disLikeCount
	 */
	public int getDisLikeCount() {
		return disLikeCount;
	}
	/**
	 * @param disLikeCount the disLikeCount to set
	 */
	public void setDisLikeCount(int disLikeCount) {
		this.disLikeCount = disLikeCount;
	}
}