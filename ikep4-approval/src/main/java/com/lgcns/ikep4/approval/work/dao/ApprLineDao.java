/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.approval.work.model.ApprLine;


/**
 * Default 결재선 Dao 정의
 *
 * @author 
 * @version $Id$
 */
public interface ApprLineDao extends GenericDao<ApprLine, String> {

	
	/*****************************************
	 * 문서 결재처리
	 ****************************************/	
	
	/**
	 * 결재선 정보 결재처리 
	 * @param object
	 */
	public void updateApproval(ApprLine object);
	
	/**
	 * 결재자의 결재취소 처리
	 * @param map
	 * @return
	 */
	public void updateApprovalLineCancel(Map<String, String> map);
	/**
	 * 다음 결재자의 대기상태 처리
	 * @param map
	 * @return
	 */	
	public void updateInitNextApprLine(Map<String, String> map);	
	 
	/**
	 * 결재회수를 위한 최초 결재자 결재 상태 체크 ( true : 결재자 결재 )
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
	 * 결재취소 가능여부 확인 
	 * @param map
	 * @return
	 */
	public boolean existsNextApprProgress(Map<String, String> map);
	
	/**
	 * 다음 결재자 존재 유무
	 * @param map
	 * @return
	 */
	public boolean getNext(Map<String, String> map);
	
	/**
	 * 결재선 갯수
	 * @param object
	 * @return
	 */
	public int countApprLine(ApprLine object);
	
	/**
	 * 결재회수 처리 --> 결재선정보의 진행상태 APPR_STATUS 0으로 초기화
	 * @param apprId
	 */
	public void updateRecoveryAppr(String apprId);
	
	/**
	 * 동일 apprOrder 반려처리
	 * @param object
	 */
	public void updateRejectApprLine(ApprLine object);
	
	/**
	 * 접수자 전결 처리 ( 부서합의/수신처 문서의 접수자 )
	 * @param object
	 * @return
	 */
	public String createRecEnd(ApprLine object);	
	
	
	/*****************************************
	 * 결재선 정보 조회
	 ****************************************/
	
	/**
	 * 결재선 정보중 마지막 결재자 정보 표시
	 * @param apprId
	 * @return
	 */
	public List<ApprLine> lastApprLine(String	apprId);
	
	/**
	 * 문서의 결재선 정보
	 * @param object
	 * @return
	 */
	List<ApprLine> listApprLine(Map<String, String> map);	
	
	/**
	 * child 결재선 정보 ( 부서합의/수신처 )
	 * @param map
	 * @return
	 */
	public List<ApprLine> childListApprLine(Map<String, String> map);
	
	/**
	 * Doc 문서의 FormId로 Default 결재선의 결재선 정보 조회 (defLineId, apprType : 3 수신만, apprType != 3 결재/합의만)
	 * @param map
	 * @return
	 */
	public List<ApprLine> listApprLineDef(Map<String, String> map);
	
	/**
	 * 문서의 결재선 존재유무
	 * @param map
	 * @return
	 */
	public boolean exists(Map<String, String> map);
	
	/**
	 * 결재/합의자의 결재선 정보 조회
	 * @param object
	 * @return
	 */
	public ApprLine readLine(ApprLine object);	

	/**
	 * 현재진행중인 결재선 Order
	 * @param apprId
	 * @return
	 */
	public Integer ApprOrderInProgress(String apprId);
	
	/**
	 * 다음 결재가 부서인 목록 조회 (합의부서)
	 * @param map
	 * @return
	 */
	public List<ApprLine> getNextApprLine(Map<String, String> map);
	
	/**
	 * 수신처 목록 조회 ( 수신부서,수신자 )
	 * @param apprId
	 * @return
	 */
	public List<ApprLine> getReceiveApprLine(String	apprId);
	
	
	
	/*****************************************
	 * 결재선 정보 갱신처리
	 ****************************************/	
	/**
	 * 미진행중인 결재선 삭제
	 * @param apprId
	 */
	public void removeNotProgress(String apprId);
	
	/**
	 * 수신처 정보 삭제
	 * @param apprId
	 */
	public void removeApprReceiveLine(String apprId);


	/**
	 * 결재/합의 자의 결재문서 Read 시간 수정
	 * @param object
	 */
	public void updateRead(ApprLine object);
	
	/**
	 * 결재선 변경시 수신처 apprOrder 값 갱신
	 * @param map
	 */
	public void updateReceiveOrder(Map<String, String> map);

	
	/*****************************************
	 * 결재선 버전 정보
	 ****************************************/	

	/**
	 * 결재선 버전 관리 등록 ( 수신처는 제외 )
	 * @param map
	 */
	public void createLineVersion(Map<String, String> map);
 
	/**
	 * 결재선버전 목록
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
	
	/**
	 * 문서버전정보 삭제
	 * @param apprId
	 */
	public void removeVersion(String apprId);
	

	/**
	 * 알람 대상자 조회
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> targetListAlarm(Map<String, String> map);
	/**
	 * 반려 알람 대상자 조회
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> targetListAlarmReject(Map<String, String> map);
		
}
