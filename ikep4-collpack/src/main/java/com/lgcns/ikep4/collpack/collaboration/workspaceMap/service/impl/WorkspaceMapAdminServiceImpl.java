/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspaceMap.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.collaboration.workspaceMap.dao.WorkspaceMapAdminDao;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.model.MapSortOrderModel;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.model.WorkspaceMap;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.search.MapBlockPageCondition;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.service.WorkspaceMapAdminService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * WorkspaceMap
 *
 * @author 홍정관
 * @version $Id: WorkspaceMapAdminServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("workspaceMapAdminService")
public class WorkspaceMapAdminServiceImpl extends GenericServiceImpl<WorkspaceMap, String> implements WorkspaceMapAdminService {

	@Autowired
	private WorkspaceMapAdminDao workspaceMapAdminDao;
	
	
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * Root 맵 조회
	 */
	public WorkspaceMap readWorkspaceMapRoot(String workspaceId) {
		WorkspaceMap workspaceMap = new WorkspaceMap();
		workspaceMap = workspaceMapAdminDao.getWorkspaceMapRoot(workspaceId);
		return workspaceMap;
	}
	/**
	 * 맵 하위 그룹 존재유무
	 */
	public boolean existLowRankGroup(String workspaceId) {
		boolean flagLowRankGroup = false;
		flagLowRankGroup = workspaceMapAdminDao.existLowRankGroup(workspaceId);
		return flagLowRankGroup;
	}
	/**
	 * 맵 Tree 목록
	 */
	public List<WorkspaceMap> listByWorkspaceMapRootId(Map<String, String> map) {
		List<WorkspaceMap> workspaceMapList = this.workspaceMapAdminDao.listByWorkspaceMapRootId(map);
		List<WorkspaceMap> mapList = new ArrayList<WorkspaceMap>();
		
		for(WorkspaceMap temp:workspaceMapList){
			StringBuffer tags = new StringBuffer();
			List<WorkspaceMap> tag = new ArrayList<WorkspaceMap>();
			tag = workspaceMapAdminDao.getTagInfo(temp.getMapId());
			
			for(int i=0;i<tag.size();i++){
				if(i==0){
					tags.append(tag.get(i).getTag());
				}else{
					tags.append(","+tag.get(i).getTag());
				}
			}
			temp.setTags(tags.toString());
			mapList.add(temp);
		}
		
		return mapList;
	}
	/**
	 * 맵 생성
	 */
	public WorkspaceMap createMap(WorkspaceMap workspaceMap) {
		String id = idgenService.getNextId();
		workspaceMap.setMapId(id);
		
		String mapId = this.workspaceMapAdminDao.createMap(workspaceMap);
		if(!StringUtil.isEmpty(mapId)){
			String[] tempTags = workspaceMap.getTags().split(",");
			if(tempTags!=null){
				for(int i=0;i<tempTags.length;i++){
					Map<String,String> mapTag = new HashMap<String,String>();
					mapTag.put("mapId", workspaceMap.getMapId());
					mapTag.put("tag", tempTags[i]);
					this.workspaceMapAdminDao.createMapTag(mapTag);
				}
			}
		}
		
		Map<String,String> mapInfo = new HashMap<String,String>();
		mapInfo.put("workspaceId", workspaceMap.getWorkspaceId());
		mapInfo.put("mapId", mapId);
		
		workspaceMap = this.readAddedNode(mapInfo);
		
		return workspaceMap;
	}
	/**
	 * 맵 추가 Node 조회
	 */
	public WorkspaceMap readAddedNode(Map<String, String> treeMap) {
		
		WorkspaceMap workspaceMap = new WorkspaceMap();
		workspaceMap = this.workspaceMapAdminDao.getAddedNode(treeMap);
		
		if(workspaceMap!=null){
			StringBuffer tags = new StringBuffer();
			List<WorkspaceMap> tag = new ArrayList<WorkspaceMap>();
			tag = workspaceMapAdminDao.getTagInfo(workspaceMap.getMapId());
	
			for(int i=0;i<tag.size();i++){
				if(i==0){
					tags.append(tag.get(i).getTag());
				}else{
					tags.append(","+tag.get(i).getTag());
				}
			}
			workspaceMap.setTags(tags.toString());
		}
		return workspaceMap;
	}

