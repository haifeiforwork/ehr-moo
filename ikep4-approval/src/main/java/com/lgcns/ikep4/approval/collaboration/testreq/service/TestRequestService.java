package com.lgcns.ikep4.approval.collaboration.testreq.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.collaboration.testreq.model.TestRequest;
import com.lgcns.ikep4.approval.collaboration.testreq.search.TestRequestSearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 시험의뢰서 SERVICE
 * 
 * @author pjh
 * @version $Id$
 */
@Transactional
public interface TestRequestService {
	
	/**
	 * 신제품 개발 목록 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public SearchResult<TestRequest> getTestRequestList( TestRequestSearchCondition testRequestSearchCondition, User user) throws Exception;
	
	/**
	 * 시험의뢰서 조회 및 기본셋팅
	 * @param newProductDevSearchCondition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTestRequestObject( TestRequestSearchCondition searchCondition, User user) throws Exception;
	
	/**
	 * 시험의뢰서 등록
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void createTestRequest( TestRequest testRequest, User user) throws Exception;
	
	/**
	 * 파일저장
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public String updateFile( TestRequest testRequest, User user) throws Exception;
	
	/**
	 * 파일 저장후 갱신데이터 리턴
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String ajaxUdateFile( TestRequest testRequest, User user) throws Exception;
	
	/**
	 * 파일정보 조회
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public TestRequest getFileObject( TestRequest testRequest, User user) throws Exception;
	
	/**
	 * 시험의뢰서 수정
	 * @param testRequest
	 * @param user
	 * @throws Exception
	 */
	public TestRequest updateTestRequest( TestRequest testRequest, User user) throws Exception;
	
	/**
	 * 시험의뢰서 삭제
	 * @param testRequest
	 * @param user
	 * @throws Exception
	 */
	public void deleteTestRequest( TestRequest testRequest, User user) throws Exception;
	
	/**
	 * 승인/부결
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void approveTestRequest( TestRequest testRequest, User user) throws Exception;
	
	
	/**
	 * 승인/부결
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void approveTestRequest( TestRequest testRequest, TestRequest oldTestRequest, User user) throws Exception;
	
	/**
	 * 메일 배치
	 * @throws Exception
	 */
	public void sendScheduleLimitSend() throws Exception;
	
	/**
	 * 시험의뢰서 추가사항 수정
	 * @param testRequest
	 * @throws Exception
	 */
	public void updateAddWriteDetail( TestRequest testRequest, User user) throws Exception;
	
	/**
	 * 시험의뢰서 재접수
	 * @param testRequest
	 * @throws Exception
	 */
	public void initTestRequest( TestRequest testRequest, User user) throws Exception;
	
	/**
	 * 권한확인  : 수정, 삭제, 재접수등
	 * @param testRequest
	 * @param user
	 * @param statusCd
	 * @throws Exception
	 */
	public TestRequest checkPermission( TestRequest testRequest, User user, int statCd) throws Exception;
	
	/**
	 * 수정, 상신 동시
	 * @param testRequest
	 * @param user
	 * @throws Exception
	 */
	public void updateWithApprove( TestRequest testRequest,User user) throws Exception;
}
