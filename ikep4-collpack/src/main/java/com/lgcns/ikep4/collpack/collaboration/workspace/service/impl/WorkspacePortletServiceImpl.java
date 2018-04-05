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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.collpack.collaboration.workspace.dao.WorkspacePortletDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortlet;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspacePortletService;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Workspace에 등록된 포틀릿 정보를 제공하는 Service
 * 
 * @author 김종철
 * @version $Id: WorkspacePortletServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("workspacePortletService")
public class WorkspacePortletServiceImpl extends GenericDaoSqlmap<WorkspacePortlet, String> implements
		WorkspacePortletService {

	@Autowired
	public WorkspacePortletDao workspacePortletDao;

	/**
	 * 포틀릿 조회
	 */
	public WorkspacePortlet read(String portletId) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		WorkspacePortlet portlet = workspacePortletDao.get(portletId);
		if (portlet != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				portlet.setPortletName(portlet.getPortletEnglishName());
			}
		}

		return portlet;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.
	 * Serializable)
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang
	 * .Object)
	 */
	public String create(WorkspacePortlet object) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang
	 * .Object)
	 */
	public void update(WorkspacePortlet object) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.
	 * Serializable)
	 */
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 포틀릿 목록
	 */
	public List<WorkspacePortlet> listAllWorkspace() {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		WorkspacePortlet workspacePortlet = new WorkspacePortlet();
		workspacePortlet.setIsDefault(-1);
		List<WorkspacePortlet> list = workspacePortletDao.listAllPortlet(workspacePortlet);

		if (list != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (WorkspacePortlet portlet : list) {
					portlet.setPortletName(portlet.getPortletEnglishName());
				}
			}
		}

		return list;
	}

	/**
	 * 포틀릿 목록 - 전체 (게시판형 포함)
	 */
	public List<WorkspacePortlet> listAllWorkspacePortlet(String workspaceId) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		List<WorkspacePortlet> list = workspacePortletDao.listAllWorkspacePortlet(workspaceId);

		if (list != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (WorkspacePortlet portlet : list) {
					portlet.setPortletName(portlet.getPortletEnglishName());
				}
			}
		}

		return list;
	}

	/**
	 * 포틀릿 기본 디폴트 목록
	 */
	public List<WorkspacePortlet> listDefaultWorkspacePortlet() {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		WorkspacePortlet workspacePortlet = new WorkspacePortlet();
		workspacePortlet.setIsDefault(1);
		List<WorkspacePortlet> list = workspacePortletDao.listAllPortlet(workspacePortlet);

		if (list != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (WorkspacePortlet portlet : list) {
					portlet.setPortletName(portlet.getPortletEnglishName());
				}
			}
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public WorkspacePortlet get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	private Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

}
