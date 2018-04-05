/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.collpack.collaboration.admin.dao.WorkspaceTypeDao;
import com.lgcns.ikep4.collpack.collaboration.admin.model.WorkspaceType;
import com.lgcns.ikep4.collpack.collaboration.admin.service.WorkspaceTypeService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * Workspace Type 구현 Service
 * 
 * @author 김종철
 * @version $Id: WorkspaceTypeServiceImpl.java 12592 2011-05-23 04:20:55Z
 *          happyi1018 $
 */
@Service("workspaceTypeService")
public class WorkspaceTypeServiceImpl extends
		GenericServiceImpl<WorkspaceType, String> implements
		WorkspaceTypeService {

	@Autowired
	private WorkspaceTypeDao workspaceTypeDao;

	@Autowired
	private IdgenService idgenService;

	/**
	 * 워크스페이스 타입 목록 + 조직
	 */
	public List<WorkspaceType> listWorkspaceTypeAll(String portalId,
			String isCodeView) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		List<WorkspaceType> list = workspaceTypeDao
				.listWorkspaceTypeAll(portalId);
		if (list != null) {
			if (!sessionuser.getLocaleCode().equals("ko")
					&& isCodeView.equals("N")) {
				for (WorkspaceType workspaceType : list) {
					workspaceType.setTypeName(workspaceType
							.getTypeEnglishName());
					workspaceType.setTypeDescription(workspaceType
							.getTypeEnglishDescription());
				}
			}
		}

		return list;
	}

	public List<WorkspaceType> listWorkspaceTypeAll(String portalId) {
		return listWorkspaceTypeAll(portalId, "N");
	}

	/**
	 * 워크스페이스 타입 목록
	 */
	public List<WorkspaceType> listWorkspaceType(String portalId) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		List<WorkspaceType> list = workspaceTypeDao.listWorkspaceType(portalId);
		if (list != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (WorkspaceType workspaceType : list) {
					workspaceType.setTypeName(workspaceType
							.getTypeEnglishName());
					workspaceType.setTypeDescription(workspaceType
							.getTypeEnglishDescription());
				}
			}
		}

		return list;
	}

	/**
	 * 유형(타입)별 워크스페이스 수
	 */
	public List<WorkspaceType> countWorkspaceByType(String portalId) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		List<WorkspaceType> list = workspaceTypeDao
				.countWorkspaceByType(portalId);
		if (list != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (WorkspaceType workspaceType : list) {
					workspaceType.setTypeName(workspaceType
							.getTypeEnglishName());
					workspaceType.setTypeDescription(workspaceType
							.getTypeEnglishDescription());
				}
			}
		}

		return list;

	}

	/**
	 * 워크스페이스 타입 등록
	 */
	public String create(WorkspaceType object) {
		object.setTypeId(idgenService.getNextId());
		return workspaceTypeDao.create(object);

	}

	/**
	 * 워크스페이스 타입 조회
	 */
	public WorkspaceType read(WorkspaceType object) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		WorkspaceType workspaceType = workspaceTypeDao.get(object);
		if (workspaceType != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				workspaceType.setTypeName(workspaceType.getTypeEnglishName());
				workspaceType.setTypeDescription(workspaceType
						.getTypeEnglishDescription());
			}
		}
		return workspaceType;
	}

	/**
	 * 워크스페이스 타입 명
	 */
	public String getTypeName(WorkspaceType object) {
		return workspaceTypeDao.getTypeName(object);
	}

	/**
	 * 타입 수정
	 */
	public void update(WorkspaceType object) {
		workspaceTypeDao.update(object);

	}

	/**
	 * 타입 순서변경
	 */
	public void updateWorkspaceTypeOrder(String typeIds, String portalId) {

		String[] typeIdList = typeIds.split(",");

		for (int i = 0, count = typeIdList.length; i < count; i++) {
			String typeId = typeIdList[i];
			WorkspaceType type = new WorkspaceType();
			type.setPortalId(portalId);
			type.setTypeId(typeId);
			type.setSortOrder(i);

			workspaceTypeDao.updateWorkspaceTypeOrder(type);
		}

	}

	/**
	 * 워크스페이스 타입 임시삭제
	 */
	public void logicalDelete(WorkspaceType object) {
		workspaceTypeDao.logicalDelete(object);

	}

	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	private Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(
				name, RequestAttributes.SCOPE_SESSION);
	}

}
