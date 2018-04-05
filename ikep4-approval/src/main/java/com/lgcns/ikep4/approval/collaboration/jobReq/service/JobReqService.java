/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.jobReq.service;


import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.collaboration.jobReq.model.JobReq;
import com.lgcns.ikep4.approval.collaboration.jobReq.search.JobReqListSearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 알림 관리 서비스
 *
 * @author jeehye
 * @version $Id: ApprNoticeService.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Transactional
public interface JobReqService {
	/**
	 * 법무관리자 여부
	 * 
	 * @param userId
	 * @return Boolean
	 */
	public boolean userAuthMgntYn(String userId);
	
	/**
	 * 업무 의뢰 요청서 List
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public SearchResult<JobReq> jobReqList(JobReqListSearchCondition searchCondition);
	
	/**
	 * 업무 의뢰 요청서 신규등록
	 * 
	 * @param jobReq 문서 상세 내용
	 * @return
	 */
	public void jobReqSave(JobReq jobReq);
	
	/**
	 * 업무 의뢰 상세정보
	 * 
	 * @param contractMgntNo 문서관리 번호
	 * @return
	 */
	public Map<String, String> jobReqDetail(Map<String, String> paramMap);
	
	/**
	 * 업무 의뢰 수정
	 * 
	 * @param jobReq 문서 상세 내용
	 * @return
	 */
	public void jobReqModify(JobReq jobReq);
	
	/**
	 * 업무 의뢰 삭제
	 * 
	 * @param jobReq 문서 상세 내용
	 * @return
	 */
	public void jobReqDelete(String jobReqMgntNo);
	
	/**
	 * 업무 의뢰 검토 이력 List
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<JobReq> jobReqHistoryList(String jobReqMgntNo);
	
	/**
	 * 검토이력 등록
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void jobReqHistorySave(JobReq jobReq, User user);
	
	/**
	 * 업무 의뢰 승인
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void jobReqAppr(JobReq jobReq);
	
	/**
	 * 업무 의뢰 기안부서 접수
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void jobReqReqReceipt(JobReq jobReq);
	
	/**
	 * 업무 의뢰 주관부서 접수
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void jobReqRcvReceipt(JobReq jobReq);
	
	/**
	 * 검토결과 등록
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void jobReqResultSave(JobReq jobReq);
	
	/**
	 * 검토결과 수정
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void jobReqResultModify(JobReq jobReq);
	
	/**
	 * 파일저장
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public String updateFile( JobReq jobReq, User user) throws Exception;
	
	/**
	 * 파일 저장후 갱신데이터 리턴
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String ajaxUdateFile( JobReq jobReq, User user) throws Exception;
	
	/**
	 * 파일정보 조회
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public JobReq getFileObject( JobReq jobReq, User user) throws Exception;
	
}
