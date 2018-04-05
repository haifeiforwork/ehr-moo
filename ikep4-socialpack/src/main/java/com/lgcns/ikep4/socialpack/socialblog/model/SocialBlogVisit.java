/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 소셜블로그 방문기록 정보를 위한 VO
 *
 * @author 이형운
 * @version $Id: SocialBlogVisit.java 19512 2012-06-26 09:36:14Z malboru80 $
 */
public class SocialBlogVisit extends BaseObject {

	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = -876862103903532551L;

	/**
	 * 소셜 블로그 ID
	 */
	private String blogId;
	
	/**
	 * 소셜블로그 방문자 ID
	 */
	private String visitorId;
	
	/**
	 * 소셜블로그 방문일시
	 */
	private Date visitDate;

	/**
	 * 방문자 정보
	 */
	private User visitor;
	
	/**
	 * 방문자 정보 조회시 사용 Flag
	 * '' : 전체, TODAY : 오늘, WEEK : 이번주, MONTH : 이번달, YEAR : 올해
	 */
	private String visitFlag;
	
	/**
	 * 최근 방문자 조회 수
	 */
	private Integer recentVisitCnt;
	
	/**
	 * 방문자의 이름
	 */
	private String userName;
	
	/**
	 * 방문자의 영문 이름
	 */
	private String userEnglishName;
	
	/**
	 * 방문자의 호칭명
	 */
	private String visitorJobTitleName;
	
	/**
	 * 방문자의 영문 호칭명
	 */
	private String visitorJobTitleEngName;
	
	/**
	 * 방문자의 팀명
	 */
	private String visitorTeamName;
	
	/**
	 * 방문자의 영문 팀명
	 */
	private String visitorTeanEngName;
	
	/**
	 * 로그인 한 사용자의 Locale Code
	 */
	private String sessUserLocale;
	
	
	/**
	 * 방문 통계 관리 화면에서 일자별 월별 방문자 카운트용 기준 일자
	 * yyyy.MM.dd 타입
	 */
	private String visitBaseDate;
	
	/**
	 * 방문 통계 관리 화면에서 일자별 월별 방문자 카운트
	 */
	private Integer visitorCount;
	
	/**
	 * 방문 통계 관리 화면에서 조회 기준 일자 또는 기준 월
	 */
	private String baseDate;
	
	/**
	 * 방문 통계 관리 화면에서 조회 기준이 되는 타입 값 
	 * DAILY : 일별 조회, MONTH : 월별 조회
	 */
	private String baseDateType;
	
	private String blogOwnerId;
	
	public String getBlogOwnerId() {
		return blogOwnerId;
	}

	public void setBlogOwnerId(String blogOwnerId) {
		this.blogOwnerId = blogOwnerId;
	}
	
	/**
	 * @return the blogId
	 */
	public String getBlogId() {
		return blogId;
	}

	/**
	 * @param blogId the blogId to set
	 */
	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}

	/**
	 * @return the visitorId
	 */
	public String getVisitorId() {
		return visitorId;
	}

	/**
	 * @param visitorId the visitorId to set
	 */
	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	/**
	 * @return the visitDate
	 */
	public Date getVisitDate() {
		return visitDate;
	}

	/**
	 * @param visitDate the visitDate to set
	 */
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	/**
	 * @return the visitor
	 */
	public User getVisitor() {
		return visitor;
	}

	/**
	 * @param visitor the visitor to set
	 */
	public void setVisitor(User visitor) {
		this.visitor = visitor;
	}

	/**
	 * @return the visitFlag
	 */
	public String getVisitFlag() {
		return visitFlag;
	}

	/**
	 * @param visitFlag the visitFlag to set
	 */
	public void setVisitFlag(String visitFlag) {
		this.visitFlag = visitFlag;
	}

	/**
	 * @return the recentVisitCnt
	 */
	public Integer getRecentVisitCnt() {
		return recentVisitCnt;
	}

	/**
	 * @param recentVisitCnt the recentVisitCnt to set
	 */
	public void setRecentVisitCnt(Integer recentVisitCnt) {
		this.recentVisitCnt = recentVisitCnt;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userEnglishName
	 */
	public String getUserEnglishName() {
		return userEnglishName;
	}

	/**
	 * @param userEnglishName the userEnglishName to set
	 */
	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	/**
	 * @return the visitorJobTitleName
	 */
	public String getVisitorJobTitleName() {
		return visitorJobTitleName;
	}

	/**
	 * @param visitorJobTitleName the visitorJobTitleName to set
	 */
	public void setVisitorJobTitleName(String visitorJobTitleName) {
		this.visitorJobTitleName = visitorJobTitleName;
	}

	/**
	 * @return the visitorTeamName
	 */
	public String getVisitorTeamName() {
		return visitorTeamName;
	}

	/**
	 * @param visitorTeamName the visitorTeamName to set
	 */
	public void setVisitorTeamName(String visitorTeamName) {
		this.visitorTeamName = visitorTeamName;
	}

	/**
	 * @return the visitorTeanEngName
	 */
	public String getVisitorTeanEngName() {
		return visitorTeanEngName;
	}

	/**
	 * @param visitorTeanEngName the visitorTeanEngName to set
	 */
	public void setVisitorTeanEngName(String visitorTeanEngName) {
		this.visitorTeanEngName = visitorTeanEngName;
	}

	/**
	 * @return the sessUserLocale
	 */
	public String getSessUserLocale() {
		return sessUserLocale;
	}

	/**
	 * @param sessUserLocale the sessUserLocale to set
	 */
	public void setSessUserLocale(String sessUserLocale) {
		this.sessUserLocale = sessUserLocale;
	}

	/**
	 * @return the visitorJobTitleEngName
	 */
	public String getVisitorJobTitleEngName() {
		return visitorJobTitleEngName;
	}

	/**
	 * @param visitorJobTitleEngName the visitorJobTitleEngName to set
	 */
	public void setVisitorJobTitleEngName(String visitorJobTitleEngName) {
		this.visitorJobTitleEngName = visitorJobTitleEngName;
	}
	
	

	/**
	 * @return the visitorBaseDate
	 */
	public String getVisitBaseDate() {
		return visitBaseDate;
	}

	/**
	 * @param visitorBaseDate the visitorBaseDate to set
	 */
	public void setVisitBaseDate(String visitBaseDate) {
		this.visitBaseDate = visitBaseDate;
	}

	/**
	 * @return the visitorCount
	 */
	public Integer getVisitorCount() {
		return visitorCount;
	}

	/**
	 * @param visitorCount the visitorCount to set
	 */
	public void setVisitorCount(Integer visitorCount) {
		this.visitorCount = visitorCount;
	}

	/**
	 * @return the baseDate
	 */
	public String getBaseDate() {
		return baseDate;
	}

	/**
	 * @param baseDate the baseDate to set
	 */
	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}

	/**
	 * @return the baseDateType
	 */
	public String getBaseDateType() {
		return baseDateType;
	}

	/**
	 * @param baseDateType the baseDateType to set
	 */
	public void setBaseDateType(String baseDateType) {
		this.baseDateType = baseDateType;
	}

}
