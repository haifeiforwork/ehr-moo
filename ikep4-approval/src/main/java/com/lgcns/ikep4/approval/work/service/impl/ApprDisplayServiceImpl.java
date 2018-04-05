/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.work.dao.ApprDisplayDao;
import com.lgcns.ikep4.approval.work.model.ApprDisplay;
import com.lgcns.ikep4.approval.work.search.ApprDisplaySearchCondition;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprDisplayService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 공람지정 Service 구현
 *
 * @author jeehye
 * @version $Id: ApprDisplayServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("apprDisplayService")
public class ApprDisplayServiceImpl extends GenericServiceImpl<ApprDisplay, String> implements ApprDisplayService {

	@Autowired
	private ApprDisplayDao apprDisplayDao;
	
	@Autowired
	private IdgenService idgenService;

	/**
	 *  공람지정 저장
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public void createApprDisplay(ApprDisplay apprDisplay) {  
		
		// displayId 생성
		apprDisplay.setDisplayId(idgenService.getNextId());
		
		apprDisplayDao.createApprDisplay(apprDisplay);
		
		// 그룹 권한
		if(!StringUtil.isEmpty(apprDisplay.getAddrGroupTypeListAll())) {
			for (String item : apprDisplay.getAddrGroupTypeListAll().split("\\^")) {	
				JSONArray jsonArray = JSONArray.fromObject(JSONSerializer.toJSON(item));
				JSONObject obj = (JSONObject) jsonArray.get(0);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("displayId", 			apprDisplay.getDisplayId());
				map.put("groupId", 				obj.getString("groupId"));
				map.put("hierarchyPermission", 	obj.getInt("hierarchyPermission"));
				
				if(obj.getInt("hierarchyPermission") == 0) {
					
					List<ApprDisplay> userIds =  apprDisplayDao.getUserIdByGroup(map);
					for(int i=0; i<userIds.size();i++) {
						apprDisplay.setUserId(userIds.get(i).getUserId());
						Map<String, Object> existUserMap = new HashMap<String, Object>();
						existUserMap.put("apprId", 			apprDisplay.getApprId());
						existUserMap.put("userId", 			apprDisplay.getUserId());
						boolean existDisplayUser = existDisplayUser(existUserMap);
						if(!existDisplayUser){
							apprDisplayDao.createApprDisplaySub(apprDisplay);
						}
					}
					
				}else if(obj.getInt("hierarchyPermission") == 1) {
					
					List<ApprDisplay> subGroupIdCntList =  apprDisplayDao.getUserIdBySubGroupCntList(map);
					
					for(int i=0; i<subGroupIdCntList.size();i++) {
						
						String groupId = subGroupIdCntList.get(i).getGroupId();
						int groupIdCnt = subGroupIdCntList.get(i).getChildGroupCount();
						map.put("groupId", 	groupId);
						
						if(groupIdCnt == 0) {
							List<ApprDisplay> userIds =  apprDisplayDao.getUserIdByGroup(map);
							for(int j=0; j<userIds.size();j++) {
								apprDisplay.setUserId(userIds.get(j).getUserId());
								Map<String, Object> existUserMap = new HashMap<String, Object>();
								existUserMap.put("apprId", 			apprDisplay.getApprId());
								existUserMap.put("userId", 			apprDisplay.getUserId());
								boolean existDisplayUser = existDisplayUser(existUserMap);
								if(!existDisplayUser){
									apprDisplayDao.createApprDisplaySub(apprDisplay);
								}
							}
						}else {
							List<ApprDisplay> subGroupIdList =  apprDisplayDao.getUserIdByParentGroup(map);
							
							for(int j=0; j<subGroupIdList.size();j++) {
								String subGroupId = subGroupIdList.get(j).getGroupId();
								int subGroupIdCnt = subGroupIdList.get(j).getChildGroupCount();
								
								if(subGroupIdCnt == 0) {
									map.put("groupId", 	subGroupId);
									List<ApprDisplay> userIds =  apprDisplayDao.getUserIdByGroup(map);
									for(int k=0; k<userIds.size();k++) {
										apprDisplay.setUserId(userIds.get(k).getUserId());
										Map<String, Object> existUserMap = new HashMap<String, Object>();
										existUserMap.put("apprId", 			apprDisplay.getApprId());
										existUserMap.put("userId", 			apprDisplay.getUserId());
										boolean existDisplayUser = existDisplayUser(existUserMap);
										if(!existDisplayUser){
											apprDisplayDao.createApprDisplaySub(apprDisplay);
										}
									}
								}else {
									//하위부서
									map.put("groupId", 	subGroupId);
									List<ApprDisplay> sub_GroupIdList =  apprDisplayDao.getUserIdByParentGroup(map);
									for(int k=0; k<sub_GroupIdList.size();k++) {
										String sub_GroupId = sub_GroupIdList.get(k).getGroupId();
										int sub_GroupIdCnt = sub_GroupIdList.get(k).getChildGroupCount();
										
										if(sub_GroupIdCnt == 0) {
											map.put("groupId", 	sub_GroupId);
											List<ApprDisplay> userIds =  apprDisplayDao.getUserIdByGroup(map);
											for(int l=0; l<userIds.size();l++) {
												apprDisplay.setUserId(userIds.get(l).getUserId());
												Map<String, Object> existUserMap = new HashMap<String, Object>();
												existUserMap.put("apprId", 			apprDisplay.getApprId());
												existUserMap.put("userId", 			apprDisplay.getUserId());
												boolean existDisplayUser = existDisplayUser(existUserMap);
												if(!existDisplayUser){
													apprDisplayDao.createApprDisplaySub(apprDisplay);
												}
											}
										}else {
											//하위부서
											
										}
										
									}
									
								}
								
							}
						}
						
					}
					
				}
			}
		}
		
		// 사용자 권한
		if(!StringUtil.isEmpty(apprDisplay.getAddrUserListAll())) {
			for (String item : apprDisplay.getAddrUserListAll().split("\\^")) {
				apprDisplay.setUserId(item);
				Map<String, Object> existUserMap = new HashMap<String, Object>();
				existUserMap.put("apprId", 			apprDisplay.getApprId());
				existUserMap.put("userId", 			apprDisplay.getUserId());
				boolean existDisplayUser = existDisplayUser(existUserMap);
				if(!existDisplayUser){
					apprDisplayDao.createApprDisplaySub(apprDisplay);
				}
			}
		}

		
		
	}
	
	public SearchResult<ApprDisplay> listBySearchCondition(ApprDisplaySearchCondition searchCondition) { 
		
		Integer count = this.apprDisplayDao.countBySearchCondition(searchCondition);
		
		searchCondition.terminateSearchCondition(count);  
		
		SearchResult<ApprDisplay> searchResult = null;
		
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprDisplay>(searchCondition);
		}
		else {
			List<ApprDisplay> apprDisplay = (List<ApprDisplay>)this.apprDisplayDao.listBySearchCondition(searchCondition);  
			searchResult = new SearchResult<ApprDisplay>(apprDisplay, searchCondition); 
		}  
		
		return searchResult;
	}
	
	/**
	 * 공람 회수
	 */
	public void deleteApprDisplay(String userId, String apprId) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		if(userId.equals("")) {
			
			map.put("apprId", 		apprId);
			map.put("userId", 		"");
			map.put("displayId", 	"");
			
			apprDisplayDao.deleteApprDisplaySub(map);
			apprDisplayDao.deleteApprDisplay(map);
			
		}else {
			
			String [] userIdSet = userId.split("\\|");
			
			map.put("apprId", 		apprId);
			map.put("userId", 		userIdSet[0]);
			map.put("displayId", 	userIdSet[1]);
			
			apprDisplayDao.deleteApprDisplaySub(map);
			
			if(apprDisplayDao.getApprDisplaySubCount(map) == 0) {
				
				map.put("apprId", 		apprId);
				map.put("displayId", 	"");
				apprDisplayDao.deleteApprDisplay(map);
			}
		}
		
	}
	
	/**
	 * 공람 대기/완료 문서
	 */
	public SearchResult<ApprDisplay> listByDisplaySearchCondition(ApprDisplaySearchCondition searchCondition) { 
		
		Integer count = this.apprDisplayDao.countByDisplaySearchCondition(searchCondition);
		
		searchCondition.terminateSearchCondition(count);  
		
		SearchResult<ApprDisplay> searchResult = null;
		
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprDisplay>(searchCondition);
		}
		else {
			List<ApprDisplay> apprDisplay = (List<ApprDisplay>)this.apprDisplayDao.listByDisplaySearchCondition(searchCondition);  
			searchResult = new SearchResult<ApprDisplay>(apprDisplay, searchCondition); 
		}  
		
		return searchResult;
	}
	
	/**
	 * 검토요청함 목록 갯수
	 * @param 	searchCondition
	 * @return 	SearchResult<ApprList>
	 */
	public Integer countByDisplaySearchCondition(ApprDisplaySearchCondition searchCondition) { 
		
		Integer count = this.apprDisplayDao.countByDisplaySearchCondition(searchCondition);
		return count;
	}
	
	public String getApprDisplayId(String userId,String apprId){
		
		String displayId = "";
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 		apprId);
		map.put("userId", 	userId);
		
		displayId = apprDisplayDao.getApprDisplayId(map);
		
		return displayId;
	}
	
	public void updateApprDisplaySub(String displayId, String userId){
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("displayId", 		displayId);
		map.put("userId", 	userId);
		
		apprDisplayDao.updateApprDisplaySub(map);
		
	}
	
	public Integer countByDisplayUserStatus(String apprId){
		return apprDisplayDao.countByDisplayUserStatus(apprId);
	}
	
	/**
	 * 공람지정 문서여부
	 * @param apprId
	 * @return ID
	 */
	public boolean existDisplayDoc(Map map) {  
		
		return this.apprDisplayDao.existDisplayDoc(map);
	
	}
	
	/**
	 * 공람지정 대상자
	 * @param apprId
	 * @return ID
	 */
	public boolean existDisplayUser(Map map) {  
		
		return this.apprDisplayDao.existDisplayUser(map);
	
	}
}
