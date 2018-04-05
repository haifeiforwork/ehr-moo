/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspaceMap.model;
/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 홍정관
 * @version $Id: MapSortOrderModel.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class MapSortOrderModel {

	/**
	 * 맵 아이디
	 */
	private String mapId;

	/**
	 * 맵 부모아이디
	 */
	private String mapParentId;
	
	/**
	 * 맵 순서
	 */
	private int sortOrder;
	
	/**
	 * 맵 순서 
	 */
	private int sortOrderFrom;
	
	/**
	 * 맵 순서 
	 */
	private int sortOrderTo;


	/**
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the sortOrderFrom
	 */
	public int getSortOrderFrom() {
		return sortOrderFrom;
	}

	/**
	 * @param sortOrderFrom the sortOrderFrom to set
	 */
	public void setSortOrderFrom(int sortOrderFrom) {
		this.sortOrderFrom = sortOrderFrom;
	}

	/**
	 * @return the sortOrderTo
	 */
	public int getSortOrderTo() {
		return sortOrderTo;
	}

	/**
	 * @param sortOrderTo the sortOrderTo to set
	 */
	public void setSortOrderTo(int sortOrderTo) {
		this.sortOrderTo = sortOrderTo;
	}

	/**
	 * Constructor
	 */
	public MapSortOrderModel() {
		
	}

	/**
	 * @return the mapId
	 */
	public String getMapId() {
		return mapId;
	}

	/**
	 * @param mapId the mapId to set
	 */
	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	/**
	 * @return the mapParentId
	 */
	public String getMapParentId() {
		return mapParentId;
	}

	/**
	 * @param mapParentId the mapParentId to set
	 */
	public void setMapParentId(String mapParentId) {
		this.mapParentId = mapParentId;
	}

	/**
	 * Constructor
	 * @param mapId
	 * @param mapParentId
	 * @param sortOrder
	 * @param sortOrderFrom
	 * @param sortOrderTo
	 */
	public MapSortOrderModel(String mapId, String mapParentId, int sortOrder, int sortOrderFrom, int sortOrderTo) {
		this.mapId = mapId;
		this.mapParentId = mapParentId;
		this.sortOrder = sortOrder;
		this.sortOrderFrom = sortOrderFrom;
		this.sortOrderTo = sortOrderTo;
	}

}