	/**
	 * 맵 태그 목록
	 */
	public List<WorkspaceMap> readTagList(Map<String, String> param) {
		
		return workspaceMapAdminDao.getTagList(param);
	}
	/**
	 * 맵 Item 목록
	 */
	public List<WorkspaceMap> listItemByTag(MapBlockPageCondition pageCondition) {

		if(pageCondition.isReInit()){
			int countTagItem = workspaceMapAdminDao.countItemByTag(pageCondition);
			pageCondition.setTotalCount(countTagItem);
			pageCondition.setReInit(false);
		}
		
		pageCondition.calculate();
		
		List<WorkspaceMap> tagItemList = this.workspaceMapAdminDao.listItemByTag(pageCondition);
		
		
		return tagItemList;
	}
	/**
	 * 맵 태그 하위 포함 삭제 
	 */
	public void deleteWithTagsHierarchy(String workspaceId, String mapId) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("workspaceId", workspaceId);
		param.put("mapId", mapId);
		
		workspaceMapAdminDao.removeByMapIdTagsHierarchy(param);
		workspaceMapAdminDao.removeByMapIdHierarchy(param);
	}
	
	/**Function**/
	/**
	 * 태그 정보 수정
	 */
	public void updateWithTags(WorkspaceMap workspaceMap) {
		
			workspaceMapAdminDao.update(workspaceMap);
		//TAG DELETEALL
			
			workspaceMapAdminDao.deleteTag(workspaceMap.getMapId());
		// TAG INSERT
			String[] tempTags = workspaceMap.getTags().split(",");
			if(tempTags!=null){
				for(int i=0;i<tempTags.length;i++){
					Map<String,String> mapTag = new HashMap<String,String>();
					mapTag.put("mapId", workspaceMap.getMapId());
					mapTag.put("tag", tempTags[i]);
					this.workspaceMapAdminDao.createMapTag(mapTag);
				}
			}
		
	}
	/**
	 * 맵 이동
	 */
	public void moveInnerMap(String sourceId, String sourceParentId, int sourceSortOrder, int targetSortOrder) {
		MapSortOrderModel parameterMap = new MapSortOrderModel();

		parameterMap.setMapParentId(sourceParentId);
		parameterMap.setSortOrderFrom(targetSortOrder);
		parameterMap.setSortOrderTo(sourceSortOrder);
		workspaceMapAdminDao.updateSortOrderPlus(parameterMap);

		parameterMap.setMapId(sourceId);
		parameterMap.setSortOrder(targetSortOrder);
		workspaceMapAdminDao.updateSortOrder(parameterMap);
		
	}
	/**
	 * 맵 이동
	 */
	public void moveOuterMap(
			String sourceId
			,String sourceParentId
			,int sourceSortOrder
			,String targetParentId
			,int targetSortOrder) {
		
		
		MapSortOrderModel parameterMap = new MapSortOrderModel();

		parameterMap.setMapParentId(sourceParentId);
		parameterMap.setSortOrder(sourceSortOrder);
		workspaceMapAdminDao.updateSortOrderMinus(parameterMap);

		parameterMap.setMapParentId(targetParentId);
		parameterMap.setSortOrderFrom(targetSortOrder);
		parameterMap.setSortOrderTo(0);
		workspaceMapAdminDao.updateSortOrderPlus(parameterMap);

		parameterMap.setMapId(sourceId);
		parameterMap.setMapParentId(targetParentId);
		parameterMap.setSortOrder(targetSortOrder);
		workspaceMapAdminDao.updateSortOrderMapParentId(parameterMap);

	}

}
