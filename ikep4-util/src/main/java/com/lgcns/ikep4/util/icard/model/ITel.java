package com.lgcns.ikep4.util.icard.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * ITelVO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ITel.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class ITel implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 2952496141991967537L;

	/**
	 * 타입(HOME,WORK,CELL,FAX)
	 */
	private String type;

	/**
	 * 전화번호
	 */
	private String number;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
