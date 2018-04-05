/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.jobReq.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.collaboration.jobReq.model.JobReq;
import com.lgcns.ikep4.approval.collaboration.jobReq.search.JobReqListSearchCondition;
import com.lgcns.ikep4.approval.collaboration.jobReq.search.JobReqListSearchCondition;
import com.lgcns.ikep4.approval.collaboration.legal.model.ApprLegal;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * TODO Javadoc주석작성
 * 
 * @author 서지혜
 * @version $Id$
 */

public interface JobReqDao {
	
	/**
	 * 법무관리자 여부
	 * @param userId
	 * @return int
	 */
	Integer userAuthMgntYn(String userId);
	
	/**
	 * 업무 의뢰 요청서 목록 갯수
	 * @param searchCondition
	 * @return
	 */
	Integer jobReqCount(JobReqListSearchCondition searchCondition);

	/**
	 * 업무 의뢰 요청서 목록
	 * @param searchCondition
	 * @return
	 */
	List<JobReq> jobReqList(JobReqListSearchCondition searchCondition);
	
	/**
	 * 업무 의뢰 요청 등록시 id값 가져오기
	 * @param 	jobReq
	 * @return 	
	 */
	public Map<String, Object> jobReqMgntNoInfo(String dateNo);
	
	/**
	 * 업무 의뢰 요청 등록시 요청서 메인테이블 insert 
	 * @param 	jobReq
	 * @return 	
	 */
	public void jobReqSaveReview(JobReq jobReq);
	
	/**
	 * 업무 의뢰 요청 등록시 결제선 insert
	 * @param 	jobReq
	 * @return 	
	 */
	public void jobReqSaveApprLine(JobReq jobReq);
	
	/**
	 * 승인 단계
	 * @param paramMap
	 * @return
	 */
	Map<String, String> getApprLv(Map<String, String> paramMap);
	
	/**
	 * 업무 의뢰 요청 상세정보
	 * @param 	paramMap
	 * @return 	
	 */
	public Map<String, String> jobReqDetail(Map<String, String> paramMap);
	
	/**
	 * 업무 의뢰 요청 상세페이지 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqModifyReview(JobReq jobReq);
	
	/**
	 * 업무 의뢰 요청 상세페이지 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqModifyApprLine(JobReq jobReq);
	
	/**
	 * 업무 의뢰 요청 상세페이지 메인테이블 delete
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqDeleteReview(String jobReqMgntNo);
	
	/**
	 * 업무 의뢰 요청 상세페이지 결제라인 테이블 delete
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqDeleteApprLine(String jobReqMgntNo);
	
	/**
	 * 검토이력 삭제
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqDeleteHistory(String jobReqMgntNo);
	
	/**
	 * 검토결과 삭제
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqDeleteResult(String jobReqMgntNo);
	
	/**
	 * 업무 의뢰 검토 이력 목록
	 * @param boardItemSearchCondition
	 * @return
	 */
	List<JobReq> jobReqHistoryList(String jobReqMgntNo);
	
	/**
	 * 검토이력 등록
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqHistorySave(JobReq jobReq);
	
	/**
	 * 검토이력 상세정보
	 * @param 	apprLegal
	 * @return 	
	 */
	public JobReq jobReqHistoryDetail(Map<String, Object> paramMap);
	
	/**
	 * 업무 의뢰 승인시 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqApprReview(JobReq jobReq);
	
	/**
	 * 업무 의뢰 승인시 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqApprApprLine(JobReq jobReq);
	
	/**
	 * 업무 의뢰 기안부서 접수시 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqReqReceiptReview(JobReq jobReq);
	
	/**
	 * 업무 의뢰 기안부서 접수시 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqReqReceiptLine(JobReq jobReq);
	
	/**
	 * 업무 의뢰 주관부서 접수시 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqRcvReceiptReview(JobReq jobReq);
	
	/**
	 * 업무 의뢰 주관부서 접수시 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqRcvReceiptLine(JobReq jobReq);
	
	/**
	 * 업무 의뢰 검토 결과 등록 
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqResultSave(JobReq jobReq);
	
	/**
	 * 업무 의뢰 검토 결과 수정
	 * @param 	apprLegal
	 * @return 	
	 */
	public void jobReqResultModify(JobReq jobReq);
	
	/**
	 * 업무 의뢰 검토 결과 상세정보
	 * @param 	apprLegal
	 * @return 	
	 */
	public JobReq jobReqResultDetail(Map<String, Object> paramMap);
	
	/**
	 * 업무의뢰 상세정보 가져오기(메일용)
	 * @param 	jobReqMgntNo
	 * @return 	
	 */
	public JobReq getJobReqDetailInfo(String jobReqMgntNo);
	
	/**
	 * 계약서 파일ID 가져오기
	 * @param 	jobReqMgntNo
	 * @return 	
	 */
	public String getFileId(String jobReqMgntNo);
	
	/**
	 * 계약서 파일ID 가져오기(검토이력)
	 * @param 	jobReqMgntNo
	 * @return 	
	 */
	public String getHistoryFileId(String jobReqMgntNo);
	
	/**
	 * 계약서 파일 ID 수정
	 * @param apprLegal
	 * @throws Exception
	 */
	public void updateFileId( JobReq jobReq) throws Exception;
	
	/**
	 * 계약서 파일 ID 수정(검토이력)
	 * @param apprLegal
	 * @throws Exception
	 */
	public void updateHistoryFileId( JobReq jobReq) throws Exception;

	/**
	 * 사용자 관리 권한에 법무팀 사원번호 가져오기
	 * @param 	workGbnCd
	 * @return 	
	 */
	public List<JobReq> getLegalEmpNo(String workGbnCd);
	
	/**
	 * 반려사유 가져오기
	 * @param 	contractMgntNo
	 * @return 	
	 */
	public Map<String, String> getReject(String contractMgntNo);
	
}
