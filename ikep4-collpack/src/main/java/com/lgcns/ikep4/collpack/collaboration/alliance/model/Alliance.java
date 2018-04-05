/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.alliance.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 동맹 요청 객체정의
 * 
 * @author 김종철
 * @version $Id: Alliance.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class Alliance extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 6484549536479596261L;

	/**
	 * AllianceId
	 */
	private String allianceId;

	/**
	 * 요청한 WORKSPACE ID(현재 Workspace)
	 */
	private String workspaceId;

	/**
	 * 동맹 요청받은 WORKSPACE ID
	 */
	private String toWorkspaceId;

	/**
	 * 동맹 요청 사유
	 */
	@NotEmpty
	@Size(min=0,max=500)
	private String requestReason;

	/**
	 * 상태 ( 0: 동맹요청, 1: 동맹승인, 2: 동맹거부, 3: 동맹종료 )
	 */
	private String status;

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

	/**************************
	 * 추가 사항
	 *************************/
	
	private String strDate;

	/**
	 * 동맹 승인일
	 */
	private Date openDate;

	/**
	 * 팀 아이디
	 */
	private String teamId;

	/**
	 * 팀명(그룹명)
	 */
	private String groupName;

	/** 
	 * 팀 영문명
	 */
	private String groupEnglishName;
	/**
	 * 포탈 아이디
	 */
	private String portalId;

	/**
	 * workspace 명
	 */
	private String workspaceName;

	/**
	 * workspace 타입
	 */
	private String typeId;

	/**
	 * workspace 타입명
	 */
	private String typeName;
	
	/**
	 * 타입 영문명
	 */
	private String typeEnglishName;

	/**
	 * workspace 소개
	 */
	private String workspaceDescription;

	/**
	 * 시샵아이디
	 */
	private String sysopId;

	/**
	 * 시샵명
	 */
	private String sysopName;
	/**
	 * 시샵 영문명
	 */
	private String sysopEnglishName;

	/**
	 * 게시판 아이디
	 */
	private String boardId;
	
	/**
	 * 게시판 이름
	 */
	private String boardName;
	
	/**
	 * 동맹에게 공유한 목록
	 */
	private List<Alliance> giveAllianceList;
	/**
	 * 동맹에게 공유받은 목록
	 */
	private List<Alliance> receiveAllianceList;	
	/**
	 * @return the allianceId
	 */
	public String getAllianceId() {
		return allianceId;
	}

	/**
	 * @param allianceId the allianceId to set
	 */
	public void setAllianceId(String allianceId) {
		this.allianceId = allianceId;
	}

	/**
	 * @return the workspaceId
	 */
	public String getWorkspaceId() {
		return workspaceId;
	}

	/**
	 * @param workspaceId the workspaceId to set
	 */
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	/**
	 * @return the toWorkspaceId
	 */
	public String getToWorkspaceId() {
		return toWorkspaceId;
	}

	/**
	 * @param toWorkspaceId the toWorkspaceId to set
	 */
	public void setToWorkspaceId(String toWorkspaceId) {
		this.toWorkspaceId = toWorkspaceId;
	}

	/**
	 * @return the requestReason
	 */
	public String getRequestReason() {
		return requestReason;
	}

	/**
	 * @param requestReason the requestReason to set
	 */
	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the workspaceName
	 */
	public String getWorkspaceName() {
		return workspaceName;
	}

	/**
	 * @param workspaceName the workspaceName to set
	 */
	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the workspaceDescription
	 */
	public String getWorkspaceDescription() {
		return workspaceDescription;
	}

	/**
	 * @param workspaceDescription the workspaceDescription to set
	 */
	public void setWorkspaceDescription(String workspaceDescription) {
		this.workspaceDescription = workspaceDescription;
	}

	/**
	 * @return the sysopId
	 */
	public String getSysopId() {
		return sysopId;
	}

	/**
	 * @param sysopId the sysopId to set
	 */
	public void setSysopId(String sysopId) {
		this.sysopId = sysopId;
	}

	/**
	 * @return the sysopName
	 */
	public String getSysopName() {
		return sysopName;
	}

	/**
	 * @param sysopName the sysopName to set
	 */
	public void setSysopName(String sysopName) {
		this.sysopName = sysopName;
	}

	/**
	 * @return the strDate
	 */
	public String getStrDate() {
		return strDate;
	}

	/**
	 * @param strDate the strDate to set
	 */
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	/**
	 * @return the openDate
	 */
	public Date getOpenDate() {
		return openDate;
	}

	/**
	 * @param openDate the openDate to set
	 */
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	/**
	 * @return the teamId
	 */
	public String getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the boardId
	 */
	public String getBoardId() {
		return boardId;
	}

	/**
	 * @param boardId the boardId to set
	 */
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	/**
	 * @return the boardName
	 */
	public String getBoardName() {
		return boardName;
	}

	/**
	 * @param boardName the boardName to set
	 */
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	/**
	 * @return the groupEnglishName
	 */
	public String getGroupEnglishName() {
		return groupEnglishName;
	}

	/**
	 * @param groupEnglishName the groupEnglishName to set
	 */
	public void setGroupEnglishName(String groupEnglishName) {
		this.groupEnglishName = groupEnglishName;
	}

	/**
	 * @return the typeEnglishName
	 */
	public String getTypeEnglishName() {
		return typeEnglishName;
	}

	/**
	 * @param typeEnglishName the typeEnglishName to set
	 */
	public void setTypeEnglishName(String typeEnglishName) {
		this.typeEnglishName = typeEnglishName;
	}

	/**
	 * @return the sysopEnglishName
	 */
	public String getSysopEnglishName() {
		return sysopEnglishName;
	}

	/**
	 * @param sysopEnglishName the sysopEnglishName to set
	 */
	public void setSysopEnglishName(String sysopEnglishName) {
		this.sysopEnglishName = sysopEnglishName;
	}

	/**
	 * @return the giveAllianceList
	 */
	public List<Alliance> getGiveAllianceList() {
		return giveAllianceList;
	}

	/**
	 * @param giveAllianceList the giveAllianceList to set
	 */
	public void setGiveAllianceList(List<Alliance> giveAllianceList) {
		this.giveAllianceList = giveAllianceList;
	}

	/**
	 * @return the receiveAllianceList
	 */
	public List<Alliance> getReceiveAllianceList() {
		return receiveAllianceList;
	}

	/**
	 * @param receiveAllianceList the receiveAllianceList to set
	 */
	public void setReceiveAllianceList(List<Alliance> receiveAllianceList) {
		this.receiveAllianceList = receiveAllianceList;
	}



}
