package com.lgcns.ikep4.approval.collaboration.testreq.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.collaboration.testreq.dao.TestRequestDao;
import com.lgcns.ikep4.approval.collaboration.testreq.model.TestRequest;
import com.lgcns.ikep4.approval.collaboration.testreq.search.TestRequestSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * 시험의뢰서 TestRequestDao 구현체 클래스
 * 
 * @author pjh
 * @version $Id$
 */
@Repository
public class TestRequestDaoImpl extends GenericDaoSqlmap<TestRequest, String> implements TestRequestDao{
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "com.lgcns.ikep4.approval.collaboration.testreq.dao.TestRequestDao.";
	
	
	/**
	 * 시험의뢰서 목록 Count
	 * @param newProductDevSearchCondition
	 * @return
	 * @throws Exception
	 */
	public int getTestRequestCount( TestRequestSearchCondition testRequestSearchCondition) throws Exception {
		// TODO Auto-generated method stub
		return (Integer) sqlSelectForObject( NAMESPACE + "getTestRequestCount" , testRequestSearchCondition);
	}
	
	/**
	 * 시험의뢰서 목록 조회
	 * @param newProductDevSearchCondition
	 * @return
	 * @throws Exception
	 */
	public List<TestRequest> getTestRequestList( TestRequestSearchCondition testRequestSearchCondition) throws Exception {
		
		return  sqlSelectForList( NAMESPACE + "getTestRequestList" , testRequestSearchCondition);
	}
	
	/**
	 * 시험의뢰서 조회
	 * @param testRequestSearchCondition
	 * @return
	 * @throws Exception
	 */
	public TestRequest getTestRequest( TestRequestSearchCondition testRequestSearchCondition) throws Exception {
		
		return (TestRequest) sqlSelectForObject( NAMESPACE + "getTestRequest" , testRequestSearchCondition);
	}
	
	/**
	 * 메일발송 배치 대상자 조회
	 * @param testRequestSearchCondition
	 * @return
	 * @throws Exception
	 */
	public List<TestRequest> getScheduleLimitTarget() throws Exception {
		
		return  sqlSelectForList( NAMESPACE + "getScheduleLimitTarget");
	}
	
	/**
	 * 시험의뢰서 저장
	 * @param testRequest
	 * @throws Exception
	 */
	public void insertTestRequest( TestRequest testRequest) throws Exception {
		
		sqlInsert( NAMESPACE + "insertTestRequest" , testRequest);
	}
	
	/**
	 * 시험의뢰서 결제선 저장
	 * @param testRequest
	 * @throws Exception
	 */
	public void insertTestReqApprLine( TestRequest testRequest) throws Exception {
		
		sqlInsert( NAMESPACE + "insertTestReqApprLine" , testRequest);
	}
	
	/**
	 * 시험의뢰서 파일 ID 수정
	 * @param testRequest
	 * @throws Exception
	 */
	public void updateFileId( TestRequest testRequest) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateFileId" , testRequest);
	}
	
	/**
	 * 시험의뢰서 수정
	 * @param testRequest
	 * @throws Exception
	 */
	public void updateTestRequest( TestRequest testRequest) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateTestRequest" , testRequest);
	}
	/**
	 * 시험의뢰서 결제선  수정
	 * @param testRequest
	 * @throws Exception
	 */
	public void updateTestReqApprLine( TestRequest testRequest) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateTestReqApprLine" , testRequest);
	}
	
	/**
	 * 시험의뢰서 삭제
	 * @param testRequest
	 * @throws Exception
	 */
	public void deleteTestRequest( TestRequest testRequest) throws Exception {
		
		sqlDelete( NAMESPACE + "deleteTestRequest" , testRequest);
	}
	
	/**
	 * 시험의뢰서 결제선 삭제
	 * @param testRequest
	 * @throws Exception
	 */
	public void deleteTestReqApprLine( TestRequest testRequest) throws Exception {
		
		sqlDelete( NAMESPACE + "deleteTestReqApprLine" , testRequest);
	}
	
	/**
	 * 시험의뢰서 승인정보 업데이트
	 * @param testRequest
	 * @throws Exception
	 */
	public void approveTestRequest( TestRequest testRequest) throws Exception {
		
		sqlUpdate( NAMESPACE + "approveTestRequest" , testRequest);
	}
	
	/**
	 * 시험의뢰서 결제선 승인정보 업데이트
	 * @param testRequest
	 * @throws Exception
	 */
	public void approveTestReqApprLine( TestRequest testRequest) throws Exception {
		
		sqlUpdate( NAMESPACE + "approveTestReqApprLine" , testRequest);
	}
	
	/**
	 * 시험의뢰서 추가사항 수정
	 * @param testRequest
	 * @throws Exception
	 */
	public void updateAddWriteDetail( TestRequest testRequest) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateAddWriteDetail" , testRequest);
	}
	
	/**
	 * 초기화
	 * @param testRequest
	 * @throws Exception
	 */
	public void initTestRequest( TestRequest testRequest) throws Exception {
		
		sqlUpdate( NAMESPACE + "initTestRequest" , testRequest);
	}
	
	/**
	 * 초기화
	 * @param testRequest
	 * @throws Exception
	 */
	public void initTestReqApprLine( TestRequest testRequest) throws Exception {
		
		sqlUpdate( NAMESPACE + "initTestReqApprLine" , testRequest);
	}
	
	public String create(TestRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public TestRequest get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(TestRequest arg0) {
		// TODO Auto-generated method stub
		
	}
}
