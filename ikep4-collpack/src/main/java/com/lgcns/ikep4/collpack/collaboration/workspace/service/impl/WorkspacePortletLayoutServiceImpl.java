/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.collaboration.workspace.dao.WorkspacePortletLayoutDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortlet;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortletLayout;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspacePortletLayoutService;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspacePortletService;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * Workspace 에서 선택된 Portlet 값을 제공 하는 Service
 *
 * @author 김종철
 * @version $Id: WorkspacePortletLayoutServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("workspacePortletLayoutService")
public class WorkspacePortletLayoutServiceImpl extends GenericDaoSqlmap<WorkspacePortletLayout, String> implements WorkspacePortletLayoutService {

	@Autowired
	public WorkspacePortletLayoutDao workspacePortletLayoutDao;
	
	@Autowired
	public WorkspacePortletService workspacePortletService;
	
	@Autowired
	public WorkspaceService workspaceService;
	
	/** The idgen service. */
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 포틀릿 레이아웃 조회
	 */
	public WorkspacePortletLayout read(String portletLayoutId) {
		WorkspacePortletLayout workspacePortletLayout= workspacePortletLayoutDao.get(portletLayoutId);
		workspacePortletLayout.setWorkspacePortlet(workspacePortletService.read(workspacePortletLayout.getPortletId()));
		return workspacePortletLayout;
	}

	/**
	 * 포틀릿 존재유무
	 */
	public boolean exists(String portletLayoutId) {
		return workspacePortletLayoutDao.exists(portletLayoutId);
	}

	/**
	 * 포틀릿 생성
	 */
	public String create(WorkspacePortletLayout object) {
		object.setPortletLayoutId(idgenService.getNextId());
		return workspacePortletLayoutDao.create(object);
	}

	/**
	 * 포틀릿 수정
	 */
	public void update(WorkspacePortletLayout object) {
		workspacePortletLayoutDao.update(object);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.Serializable)
	 */
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 포틀릿 레이아웃 목록
	 */
	public List<WorkspacePortletLayout> listLayoutByWorkspace(String workspaceId) {
		
		List<WorkspacePortletLayout> workspacePortletLayoutList = workspacePortletLayoutDao.listLayoutByWorkspace(workspaceId);
		
		for( WorkspacePortletLayout workspacePortletLayout : workspacePortletLayoutList){
			workspacePortletLayout.setWorkspacePortlet(workspacePortletService.read(workspacePortletLayout.getPortletId()));
		}

		return workspacePortletLayoutList;
	}
	/**
	 * 포틀릿 디폴트 레이아웃  저장
	 */
	public void saveDefaultWorkspacePortletLayout(String workspaceId) {
		List<WorkspacePortlet> workspacePortletList = workspacePortletService.listDefaultWorkspacePortlet();
		int i = 1;
		for(WorkspacePortlet workspacePortlet : workspacePortletList) {
			
			WorkspacePortletLayout workspacePortletLayout = new WorkspacePortletLayout();
			workspacePortletLayout.setPortletLayoutId(idgenService.getNextId());
			workspacePortletLayout.setWorkspaceId(workspaceId);
			workspacePortletLayout.setPortletId(workspacePortlet.getPortletId());
			workspacePortletLayout.setColIndex(1);
			workspacePortletLayout.setRowIndex(i);
			
			this.create(workspacePortletLayout);
			i++;
		}

	}

	/**
	 * 포틀릿 레이아웃 저장
	 */
	public void saveWorkspacePortletLayout(String workspaceId, List<WorkspacePortletLayout> workspacePortletLayoutList) {
		//기존 데이타 삭제
		workspacePortletLayoutDao.physicalDeleteByWorkspaceId(workspaceId);
		
		//신규 데이타입력
		for(WorkspacePortletLayout workspacePortletLayout : workspacePortletLayoutList) {
			
			workspacePortletLayout.setPortletLayoutId(idgenService.getNextId());
			
			workspacePortletLayout.setWorkspaceId(workspaceId);			
			
			this.create(workspacePortletLayout);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public WorkspacePortletLayout get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 포틀릿 레이아웃 삭제
	 */
	public void physicalDeleteByWorkspaceId(String workspaceId) {

		workspacePortletLayoutDao.physicalDeleteByWorkspaceId(workspaceId);
		
	}

}
