/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.legal.service;


import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.collaboration.legal.model.ApprLegal;
import com.lgcns.ikep4.approval.collaboration.legal.search.ApprContractListSearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 알림 관리 서비스
 *
 * @author jeehye
 * @version $Id: ApprNoticeService.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Transactional
public interface ApprLegalService {
	/**
	 * 법무관리자 여부
	 * 
	 * @param userId
	 * @return Boolean
	 */
	public boolean userAuthMgntYn(String userId);
	
	/**
	 * 계약서 List(일반 사용자)
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public SearchResult<ApprLegal> apprContractList(ApprContractListSearchCondition searchCondition);
	
	/**
	 * 계약서 List(법무 관리자)
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public SearchResult<ApprLegal> apprContractAdminList(ApprContractListSearchCondition searchCondition);

	/**
	 * 계약서 검토 이력 List
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<ApprLegal> apprContractHistoryList(String contractMgntNo);
	
	/**
	 * 계약서 상세정보
	 * 
	 * @param contractMgntNo 문서관리 번호
	 * @return
	 */
	public Map<String, String> apprContractDetail(Map<String, String> paramMap);
	
	/**
	 * 계약서 기안부서 접수
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void contractReqReceipt(ApprLegal apprLegal);
	
	/**
	 * 계약서 주관부서 접수
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void contractRcvReceipt(ApprLegal apprLegal);
	
	/**
	 * 계약서 신규등록
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void contractSave(ApprLegal apprLegal);
	
	/**
	 * 계약서 수정
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void contractModify(ApprLegal apprLegal);
	
	/**
	 * 계약서 삭제
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void contractDelete(String contractMgntNo);
	
	/**
	 * 계약서 승인
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void contractAppr(ApprLegal apprLegal);
	
	/**
	 * 검토이력 등록
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void historySave(ApprLegal apprLegal, User user);
	
	/**
	 * 검토결과 등록
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void apprContractResultSave(ApprLegal apprLegal);
	
	/**
	 * 검토결과 수정
	 * 
	 * @param apprLegal 문서 상세 내용
	 * @return
	 */
	public void apprContractResultModify(ApprLegal apprLegal);
	
	/**
	 * 파일저장
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public String updateFile( ApprLegal apprLegal, User user) throws Exception;
	
	/**
	 * 파일 저장후 갱신데이터 리턴
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String ajaxUdateFile( ApprLegal apprLegal, User user) throws Exception;
	
	/**
	 * 파일정보 조회
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public ApprLegal getFileObject( ApprLegal apprLegal, User user) throws Exception;
}
