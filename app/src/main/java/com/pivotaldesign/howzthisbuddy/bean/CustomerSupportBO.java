package com.pivotaldesign.howzthisbuddy.bean;

public class CustomerSupportBO extends UserBO {

	private static final long serialVersionUID = 1L;
	private static final String className = CustomerSupportBO.class.getName();

	private String query;
	private String resolution;

	public CustomerSupportBO() {

	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String toString() {
		return "className : " + className + "{\n query:" + query
				+ " resolution:" + resolution + "\n}";
	}
}
