/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.model;

import java.util.Date;

/**
 * @author Satish Kolawale
 *
 */
public class HBOpinionReceivedDetail {
	
	private String respondedBuddyName = null;
	private Date date = null;
	private long respondedStatus = 0;
	public long getRespondedStatus() {
		return respondedStatus;
	}
	public void setRespondedStatus(long respondedStatus) {
		this.respondedStatus = respondedStatus;
	}
	private String description = null;
	
	
	/**
	 * @return the respondedBuddyName
	 */
	public String getRespondedBuddyName() {
		return respondedBuddyName;
	}
	/**
	 * @param respondedBuddyName the respondedBuddyName to set
	 */
	public void setRespondedBuddyName(String respondedBuddyName) {
		this.respondedBuddyName = respondedBuddyName;
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
	 * @return the respondedStatus
	 */
	/*public int getRespondedStatus() {
		return respondedStatus;
	}
	*//**
	 * @param l the respondedStatus to set
	 *//*
	public void setRespondedStatus(long l) {
		this.respondedStatus = l;
	}*/
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}	 
}