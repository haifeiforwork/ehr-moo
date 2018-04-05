/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.search;

import java.util.Date;

import com.lgcns.ikep4.framework.web.SearchCondition;


/**
 * Person 주소록 등록된 사용자 VO
 * 
 * @author 이형운
 * @version $Id: PersonSearchCondition.java 13956 2011-05-31 03:59:54Z handul32
 *          $
 */
public class PersonSearchCondition extends SearchCondition {

	/**
	 *
	 */
	private static final long serialVersionUID = 3667141960808461753L;

	/**
	 * 개인 주소록 사용자 ID
	 */
	private String personId;

	/**
	 * 사용자명
	 */
	private String personName;

	/**
	 * Portal ID
	 */
	private String portalId;

	/**
	 * 사용자명
	 */
	private String personEnglishName;

	/**
	 * 회사명
	 */
	private String companyName;

	/**
	 * 부서명
	 */
	private String teamName;

	/**
	 * 직급명
	 */
	private String jobRankName;

	/**
	 * 사무실 전화번호
	 */
	private String officePhoneno;

	/**
	 * 휴대폰 번호
	 */
	private String mobilePhoneno;

	/**
	 * EMAIL
	 */
	private String mailAddress;

	/**
	 * 메모
	 */
	private String personMemo;

	/**
	 * user type 이 사내 인물인 경우 사용자 아이디
	 */
	private String companyUserId;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * 수정일
	 */
	private Date updateDate;

	/**
	 * 속해 있는 address group Id
	 */
	private String addrgroupId;

	/**
	 * 속해 있는 address group의 Name
	 */
	private String addrgroupName;

	/**
	 * 속해 있는 Group Type
	 */
	private String groupType;

	/**
	 * 주소 Type I 내부 사용자 O 외부 사용자
	 */
	private String userType;

	private String userTypeIn;

	private String userTypeOut;

	/**
	 * 카드 or 목록 페이지 타입
	 */
	private String viewMode;

	/**
	 * 검색 조건 값
	 */
	private String searchColumn;

	/**
	 * 검색 키워드
	 */
	private String searchKeyword;

	/**
	 * 인덱스 검색 키워드
	 */
	private String indexSearchText;

	/**
	 * 인덱스 검색 키워드의 Locale ;
	 */
	private String indexSearchLocale;

	/**
	 * 다국어 검색 필드
	 */
	private String fieldName;

	/**
	 * 사용자 로케일
	 */
	private String userLocaleCode;

	/**
	 * @return the personId
	 */
	public String getPersonId() {
		return personId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	/**
	 * @return the persionName
	 */
	public String getPersonName() {
		return personName;
	}

	/**
	 * @param persionName the persionName to set
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the jobRankName
	 */
	public String getJobRankName() {
		return jobRankName;
	}

	/**
	 * @param jobRankName the jobRankName to set
	 */
	public void setJobRankName(String jobRankName) {
		this.jobRankName = jobRankName;
	}

	/**
	 * @return the officePhoneno
	 */
	public String getOfficePhoneno() {
		return officePhoneno;
	}

	/**
	 * @param officePhoneno the officePhoneno to set
	 */
	public void setOfficePhoneno(String officePhoneno) {
		this.officePhoneno = officePhoneno;
	}

	/**
	 * @return the mobilePhoneno
	 */
	public String getMobilePhoneno() {
		return mobilePhoneno;
	}

	/**
	 * @param mobilePhoneno the mobilePhoneno to set
	 */
	public void setMobilePhoneno(String mobilePhoneno) {
		this.mobilePhoneno = mobilePhoneno;
	}

	/**
	 * @return the mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * @param mailAddress the mailAddress to set
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * @return the personMemo
	 */
	public String getPersonMemo() {
		return personMemo;
	}

	/**
	 * @param personMemo the personMemo to set
	 */
	public void setPersonMemo(String personMemo) {
		this.personMemo = personMemo;
	}

	/**
	 * @return the companyUserId
	 */
	public String getCompanyUserId() {
		return companyUserId;
	}

	/**
	 * @param companyUserId the companyUserId to set
	 */
	public void setCompanyUserId(String companyUserId) {
		this.companyUserId = companyUserId;
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
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
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
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the addrgroupId
	 */
	public String getAddrgroupId() {
		return addrgroupId;
	}

	/**
	 * @param addrgroupId the addrgroupId to set
	 */
	public void setAddrgroupId(String addrgroupId) {
		this.addrgroupId = addrgroupId;
	}

	/**
	 * @return the groupType
	 */
	public String getGroupType() {
		return groupType;
	}

	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the searchColumn
	 */
	public String getSearchColumn() {
		return searchColumn;
	}

	/**
	 * @param searchColumn the searchColumn to set
	 */
	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	/**
	 * @return the searchKeyword
	 */
	public String getSearchKeyword() {
		return searchKeyword;
	}

	/**
	 * @param searchKeyword the searchKeyword to set
	 */
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	/**
	 * @return the indexSearchText
	 */
	public String getIndexSearchText() {
		return indexSearchText;
	}

	/**
	 * @param indexSearchText the indexSearchText to set
	 */
	public void setIndexSearchText(String indexSearchText) {
		this.indexSearchText = indexSearchText;
	}

	/**
	 * @return the indexSearchLocale
	 */
	public String getIndexSearchLocale() {
		return indexSearchLocale;
	}

	/**
	 * @param indexSearchLocale the indexSearchLocale to set
	 */
	public void setIndexSearchLocale(String indexSearchLocale) {
		this.indexSearchLocale = indexSearchLocale;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getUserLocaleCode() {
		return userLocaleCode;
	}

	public void setUserLocaleCode(String userLocaleCode) {
		this.userLocaleCode = userLocaleCode;
	}

	/**
	 * @return the personEnglishName
	 */
	public String getPersonEnglishName() {
		return personEnglishName;
	}

	/**
	 * @param personEnglishName the personEnglishName to set
	 */
	public void setPersonEnglishName(String personEnglishName) {
		this.personEnglishName = personEnglishName;
	}

	/**
	 * @return the addrgroupName
	 */
	public String getAddrgroupName() {
		return addrgroupName;
	}

	/**
	 * @param addrgroupName the addrgroupName to set
	 */
	public void setAddrgroupName(String addrgroupName) {
		this.addrgroupName = addrgroupName;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getUserTypeIn() {
		return userTypeIn;
	}

	public void setUserTypeIn(String userTypeIn) {
		this.userTypeIn = userTypeIn;
	}

	public String getUserTypeOut() {
		return userTypeOut;
	}

	public void setUserTypeOut(String userTypeOut) {
		this.userTypeOut = userTypeOut;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

}
