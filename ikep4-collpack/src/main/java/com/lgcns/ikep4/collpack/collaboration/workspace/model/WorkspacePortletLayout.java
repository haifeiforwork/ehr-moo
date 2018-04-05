/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 개별 Workspace의 포틀릿 레이아웃
 *
 * @author 김종철
 * @version $Id: WorkspacePortletLayout.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class WorkspacePortletLayout extends BaseObject {



	/**
	 *
	 */
	private static final long serialVersionUID = -2124074720756342543L;

	/**
	 * 포틀릿_배치_ID
	 */
	private String portletLayoutId;
	
	
	/**
	 * 포틀릿_ID
	 */
	private String portletId;
	
	/**
	 * 컬럼 인덱스
	 */
	private Integer colIndex;
	
	/**
	 * 행 인덱스(정렬 순서)
	 */
	private Integer rowIndex;
	/**
	 * 게시판 여부
	 */
	private Integer isBoardPortlet;
	/**
	 * WS ID
	 */
	private String workspaceId;
	/**
	 * 게시판 ID
	 */
	private String boardId;
	
	/**
	 * 해당 포틀릿 레이아웃의 포틀릿 정보
	 */
	private WorkspacePortlet workspacePortlet;

	/**
	 * @return the portletLayoutId
	 */
	public String getPortletLayoutId() {
		return portletLayoutId;
	}

	/**
	 * @param portletLayoutId the portletLayoutId to set
	 */
	public void setPortletLayoutId(String portletLayoutId) {
		this.portletLayoutId = portletLayoutId;
	}

	/**
	 * @return the portletId
	 */
	public String getPortletId() {
		return portletId;
	}

	/**
	 * @param portletId the portletId to set
	 */
	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}

	/**
	 * @return the colIndex
	 */
	public Integer getColIndex() {
		return colIndex;
	}

	/**
	 * @param colIndex the colIndex to set
	 */
	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}

	/**
	 * @return the rowIndex
	 */
	public Integer getRowIndex() {
		return rowIndex;
	}

	/**
	 * @param rowIndex the rowIndex to set
	 */
	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * @return the workspacePortlet
	 */
	public WorkspacePortlet getWorkspacePortlet() {
		return workspacePortlet;
	}

	/**
	 * @param workspacePortlet the workspacePortlet to set
	 */
	public void setWorkspacePortlet(WorkspacePortlet workspacePortlet) {
		this.workspacePortlet = workspacePortlet;
	}

	/**
	 * @return the isBoardPortlet
	 */
	public Integer getIsBoardPortlet() {
		return isBoardPortlet;
	}

	/**
	 * @param isBoardPortlet the isBoardPortlet to set
	 */
	public void setIsBoardPortlet(Integer isBoardPortlet) {
		this.isBoardPortlet = isBoardPortlet;
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


}
