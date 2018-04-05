/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspaceMap.tree;

import com.lgcns.ikep4.collpack.common.tree.TreeNodeAttr;

/**
 * Tree Node Attr model
 * Knowledge Map 용
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: TreeNodeAttrWorkspaceMap.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class TreeNodeAttrWorkspaceMap extends TreeNodeAttr{

	/**
	 * 정렬순서
	 */
	private int order;

	/**
	 * WorkspaceId
	 */
	private String workspaceId;
	/**
	 * Blog 검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isBlog;

	/**
	 * Team Collaboration 검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isColl;

	/**
	 * 업무매뉴얼  검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isManual;

	/**
	 * QNA  검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isQna;

	/**
	 * 용어사전  검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isDic;

	/**
	 * Cafe  검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isCafe;

	/**
	 * Forum  검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isForum;

	/**
	 * Idea  검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isIdea;

	/**
	 * BBS 검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isBbs;

	/**
	 * Tags (,로 분리된)
	 */
	private String tags;
	
	/**
	 * Map Desc
	 */
	private String mapDescription;
	
	/**
	 * @return the isBlog
	 */
	public int getIsBlog() {
		return isBlog;
	}

	/**
	 * @param isBlog the isBlog to set
	 */
	public void setIsBlog(int isBlog) {
		this.isBlog = isBlog;
	}

	/**
	 * @return the isColl
	 */
	public int getIsColl() {
		return isColl;
	}

	/**
	 * @param isColl the isColl to set
	 */
	public void setIsColl(int isColl) {
		this.isColl = isColl;
	}

	/**
	 * @return the isManual
	 */
	public int getIsManual() {
		return isManual;
	}

	/**
	 * @param isManual the isManual to set
	 */
	public void setIsManual(int isManual) {
		this.isManual = isManual;
	}

	/**
	 * @return the isQna
	 */
	public int getIsQna() {
		return isQna;
	}

	/**
	 * @param isQna the isQna to set
	 */
	public void setIsQna(int isQna) {
		this.isQna = isQna;
	}

	/**
	 * @return the isDic
	 */
	public int getIsDic() {
		return isDic;
	}

	/**
	 * @param isDic the isDic to set
	 */
	public void setIsDic(int isDic) {
		this.isDic = isDic;
	}

	/**
	 * @return the isCafe
	 */
	public int getIsCafe() {
		return isCafe;
	}

	/**
	 * @param isCafe the isCafe to set
	 */
	public void setIsCafe(int isCafe) {
		this.isCafe = isCafe;
	}

	/**
	 * @return the isForum
	 */
	public int getIsForum() {
		return isForum;
	}

	/**
	 * @param isForum the isForum to set
	 */
	public void setIsForum(int isForum) {
		this.isForum = isForum;
	}

	/**
	 * @return the isIdea
	 */
	public int getIsIdea() {
		return isIdea;
	}

	/**
	 * @param isIdea the isIdea to set
	 */
	public void setIsIdea(int isIdea) {
		this.isIdea = isIdea;
	}

	/**
	 * @return the isBbs
	 */
	public int getIsBbs() {
		return isBbs;
	}

	/**
	 * @param isBbs the isBbs to set
	 */
	public void setIsBbs(int isBbs) {
		this.isBbs = isBbs;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return the mapDescription
	 */
	public String getMapDescription() {
		return mapDescription;
	}

	/**
	 * @param mapDescription the mapDescription to set
	 */
	public void setMapDescription(String mapDescription) {
		this.mapDescription = mapDescription;
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

}
