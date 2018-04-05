package com.lgcns.ikep4.support.addressbook.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Contact 대상 VO 
 *
 * @author 이형운
 * @version $Id: Contact.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class Contact extends BaseObject {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1345966113208118037L;

	/**
	 * CONTACT ID
	 */
	private String contactId;
	
	/**
	 * PF:프로파일 방문, BG:블로그 방문, MB:마이크로블로깅 홈 방문,  ML: 메일발송, MS: 메시지 발송, SM:문자 발송
	 */
	private String contactType;
	
	/**
	 * CONTACT한 대상자 ID
	 */
	private String contactUserId;
	
	/**
	 * CONTACT 대상자 이름
	 */
	private String contactUserName;
	
	/**
	 * CONTACT한 사용자 아이디
	 */
	private String registerId;
	
	/**
	 * CONTACT한 일시
	 */
	private Date registDate;
	
	/**
	 * CONTACT한 사용자 이름
	 */
	private String registerName;

	/**
	 * @return the contactId
	 */
	public String getContactId() {
		return contactId;
	}

	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	/**
	 * @return the contactType
	 */
	public String getContactType() {
		return contactType;
	}

	/**
	 * @param contactType the contactType to set
	 */
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	/**
	 * @return the contactUserId
	 */
	public String getContactUserId() {
		return contactUserId;
	}

	/**
	 * @param contactUserId the contactUserId to set
	 */
	public void setContactUserId(String contactUserId) {
		this.contactUserId = contactUserId;
	}

	/**
	 * @return the contactUserName
	 */
	public String getContactUserName() {
		return contactUserName;
	}

	/**
	 * @param contactUserName the contactUserName to set
	 */
	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	/**
	 * @return the registDate
	 */
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	/**
	 * @return the registName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registName the registName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	
	

}

