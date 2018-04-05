/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.framework.validator.constraints.Tel;

/**
 * Person 주소록 등록된 사용자 VO
 *
 * @author 이형운
 * @version $Id: Person.java 16271 2011-08-18 07:06:14Z giljae $
 */
public class Person extends BaseObject {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -5853507515167125109L;

	/**
	 * 개인 주소록 사용자 ID
	 */
	private String personId;
	
	private String orgPersonId;
	
	/**
	 * 사용자명
	 */
	@NotNull
	@NotEmpty
	@Size(min=0, max=15)
	private String personName;
	
	/**
	 * 사용자명
	 */
	private String personEnglishName;
	
	/**
	 * 회사명
	 */
	//@NotNull
	//@NotEmpty
	@Size(min=0, max=20)
	private String companyName;
	
	/**
	 * 부서명
	 */
	//@NotNull
	//@NotEmpty
	@Size(min=0, max=20)
	private String teamName;
	
	/**
	 * 부서명
	 */
	private String teamEnglishName;
	
	/**
	 * 직급명
	 */
	//@NotNull
	//@NotEmpty
	@Size(min=0, max=20)
	private String jobRankName;
	
	/**
	 * 직급명
	 */
	private String jobRankEnglishName;
	
	/**
	 * 사무실 전화번호
	 */
	@Tel
	@Size(min=0, max=20)
	private String officePhoneno;
	
	/**
	 * 휴대폰 번호
	 */
	@Tel
	@Size(min=0, max=20)
	private String mobilePhoneno;
	
	/**
	 * EMAIL
	 */
	@Email
	@Size(min=0, max=50)
	private String mailAddress;
	
	/**
	 * 메모
	 */
	@Size(min=0, max=150)
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
	 * 주소 Type I 내부 사용자 O 외부 사용자
	 */
	private String userType;

	/**
	 * 속한 그룹의 ID
	 */
	private List<String> addrgroupIdList;
	
	/**
	 * 개별 조회시 소속 그룹의 ID
	 */
	private String addrgroupId;
	
	/**
	 * 개별 조회시 소속 그룹의 Name
	 */
	private String addrgroupName;
	
	/**
	 * 일괄 저장,수정시 사용하는 CheckFlag
	 */
	private String checkFlag;
	
	private String empNo;
	

	
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
	 * @return the addrgroupId
	 */
	public List<String> getAddrgroupIdList() {
		return addrgroupIdList;
	}

	/**
	 * @param addrgroupId the addrgroupId to set
	 */
	public void setAddrgroupIdList(List<String> addrgroupIdList) {
		this.addrgroupIdList = addrgroupIdList;
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
	 * @return the checkFlag
	 */
	public String getCheckFlag() {
		return checkFlag;
	}

	/**
	 * @param checkFlag the checkFlag to set
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getPersonEnglishName() {
		return personEnglishName;
	}

	public void setPersonEnglishName(String personEnglishName) {
		this.personEnglishName = personEnglishName;
	}

	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}

	public String getJobRankEnglishName() {
		return jobRankEnglishName;
	}

	public void setJobRankEnglishName(String jobRankEnglishName) {
		this.jobRankEnglishName = jobRankEnglishName;
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

	public String getOrgPersonId() {
		return orgPersonId;
	}

	public void setOrgPersonId(String orgPersonId) {
		this.orgPersonId = orgPersonId;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}


}

