package com.lgcns.ikep4.collpack.kms.main.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class KmsPortletLayout extends BaseObject {

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
	
	private Integer isDefault;
	
	/**
	 * 게시판 ID
	 */
	private String boardId;
	
	/**
	 * 해당 포틀릿 레이아웃의 포틀릿 정보
	 */
	private KmsPortlet kmsPortlet;

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
	 * @return the kmsPortlet
	 */
	public KmsPortlet getKmsPortlet() {
		return kmsPortlet;
	}

	/**
	 * @param kmsPortlet the kmsPortlet to set
	 */
	public void setKmsPortlet(KmsPortlet kmsPortlet) {
		this.kmsPortlet = kmsPortlet;
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

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}


}
