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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.work.dao.ApprListDao;
import com.lgcns.ikep4.approval.work.model.ApprList;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprListService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;

/**
 * 기안함 Service 구현
 *
 * @author jeehye
 * @version $Id: ApprListtServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("ApprListService")
public class ApprListServiceImpl extends GenericServiceImpl<ApprList, String> implements ApprListService {

	@Autowired
	private ApprListDao apprListDao;

	/**
	 * 기안함 목록
	 * @param 	searchCondition
	 * @return 	SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listByMyRequest(ApprListSearchCondition searchCondition) { 
		
		SearchResult<ApprList> searchResult = null;
		
		if(searchCondition.getLinkType() != null && searchCondition.getLinkType().equals("MAIN")) {
			
			searchCondition.terminateSearchCondition(5); 
			searchCondition.setEndRowIndex(5);
			
			List<ApprList> apprList = (List<ApprList>)this.apprListDao.listByMyRequest(searchCondition);  
			searchResult = new SearchResult<ApprList>(apprList, searchCondition); 
			
		}else {
			Integer count = this.apprListDao.countByMyRequest(searchCondition);
			
			searchCondition.terminateSearchCondition(count);  
			
			
			if(searchCondition.isEmptyRecord()) {
				searchResult = new SearchResult<ApprList>(searchCondition);
			}
			else {
				List<ApprList> apprList = (List<ApprList>)this.apprListDao.listByMyRequest(searchCondition);  
				searchResult = new SearchResult<ApprList>(apprList, searchCondition); 
			}  
		}
		
		
		return searchResult;
	}
	
	/**
	 * 결재함 목록 
	 * @param 	searchCondition
	 * @return 	SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchCondition(ApprListSearchCondition searchCondition) { 
		
		SearchResult<ApprList> searchResult = null;
		
		if(searchCondition.getLinkType() != null && searchCondition.getLinkType().equals("MAIN")) {
			
			searchCondition.terminateSearchCondition(5); 
			searchCondition.setEndRowIndex(5);
			List<ApprList> apprList = (List<ApprList>)this.apprListDao.listBySearchCondition(searchCondition);  
			searchResult = new SearchResult<ApprList>(apprList, searchCondition); 
			
		}else {
			Integer count = this.apprListDao.countBySearchCondition(searchCondition);
			
			searchCondition.terminateSearchCondition(count);  
			
			if(searchCondition.isEmptyRecord()) {
				searchResult = new SearchResult<ApprList>(searchCondition);
			}
			else {
				List<ApprList> apprList = (List<ApprList>)this.apprListDao.listBySearchCondition(searchCondition);  
				searchResult = new SearchResult<ApprList>(apprList, searchCondition); 
			}  
			
		}
		
		return searchResult;
	}
	
	/**
	 * 결재함 목록 갯수
	 * @param 	searchCondition
	 * @return 	SearchResult<ApprList>
	 */
	public Integer countBySearchCondition(ApprListSearchCondition searchCondition) { 
		
		Integer count = this.apprListDao.countBySearchCondition(searchCondition);
		return count;
	}
	
	/**
	 * 참조문서
	 * @param 	searchCondition
	 * @return 	SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchConditionRef(ApprListSearchCondition searchCondition) { 
		
		Integer count = this.apprListDao.countBySearchConditionRef(searchCondition);
		
		searchCondition.terminateSearchCondition(count);  
		
		SearchResult<ApprList> searchResult = null;
		
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprList>(searchCondition);
		}
		else {
			List<ApprList> apprList = (List<ApprList>)this.apprListDao.listBySearchConditionRef(searchCondition);  
			searchResult = new SearchResult<ApprList>(apprList, searchCondition); 
		}  
		
		return searchResult;
	}
	
	/**
	 * 열람문서
	 * @param 	searchCondition
	 * @return 	SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchConditionRead(ApprListSearchCondition searchCondition) { 
		
		Integer count = this.apprListDao.countBySearchConditionRead(searchCondition);
		
		searchCondition.terminateSearchCondition(count);  
		
		SearchResult<ApprList> searchResult = null;
		
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprList>(searchCondition);
		}
		else {
			List<ApprList> apprList = (List<ApprList>)this.apprListDao.listBySearchConditionRead(searchCondition);  
			searchResult = new SearchResult<ApprList>(apprList, searchCondition); 
		}  
		
		return searchResult;
	}
	
	/**
	 * 통합결재함
	 * @param 	searchCondition
	 * @return 	SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchConditionIntegrate(ApprListSearchCondition searchCondition) { 
		
		Integer count = this.apprListDao.countBySearchConditionIntegrate(searchCondition);
		
		searchCondition.terminateSearchCondition(count);  
		
		SearchResult<ApprList> searchResult = null;
		
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprList>(searchCondition);
		}
		else {
			List<ApprList> apprList = (List<ApprList>)this.apprListDao.listBySearchConditionIntegrate(searchCondition);  
			searchResult = new SearchResult<ApprList>(apprList, searchCondition); 
		}  
		
		return searchResult;
	}
	
	/**
	 * 부서수신함/부서결재함
	 * @param 	searchCondition
	 * @return 	SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchConditionDeptRec(ApprListSearchCondition searchCondition) { 
		
		SearchResult<ApprList> searchResult = null;
		
		if(searchCondition.getLinkType() != null && searchCondition.getLinkType().equals("MAIN")) {
			
			searchCondition.terminateSearchCondition(5); 
			searchCondition.setEndRowIndex(5);
			
			List<ApprList> apprList = (List<ApprList>)this.apprListDao.listBySearchConditionDeptRec(searchCondition);  
			searchResult = new SearchResult<ApprList>(apprList, searchCondition); 
			
		}else {
			Integer count = this.apprListDao.countBySearchConditionDeptRec(searchCondition);
			
			searchCondition.terminateSearchCondition(count);  
			
			if(searchCondition.isEmptyRecord()) {
				searchResult = new SearchResult<ApprList>(searchCondition);
			}
			else {
				List<ApprList> apprList = (List<ApprList>)this.apprListDao.listBySearchConditionDeptRec(searchCondition);  
				searchResult = new SearchResult<ApprList>(apprList, searchCondition); 
			}  
			
		}
		
		return searchResult;
	}
	
	/**
	 * 부서수신함 목록 갯수
	 * @param 	searchCondition
	 * @return 	SearchResult<ApprList>
	 */
	public Integer countBySearchConditionDeptRec(ApprListSearchCondition searchCondition) { 
		
		Integer count = this.apprListDao.countBySearchConditionDeptRec(searchCondition);
		return count;
	}
	
	/**
	 * 검토요청함
	 * @param 	searchCondition
	 * @return 	SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchConditionExam(ApprListSearchCondition searchCondition) { 
		
		Integer count = this.apprListDao.countBySearchConditionExam(searchCondition);
		
		searchCondition.terminateSearchCondition(count);  
		
		SearchResult<ApprList> searchResult = null;
		
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprList>(searchCondition);
		}
		else {
			List<ApprList> apprList = (List<ApprList>)this.apprListDao.listBySearchConditionExam(searchCondition);  
			searchResult = new SearchResult<ApprList>(apprList, searchCondition); 
		}  
		
		return searchResult;
	}
	
	/**
	 * 검토요청함 목록 갯수
	 * @param 	searchCondition
	 * @return 	SearchResult<ApprList>
	 */
	public Integer countBySearchConditionExam(ApprListSearchCondition searchCondition) { 
		
		Integer count = this.apprListDao.countBySearchConditionExam(searchCondition);
		return count;
	}
	
	/**
	 * 위임문서
	 * @param 	searchCondition
	 * @return 	SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchConditionLineEntrust(ApprListSearchCondition searchCondition) { 
		
		Integer count = 0;
		try {
			count = this.apprListDao.countBySearchConditionLineEntrust(searchCondition);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		searchCondition.terminateSearchCondition(count);  
		
		SearchResult<ApprList> searchResult = null;
		
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprList>(searchCondition);
		}
		else {
			try {
				List<ApprList> apprList = (List<ApprList>) this.apprListDao
						.listBySearchConditionLineEntrust(searchCondition);
				searchResult = new SearchResult<ApprList>(apprList, searchCondition);
			} catch (Exception e) {
				// TODO: handle exception
			} 
		}  
		
		return searchResult;
	}
	
	/**
	 * 개인보관함
	 * @param 	searchCondition
	 * @return 	SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchConditionUserDoc(ApprListSearchCondition searchCondition) { 
		
		Integer count = 0;
		try {
			count = this.apprListDao.countBySearchConditionUserDoc(searchCondition);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		searchCondition.terminateSearchCondition(count);  
		
		SearchResult<ApprList> searchResult = null;
		
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprList>(searchCondition);
		}
		else {
			try {
				List<ApprList> apprList = (List<ApprList>) this.apprListDao
						.listBySearchConditionUserDoc(searchCondition);
				searchResult = new SearchResult<ApprList>(apprList, searchCondition);
			} catch (Exception e) {
				// TODO: handle exception
			} 
		}  
		
		return searchResult;
	}
	
	/**
	 * 보안설정 및 해제
	 * @param apprId
	 * @param isHidden
	 * @return  String
	 */
	public void updateSetSecurity(String apprId, String isHidden) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 		apprId);
		map.put("isHidden", 	isHidden);
		
		apprListDao.updateSetSecurity(map);
	}
	
	
	/**
	 * 이름가져오기
	 * @param userId
	 * @return  String
	 */
	public String getUserName(String userId) { 
		
		String userName = this.apprListDao.getUserName(userId);
		
		return userName;
	}
	
	
}
