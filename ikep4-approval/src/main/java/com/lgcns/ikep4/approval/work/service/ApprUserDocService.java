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

import com.lgcns.ikep4.approval.work.model.ApprNotice;
import com.lgcns.ikep4.approval.work.model.ApprUserDocFolder;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * 개인보관함 서비스
 *
 * @author jeehye
 * @version $Id: AApprUserDocService.java 16234 2011-08-18 02:44:36Z jeehye $
 */
public interface ApprUserDocService extends GenericService<ApprUserDocFolder, String> {
	
	/**
	 * 하위 시스템 개수
	 * @param param Map(systemCode:부모 시스템 코드, portalId:포탈 아이디)
	 * @return int 하위 시스템 개수
	 */
	public int getChildCount(Map<String, String> param);
	
	/**
	 * 폴더별 관리 트리 목록
	 * @param param Map(portalId:포탈 아이디, 사용자 아이디)
	 * @return List<ApprUserDocFolder> 포탈 시스템 별 관리 트리 목록
	 */
	public List<ApprUserDocFolder> treeList(Map<String, String> param);
	
	/**
	 * 폴더별 관리 트리 목록
	 * @param param Map(portalId:포탈 아이디, 사용자 아이디)
	 * @return List<ApprUserDocFolder> 포탈 시스템 별 관리 트리 목록
	 */
	public List<ApprUserDocFolder> treeChildrenList(Map<String, String> param);
	
	/**
	 * 폴더 저장
	 * @param ApprUserDocFolder 모델
	 */
	public void createFolder(ApprUserDocFolder apprUserDocFolder);
	
	/**
	 * 폴더 수정
	 * @param ApprUserDocFolder 모델
	 */
	public void updateFolder(ApprUserDocFolder apprUserDocFolder);
	
	/**
	 * 폴더정보 
	 * @param folderId
	 * @return ApprUserDocFolder
	 */
	public ApprUserDocFolder getFolderInfo(String folderId);
	
	/**
	 * 폴더에 문서  저장
	 * @param ApprUserDocFolder 모델
	 */
	public void createApprUserDoc(ApprUserDocFolder apprUserDocFolder);
	
	/**
	 * 폴더에 문서 삭제
	 * @param 	apprId
	 * @return 	void
	 */
	public void deleteApprUserDoc(Map<String, String> param);
	
	/**
	 * 폴더에 삭제
	 * @param 	apprId
	 * @return 	void
	 */
	public void deleteApprDocFolder(String folderId);
	
	/**
	 * 폴더의 위치을 변경한다.
	 *
	 * @param apprUserDocFolder 이동 대상 폴더 모델 객체
	 */
	void updateBoardMove(ApprUserDocFolder apprUserDocFolder);
	
	/**
	 * 개인보관함에 존재하는 체크
	 * @param 	Map<String, String>
	 * @return 	void
	 */
	public boolean checkExist(Map<String, String> param);
	
	/**
	 * 폴더명 존재하는 체크
	 * @param 	Map<String, String>
	 * @return 	void
	 */
	public boolean existFolderName(Map<String, String> param);
	
}
