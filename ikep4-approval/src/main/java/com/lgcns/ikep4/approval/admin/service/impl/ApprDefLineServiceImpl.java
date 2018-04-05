/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.approval.admin.dao.ApprDefLineDao;
import com.lgcns.ikep4.approval.admin.dao.ApprDefLineInfoDao;
import com.lgcns.ikep4.approval.admin.model.ApprDefLine;
import com.lgcns.ikep4.approval.admin.model.ApprDefLineInfo;
import com.lgcns.ikep4.approval.admin.search.ApprDefLineSearchCondition;
import com.lgcns.ikep4.approval.admin.service.ApprDefLineService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 결재선 관리 Service 구현
 *
 * @author 
 * @version $Id$
 */
@Service("apprDefLineService")
public class ApprDefLineServiceImpl extends GenericServiceImpl<ApprDefLine, String> implements ApprDefLineService {

	@Autowired
	private ApprDefLineDao		apprDefLinedao;

	@Autowired
	private ApprDefLineInfoDao	apprDefLineInfodao;
	
	@Autowired
	private IdgenService idgenService;
	
	/* 
	 * Default 결재선 등록
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	public String create(ApprDefLine object) {
		
		String defLineId = idgenService.getNextId();
		// Default Line Id
		// Default 결재선 정보 등록
		object.setDefLineId(defLineId);
		apprDefLinedao.create(object);	
		
		// 결재순서 지정 : 합의방법이 병렬인경우
		int apprOrder=0;// 최초 합의 순서 설정
		int	apprReceive=0; // 최초 수신 순서 설정
		int i=0;
		int j=0;
		
		for(ApprDefLineInfo apprDefLineInfo : object.getApprDefLineInfo()){
			
			if(object.getDefLineWay()==1 && j==0 && ( apprDefLineInfo.getApprType()==1|| apprDefLineInfo.getApprType() ==2) )	//	병렬합의
			{
				i++;
				apprOrder=i;
				j++;
			}
			
			if(object.getDefLineWay()==1 && j>0 && ( apprDefLineInfo.getApprType()==1|| apprDefLineInfo.getApprType() ==2) ) {
				apprDefLineInfo.setApprOrder(apprOrder);
			} else {
				if(apprDefLineInfo.getApprType()==3){	// 수신
					if(apprReceive==0)
						i++;
					apprReceive=i;
					apprDefLineInfo.setApprOrder(apprReceive);
				} else {				
					i++;
					apprDefLineInfo.setApprOrder(i);
				}
			}
			if(apprDefLineInfo.getApprType()==3){	// 수신
				apprReceive=i;
				apprDefLineInfo.setApprOrder(apprReceive);
			}
			apprDefLineInfo.setDefLineId(defLineId);
			apprDefLineInfo.setInfoId(idgenService.getNextId());			
			apprDefLineInfodao.create(apprDefLineInfo);	
		}	
		return object.getDefLineId();
	}

	/*
	 * 검색어에 의한 결재선 목록
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.service.ApprDefLineService#listBySearchCondition(com.lgcns.ikep4.approval.admin.search.ApprDefLineSearchCondition)
	 */
	public SearchResult<ApprDefLine> listBySearchCondition(ApprDefLineSearchCondition searchCondition) { 
		
		Integer count = this.apprDefLinedao.countBySearchCondition(searchCondition);
		
		searchCondition.terminateSearchCondition(count);  
		
		SearchResult<ApprDefLine> searchResult = null;
		
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprDefLine>(searchCondition);
		}
		else {
			List<ApprDefLine> apprDefLineList = (List<ApprDefLine>)this.apprDefLinedao.listBySearchCondition(searchCondition);  
			searchResult = new SearchResult<ApprDefLine>(apprDefLineList, searchCondition); 
		}  
		
		return searchResult;
	}

	/*
	 * 결재선 전체 목록
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.service.ApprDefLineService#listApprDefLine()
	 */
	public List<ApprDefLine> listApprDefLine() {
		List<ApprDefLine> list = (List<ApprDefLine>)this.apprDefLinedao.listApprDefLine();  
		return list;
	}

	/* 
	 * 결재선의 시스템,결재 유형(내부/협조)별 목록
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.service.ApprDefLineService#listApprDefLine(java.lang.String)
	 */
	public List<ApprDefLine> listApprDefLineType(ApprDefLine apprDefLine) {
		List<ApprDefLine> list = (List<ApprDefLine>)this.apprDefLinedao.listApprDefLineType(apprDefLine);  
		return list;
	}
	

	/* 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/* 
	 * Default 결재선 조회
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.Serializable)
	 */
	public ApprDefLine read(String defLineId) {
		ApprDefLine apprDefLine = apprDefLinedao.get(defLineId);
		apprDefLine.setApprDefLineInfo(apprDefLineInfodao.getDefLineInfoList(defLineId));
		return apprDefLine;
	}

	/* 
	 * 결재선 정보 수정
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang.Object)
	 */
	public void update(ApprDefLine object) {

		// Default 결재선 정보 수정
		apprDefLinedao.update(object);
		
		// 결재문서 Default Line Info 삭제
		apprDefLineInfodao.remove(object.getDefLineId());
		
		// 결재순서 지정 : 합의방법이 병렬인경우
		int apprOrder=0;// 최초 합의 순서 설정
		int i=0;
		int j=0;
		//int k=0;
		
		for(ApprDefLineInfo apprDefLineInfo : object.getApprDefLineInfo()){
			
			if(object.getDefLineWay()==1 && j==0 && ( apprDefLineInfo.getApprType()==1|| apprDefLineInfo.getApprType() ==2) )	//	병렬합의
			{
				i++;
				apprOrder=i;
				j++;
			}
			
			if(object.getDefLineWay()==1 && j>0 && ( apprDefLineInfo.getApprType()==1|| apprDefLineInfo.getApprType() ==2) ) {
				apprDefLineInfo.setApprOrder(apprOrder);
				//k++;
			} else {
				i++;
				apprDefLineInfo.setApprOrder(i);
				//i++;
				//k=0;
			}
			apprDefLineInfo.setDefLineId(object.getDefLineId());
			apprDefLineInfo.setInfoId(idgenService.getNextId());			
			apprDefLineInfodao.create(apprDefLineInfo);	
			//if(k==1)
			//	i++;
		}	
	}
	


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.Serializable)
	 */
	public void delete(String arg0) {
		// TODO Auto-generated method stub

	}
	
	/*
	 * 결재선 삭제
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.service.ApprDefLineService#delete(java.util.List)
	 */
	public void delete(List<String> defLineIds) {
		for (int i = 0; i < defLineIds.size(); i++) {
			
			apprDefLineInfodao.remove(defLineIds.get(i));
			apprDefLinedao.remove(defLineIds.get(i));	
		}
	}	
}
