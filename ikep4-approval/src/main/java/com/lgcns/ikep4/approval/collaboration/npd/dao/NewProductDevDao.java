/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.npd.dao;

import java.util.List;

import com.lgcns.ikep4.approval.collaboration.npd.model.NewProductDev;
import com.lgcns.ikep4.approval.collaboration.npd.search.NewProductDevSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * 신제품관리 DAO
 * 
 * @author pjh
 * @version $Id$
 */
public interface NewProductDevDao extends GenericDao<NewProductDev, String> {
	
	/**
	 * 개발검토의뢰서 목록 Count
	 * @param newProductDevSearchCondition
	 * @return
	 * @throws Exception
	 */
	public int getProductDevCount( NewProductDevSearchCondition newProductDevSearchCondition) throws Exception;
	
	/**
	 * 개발검토의뢰서 목록 조회
	 * @param newProductDevSearchCondition
	 * @return
	 * @throws Exception
	 */
	public List<NewProductDev> getProductDevList( NewProductDevSearchCondition newProductDevSearchCondition) throws Exception;
	
	/**
	 * 개발검토의뢰서 단일 조회
	 * @param newProductDevSearchCondition
	 * @return
	 * @throws Exception
	 */
	public NewProductDev getDevRequest( NewProductDevSearchCondition newProductDevSearchCondition) throws Exception;
	
	/**
	 * 개발 의뢰 검토 완료 기간 초과 알림 대상 조회
	 * @return
	 * @throws Exception
	 */
	public List<NewProductDev> getScheduleLimitTarget() throws Exception;
	
	/**
	 * 개발검토의뢰서 등록
	 * @param dev
	 * @return
	 * @throws Exception
	 */
	public void insertTbDevRequest( NewProductDev newProductDev) throws Exception;
	
	/**
	 * 개발검토의뢰서 결재라인
	 * @param newProductDev
	 * @throws Exception
	 */
	public void insertTbDevReqApprLine( NewProductDev newProductDev) throws Exception;
	
	/**
	 * 개발검토의뢰서 수정
	 * @param newProductDev
	 * @throws Exception
	 */
	public void updateTbDevRequest( NewProductDev newProductDev) throws Exception;
	
	/**
	 * 승인/반려 - 개발검토의뢰서 수정
	 */
	public void updateApproveTbDevRequest( NewProductDev newProductDev) throws Exception;
	
	/**
	 * 승인/반려 - 개발검토의뢰서 결재라인 수정
	 */
	public void updateApproveTbDevReqApprLine( NewProductDev newProductDev) throws Exception;
	
	/**
	 * 결재라인 수정 
	 */
	public void updateTbDevReqApprLine( NewProductDev newProductDev) throws Exception;
	
	/**
	 * 결과파일항목ID 업데이트
	 * @param newProductDev
	 * @throws Exception
	 */
	public void updateRsltFileId( NewProductDev newProductDev) throws Exception;
	
	/**
	 * 개발검토의뢰서 삭제
	 * @param newProductDev
	 * @throws Exception
	 */
	public void deleteTbDevRequest( String mgntNo) throws Exception;
	
	/**
	 * 개발검토의뢰서 결재라인삭제
	 * @param newProductDev
	 * @throws Exception
	 */
	public void deleteTbDevReqApprLine( String mgntNo) throws Exception;
	
	/**
	 * 초기화 - 개발검토의뢰서
	 */
	public void initTbDevRequest( NewProductDev newProductDev) throws Exception;
	
	/**
	 * 초기화 - 개발검토의뢰서  결재라인
	 */
	public void initTbDevReqApprLine( NewProductDev newProductDev) throws Exception;
	
	/**
	 * 접수 개발검토의뢰서
	 */
	public void updateReciptTbDevRequest( NewProductDev newProductDev) throws Exception;
	
	/**
	 * 접수 개발검토의뢰서 결재라인
	 */
	public void updateReciptTbDevReqApprLine( NewProductDev newProductDev) throws Exception;
	
	/**
	 * 개발제안서 파일 ID 수정
	 * @param testRequest
	 * @throws Exception
	 */
	public void updateFileId( NewProductDev newProductDev) throws Exception;
	
	/**
	 * 요구일정 수정
	 * @param newProductDev
	 * @throws Exception
	 */
	public void updateReqScheduleCd( NewProductDev newProductDev) throws Exception;
}
