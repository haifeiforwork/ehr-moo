/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author happyi1018
 * @version $Id: BoardMigration.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class BoardMigration extends BaseObject {



	/**
	 *
	 */
	private static final long serialVersionUID = 3765875553440404927L;

	private String migrationId;
	
	/**
	 * WSID
	 */
	private String workspaceId;
	
	/**
	 * 게시판 ID
	 */
	private String boardId;
	
	/**
	 * 이관할 WSID
	 */
	private String targetWorkspaceId;
	
	/**
	 * 이관할 부모 게시판 ID
	 */
	private String targetParentBoardId;
	
	/**
	 * 이관 상태 (1:완료)
	 */
	private String migrationStatus;
	// 등록자
	private String registerId;
	// 등록자 명
	private String registerName;
	// 등록일
	private Date registDate;

	/**
	 * @return the migrationId
	 */
	public String getMigrationId() {
		return migrationId;
	}

	/**
	 * @param migrationId the migrationId to set
	 */
	public void setMigrationId(String migrationId) {
		this.migrationId = migrationId;
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
	 * @return the targetWorkspaceId
	 */
	public String getTargetWorkspaceId() {
		return targetWorkspaceId;
	}

	/**
	 * @param targetWorkspaceId the targetWorkspaceId to set
	 */
	public void setTargetWorkspaceId(String targetWorkspaceId) {
		this.targetWorkspaceId = targetWorkspaceId;
	}

	/**
	 * @return the targetParentBoardId
	 */
	public String getTargetParentBoardId() {
		return targetParentBoardId;
	}

	/**
	 * @param targetParentBoardId the targetParentBoardId to set
	 */
	public void setTargetParentBoardId(String targetParentBoardId) {
		this.targetParentBoardId = targetParentBoardId;
	}

	/**
	 * @return the migrationStatus
	 */
	public String getMigrationStatus() {
		return migrationStatus;
	}

	/**
	 * @param migrationStatus the migrationStatus to set
	 */
	public void setMigrationStatus(String migrationStatus) {
		this.migrationStatus = migrationStatus;
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
	
	
}
