/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.guestbook.search;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.support.guestbook.model.Guestbook;

/**
 * 방명록 작성 글 Model Object
 *
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: GuestbookSearchCondition.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class GuestbookSearchCondition extends SearchCondition {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7303699135036702146L;
	
	/**
	 * 방명록 ID
	 */
	private String guestbookId;
	
	/**
	 * 방명록 게시물 부모 
	 */
	private String parentId;
	
	/**
	 * 방명록 게시물 그룹 ID
	 */
	private String groupId;
	
	/**
	 * 같은 Thread 그룹에 속해있는 게시물들 간의 순서. 같은 Thread에서 단순 정렬 순서
	 */
	private Integer step;
	
	/**
	 * 게시물 Thread 표시할때 들여쓰기의 숫자
	 */
	private Integer indentation;
	
	/**
	 * 방명록 Message
	 */
	private String contents;
	
	/**
	 * 방명록 대상자 ID
	 */
	private String targetUserId;
	
	/**
	 * 방명록 작성자 ID
	 */
	private String registerId;
	
	/**
	 * 방명록 대상자 이름
	 */
	private String registerName;
	
	/**
	 * 방명록 작성일자
	 */
	private Date registDate;
	
	/**
	 * 방명록 작성자 한글 호칭
	 */
	private String registerJobTitleName;
	
	/**
	 * 방명록 작성자 영문호칭
	 */
	private String registerJobTitleEngName;
	
	/**
	 * 방명록 작성자 팀명
	 */
	private String registerTeamName;
	
	/**
	 * 방명록 작성자 영문 팀명
	 */
	private String registerTeamEngName;


	/**
	 * 방명록 댓글 수
	 */
	private int guestbookLineReplyCnt;
	
	/**
	 * 방명록 답글 목록
	 */
	private List<Guestbook> guestbookList;
	
	/**
	 * 방명록 작성자의 이름
	 */
	private String guestbookUserName;
	
	/**
	 * 방명록 작성자의 영문이름
	 */
	private String guestbookUserEngName;
	
	/**
	 * 로그인 한 사용자 세션의 Locale 정보
	 */
	private String sessUserLocale;
	
	/**
	 * @return the id
	 */
	public String getGuestbookId() {
		return guestbookId;
	}
	/**
	 * @param id the id to set
	 */
	public void setGuestbookId(String guestbookId) {
		this.guestbookId = guestbookId;
	}
	/**
	 * @return the message
	 */
	public String getContents() {
		return contents;
	}
	/**
	 * @param message the message to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	/**
	 * @return the targetUserId
	 */
	public String getTargetUserId() {
		return targetUserId;
	}
	/**
	 * @param targetUserId the targetUserId to set
	 */
	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
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
	 * @return the registerTeamName
	 */
	public String getRegisterTeamName() {
		return registerTeamName;
	}
	/**
	 * @param registerTeamName the registerTeamName to set
	 */
	public void setRegisterTeamName(String registerTeamName) {
		this.registerTeamName = registerTeamName;
	}
	/**
	 * @return the guestbookLineReplySize
	 */
	public int getGuestbookLineReplyCnt() {
		return guestbookLineReplyCnt;
	}
	/**
	 * @param guestbookLineReplyCnt the guestbookLineReplyCnt to set
	 */
	public void setGuestbookLineReplyCnt(int guestbookLineReplyCnt) {
		this.guestbookLineReplyCnt = guestbookLineReplyCnt;
	}
	
	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the step
	 */
	public Integer getStep() {
		return step;
	}
	/**
	 * @param step the step to set
	 */
	public void setStep(Integer step) {
		this.step = step;
	}
	/**
	 * @return the indentation
	 */
	public Integer getIndentation() {
		return indentation;
	}
	/**
	 * @param indentation the indentation to set
	 */
	public void setIndentation(Integer indentation) {
		this.indentation = indentation;
	}
	/**
	 * @return the guestbookUserName
	 */
	public String getGuestbookUserName() {
		return guestbookUserName;
	}
	/**
	 * @param guestbookUserName the guestbookUserName to set
	 */
	public void setGuestbookUserName(String guestbookUserName) {
		this.guestbookUserName = guestbookUserName;
	}
	/**
	 * @return the guestbookUserEngName
	 */
	public String getGuestbookUserEngName() {
		return guestbookUserEngName;
	}
	/**
	 * @param guestbookUserEngName the guestbookUserEngName to set
	 */
	public void setGuestbookUserEngName(String guestbookUserEngName) {
		this.guestbookUserEngName = guestbookUserEngName;
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
	 * @return the registerTeamEngName
	 */
	public String getRegisterTeamEngName() {
		return registerTeamEngName;
	}
	/**
	 * @param registerTeamEngName the registerTeamEngName to set
	 */
	public void setRegisterTeamEngName(String registerTeamEngName) {
		this.registerTeamEngName = registerTeamEngName;
	}
	/**
	 * @return the guestbookList
	 */
	public List<Guestbook> getGuestbookList() {
		return guestbookList;
	}
	/**
	 * @param guestbookList the guestbookList to set
	 */
	public void setGuestbookList(List<Guestbook> guestbookList) {
		this.guestbookList = guestbookList;
	}
	/**
	 * @return the registerJobTitleName
	 */
	public String getRegisterJobTitleName() {
		return registerJobTitleName;
	}
	/**
	 * @param registerJobTitleName the registerJobTitleName to set
	 */
	public void setRegisterJobTitleName(String registerJobTitleName) {
		this.registerJobTitleName = registerJobTitleName;
	}
	/**
	 * @return the registerJobTitleEngName
	 */
	public String getRegisterJobTitleEngName() {
		return registerJobTitleEngName;
	}
	/**
	 * @param registerJobTitleEngName the registerJobTitleEngName to set
	 */
	public void setRegisterJobTitleEngName(String registerJobTitleEngName) {
		this.registerJobTitleEngName = registerJobTitleEngName;
	}

	
}
