/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 
 * InvitedMbgroupMember VO
 *
 * @author 최성우
 * @version $Id: InvitingInfo.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class InvitingInfo extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * serial Version UID
	 */

	/**
	 * 멤버의 유저 아이디
	 */
	private String memberId;

	/**
	 * 마이크로블로깅그룹 아이디
	 */
	private String mbgroupId;

	/**
	 * 마이크로블로깅그룹명
	 */
	private String mbgroupName;
	
	/**
	 * STATUS
	 */
	private String status;

	/**
	 * 등록자 아이디
	 */
	private String registerId;

	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록자 이름(영문)
	 */
	private String registerEnglishName;
	
	/**
	 * 등록일
	 */
	private Date registDate;


	/**
	 * 멤버의 유저 이름
	 */
	private String memberName;
	
	/**
	 * 멤버의 유저 영문이름
	 */
	private String memberEnglishName;
	
	/**
	 * 멤버의 유저가 해당된 부서(그룹) 이름
	 */
	private String teamName;

	/**
	 * 멤버의 유저가 해당된 부서(그룹) 영문이름
	 */
	private String teamEnglishName;

	
	/**
	 * 해당 호칭코드의 이름
	 */
	private String jobTitleName;
	
	/**
	 * 해당 호칭코드의 영문이름
	 */
	private String jobTitleEnglishName;
	
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMbgroupId() {
		return mbgroupId;
	}

	public void setMbgroupId(String mbgroupId) {
		this.mbgroupId = mbgroupId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public String getMbgroupName() {
		return mbgroupName;
	}

	public void setMbgroupName(String mbgroupName) {
		this.mbgroupName = mbgroupName;
	}

	public String getMemberEnglishName() {
		return memberEnglishName;
	}

	public void setMemberEnglishName(String memberEnglishName) {
		this.memberEnglishName = memberEnglishName;
	}

	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}

	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}

}
