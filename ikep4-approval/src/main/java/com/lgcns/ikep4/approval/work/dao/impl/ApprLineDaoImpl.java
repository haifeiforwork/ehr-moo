/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.approval.work.dao.ApprLineDao;
import com.lgcns.ikep4.approval.work.model.ApprLine;

/**
 * 결재선 Dao 구현
 *
 * @author 
 * @version $Id$
 */
@Repository("apprLineDao")
public class ApprLineDaoImpl extends GenericDaoSqlmap<ApprLine, String> implements ApprLineDao {

	private static final String NAMESPACE = "approval.work.dao.ApprLine.";
	
	/*
	 * 결재선 등록
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ApprLine object) {
		sqlInsert(NAMESPACE + "create", object);
		return object.getApprId();
	}

	/*
	 * 접수자 전결 처리 ( 부서합의/수신처 문서의 접수자 )
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#createRecEnd(com.lgcns.ikep4.approval.work.model.ApprLine)
	 */
	public String createRecEnd(ApprLine object) {
		sqlInsert(NAMESPACE + "createRecEnd", object);
		return object.getApprId();
	}	
	
	/*
	 * 결재선 목록 (조건에 따른 )
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#listApprLine(com.lgcns.ikep4.approval.work.model.ApprLine)
	 */
	public List<ApprLine> listApprLine(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listApprLine",map);
	}	
		
