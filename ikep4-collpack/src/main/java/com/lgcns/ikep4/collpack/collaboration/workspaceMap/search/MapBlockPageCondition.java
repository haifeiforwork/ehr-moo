/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspaceMap.search;

import com.lgcns.ikep4.collpack.common.search.BlockPageCondition;

/**
 * Workspace Map 페이징
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: MapBlockPageCondition.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class MapBlockPageCondition extends BlockPageCondition {

	/**
	 *
	 */
	private static final long serialVersionUID = -2998737676929258558L;

	/**
	 * 카테고리 ID
	 */
	private String mapId;

	

	/**
	 * 카테고리 Name
	 */
	private String mapName;

	
	/**
	 * WorkspaceId
	 */
	private String workspaceId;
	
	/**
	 * tag
	 */
	private String tag;
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
	 * @return the mapName
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * @param mapName the mapName to set
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

}
