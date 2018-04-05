/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.admin.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.collaboration.admin.model.WorkspaceType;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * 카테고리 DAO
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: WorkspaceTypeDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface WorkspaceTypeDao extends GenericDao<WorkspaceType, String> {

	/**
	 * 워크스페이스 타입 목록 + 조직 포함
	 * @param portalId
	 * @return
	 */
	public List<WorkspaceType> listWorkspaceTypeAll(String portalId);
	/**
	 * 워크스페이스 타입 목록
	 * @param portalId
	 * @return
	 */
	public List<WorkspaceType> listWorkspaceType(String portalId);
	
	/**
	 * TODO 타입별 WS 갯수
	 * @param portalId
	 * @return
	 */
	public List<WorkspaceType> countWorkspaceByType(String portalId);
	
	/**
	 * 워크스페이스 타입 내용 조회
	 * @param object
	 * @return
	 */
	public WorkspaceType get(WorkspaceType object);
	/**
	 * 워크스페이스 타입명
	 * @param object
	 * @return
	 */
	public String getTypeName(WorkspaceType object);
	/**
	 * 워크스페이스 타입 존재유무
	 * @param object
	 * @return
	 */
	public boolean exists(WorkspaceType object);
	
	/**
	 * TODO Type 순서 변경
	 * @param object
	 */
	public void updateWorkspaceTypeOrder(WorkspaceType object);
	
	/**
	 * 워크스페이스 타입 영구 삭제
	 * 
	 * @param workspaceId
	 */
	public void logicalDelete(WorkspaceType object);


}
