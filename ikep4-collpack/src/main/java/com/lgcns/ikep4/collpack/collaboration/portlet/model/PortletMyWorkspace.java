package com.lgcns.ikep4.collpack.collaboration.portlet.model;


import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * MyWorkspace PORTLET 모델
 *
 * @author happyi1018
 * @version $Id: PortletMyWorkspace.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class PortletMyWorkspace extends BaseObject{
	

	/**
	 *
	 */
	private static final long serialVersionUID = -5181742750685229926L;

	// WS ID
	private String workspaceId;
	// 포틀릿에서 선택한 WS ID
	private String selWorkspaceId;
	// WS IDS
	private List<String> workspaceIds;
	// WS 명
	private String workspaceName;
	// 등록자 ID
	private String registerId;
	// 등록자 이름
	private String registerName;
	private String registerEnglishName;
	// 등록일
	private Date registDate;
	// 수정자 ID
	private String updaterId;
	// 수정자 이름
	private String updaterName;
	// 수정일
	private Date updateDate;
	// 게시판 ID
	private String boardId;
	// 게시판 이름
	private String boardName;
	// 게시물 ID
	private String itemId;
	// 게시물 제목
	private String title;
	// 게시물 등록일
	private Date itemRegistDate;
	

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
	 * @return the workspaceIds
	 */
	public List<String> getWorkspaceIds() {
		return workspaceIds;
	}

	/**
	 * @param workspaceIds the workspaceIds to set
	 */
	public void setWorkspaceIds(List<String> workspaceIds) {
		this.workspaceIds = workspaceIds;
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
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the registDate
	 */
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}



	/**
	 * @return the selWorkspaceId
	 */
	public String getSelWorkspaceId() {
		return selWorkspaceId;
	}

	/**
	 * @param selWorkspaceId the selWorkspaceId to set
	 */
	public void setSelWorkspaceId(String selWorkspaceId) {
		this.selWorkspaceId = selWorkspaceId;
	}

	/**
	 * @return the itemRegistDate
	 */
	public Date getItemRegistDate() {
		return itemRegistDate;
	}

	/**
	 * @param itemRegistDate the itemRegistDate to set
	 */
	public void setItemRegistDate(Date itemRegistDate) {
		this.itemRegistDate = itemRegistDate;
	}

	/**
	 * @return the registerEnglishName
	 */
	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	/**
	 * @param registerEnglishName the registerEnglishName to set
	 */
	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
	}
	

	
}