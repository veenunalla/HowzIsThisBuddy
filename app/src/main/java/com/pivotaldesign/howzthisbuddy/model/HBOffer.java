/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.model;

/**
 * @author Satish Kolawale
 *
 */
public class HBOffer {
	private int offerId = 0;
	private String offerName = null;
	private String offerDescription = null;
	
	
	/**
	 * @return the offerId
	 */
	public int getOfferId() {
		return offerId;
	}
	/**
	 * @param offerId the offerId to set
	 */
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}
	/**
	 * @return the offerName
	 */
	public String getOfferName() {
		return offerName;
	}
	/**
	 * @param offerName the offerName to set
	 */
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	/**
	 * @return the offerDescription
	 */
	public String getOfferDescription() {
		return offerDescription;
	}
	/**
	 * @param offerDescription the offerDescription to set
	 */
	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}
}