/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.work.model.ApprExam;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * 검토요청 서비스
 *
 * @author jeehye
 * @version $Id: ApprExamService.java 16234 2011-08-18 02:44:36Z jeehye $
 */
public interface ApprExamService extends GenericService<ApprExam, String> {
	
	/**
	 * 검토요청 저장
	 * @param ApprExam 모델
	 * @return  void
	 */
	public void apprExamCreate(ApprExam apprExam);
	
	/**
	 * 검토요청 목록
	 * @param ApprExam 모델
	 * @return  void
	 */
	public List<ApprExam> listApprExamInfo(ApprExam apprExam);
	
	/**
	 * 최초 검토 요청자 ID
	 * @param apprId
	 * @return  String
	 */
	public String examFirstReqId(String apprId);
	
	/**
	 * 검토요청 저장
	 * @param ApprExam 모델
	 * @return  void
	 */
	public void updateApprExamInfoSave(ApprExam apprExam);
	/**
	 * 관리자가 결재복원(결재회수,결재복원)시  해당 결재자의 검토요청 내용 삭제처리
	 * @param map
	 */
	public void remove(Map<String, String> map);
	
	/**
	 * 검토자여부 
	 * @param ApprExam 모델
	 * @return  void
	 */
	public boolean existExamUser (Map map);
	
	/**
	 * 검토자 존재 여부 
	 * @param ApprExam 모델
	 * @return  void
	 */
	public boolean existSetExamUser (ApprExam apprExam);
	
	/**
	 * 검토요청 권한 부여 여부 
	 * @param ApprExam 모델
	 * @return  void
	 */
	public boolean existExamIsRequest (Map map);
	
	/**
	 * 검토한 문서여부
	 * @param ApprExam 모델
	 * @return  void
	 */
	public boolean existExamStatus (Map map);
	
	
}
