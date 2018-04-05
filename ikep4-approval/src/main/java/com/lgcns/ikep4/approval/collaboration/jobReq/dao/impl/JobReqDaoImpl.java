/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.jobReq.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.collaboration.jobReq.dao.JobReqDao;
import com.lgcns.ikep4.approval.collaboration.jobReq.model.JobReq;
import com.lgcns.ikep4.approval.collaboration.jobReq.search.JobReqListSearchCondition;
import com.lgcns.ikep4.approval.collaboration.legal.model.ApprLegal;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 알람 관리 DAO 구현
 * 
 * @author jeehye
 * @version $Id: ApprNoticeDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("JobReqDao")
public class JobReqDaoImpl extends GenericDaoSqlmap<JobReq, String> implements JobReqDao {

	private static final String NAMESPACE = "approval.collaboration.jobReq.dao.JobReq.";

	/**
	 * 법무관리자 여부
	 * @param userId
	 * @return int
	 */
	public Integer userAuthMgntYn(String userId) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "userAuthMgntYn", userId);
	}
	
	/**
	 * 업무의뢰 요청 목록 갯수 
	 * @param 	ApprFormSearchCondition
	 * @return 	SearchResult
	 */
	public Integer jobReqCount(JobReqListSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "jobReqCount", searchCondition);
	}
	
	/**
	 * 업무 의뢰 요청 목록 LIST
	 * @param 	ApprFormSearchCondition
	 * @return 	SearchResult
	 */
	public List<JobReq> jobReqList(JobReqListSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "jobReqList", searchCondition);
	}
	
	/**
	 * 업무 의뢰 요청 번호 가져오기
	 * @param 	dateNo
	 * @return 	
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> jobReqMgntNoInfo(String dateNo) {
		return  (Map<String, Object>) sqlSelectForObject(NAMESPACE + "jobReqMgntNoInfo", dateNo);
	}
	
	/**
	 * 업무 의뢰 요청 등록시 요청서 메인테이블 insert 
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqSaveReview(JobReq jobReq) {
		sqlInsert(NAMESPACE + "jobReqSaveReview", jobReq);
	}

	/**
	 * 업무 의뢰 요청 등록시 결제선 insert
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqSaveApprLine(JobReq jobReq) {
		sqlInsert(NAMESPACE + "jobReqSaveApprLine", jobReq);
	}
	
	/**
	 * 승인 단계
	 * @param 	paramMap
	 * @return 	
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getApprLv(Map<String, String> paramMap) {
		return (Map<String, String>) sqlSelectForObject(NAMESPACE + "getApprLv", paramMap);
	}
	
	/**
	 * 업무 의뢰 요청서 상세 정보
	 * @param 	paramMap
	 * @return 	
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> jobReqDetail(Map<String, String> paramMap) {
		return  (Map<String, String>) sqlSelectForObject(NAMESPACE + "jobReqDetail", paramMap);
	}
	
	/**
	 * 업무 의뢰 요청 상세페이지 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqModifyReview(JobReq jobReq) {
		sqlInsert(NAMESPACE + "jobReqModifyReview", jobReq);
	}
	
	/**
	 * 업무 의뢰 요청 상세페이지 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqModifyApprLine(JobReq jobReq) {
		sqlInsert(NAMESPACE + "jobReqModifyApprLine", jobReq);
	}
	
	/**
	 * 업무 의뢰 요청 상세페이지 메인테이블 delete
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqDeleteReview(String jobReqMgntNo) {
		sqlInsert(NAMESPACE + "jobReqDeleteReview", jobReqMgntNo);
	}
	
	/**
	 * 업무 의뢰 요청 상세페이지 결제라인 테이블 delete
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqDeleteApprLine(String jobReqMgntNo) {
		sqlInsert(NAMESPACE + "jobReqDeleteApprLine", jobReqMgntNo);
	}
	
	/**
	 * 검토이력 삭제
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqDeleteHistory(String jobReqMgntNo) {
		sqlInsert(NAMESPACE + "jobReqDeleteHistory", jobReqMgntNo);
	}
	
	/**
	 * 검토결과 삭제
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqDeleteResult(String jobReqMgntNo) {
		sqlInsert(NAMESPACE + "jobReqDeleteResult", jobReqMgntNo);
	}
	
	/**
	 * 업무 의뢰 검토 이력 목록
	 * @param 	ApprFormSearchCondition
	 * @return 	SearchResult
	 */
	public List<JobReq> jobReqHistoryList(String jobReqMgntNo) {
		return sqlSelectForList(NAMESPACE + "jobReqHistoryList", jobReqMgntNo);
	}
	
	/**
	 * 검토이력등록
	 * @param 	jobReq
	 * @return 	
	 */
	public void jobReqHistorySave(JobReq jobReq) {
		sqlInsert(NAMESPACE + "jobReqHistorySave", jobReq);
	}
	
	/**
	 * 검토 이력 상세 정보
	 * @param 	apprLegal
	 * @return 	
	 */
	public JobReq jobReqHistoryDetail(Map<String, Object> paramMap) {
		return  (JobReq) sqlSelectForObject(NAMESPACE + "jobReqHistoryDetail", paramMap);
	}
	
	/**
	 * 업무 의뢰 승인 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqApprReview(JobReq jobReq) {
		sqlInsert(NAMESPACE + "jobReqApprReview", jobReq);
	}
	
	/**
	 * 업무 의뢰 승인 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqApprApprLine(JobReq jobReq) {
		sqlInsert(NAMESPACE + "jobReqApprLine", jobReq);
	}
	
	/**
	 * 업무 의뢰 기안부서 접수시 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqReqReceiptReview(JobReq jobReq) {
		sqlInsert(NAMESPACE + "jobReqReqReceiptReview", jobReq);
	}
	
	/**
	 * 업무 의뢰 기안부서 접수시 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqReqReceiptLine(JobReq jobReq) {
		sqlInsert(NAMESPACE + "jobReqReqReceiptLine", jobReq);
	}
	
	/**
	 * 업무 의뢰 주관부서 접수시 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqRcvReceiptReview(JobReq jobReq) {
		sqlInsert(NAMESPACE + "jobReqRcvReceiptReview", jobReq);
	}
	
	/**
	 * 업무 의뢰 주관부서 접수시 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqRcvReceiptLine(JobReq jobReq) {
		sqlInsert(NAMESPACE + "jobReqRcvReceiptLine", jobReq);
	}
	
	/**
	 * 업무의뢰 검토 결과 등록 insert 
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqResultSave(JobReq jobReq) {
		 sqlInsert(NAMESPACE + "jobReqResultSave", jobReq);
	}
	
	/**
	 * 업무의뢰 검토 결과 수정 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqResultModify(JobReq jobReq) {
		sqlInsert(NAMESPACE + "jobReqResultModify", jobReq);
	}
	
	/**
	 * 업무의뢰 검토 결과 상세 정보
	 * @param 	apprLegal
	 * @return 	
	 */
	public JobReq jobReqResultDetail(Map<String, Object> paramMap) {
		return  (JobReq) sqlSelectForObject(NAMESPACE + "jobReqResultDetail", paramMap);
	}
	
	/**
	 * 업무의뢰 검토 이력 상세 정보(이메일용)
	 * @param 	apprLegal
	 * @return 	
	 */
	public JobReq getJobReqDetailInfo(String jobReqMgntNo) {
		return  (JobReq) sqlSelectForObject(NAMESPACE + "getJobReqDetailInfo", jobReqMgntNo);
	}
	
	/**
	 * 파일 아이디 가져오기
	 * @param 	apprLegal
	 * @return 	
	 */
	public String getFileId(String jobReqMgntNo) {
		return  (String) sqlSelectForObject(NAMESPACE + "getFileId", jobReqMgntNo);
	}
	
	/**
	 * 파일 아이디 가져오기(검토이력)
	 * @param 	apprLegal
	 * @return 	
	 */
	public String getHistoryFileId(String jobReqMgntNo) {
		return  (String) sqlSelectForObject(NAMESPACE + "getHistoryFileId", jobReqMgntNo);
	}
	
	/**
	 * 계약서 파일 ID 수정
	 * @param apprLegal
	 * @throws Exception
	 */
	public void updateFileId( JobReq jobReq) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateFileId" , jobReq);
	}
	
	/**
	 * 계약서 파일 ID 수정(검토이력)
	 * @param apprLegal
	 * @throws Exception
	 */
	public void updateHistoryFileId( JobReq jobReq) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateHistoryFileId" , jobReq);
	}
	
	/**
	 * 사용자 관리 권한에 법무팀 사원번호 가져오기
	 * @param 	workGbnCd
	 * @return 	
	 */
	public List<JobReq> getLegalEmpNo(String workGbnCd)  {
		return  (List<JobReq>) sqlSelectForList(NAMESPACE + "getLegalEmpNo", workGbnCd);
	}
	
	/**
	 * 반려사유 가져오기
	 * @param 	contractMgntNo
	 * @return 	
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getReject(String contractMgntNo) {
		return  (Map<String, String>) sqlSelectForObject(NAMESPACE + "getReject", contractMgntNo);
	}
	
	/*     */
	public String create(JobReq arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public JobReq get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(JobReq arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
