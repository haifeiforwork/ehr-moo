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
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.approval.admin.model.ApprDoc;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;



/**
 * TODO Javadoc주석작성
 *
 * @author wonchu
 * @version $Id: FormListDao.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface ApprWorkDocDao extends GenericDao<ApprDoc, String> {
	
	/**
	 * 기안문복사 
	 * @param 	Map
	 * @return 	void
	 */
	public void duplicateApprDoc(Map<String, String> map);
	
	/**
	 * 기안문 상세 복사
	 * @param 	Map
	 * @return 	void
	 */
	public void duplicateApprDocContent(Map<String, String> map);
	
	/**
	 * 기안문상세 생성
	 * @param 	ApprDoc
	 * @return 	void
	 */
	public void createApprDocContent(ApprDoc apprDoc);
	
	/**
	 * 기안문 상세 수정
	 * @param 	ApprDoc
	 * @return 	void
	 */
	public void updateApprDocContent(ApprDoc apprDoc);
	
	
	/**
	 * 기안문 상세 삭제
	 * @param 	apprId
	 * @return 	void
	 */
	public void deleteApprDocContent(String apprId);
	
	/**
	 * 참조자 생성
	 * @param 	Map
	 * @return 	void
	 */
	public void createApprReference(Map<String, String> map);
	
	/**
	 * 참조자 삭제
	 * @param 	void
	 * @return 	void
	 */
	public void deleteApprReference(String apprId);
	
	/**
	 * apprForm 열람권한 생성
	 * @param 	Map
	 * @return 	void
	 */
	public void createApprRead(Map<String, String> map);
	
	/**
	 * apprForm 열람권한 삭제
	 * @param 	Map
	 * @return 	void
	 */
	public void deleteApprRead(String apprId);
	
	/**
	 * apprForm 기결재 생성
	 * @param 	Map
	 * @return 	void
	 */
	public void createApprRelations(Map<String, String> map);
	
	/**
	 * apprForm 기결재 삭제
	 * @param 	Map
	 * @return 	void
	 */
	public void deleteApprRelations(String apprId);
	
	/**
	 * apprDoc 기안서상세
	 * @param 	Map
	 * @return  ApprDoc
	 */
	public ApprDoc readApprDoc(Map<String, String> map);
	
	/**
	 * apprDoc Sub 기안서상세
	 * @param map
	 * @return
	 */
	public ApprDoc readApprDocSub(Map<String, String> map);
	/**
	 * 기안문 이력 조회
	 * @param 	apprId
	 * @return  List<ApprDoc>
	 */
	public List<ApprDoc> getApprDocHistoryList(String apprId);
	
	/**
	 * 열람권한 조회
	 * @param 	Map
	 * @return  List<ApprDoc>
	 */
	public List<ApprDoc> getApprReadList(Map<String, String> map);
	
	/**
	 * apprDoc 참조자
	 * @param 	Map
	 * @return  List<ApprDoc>
	 */
	public List<ApprDoc> getApprReferenceList(Map<String, String> map);
	
	/**
	 * apprDoc 기결재 조회
	 * @param 	String
	 * @return 	List<ApprDoc>
	 */
	public List<ApprDoc> getApprRelationsList(String apprId);
	
	/**
	 * 상태값 변경
	 * @param 	ApprDoc
	 * @return 	void
	 */
	public void updateApprDocStatus(ApprDoc apprDoc);
	
	/**
	 *  부서 수신 문서 접수자 처리(전결,내부결재진행)
	 * @param apprDoc
	 * @return 	void
	 */
	public void updateReceiverApprDocStatus(ApprDoc apprDoc);
	
	public List<ApprDoc> subList(Map<String, String> map);
	
	/**
	 * 기안문 원본 + Sub 문서 목록
	 * @param map
	 * @return
	 */	
	public List<ApprDoc> listApprDoc(Map<String, String> map);
	
	
	/**
	 * 참조권한
	 * @param map
	 * @return
	 */	
	public boolean getApprReferenceCount(Map<String, String> map);
	
	/**
	 * 열람권한
	 * @param map
	 * @return
	 */	
	public boolean getApprReadCount(Map<String, String> map);
	
	public int getApprDocMaxNo(String searchDocNo);
}