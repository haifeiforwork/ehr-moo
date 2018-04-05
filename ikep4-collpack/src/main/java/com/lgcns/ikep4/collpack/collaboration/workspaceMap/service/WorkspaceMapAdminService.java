/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspaceMap.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.collaboration.workspaceMap.model.WorkspaceMap;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.search.MapBlockPageCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
/**
 * 
 * WorkspaceMapService
 *
 * @author 홍정관
 * @version $Id: WorkspaceMapAdminService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface WorkspaceMapAdminService extends GenericService<WorkspaceMap, String> {

	/**
	 * Root 맵 조회
	 * @param workspaceId
	 * @return
	 */
	WorkspaceMap readWorkspaceMapRoot(String workspaceId);

	/**
	 * 맵 Tree 목록
	 * @param map
	 * @return
	 */
	List<WorkspaceMap> listByWorkspaceMapRootId(Map<String, String> map);

	/**
	 * 맵 생성
	 * @param workspaceMap
	 * @return
	 */
	WorkspaceMap createMap(WorkspaceMap workspaceMap);

	/**
	 * 맵 정보 수정
	 * @param workspaceMap
	 */
	void updateWithTags(WorkspaceMap workspaceMap);

	/**
	 * 맵 태그 하위 포함 삭제 
	 * @param workspaceId
	 * @param mapId
	 */
	void deleteWithTagsHierarchy(String workspaceId, String mapId);

	/**
	 * 맵 이동
	 * @param sourceId
	 * @param sourceParentId
	 * @param sourceSortOrder
	 * @param targetSortOrder
	 */
	void moveInnerMap(String sourceId, String sourceParentId, int sourceSortOrder, int targetSortOrder);

	/**
	 * 맵 이동 
	 * @param sourceId
	 * @param sourceParentId
	 * @param sourceSortOrder
	 * @param targetParentId
	 * @param targetSortOrder
	 */
	void moveOuterMap(String sourceId, String sourceParentId, int sourceSortOrder, String targetParentId,
			int targetSortOrder);

	/**
	 * 맵 하위 그룹 존재유무
	 * @param workspaceId
	 * @return
	 */
	boolean existLowRankGroup(String workspaceId);
	
	/**
	 * 맵 추가 Node 조회
	 * @param map
	 * @return
	 */
	WorkspaceMap readAddedNode(Map<String, String> map);

	/**
	 * 맵 태그 목록
	 * @param param
	 * @return
	 */
	List<WorkspaceMap> readTagList(Map<String, String> param);

	/**
	 * 맵 Item 목록
	 * @param pageCondition
	 * @return
	 */
	List<WorkspaceMap> listItemByTag(MapBlockPageCondition pageCondition);
}
