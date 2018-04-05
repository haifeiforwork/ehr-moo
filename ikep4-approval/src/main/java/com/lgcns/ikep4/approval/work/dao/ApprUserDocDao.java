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

import com.lgcns.ikep4.approval.work.model.ApprNotice;
import com.lgcns.ikep4.approval.work.model.ApprUserDocFolder;
import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystem;


/**
 * 개인보관함
 * 
 * @author 서지혜
 * @version $Id$
 */

public interface ApprUserDocDao extends GenericDao<ApprUserDocFolder, String> {

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
	 * 폴더저장
	 * @param apprUserDocFolder
	 */
	void createFolder(ApprUserDocFolder apprUserDocFolder);
	
	/**
	 * 폴더수정
	 * @param apprUserDocFolder
	 */
	void updateFolder(ApprUserDocFolder apprUserDocFolder);
	
	/**
	 * 폴더정보 
	 * @param folderId
	 * @return ApprUserDocFolder
	 */
	public ApprUserDocFolder getFolderInfo(String folderId);
	
	/**
	 * 폴더에 문서  저장
	 * @param apprUserDocFolder
	 */
	void createApprUserDoc(ApprUserDocFolder apprUserDocFolder);
	
	/**
	 * 폴더에 문서 삭제
	 * @param apprUserDocFolder
	 */
	void deleteApprUserDoc(Map<String, String> param);
	
	/**
	 * 폴더에 삭제
	 * @param apprUserDocFolder
	 */
	void deleteApprDocFolder(String folderId);
	
	/**
	 * 폴더의 위치을 변경한다.
	 *
	 * @param apprUserDocFolder 이동 대상 폴더 모델 객체
	 */
	public void updateMove(ApprUserDocFolder apprUserDocFolder);

	/**
	 * 파라미터로 들어온 폴더보다 정렬값이 큰 폴더을 대상으로 현재 정렬값을 1씩 증가시킨다.
	 *
	 * @param apprUserDocFolder 정렬 대상 폴더 모델 객체
	 */
	public void updateSortOderIncrease(ApprUserDocFolder apprUserDocFolder);
 
	/**
	 * 파라미터로 들어온폴더보다 정렬값이 큰 폴더을 대상으로 현재 정렬값을 1씩 감소시킨다.
	 *
	 * @param apprUserDocFolder 정렬 대상 폴더 모델 객체
	 */
	public void updateSortOderDecrease(ApprUserDocFolder apprUserDocFolder);
	
	/**
	 * 개인보관함에 존재하는 체크
	 * @param Map<String, String>
	 */
	public boolean checkExist(Map<String, String> param);
	
	/**
	 *  폴더명 존재하는 체크
	 * @param Map<String, String>
	 */
	public boolean existFolderName(Map<String, String> param);

}
