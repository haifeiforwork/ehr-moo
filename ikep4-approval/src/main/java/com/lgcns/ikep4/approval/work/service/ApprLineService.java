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

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.approval.admin.model.ApprDoc;
import com.lgcns.ikep4.approval.work.model.ApprLine;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Default 결재선 관리 Service 정의
 *
 * @author 
 * @version $Id$
 */
@Transactional
public interface ApprLineService extends GenericService<ApprLine, String> {

	/*****************************************
	 * 문서 결재처리
	 ****************************************/
	
	/**
	 * 결재 처리
	 * @param apprLine
	 * @param user
	 */
	public void updateApproval(ApprLine apprLine,User user, HttpServletRequest request);	
	
	
	/**
	 * 부서수신함/개인수신함 접수자 전결 처리
	 * @param apprLine
	 * @param user
	 */
	public void updateApprovalRecEnd(ApprLine apprLine,User user, HttpServletRequest request);
	
	
	/**
	 * 결재취소 가능여부 확인 
	 * @param map
	 * @return
	 */
	public boolean existsNextApprProgress(Map<String, String> map);	
	
	/**
	 * 결재자의 결재취소 처리
	 * @param map
	 * @return
	 */
	public void updateApprovalLineCancel(ApprLine object, User user);
	
	/**
	 * 관리자>결재문서관리>문서조회>결재복원>결재 복원 ( 특정시점으로 )
	 * @param apprIds
	 * @param user
	 */
	public void updateApprovalLineRestore(String	apprId,String[] approverIds,  User user);	
	
	/**
	 * 결재회수를 위한 최초 결재자 결재 상태 체크	
	 * @param apprId
	 * @return
	 */
	public boolean beforeAppr(String apprId);
	
	/**
	 * 결재가 진행중인지 확인 ( APPR_STATUS >1 )
	 * @param apprId
	 * @return
	 */
	public boolean countProgress(String apprId);
	
	/**
	 * 결재회수 처리 --> 결재선정보의 진행상태 APPR_STATUS 0으로 초기화	
	 * @param apprId
	 * @param userLocale
	 */
	public void updateRecoveryAppr(String apprId,String userLocale);
	
	
	
	
	/*****************************************
	 * 결재선 정보 조회
	 ****************************************/
	
	/**
	 * 최종 결재자 정보( 결재선내의 마지막 결재자 정보 조회 -> 부서/수신처 문서의 마지막 진행자 정보 표시 ( 진행현황 ) )
	 * @param apprId
	 * @return
	 */
	public List<ApprLine> lastApprLine(String	apprId);
	
	/**
	 * 문서 결재선 정보 목록
	 * @param object
	 * @return
	 */
	public List<ApprLine> listApprLine(Map<String, String> map);
	
	/**
	 * child 결재선 정보 목록 ( 부서합의/수신처 )
	 * @param map
	 * @return
	 */
	public List<ApprLine> childListApprLine(Map<String, String> map);
	
	/**
	 * 문서의 결재선 존재유무
	 * @param map
	 * @return
	 */
	public boolean exists(Map<String, String> map);

	/**
	 * Doc 문서의 FormId로 Default 결재선의 결재선 정보 조회 (defLineId, apprType : 3 수신만, apprType != 3 결재/합의만) 
	 * @param map
	 * @return
	 */
	public List<ApprLine> listApprLineDef(Map<String, String> map);
		
	/**
	 * 결재/합의자의 결재선 정보 조회	
	 * @param apprLine
	 * @return
	 */
	public ApprLine readLine(ApprLine apprLine);
	
	/**
	 * 결재자의 다음 결재/합의 대상자 존재유무 확인
	 * @param map
	 * @return
	 */
	public boolean getNext(Map<String, String> map);
	

	
	/*****************************************
	 * 결재선 정보 갱신처리
	 ****************************************/	
	/**
	 * 결재선 등록/수정/변경처리
	 * @param apprLine
	 * @param user
	 * @return
	 */
	public String create(ApprLine apprLine,User user);
	
	/**
	 * 결재선 변경
	 * @param object
	 * @param apprType
	 * @param user
	 * @return
	 */
	public String updateApprLine(ApprLine object,  User user);
	/**
	 * 수신처 변경
	 * @param object
	 * @param user
	 * @return
	 */
	public String updateReceiverApprLine(ApprLine object , User user);
	
	/**
	 * Doc에서 선택한 결재선 정보 전체 삭제(결재/합의/수신처정보)	
	 * @param apprIds
	 */
	public void delete(List<String> apprIds);

	
	/**
	 * 결재/합의 자의 결재문서 Read 시간 수정	
	 * @param apprLine
	 */
	public void updateRead(ApprLine apprLine);
	
	
	/*****************************************
	 * 결재선 버전 정보
	 ****************************************/		
	/**
	 * 결재선 버전 목록
	 * @param apprId
	 * @return
	 */
	public List<ApprLine> listVersion(String	apprId);
	
	/**
	 * 결재선 버전별 중복제거 목록
	 * @param apprId
	 * @return
	 */
	public List<ApprLine> groupByVersion(String	apprId);
	



	/*****************************************
	 * 사용자 알림발송
	 ****************************************/	
	/**
	 * 사용자 알림발송
	 */
	public void	sendAlarm(User user,ApprDoc apprDoc, HttpServletRequest request);

}
