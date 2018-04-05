/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspaceMap.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.collaboration.workspaceMap.model.MapSortOrderModel;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.model.WorkspaceMap;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.search.MapBlockPageCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;
/**
 * 
 * WorkspaceMapDao
 *
 * @author 홍정관
 * @version $Id: WorkspaceMapAdminDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface WorkspaceMapAdminDao extends GenericDao<WorkspaceMap, String> {

	/**
	 * WS 별 Root 맵 정보
	 * @param workspaceId
	 * @return
	 */
	WorkspaceMap getWorkspaceMapRoot(String workspaceId);
	/**
	 * 맵 전체 Tree 목록
	 * @param map
	 * @return
	 */
	List<WorkspaceMap> listByWorkspaceMapRootId(Map<String, String> map);
	/**
	 * 맵 태그 정보
	 * @param mapId
	 * @return
	 */
	List<WorkspaceMap> getTagInfo(String mapId);

	/** 
	 * 멥 등록
	 * @param workspaceMap
	 * @return
	 */
	String createMap(WorkspaceMap workspaceMap);
	/**
	 * 맵 Root 생성
	 * @param workspaceMap
	 * @return
	 */
	String createMapRoot(WorkspaceMap workspaceMap);
	
	/**
	 * 맵 태그 등록
	 * @param mapTag
	 */
	void createMapTag(Map<String, String> mapTag);

	/**
	 * 맵 노드 추가
	 * @param treeMap
	 * @return
	 */
	WorkspaceMap getAddedNode(Map<String, String> treeMap);

	/**
	 * 맵 태그 삭제
	 * @param mapId
	 */
	void deleteTag(String mapId);

	/** 
	 * 맵 하위 정보 포함 삭제
	 * @param param
	 */
	void removeByMapIdHierarchy(Map<String, String> param);

	/**
	 * 맵 태그 하위 포함 삭제
	 * @param param
	 */
	void removeByMapIdTagsHierarchy(Map<String, String> param);

	/**
	 * 맵 순서 증가
	 * @param parameterMap
	 */
	void updateSortOrderPlus(MapSortOrderModel parameterMap);

	/**
	 * 맵 순서 변경
	 * @param parameterMap
	 */
	void updateSortOrder(MapSortOrderModel parameterMap);

	/**
	 * 맵 순서 감소
	 * @param parameterMap
	 */
	void updateSortOrderMinus(MapSortOrderModel parameterMap);

	/**
	 * 상위맵의 순서 변경
	 * @param parameterMap
	 */
	void updateSortOrderMapParentId(MapSortOrderModel parameterMap);

	/**
	 * 맵의 하위 존재 유무
	 * @param workspaceId
	 * @return
	 */
	boolean existLowRankGroup(String workspaceId);
	
	/**
	 * 태그 목록
	 * @param param
	 * @return
	 */
	List<WorkspaceMap> getTagList(Map<String, String> param);//add

	/**
	 * 태그의 Item 갯수
	 * @param pageCondition
	 * @return
	 */
	int countItemByTag(MapBlockPageCondition pageCondition);

	/**
	 * 태그의 Item 목록
	 * @param pageCondition
	 * @return
	 */
	List<WorkspaceMap> listItemByTag(MapBlockPageCondition pageCondition);

}
