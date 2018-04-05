/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspaceMap.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.collaboration.workspaceMap.dao.WorkspaceMapAdminDao;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.model.MapSortOrderModel;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.model.WorkspaceMap;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.search.MapBlockPageCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * 
 * WorkspaceMapDaoImpl
 *
 * @author 홍정관
 * @version $Id: WorkspaceMapAdminDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("workspaceMapAdminDao")
public class WorkspaceMapAdminDaoImpl extends GenericDaoSqlmap<WorkspaceMap, String> implements WorkspaceMapAdminDao {

	private static final String NAMESPACE = "collpack.collaboration.workspaceMap.dao.WorkspaceMap.";

	public WorkspaceMap get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public String create(WorkspaceMap object) {
		
		return null;
	}

	public void update(WorkspaceMap object) {
		sqlUpdate(NAMESPACE+"update", object);
	}

	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Workspace Root 맵 정보
	 */
	public WorkspaceMap getWorkspaceMapRoot(String workspaceId) {
		return (WorkspaceMap) sqlSelectForObject(NAMESPACE+"getRootMap",workspaceId);
	}
	/**
	 * 맵 전체 Tree 목록
	 */
	public List<WorkspaceMap> listByWorkspaceMapRootId(Map<String, String> map) {
		return (List<WorkspaceMap>)this.sqlSelectForList(NAMESPACE + "listByWorkspaceMapRootId", map);
	}
	/**
	 * 맵 태그 정보
	 */
	public List<WorkspaceMap> getTagInfo(String mapId) {
		return (List<WorkspaceMap>)this.sqlSelectForList(NAMESPACE + "getTagInfo", mapId);
	}
	/**
	 * 맵 등록
	 */
	public String createMap(WorkspaceMap workspaceMap) {
		sqlInsert(NAMESPACE+"createMap", workspaceMap);
		return workspaceMap.getMapId();
	}
	/**
	 * 맵 Root 등록
	 */
	public String createMapRoot(WorkspaceMap workspaceMap) {
		sqlInsert(NAMESPACE+"createMapRoot", workspaceMap);
		return workspaceMap.getMapId();
	}
	/**
	 * 맵 태그 등록
	 */
	public void createMapTag(Map<String, String> mapTag) {
		sqlInsert(NAMESPACE+"createMapTag",mapTag);
	}
	/**
	 * 맵 노드 추가
	 */
	public WorkspaceMap getAddedNode(Map<String, String> treeMap) {
		return (WorkspaceMap) this.sqlSelectForObject(NAMESPACE + "getAddedNode", treeMap);

	}
	/**
	 * 맴 태그 삭제
	 */
	public void deleteTag(String mapId) {
		sqlDelete(NAMESPACE+"deleteTagAll", mapId);
	}
	/**
	 * 맵 태그 하위 포함 삭제
	 */
	public void removeByMapIdTagsHierarchy(Map<String,String> param) {
		sqlDelete(NAMESPACE + "removeByMapIdTagHierarchy", param);
	}
	/**
	 * 맵 하위 정보 포함 삭제
	 */
	public void removeByMapIdHierarchy(Map<String, String> param) {
		sqlDelete(NAMESPACE + "removeByMapIdHierarchy", param);
		
	}
	/**
	 * 맵 순서 증가
	 */
	public void updateSortOrderPlus(MapSortOrderModel parameterMap) {
		sqlUpdate(NAMESPACE + "updateSortOrderPlus", parameterMap);
	}
	/**
	 * 맵 순서 변경
	 */
	public void updateSortOrder(MapSortOrderModel parameterMap) {
		sqlUpdate(NAMESPACE + "updateSortOrder", parameterMap);
	}
	/**
	 * 맵 순서 감소
	 */
	public void updateSortOrderMinus(MapSortOrderModel parameterMap) {
		sqlUpdate(NAMESPACE + "updateSortOrderMinus", parameterMap);
		
	}
	/**
	 * 상위맵의 순서 변경
	 */
	public void updateSortOrderMapParentId(MapSortOrderModel parameterMap) {
		sqlUpdate(NAMESPACE + "updateSortOrderMapParentId", parameterMap);
	}
	/**
	 * 맵의 하위 존재 유무
	 */
	public boolean existLowRankGroup(String workspaceId) {
		int count = (Integer) sqlSelectForObject(NAMESPACE+"getLowRankGroup",workspaceId);
		return count>0; 
	}
	/**
	 * 태그 목록
	 */
	public List<WorkspaceMap> getTagList(Map<String, String> param) {
		return (List<WorkspaceMap>)sqlSelectForList(NAMESPACE + "getTagList", param);

	}
	/**
	 * 태그의 Item 갯수
	 */
	public int countItemByTag(MapBlockPageCondition pageCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countItemByTag", pageCondition);
	}
	/**
	 * 태그의 Item 목록
	 */
	public List<WorkspaceMap> listItemByTag(MapBlockPageCondition pageCondition) {
		return (List<WorkspaceMap>)sqlSelectForList(NAMESPACE + "listItemByTag", pageCondition);

	}

}
