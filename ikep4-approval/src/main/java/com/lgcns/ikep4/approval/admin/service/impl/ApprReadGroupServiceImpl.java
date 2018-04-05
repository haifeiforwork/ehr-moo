/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.admin.dao.ApprReadGroupDao;
import com.lgcns.ikep4.approval.admin.model.ApprReadGroup;
import com.lgcns.ikep4.approval.admin.search.ApprReadGroupSearchCondition;
import com.lgcns.ikep4.approval.admin.service.ApprReadGroupService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;


/**
 * 부서 결재함 열람권한 Service 구현
 *
 * @author 
 * @version $Id$
 */
@Service("apprReadGroupService")
public class ApprReadGroupServiceImpl extends GenericServiceImpl<ApprReadGroup, String> implements ApprReadGroupService {

	@Autowired
	private ApprReadGroupDao		apprReadGroupdao;
	
	/**
	 * 부서 결재함 열람권한 목록
	 * @param 	searchCondition
	 * @return 	SearchResult<ApprReadGroup>
	 */
	public SearchResult<ApprReadGroup> listBySearchCondition(ApprReadGroupSearchCondition searchCondition) { 
		
		Integer count = this.apprReadGroupdao.countBySearchCondition(searchCondition);
		
		searchCondition.terminateSearchCondition(count);  
		
		SearchResult<ApprReadGroup> searchResult = null;
		
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprReadGroup>(searchCondition);
		}
		else {
			List<ApprReadGroup> apprReadGroupList = (List<ApprReadGroup>)this.apprReadGroupdao.listBySearchCondition(searchCondition);  
			searchResult = new SearchResult<ApprReadGroup>(apprReadGroupList, searchCondition); 
		}  
		
		return searchResult;
	}
	
	/**
	 * ApprReadGroupForm 생성
	 * @param 	
	 * @return 	void
	 */
	public void createApprReadGroup(ApprReadGroup apprReadGroup) {
		
		Map map = new HashMap<String, String>();
		map.put("userId", 	apprReadGroup.getUserId());
		map.put("portalId", apprReadGroup.getPortalId());
		
		//기존 부서목록 삭제
		apprReadGroupdao.deleteApprReadGroup(map);
		
		//부서목록 저장
		if(!"".equals(apprReadGroup.getGroupId())){
			String [] groupSet = StringUtils.split(apprReadGroup.getGroupId(), ";");
			for(int i=0; i<groupSet.length; i++){
				
				apprReadGroup.setGroupId(groupSet[i]);
				apprReadGroupdao.createApprReadGroup(apprReadGroup);
			}
		}
	}
	
	/**
	 * 부서 목록
	 * @param 	
	 * @return 	userId
	 */
	public List<ApprReadGroup> getGroupList(String userId, String portalId){
		
		Map map = new HashMap<String, String>();
		map.put("userId", 	userId);
		map.put("portalId", portalId);
		
		List<ApprReadGroup> getGroupList = apprReadGroupdao.getGroupList(map);
		
		return getGroupList;
	}
	
	/**
	 * 열람권한 조회
	 * @param 	
	 * @return 	userId
	 */
	public ApprReadGroup getBasicInfo(String userId, String portalId){
		
		Map map = new HashMap<String, String>();
		map.put("userId", 	userId);
		map.put("portalId", portalId);
		
		ApprReadGroup apprReadGroup = apprReadGroupdao.getBasicInfo(map);
		
		return apprReadGroup;
	}
	
	/**
	 * ApprReadGroupForm 수정
	 * @param 	
	 * @return 	void
	 */
	public void updateApprReadGroup(ApprReadGroup apprReadGroup) {
		
		Map map = new HashMap<String, String>();
		map.put("userId", 	apprReadGroup.getUserId());
		map.put("portalId", apprReadGroup.getPortalId());
		
		if(apprReadGroup.getDelFlag() == null || apprReadGroup.getDelFlag().equals("")) {
			//기존 부서목록 삭제
			apprReadGroupdao.deleteApprReadGroup(map);
			
			//부서목록저장
			if(!"".equals(apprReadGroup.getGroupId())){
				String [] groupSet = StringUtils.split(apprReadGroup.getGroupId(), ";");
				for(int i=0; i<groupSet.length; i++){
					
					log.debug(groupSet[i]);
					apprReadGroup.setGroupId(groupSet[i]);
					apprReadGroupdao.createApprReadGroup(apprReadGroup);
				}
			}
		}else {
			apprReadGroupdao.deleteApprReadGroup(map);
		}
		
	}
	
	/**
	 * 삭제
	 * @param 	
	 * @return 	void
	 */
	public void deleteReadGroup(List<String> userIds,String portalId) {
		
		for (int i = 0; i < userIds.size(); i++) {
			String [] id = StringUtils.split(userIds.get(i), "|");
			Map map = new HashMap<String, Object>();
			map.put("userId", 	id[0]);
			map.put("groupId", 	id[1]);
			map.put("portalId", 	portalId);
			
			apprReadGroupdao.deleteApprReadGroupAjax(map);
		}
	}
	
	
}
