/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.model;

/**
 * @author Satish Kolawale
 *
 */
public class HBGiven {

	private int id;
	private String mobilenumber;
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	private String givenName = null;
	private int givenCount = 0;
	private int pendingCount = 0;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the givenName
	 */
	public String getGivenName() {
		return givenName;
	}
	/**
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	/**
	 * @return the givenCount
	 */
	public int getGivenCount() {
		return givenCount;
	}
	/**
	 * @param givenCount the givenCount to set
	 */
	public void setGivenCount(int givenCount) {
		this.givenCount = givenCount;
	}
	/**
	 * @return the pendingCount
	 */
	public int getPendingCount() {
		return pendingCount;
	}
	/**
	 * @param pendingCount the pendingCount to set
	 */
	public void setPendingCount(int pendingCount) {
		this.pendingCount = pendingCount;
	}
	
	
}
