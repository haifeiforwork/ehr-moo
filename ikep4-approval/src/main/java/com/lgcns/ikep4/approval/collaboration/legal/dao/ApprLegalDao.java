/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.legal.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.collaboration.legal.model.ApprLegal;
import com.lgcns.ikep4.approval.collaboration.legal.search.ApprContractListSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * TODO Javadoc주석작성
 * 
 * @author 서지혜
 * @version $Id$
 */

public interface ApprLegalDao {
	
	/**
	 * 법무관리자 여부
	 * @param userId
	 * @return int
	 */
	Integer userAuthMgntYn(String userId);
	
	/**
	 * 계약서 검토 요청서 목록 갯수
	 * @param boardItemSearchCondition
	 * @return
	 */
	Integer apprContractCount(ApprContractListSearchCondition searchCondition);

	/**
	 * 계약서 검토 요청서 목록
	 * @param boardItemSearchCondition
	 * @return
	 */
	List<ApprLegal> apprContractList(ApprContractListSearchCondition searchCondition);
	
	/**
	 * 계약서 검토 이력 목록
	 * @param boardItemSearchCondition
	 * @return
	 */
	List<ApprLegal> apprContractHistoryList(String contractMgntNo);
	
	/**
	 * 승인 단계
	 * @param paramMap
	 * @return
	 */
	Map<String, String> getApprLv(Map<String, String> paramMap);
	
	/**
	 * 계약서 검토 요청 상세정보
	 * @param 	apprLegal
	 * @return 	
	 */
	public Map<String, String> apprContractDetail(Map<String, String> paramMap);
	
	/**
	 * 계약서 검토 요청 등록시 id값 가져오기
	 * @param 	apprLegal
	 * @return 	
	 */
	public Map<String, Object> getContractMgntNo(String dateNo);
	
	/**
	 * 계약서 검토 결과 등록 
	 * @param 	apprLegal
	 * @return 	
	 */
	public void apprContractResultSave(ApprLegal apprLegal);
	
	/**
	 * 계약서 검토 결과 수정
	 * @param 	apprLegal
	 * @return 	
	 */
	public void apprContractResultModify(ApprLegal apprLegal);

	/**
	 * 계약서 검토 결과 상세정보
	 * @param 	apprLegal
	 * @return 	
	 */
	public ApprLegal apprContractResultDetail(Map<String, Object> paramMap);
	
	/**
	 * 계약서 검토 요청 등록시 요청서 메인테이블 insert 
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractSaveReview(ApprLegal apprLegal);
	
	/**
	 * 계약서 검토 요청 등록시 결제선 insert
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractSaveApprLine(ApprLegal apprLegal);
	
	/**
	 * 계약서 검토 요청 상세페이지 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractModifyReview(ApprLegal apprLegal);
	
	/**
	 * 계약서 검토 요청 상세페이지 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractModifyApprLine(ApprLegal apprLegal);
	
	/**
	 * 계약서 검토 요청 상세페이지 메인테이블 delete
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractDeleteReview(String contractMgntNo);
	
	/**
	 * 계약서 검토 요청 상세페이지 결제라인 테이블 delete
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractDeleteApprLine(String contractMgntNo);
	
	/**
	 * 검토이력 삭제
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractDeleteHistory(String contractMgntNo);
	
	/**
	 * 검토결과 삭제
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractDeleteResult(String contractMgntNo);
	
	/**
	 * 계약서 승인시 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractApprReview(ApprLegal apprLegal);
	
	/**
	 * 계약서 승인시 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractApprApprLine(ApprLegal apprLegal);
	
	/**
	 * 계약서 기안부서 접수시 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractReqReceiptReview(ApprLegal apprLegal);
	
	/**
	 * 계약서 기안부서 접수시 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractReqReceiptLine(ApprLegal apprLegal);
	
	/**
	 * 계약서 주관부서 접수시 메인테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractRcvReceiptReview(ApprLegal apprLegal);
	
	/**
	 * 계약서 주관부서 접수시 결제라인 테이블 update
	 * @param 	apprLegal
	 * @return 	
	 */
	public void contractRcvReceiptLine(ApprLegal apprLegal);
	
	/**
	 * 검토이력 등록
	 * @param 	apprLegal
	 * @return 	
	 */
	public void historySave(ApprLegal apprLegal);
	
	
	/**
	 * 검토이력 상세정보
	 * @param 	apprLegal
	 * @return 	
	 */
	public ApprLegal apprContractHistoryDetail(Map<String, Object> paramMap);
	
	
	/**
	 * 계약서 상세정보 가져오기(메일용)
	 * @param 	apprLegal
	 * @return 	
	 */
	public ApprLegal getContractDetailInfo(String contractMgntNo);
	
	/**
	 * 계약서 파일ID 가져오기
	 * @param 	contractMgntNo
	 * @return 	
	 */
	public String getFileId(String contractMgntNo);
	
	/**
	 * 계약서 파일ID 가져오기(검토이력)
	 * @param 	contractMgntNo
	 * @return 	
	 */
	public String getHistoryFileId(String contractMgntNo);
	
	/**
	 * 계약서 파일 ID 수정
	 * @param apprLegal
	 * @throws Exception
	 */
	public void updateFileId( ApprLegal apprLegal) throws Exception;
	
	/**
	 * 계약서 파일 ID 수정(검토이력)
	 * @param apprLegal
	 * @throws Exception
	 */
	public void updateHistoryFileId( ApprLegal apprLegal) throws Exception;
	
	/**
	 * 사용자 관리 권한에 법무팀 사원번호 가져오기
	 * @param 	workGbnCd
	 * @return 	
	 */
	public List<ApprLegal> getLegalEmpNo(String workGbnCd);
	
	/**
	 * 반려사유 가져오기
	 * @param 	contractMgntNo
	 * @return 	
	 */
	public Map<String, String> getReject(String contractMgntNo);
}