	/*
	 * child 결재선 정보 ( 부서합의/수신처 )
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#childListApprLine(java.util.Map)
	 */
	public List<ApprLine> childListApprLine(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "childListApprLine",map);
	}
	
	/*
	 * Doc 문서의 FormId로 Default 결재선의 결재선 정보 조회 (defLineId, apprType : 3 수신만, apprType != 3 결재/합의만)
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#listApprLineDef(java.util.Map)
	 */
	public List<ApprLine> listApprLineDef(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listApprLineDef",map);
	}	
	
	/*
	 * 문서의 결재선 존재유무
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(Map<String, String> map) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", map);
		return count > 0;
	}	
	
	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}	
	
	/*
	 * 결재취소 가능여부 확인 (true: 다음 결재 및 Sub 문서 진행이 있으면)
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#existsProgressNextApprLine(java.util.Map)
	 */
	public boolean existsNextApprProgress(Map<String, String> map) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "existsNextApprProgress", map);
		return count > 0;
	}	
	
	/*
	 * 다음 결재자 존재 유무
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#getNext(java.lang.String)
	 */
	public boolean getNext(Map<String, String> map) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "getNext", map);
		return count > 0;
	}
	
	public ApprLine get(String apprId) {
		return null;
	}
	
	/*
	 * 다음 결재가 부서인 목록 조회 (합의부서)
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#getNextApprLine(java.lang.String)
	 */
	public List<ApprLine> getNextApprLine(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "getNextApprLine",map);
	}
	
	/*
	 * 수신처 목록 조회 ( 수신부서,수신자 )
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#getReceiveApprLine(java.lang.String)
	 */
	public List<ApprLine> getReceiveApprLine(String	apprId) {
		return this.sqlSelectForList(NAMESPACE + "getReceiveApprLine",apprId);
	}	

	
	/*
	 * 결재회수를 위한 최초 결재자 결재 상태 체크 ( true : 결재자 결재 )
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#beforeAppr(java.lang.String)
	 */
	public boolean beforeAppr(String apprId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "beforeAppr", apprId);
		return count > 0;
	}	
	
	/*
	 * 결재가 진행중인지 확인 ( APPR_STATUS >1 )
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#countProgress(java.lang.String)
	 */
	public boolean countProgress(String apprId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "countProgress", apprId);
		return count > 0;
	}	
	
	/*
	 * 결재/합의자의 결재선 정보 조회
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#readLine(com.lgcns.ikep4.approval.work.model.ApprLine)
	 */
	public ApprLine readLine(ApprLine apprLine) {
		return (ApprLine) sqlSelectForObject(NAMESPACE + "readLine", apprLine);
	}
	
	/*
	 * 현재진행중인 결재선 Order
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#ApprOrderInProgress(java.lang.String)
	 */
	public Integer ApprOrderInProgress(String apprId) {
		Integer apprOrder = (Integer)sqlSelectForObject(NAMESPACE + "ApprOrderInProgress", apprId);
		if(apprOrder==null)
			return 0;
		return apprOrder;
	}
	/* 
	 * 문서의 결재선 삭제
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String apprId) {
		sqlDelete(NAMESPACE + "delete", apprId);
	}
	
	/*
	 * 문서버전정보 삭제
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#removeVersion(java.lang.String)
	 */
	public void removeVersion(String apprId) {
		sqlDelete(NAMESPACE + "removeVersion", apprId);
	}
	
	/*
	 * 미진행중인 결재선 삭제
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#removeNotProgress(java.lang.String)
	 */
	public void removeNotProgress(String apprId) {
		sqlDelete(NAMESPACE + "removeNotProgress", apprId);
	}
	
	/* 
	 * 수신처 정보 삭제
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#removeApprReceiveLine(java.lang.String)
	 */
	public void removeApprReceiveLine(String apprId) {
		sqlDelete(NAMESPACE + "removeApprReceiveLine", apprId);		
	}

	/*
	 * 결재회수 처리 --> 결재선정보의 진행상태 APPR_STATUS 0으로 초기화
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#updateRecoveryAppr(java.lang.String)
	 */
	public void updateRecoveryAppr(String apprId) {
		sqlUpdate(NAMESPACE + "updateRecoveryAppr", apprId);
	}
		
	/* 
	 * 결재선 정보의 결재상태 수정 ( 다음결재자 결재진행 )
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ApprLine object) {
		sqlUpdate(NAMESPACE + "update", object);
	}
	
	/*
	 * 결재/합의 자의 결재문서 Read 시간 수정
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#updateRead(com.lgcns.ikep4.approval.work.model.ApprLine)
	 */
	public void updateRead(ApprLine object) {
		sqlUpdate(NAMESPACE + "updateRead", object);
	}

	/*
	 * 결재선 정보 결재처리 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#updateApproval(com.lgcns.ikep4.approval.work.model.ApprLine)
	 */
	public void updateApproval(ApprLine object) {
		sqlUpdate(NAMESPACE + "updateApproval", object);
	}

	/*
	 * 결재선 변경시 수신처 apprOrder 값 갱신
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#updateReceiveOrder(java.util.Map)
	 */
	public void updateReceiveOrder(Map<String, String> map) {
		sqlUpdate(NAMESPACE + "updateReceiveOrder", map);
	}
	/*
	 * 결재선 정보중 마지막 결재자 정보 표시
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#lastApprLine(java.lang.String)
	 */
	public List<ApprLine> lastApprLine(String	apprId) {
		return this.sqlSelectForList(NAMESPACE + "lastApprLine",apprId);
	}
 
	/*
	 * 결재선 갯수
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#countApprLine(com.lgcns.ikep4.approval.work.model.ApprLine)
	 */
	public int countApprLine(ApprLine object) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countApprLine", object);
	}	

	/*
	 * 결재자의 결재취소 처리 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#updateApprovalLineCancel(java.util.Map)
	 */
	public void updateApprovalLineCancel(Map<String, String> map) {
		sqlUpdate(NAMESPACE + "updateApprovalLineCancel", map);
	}	
	
	/*
	 * 다음 결재자의 대기상태 처리
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#updateApprovalLineCancel(java.util.Map)
	 */	
	public void updateInitNextApprLine(Map<String, String> map) {
		sqlUpdate(NAMESPACE + "updateInitNextApprLine", map);
	}	
	
	/*
	 * 동일 apprOrder 반려처리
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#updateRejectApprLine(com.lgcns.ikep4.approval.work.model.ApprLine)
	 */
	public void updateRejectApprLine(ApprLine object) {
		sqlUpdate(NAMESPACE + "updateRejectApprLine", object);
	}		
	
	/*
	 * 결재선 버전 관리 등록 ( 수신처는 제외 )
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#insertLineVersion(java.util.Map)
	 */
	public void createLineVersion(Map<String, String> map) {
		sqlUpdate(NAMESPACE + "createLineVersion",map);
	}

	/*
	 * 결재선버전 목록
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#listVersion(java.lang.String)
	 */
	public List<ApprLine> listVersion(String	apprId) {
		return this.sqlSelectForList(NAMESPACE + "listVersion",apprId);
	}
	
	/*
	 * 결재선 버전별 중복제거 목록
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#groupByVersion(java.lang.String)
	 */
	public List<ApprLine> groupByVersion(String	apprId) {
		return this.sqlSelectForList(NAMESPACE + "groupByVersion",apprId);
	}

	/*
	 * 알람대상자조회(non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#targetListAlarm(java.util.Map)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, String>> targetListAlarm(Map<String, String> map) {

		return (List) sqlSelectForList(NAMESPACE + "targetListAlarm", map);
	}
	/*
	 * 반려 알람대상자조회(non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprLineDao#targetListAlarm(java.util.Map)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, String>> targetListAlarmReject(Map<String, String> map) {

		return (List) sqlSelectForList(NAMESPACE + "targetListAlarmReject", map);
	}	
	
}
