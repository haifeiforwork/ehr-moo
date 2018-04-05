/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.admin.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.collaboration.admin.model.WorkspaceType;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Workspace Type Service
 * 
 * @author 김종철
 * @version $Id: WorkspaceTypeService.java 16543 2011-09-16 07:19:03Z giljae $
 */
@Transactional
public interface WorkspaceTypeService extends
		GenericService<WorkspaceType, String> {

	/**
	 * 워크스페이스 타입 목록 + 조직
	 * 
	 * @param portalId
	 * @return
	 */
	public List<WorkspaceType> listWorkspaceTypeAll(String portalId);

	public List<WorkspaceType> listWorkspaceTypeAll(String portalId,
			String isCodeView);

	/**
	 * 워크스페이스 타입별 갯수
	 * 
	 * @param portalId
	 * @return
	 */
	public List<WorkspaceType> countWorkspaceByType(String portalId);

	/**
	 * 워크스페이스 타입 목록
	 * 
	 * @param portalId
	 * @return
	 */
	public List<WorkspaceType> listWorkspaceType(String portalId);

	/**
	 * 워크스페이스 타입 내용조회
	 * 
	 * @param object
	 * @return
	 */
	public WorkspaceType read(WorkspaceType object);

	/**
	 * 워크스페이스 타입명
	 * 
	 * @param object
	 * @return
	 */
	public String getTypeName(WorkspaceType object);

	/**
	 * 타입 순서변경
	 * 
	 * @param typeIdes
	 * @param portalId
	 */
	public void updateWorkspaceTypeOrder(String typeIdes, String portalId);

	/**
	 * 워크스페이스 타입 영구 삭제
	 * 
	 * @param categoryId
	 */
	public void logicalDelete(WorkspaceType object);

}
