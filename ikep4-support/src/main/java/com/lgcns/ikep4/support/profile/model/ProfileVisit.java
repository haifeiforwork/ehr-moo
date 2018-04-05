/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.profile.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 프로파일 방문자 기록용 VO
 *
 * @author 이형운
 * @version $Id: ProfileVisit.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class ProfileVisit extends BaseObject {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2874077147288374741L;

	/**
	 * 프로파일 대상자 ID
	 */
	private String ownerId;
	
	/**
	 * 프로파일 방문자  방문자 ID
	 */
	private String visitorId;
	
	/**
	 * 프로파일 방문자  방문일시
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
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
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
	
}
