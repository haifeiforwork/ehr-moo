/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.member.model;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Cafe Member 객체
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: Member.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class Member extends BaseObject {

	private static final long serialVersionUID = 3065416231073855590L;

	/**
	 * 워크스페이스 ID
	 */
	private String cafeId;

	/**
	 * 워크스페이스 멤버 아이디
	 */
	private String memberId;

	/**
	 * 회원 소개글
	 */
	@NotEmpty
	@Size(min = 0, max = 500)
	private String memberIntroduction;

	/**
	 * 회원 등급
	 */
	private String memberLevel;

	/**
	 * 가입 신청일
	 */
	private Date joinApplyDate;

	/**
	 * 가입일
	 */
	private Date joinDate;

	/**
	 * 가입타입( 0: 본인가입, 1: 시샵 회원등록)
	 */
	private String joinType;

	/**
	 * 디폴트 워크스페이스 여부
	 */
	private int isDefault;

	/**
	 * 등록자ID
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
	 * 수정자 ID
	 */
	private String updaterId;

	/**
	 * 수정자 명
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private Date updateDate;

	/***********************
	 * 추가 사항
	 ***********************/

	/**
	 * 멤버조회용 : 멤버(시샵제외):member,전체:all,가입신청:associate
	 */
	private String memberType;

	/**
	 * 포탈 아이디
	 */
	private String portalId;

	/**
	 * 워크스페이스 명
	 */
	private String cafeName;

	/**
	 * 멤버명
	 */
	private String memberName;

	private String memberEnglishName;

	/**
	 * 팀명
	 */
	private String teamName;

	private String teamEnglishName;

	/**
	 * 멤버 직급명
	 */
	private String jobTitleName;

	private String jobTitleEnglishName;

	/**
	 * 저장 타입 ( 내용 수정 : modify, 승인:join, 등급 :level, 운영진 :manage, 디폴트 :
	 * defaultSet, 디폴트 초기화 :defautInit )
	 */
	private String updateType;

	private String mailId;

	// private String mailTitle;

	// private String mailContent;

	/**
	 * @return the cafeId
	 */
	public String getCafeId() {
		return cafeId;
	}

	/**
	 * @param cafeId the cafeId to set
	 */
	public void setCafeId(String cafeId) {
		this.cafeId = cafeId;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the memberIntroduction
	 */
	public String getMemberIntroduction() {
		return memberIntroduction;
	}

	/**
	 * @param memberIntroduction the memberIntroduction to set
	 */
	public void setMemberIntroduction(String memberIntroduction) {
		this.memberIntroduction = memberIntroduction;
	}

	/**
	 * @return the memberLevel
	 */
	public String getMemberLevel() {
		return memberLevel;
	}

	/**
	 * @param memberLevel the memberLevel to set
	 */
	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	/**
	 * @return the joinApplyDate
	 */
	public Date getJoinApplyDate() {
		return joinApplyDate;
	}

	/**
	 * @param joinApplyDate the joinApplyDate to set
	 */
	public void setJoinApplyDate(Date joinApplyDate) {
		this.joinApplyDate = joinApplyDate;
	}

	/**
	 * @return the joinDate
	 */
	public Date getJoinDate() {
		return joinDate;
	}

	/**
	 * @param joinDate the joinDate to set
	 */
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	/**
	 * @return the joinType
	 */
	public String getJoinType() {
		return joinType;
	}

	/**
	 * @param joinType the joinType to set
	 */
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	/**
	 * @return the isDefault
	 */
	public int getIsDefault() {
		return isDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
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
	 * @return the updaterId
	 */
	public String getUpdaterId() {
		return updaterId;
	}

	/**
	 * @param updaterId the updaterId to set
	 */
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	/**
	 * @return the updaterName
	 */
	public String getUpdaterName() {
		return updaterName;
	}

	/**
	 * @param updaterName the updaterName to set
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
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
	 * @return the cafeName
	 */
	public String getCafeName() {
		return cafeName;
	}

	/**
	 * @param cafeName the cafeName to set
	 */
	public void setCafeName(String cafeName) {
		this.cafeName = cafeName;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
	 * @return the jobTitleName
	 */
	public String getJobTitleName() {
		return jobTitleName;
	}

	/**
	 * @param jobTitleName the jobTitleName to set
	 */
	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	/**
	 * @return the portalId
	 */
	public String getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return the updateType
	 */
	public String getUpdateType() {
		return updateType;
	}

	/**
	 * @param updateType the updateType to set
	 */
	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	/**
	 * @return the memberType
	 */
	public String getMemberType() {
		return memberType;
	}

	/**
	 * @param memberType the memberType to set
	 */
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	/**
	 * @return the mailId
	 */
	public String getMailId() {
		return mailId;
	}

	/**
	 * @param mailId the mailId to set
	 */
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	/**
	 * @return the memberEnglishName
	 */
	public String getMemberEnglishName() {
		return memberEnglishName;
	}

	/**
	 * @param memberEnglishName the memberEnglishName to set
	 */
	public void setMemberEnglishName(String memberEnglishName) {
		this.memberEnglishName = memberEnglishName;
	}

	/**
	 * @return the teamEnglishName
	 */
	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	/**
	 * @param teamEnglishName the teamEnglishName to set
	 */
	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}

	/**
	 * @return the jobTitleEnglishName
	 */
	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	/**
	 * @param jobTitleEnglishName the jobTitleEnglishName to set
	 */
	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}

	/**
	 * @return the mailTitle
	 */
	// public String getMailTitle() {
	// return mailTitle;
	// }

	/**
	 * @param mailTitle the mailTitle to set
	 */
	// public void setMailTitle(String mailTitle) {
	// this.mailTitle = mailTitle;
	// }

	/**
	 * @return the mailContent
	 */
	// public String getMailContent() {
	// return mailContent;
	// }

	/**
	 * @param mailContent the mailContent to set
	 */
	// public void setMailContent(String mailContent) {
	// this.mailContent = mailContent;
	// }

}
