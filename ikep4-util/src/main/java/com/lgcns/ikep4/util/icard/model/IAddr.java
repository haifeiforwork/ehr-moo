package com.lgcns.ikep4.util.icard.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * IAddrVO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: IAddr.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class IAddr implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5837258263927435290L;

	/**
	 * 타입(HOME,WORK)
	 */
	private String type;

	/**
	 * 
	 */
	private String poBox;

	/**
	 * 
	 */
	private String extended;

	/**
	 * 상세주소
	 */
	private String street;

	/**
	 * 동명
	 */
	private String locality;

	/**
	 * 구명
	 */
	private String region;

	/**
	 * 우편번호
	 */
	private String postcode;

	/**
	 * 국가
	 */
	private String country;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPoBox() {
		return poBox;
	}

	public void setPoBox(String poBox) {
		this.poBox = poBox;
	}

	public String getExtended() {
		return extended;
	}

	public void setExtended(String extended) {
		this.extended = extended;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
