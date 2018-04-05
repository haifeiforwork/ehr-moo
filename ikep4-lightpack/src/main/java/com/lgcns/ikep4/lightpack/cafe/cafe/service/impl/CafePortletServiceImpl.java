/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafePortletDao;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortlet;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafePortletService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Cafe에 등록된 포틀릿 정보를 제공하는 Service
 * 
 * @author 김종철
 * @version $Id: CafePortletServiceImpl.java 15328 2011-06-21 06:40:19Z handul32
 *          $
 */
@Service("cafePortletService")
public class CafePortletServiceImpl extends GenericDaoSqlmap<CafePortlet, String> implements CafePortletService {

	@Autowired
	public CafePortletDao cafePortletDao;

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.
	 * Serializable)
	 */
	public CafePortlet read(String portletId) {
		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		CafePortlet portlet = cafePortletDao.get(portletId);
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
	public String create(CafePortlet object) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang
	 * .Object)
	 */
	public void update(CafePortlet object) {
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

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.workspace.service.CafePortletService
	 * #listAllCafePortlet()
	 */
	public List<CafePortlet> listAllCafe() {
		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		CafePortlet cafePortlet = new CafePortlet();
		cafePortlet.setIsDefault(-1);
		List<CafePortlet> list = cafePortletDao.listAllPortlet(cafePortlet);

		if (list != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (CafePortlet portlet : list) {
					portlet.setPortletName(portlet.getPortletEnglishName());
				}
			}
		}

		return list;
	}

	public List<CafePortlet> listAllCafePortlet(String workspaceId) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		List<CafePortlet> list = cafePortletDao.listAllCafePortlet(workspaceId);

		if (list != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (CafePortlet portlet : list) {
					portlet.setPortletName(portlet.getPortletEnglishName());
				}
			}
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.workspace.service.CafePortletService
	 * #listDefaultCafePortlet()
	 */
	public List<CafePortlet> listDefaultCafePortlet() {
		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		CafePortlet cafePortlet = new CafePortlet();
		cafePortlet.setIsDefault(1);
		List<CafePortlet> list = cafePortletDao.listAllPortlet(cafePortlet);

		if (list != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (CafePortlet portlet : list) {
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
	public CafePortlet get(String id) {
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
