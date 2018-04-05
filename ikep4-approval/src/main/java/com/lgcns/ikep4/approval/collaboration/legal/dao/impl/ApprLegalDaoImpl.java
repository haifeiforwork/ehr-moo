/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.legal.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.collaboration.legal.dao.ApprLegalDao;
import com.lgcns.ikep4.approval.collaboration.legal.model.ApprLegal;
import com.lgcns.ikep4.approval.collaboration.legal.search.ApprContractListSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 알람 관리 DAO 구현
 * 
 * @author jeehye
 * @version $Id: ApprNoticeDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apprLegalDao")
public class ApprLegalDaoImpl extends GenericDaoSqlmap<ApprLegal, String> implements ApprLegalDao {

	private static final String NAMESPACE = "approval.collaboration.legal.dao.ApprLegal.";

	/**
	 * 법무관리자 여부
	 * @param userId
	 * @return int
	 */
	public Integer userAuthMgntYn(String userId) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "userAuthMgntYn", userId);
	}
	
	/**
	 * 계약서 
	 * @param 	ApprFormSearchCondition
	 * @return 	SearchResult
	 */
	public Integer apprContractCount(ApprContractListSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "apprContractCount", searchCondition);
	}
	
	/**
	 * 계약서 검토 요청 목록
	 * @param 	ApprFormSearchCondition
	 * @return 	SearchResult
	 */
	public List<ApprLegal> apprContractList(ApprContractListSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "apprContractList", searchCondition);
	}
	
	/**
	 * 계약서 검토 이력 목록
	 * @param 	ApprFormSearchCondition
	 * @return 	SearchResult
	 */
	public List<ApprLegal> apprContractHistoryList(String contractMgntNo) {
		return sqlSelectForList(NAMESPACE + "apprContractHistoryList", contractMgntNo);
	}
	
	/**
	 * 계약서 번호 가져오기
	 * @param 	dateNo
	 * @return 	
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getContractMgntNo(String dateNo) {
		return  (Map<String, Object>) sqlSelectForObject(NAMESPACE + "contractMgntNoInfo", dateNo);
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
	 * 계약서 검토 요청서 상세 정보
	 * @param 	paramMap
	 * @return 	
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> apprContractDetail(Map<String, String> paramMap) {
		return  (Map<String, String>) sqlSelectForObject(NAMESPACE + "apprContractDetail", paramMap);
	}
	
	
	/**
	 * 계약서 검토 결과 등록 insert 
	 * @param 	apprLegal
	 * @return 	
	 */
	public void apprContractResultSave(ApprLegal apprLegal) {
		 sqlInsert(NAMESPACE + "apprContractResultSave", apprLegal);
	}
	
	/**
	 * 계약서 검토 결과 수정 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void apprContractResultModify(ApprLegal apprLegal) {
		sqlInsert(NAMESPACE + "apprContractResultModify", apprLegal);
	}
	
	/**
	 * 계약서 검토 결과 상세 정보
	 * @param 	apprLegal
	 * @return 	
	 */
	public ApprLegal apprContractResultDetail(Map<String, Object> paramMap) {
		return  (ApprLegal) sqlSelectForObject(NAMESPACE + "apprContractResultDetail", paramMap);
	}
	
	/**
	 * 계약서 검토 요청 등록시 요청서 메인테이블 insert 
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractSaveReview(ApprLegal apprLegal) {
		sqlInsert(NAMESPACE + "contractSaveReview", apprLegal);
	}

	/**
	 * 계약서 검토 요청 등록시 결제선 insert
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractSaveApprLine(ApprLegal apprLegal) {
		sqlInsert(NAMESPACE + "contractSaveApprLine", apprLegal);
	}
	
	/**
	 * 계약서 검토 요청 상세페이지 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractModifyReview(ApprLegal apprLegal) {
		sqlInsert(NAMESPACE + "contractModifyReview", apprLegal);
	}
	
	/**
	 * 계약서 검토 요청 상세페이지 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractModifyApprLine(ApprLegal apprLegal) {
		sqlInsert(NAMESPACE + "contractModifyApprLine", apprLegal);
	}
	
	/**
	 * 계약서 검토 요청 상세페이지 메인테이블 delete
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractDeleteReview(String contractMgntNo) {
		sqlInsert(NAMESPACE + "contractDeleteReview", contractMgntNo);
	}
	
	/**
	 * 계약서 검토 요청 상세페이지 결제라인 테이블 delete
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractDeleteApprLine(String contractMgntNo) {
		sqlInsert(NAMESPACE + "contractDeleteApprLine", contractMgntNo);
	}
	
	/**
	 * 검토이력 삭제
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractDeleteHistory(String contractMgntNo) {
		sqlInsert(NAMESPACE + "contractDeleteHistory", contractMgntNo);
	}
	
	/**
	 * 검토결과 삭제
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractDeleteResult(String contractMgntNo) {
		sqlInsert(NAMESPACE + "contractDeleteResult", contractMgntNo);
	}
	
	/**
	 * 계약서 승인 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractApprReview(ApprLegal apprLegal) {
		sqlInsert(NAMESPACE + "contractApprReview", apprLegal);
	}
	
	/**
	 * 계약서 승인 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractApprApprLine(ApprLegal apprLegal) {
		sqlInsert(NAMESPACE + "contractApprLine", apprLegal);
	}
	
	/**
	 * 계약서 기안부서 접수시 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractReqReceiptReview(ApprLegal apprLegal) {
		sqlInsert(NAMESPACE + "contractReqReceiptReview", apprLegal);
	}
	
	/**
	 * 계약서 기안부서 접수시 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractReqReceiptLine(ApprLegal apprLegal) {
		sqlInsert(NAMESPACE + "contractReqReceiptLine", apprLegal);
	}
	
	/**
	 * 계약서 주관부서 접수시 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractRcvReceiptReview(ApprLegal apprLegal) {
		sqlInsert(NAMESPACE + "contractRcvReceiptReview", apprLegal);
	}
	
	/**
	 * 계약서 주관부서 접수시 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractRcvReceiptLine(ApprLegal apprLegal) {
		sqlInsert(NAMESPACE + "contractRcvReceiptLine", apprLegal);
	}
	
	/**
	 * 검토이력등록
	 * @param 	apprLegal
	 * @return 	
	 */
	public void historySave(ApprLegal apprLegal) {
		sqlInsert(NAMESPACE + "historySave", apprLegal);
	}
	
	/**
	 * 계약서 검토 이력 상세 정보
	 * @param 	apprLegal
	 * @return 	
	 */
	public ApprLegal apprContractHistoryDetail(Map<String, Object> paramMap) {
		return  (ApprLegal) sqlSelectForObject(NAMESPACE + "apprContractHistoryDetail", paramMap);
	}
	
	/**
	 * 계약서 검토 이력 상세 정보(이메일용)
	 * @param 	apprLegal
	 * @return 	
	 */
	public ApprLegal getContractDetailInfo(String contractMgntNo) {
		return  (ApprLegal) sqlSelectForObject(NAMESPACE + "getContractDetailInfo", contractMgntNo);
	}
	
	/**
	 * 파일 아이디 가져오기
	 * @param 	apprLegal
	 * @return 	
	 */
	public String getFileId(String contractMgntNo) {
		return  (String) sqlSelectForObject(NAMESPACE + "getFileId", contractMgntNo);
	}
	
	/**
	 * 파일 아이디 가져오기(검토이력)
	 * @param 	apprLegal
	 * @return 	
	 */
	public String getHistoryFileId(String contractMgntNo) {
		return  (String) sqlSelectForObject(NAMESPACE + "getHistoryFileId", contractMgntNo);
	}
	
	/**
	 * 계약서 파일 ID 수정
	 * @param apprLegal
	 * @throws Exception
	 */
	public void updateFileId( ApprLegal apprLegal) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateFileId" , apprLegal);
	}
	
	/**
	 * 계약서 파일 ID 수정(검토이력)
	 * @param apprLegal
	 * @throws Exception
	 */
	public void updateHistoryFileId( ApprLegal apprLegal) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateHistoryFileId" , apprLegal);
	}
	
	/**
	 * 사용자 관리 권한에 법무팀 사원번호 가져오기
	 * @param 	workGbnCd
	 * @return 	
	 */
	public List<ApprLegal> getLegalEmpNo(String workGbnCd)  {
		return  (List<ApprLegal>) sqlSelectForList(NAMESPACE + "getLegalEmpNo", workGbnCd);
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
	

	public String create(ApprLegal arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public ApprLegal get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(ApprLegal arg0) {
		// TODO Auto-generated method stub
		
	}

}
