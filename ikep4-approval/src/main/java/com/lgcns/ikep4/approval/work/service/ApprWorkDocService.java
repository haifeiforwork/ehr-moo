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

import com.lgcns.ikep4.approval.work.model.ApprDocAuth;
import com.lgcns.ikep4.approval.work.model.ApprLine;
import com.lgcns.ikep4.approval.admin.model.ApprDoc;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * WorkPlace List 서비스
 *
 * @author wonchu
 * @version $Id: ApprWorkDocListService.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Transactional
public interface ApprWorkDocService extends GenericService<ApprDoc, String> {
	
	/**
	 * 기안문 생성
	 * @param 	ApprDoc
	 * @return 	void
	 */
	public void createApprDoc(ApprDoc apprDoc, User user, HttpServletRequest request);
	
	/**
	 * 기안문 복사
	 * @param 	
	 * @return 	String
	 */
	public String duplicateApprDoc(String apprId,int approverType,String approverId,String approverGroupName,int apprType);
	
	/**
	 * 기안문 수정
	 * @param 	apprDoc, user
	 * @return 	void
	 */
	public void updateApprDoc(ApprDoc apprDoc, User user, HttpServletRequest request);
	
	/**
	 * 기안문 상세 수정
	 * @param 	apprDoc, user
	 * @return 	void
	 */
	public void updateApprDocContent(ApprDoc apprDoc, User user);
	
	/**
	 * 기안문 삭제
	 * @param 	apprId
	 * @return 	void
	 */
	public void deleteApprDoc(String apprId);
	
	/**
	 * apprDoc 기안서상세
	 * @param 	Map
	 * @return 	ApprDoc
	 */
	public ApprDoc readApprDoc(Map<String, String> map);
	
	/**
	 * apprDoc Sub 기안서상세
	 * @param map
	 * @return
	 */
	public ApprDoc readApprDocSub(Map<String, String> map);
	
	/**
	 * apprDoc 열람권한 그룹 조회
	 * @param 	apprId
	 * @return 	List<ApprDoc>
	 */
	public List<ApprDoc> getApprDocHistoryList(String apprId);
	
	/**
	 * apprDoc 열람권한 그룹 조회
	 * @param 	Map
	 * @return 	List<Group
	 */
	public List<Group> getApprReadGroupList(Map<String, String> map);
	
	/**
	 * apprDoc 열람권한 사용자 조회
	 * @param 	Map
	 * @return 	List<User>
	 */
	public List<User> getApprReadUserList(Map<String, String> map);
	

	/**
	 * apprDoc 참조자 조회
	 * @param 	Map
	 * @return 	List<User>
	 */
	public List<User> getApprReferenceList(Map<String, String> map);
	
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
	 * 부서 수신 문서 접수자 처리(전결,내부결재진행)
	 * @param apprDoc
	 * @return 	void
	 */
	public void updateReceiverApprDocStatus(ApprDoc apprDoc);
	
	/**
	 * 참조자 변경
	 * @param 	referenceId
	 * @return 	void
	 */
	public void tranApprReference(String mode, String apprId, String referenceId);
	
	/**
	 * 열람권한 변경
	 * @param 	registerId
	 * @return 	void
	 */
	public void tranApprRead(String mode, String apprId, String readId, String registerId, String registerName);

	public void updateChildApprDoc(ApprDoc apprDoc, User user, HttpServletRequest request);
	
	public List<ApprDoc> subList(Map<String, String> map);

	/**
	 * 기안문 원본 + Sub 문서 목록
	 * @param map
	 * @return
	 */	
	public List<ApprDoc> listApprDoc(Map<String, String> map);
	
	/**
	 * 겹직시 해당 그룹아이디를 가지고 있는지 여부
	 * @param String, String
	 * @return boolean
	 */
	public boolean hasGroupId(String userId, String groupId);
	
	/**
	 * apprDoc  문서권한
	 * @param 	ApprDoc
	 * @return 	ApprDocAuth
	 */
	public ApprDocAuth setApprDocAuth(ApprDoc apprDoc, ApprLine apprLine, User user, boolean isSystemAdmin, boolean isReadAll);
	
	/**
	 * apprDoc  문서조회 가능여부
	 * @param 	ApprDoc
	 * @return 	ApprDocAuth
	 */
	public ApprDocAuth setApprDocAuth(String apprId, String listType, User user, String entrustUserId, boolean isSystemAdmin, boolean isReadAll);
}