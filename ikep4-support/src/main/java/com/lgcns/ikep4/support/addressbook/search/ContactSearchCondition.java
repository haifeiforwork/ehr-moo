package com.lgcns.ikep4.support.addressbook.search;

import java.util.Date;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * Contact 대상 VO 
 *
 * @author 이형운
 * @version $Id: ContactSearchCondition.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class ContactSearchCondition extends SearchCondition {


	/**
	 *
	 */
	private static final long serialVersionUID = 8521736401018629389L;

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
	 * 한번에 가지고 오는 페이지 사이즈
	 */
	private Integer fetchSize;
	
	/**
	 * 조회 기준 contactId
	 */
	private String searchContactId;
	
	/**
	 * 조회 기준 Type pre : 앞으로 prepend, post : 뒤로 append 
	 */
	private String searchType;
	
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

	/**
	 * @return the fetchSize
	 */
	public Integer getFetchSize() {
		return fetchSize;
	}

	/**
	 * @param fetchSize the fetchSize to set
	 */
	public void setFetchSize(Integer fetchSize) {
		this.fetchSize = fetchSize;
	}

	/**
	 * @return the searchContactId
	 */
	public String getSearchContactId() {
		return searchContactId;
	}

	/**
	 * @param searchContactId the searchContactId to set
	 */
	public void setSearchContactId(String searchContactId) {
		this.searchContactId = searchContactId;
	}

	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

}

