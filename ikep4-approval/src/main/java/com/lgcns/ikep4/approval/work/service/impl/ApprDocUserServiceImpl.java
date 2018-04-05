/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.work.dao.ApprUserDocDao;
import com.lgcns.ikep4.approval.work.model.ApprUserDocFolder;
import com.lgcns.ikep4.approval.work.service.ApprUserDocService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 개인보관함 Service 구현
 *
 * @author jeehye
 * @version $Id: ApprDocUserServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("apprUserDocService")
public class ApprDocUserServiceImpl extends GenericServiceImpl<ApprUserDocFolder, String> implements ApprUserDocService {

	@Autowired
	private ApprUserDocDao apprUserDocDao;
	
	@Autowired
	private IdgenService idgenService;

	/**
	 * 하위 시스템 개수
	 * @param param Map(systemCode:부모 시스템 코드, portalId:포탈 아이디)
	 * @return int 하위 시스템 개수
	 */
	public int getChildCount(Map<String, String> param) {

		int count = apprUserDocDao.getChildCount(param);

		return count;

	}
	
	/**
	 * 폴더별 관리 트리 목록
	 * @param param Map(portalId:포탈 아이디, 사용자 아이디)
	 * @return List<ApprUserDocFolder> 포탈 시스템 별 관리 트리 목록
	 */
	public List<ApprUserDocFolder> treeList(Map<String, String> param) {

		List<ApprUserDocFolder> list = apprUserDocDao.treeList(param);

		return list;

	}

	/**
	 * 폴더별 관리 트리 목록
	 * @param param Map(portalId:포탈 아이디, 사용자 아이디)
	 * @return List<ApprUserDocFolder> 포탈 시스템 별 관리 트리 목록
	 */
	public List<ApprUserDocFolder> treeChildrenList(Map<String, String> param) {

		List<ApprUserDocFolder> list = apprUserDocDao.treeChildrenList(param);

		return list;

	}
	
	/**
	 * 폴더  생성
	 * @param apprUserDocFolder 모델
	 */
	public void createFolder(ApprUserDocFolder apprUserDocFolder) { 
		
		try {
			apprUserDocFolder.setFolderId(idgenService.getNextId());
			
			this.apprUserDocDao.createFolder(apprUserDocFolder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 폴더  수정
	 * @param apprUserDocFolder 모델
	 */
	public void updateFolder(ApprUserDocFolder apprUserDocFolder) { 
		
		try {
			this.apprUserDocDao.updateFolder(apprUserDocFolder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 폴더정보 
	 * @param folderId
	 * @return ApprUserDocFolder
	 */
	public ApprUserDocFolder getFolderInfo(String folderId) {

		ApprUserDocFolder apprUserDocFolder = apprUserDocDao.getFolderInfo(folderId);
		return apprUserDocFolder;

	}
	
	/**
	 * 폴더에 문서  저장
	 * @param apprUserDocFolder 모델
	 */
	public void createApprUserDoc(ApprUserDocFolder apprUserDocFolder) { 
		
		try {
			this.apprUserDocDao.createApprUserDoc(apprUserDocFolder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 폴더에 문서 삭제
	 * @param 	param
	 * @return 	void
	 */
	public void deleteApprUserDoc(Map<String, String> param) {
		
		apprUserDocDao.deleteApprUserDoc(param);
		
	}
	
	/**
	 * 폴더에 삭제
	 * @param 	apprId
	 * @return 	void
	 */
	public void deleteApprDocFolder(String folderId) {
		//폴더에 삭제
		apprUserDocDao.deleteApprDocFolder(folderId);
	}
	
	/**
	 * 게시판 이동
	 */
	public void updateBoardMove(ApprUserDocFolder after) {

		ApprUserDocFolder before = apprUserDocDao.getFolderInfo(after.getFolderId());

		// 이동전의 위치에서는 자기 기준으로 정렬숫자가 높은 놈들은 -1 해준다.
		apprUserDocDao.updateSortOderDecrease(before); 

		// 이동후의 위치에서는 자기 기준으로 정렬숙자가 높은 놈들은 +1 해준다.
		apprUserDocDao.updateSortOderIncrease(after);
		// 1Level Board 생성시 Foreign key 제약조건 오류 방지
		if ("".equals(after.getFolderParentId())) {
			after.setFolderParentId(null);
		}
		apprUserDocDao.updateMove(after);
		
	}
	
	/**
	 * 개인보관함에 존재하는 체크
	 * @param 	Map<String, String> 
	 * @return 	boolean
	 */
	public boolean checkExist(Map<String, String> param) {
		//폴더에 삭제
		return apprUserDocDao.checkExist(param);
	}
	
	/**
	 * 폴더명 존재하는 체크
	 * @param 	Map<String, String> 
	 * @return 	boolean
	 */
	public boolean existFolderName(Map<String, String> param) {
		//폴더에 삭제
		return apprUserDocDao.existFolderName(param);
	}
	
}
