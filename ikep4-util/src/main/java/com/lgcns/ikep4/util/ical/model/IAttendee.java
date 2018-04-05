package com.lgcns.ikep4.util.ical.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * IDurVO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: IAttendee.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class IAttendee extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 3254075437859358974L;

	/**
	 * 이름
	 */
	private String cn;

	/**
	 * 메일
	 */
	private String mail;

	/**
	 * 역활
	 */
	private String role;

	/**
	 * 사용자 ID
	 */
	private String member;

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

}
