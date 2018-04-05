/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.testreq.dao;

import java.util.List;

import com.lgcns.ikep4.approval.collaboration.testreq.model.TestRequest;
import com.lgcns.ikep4.approval.collaboration.testreq.search.TestRequestSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * 시험의뢰서 DAO
 * 
 * @author pjh
 * @version $Id$
 */
public interface TestRequestDao extends GenericDao<TestRequest, String> {
	
	/**
	 * 시험의뢰서 목록 Count
	 * @param newProductDevSearchCondition
	 * @return
	 * @throws Exception
	 */
	public int getTestRequestCount( TestRequestSearchCondition testRequestSearchCondition) throws Exception;
	
	/**
	 * 시험의뢰서 목록 조회
	 * @param newProductDevSearchCondition
	 * @return
	 * @throws Exception
	 */
	public List<TestRequest> getTestRequestList( TestRequestSearchCondition testRequestSearchCondition) throws Exception;
	
	/**
	 * 시험의뢰서 조회
	 * @param testRequestSearchCondition
	 * @return
	 * @throws Exception
	 */
	public TestRequest getTestRequest( TestRequestSearchCondition testRequestSearchCondition) throws Exception;
	
	/**
	 * 메일발송 배치 대상자 조회
	 * @param testRequestSearchCondition
	 * @return
	 * @throws Exception
	 */
	public List<TestRequest> getScheduleLimitTarget() throws Exception;
	
	/**
	 * 시험의뢰서 저장
	 * @param testRequest
	 * @throws Exception
	 */
	public void insertTestRequest( TestRequest testRequest) throws Exception;
	
	/**
	 * 시험의뢰서 결제선 저장
	 * @param testRequest
	 * @throws Exception
	 */
	public void insertTestReqApprLine( TestRequest testRequest) throws Exception;
	
	/**
	 * 시험의뢰서 파일 ID 수정
	 * @param testRequest
	 * @throws Exception
	 */
	public void updateFileId( TestRequest testRequest) throws Exception;
	
	/**
	 * 시험의뢰서 수정
	 * @param testRequest
	 * @throws Exception
	 */
	public void updateTestRequest( TestRequest testRequest) throws Exception;
	
	/**
	 * 시험의뢰서 결제선  수정
	 * @param testRequest
	 * @throws Exception
	 */
	public void updateTestReqApprLine( TestRequest testRequest) throws Exception;
	
	/**
	 * 시험의뢰서 삭제
	 * @param testRequest
	 * @throws Exception
	 */
	public void deleteTestRequest( TestRequest testRequest) throws Exception;
	
	/**
	 * 시험의뢰서 결제선 삭제
	 * @param testRequest
	 * @throws Exception
	 */
	public void deleteTestReqApprLine( TestRequest testRequest) throws Exception;
	
	/**
	 * 시험의뢰서 승인정보 업데이트
	 * @param testRequest
	 * @throws Exception
	 */
	public void approveTestRequest( TestRequest testRequest) throws Exception;
	
	/**
	 * 시험의뢰서 결제선 승인정보 업데이트
	 * @param testRequest
	 * @throws Exception
	 */
	public void approveTestReqApprLine( TestRequest testRequest) throws Exception;
	
	/**
	 * 시험의뢰서 추가사항 수정
	 * @param testRequest
	 * @throws Exception
	 */
	public void updateAddWriteDetail( TestRequest testRequest) throws Exception;
	
	/**
	 * 초기화
	 * @param testRequest
	 * @throws Exception
	 */
	public void initTestRequest( TestRequest testRequest) throws Exception;
	
	/**
	 * 초기화
	 * @param testRequest
	 * @throws Exception
	 */
	public void initTestReqApprLine( TestRequest testRequest) throws Exception;
}

