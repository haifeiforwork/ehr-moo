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

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeDao;
import com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafePortletLayoutDao;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortlet;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortletLayout;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafePortletLayoutService;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafePortletService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * Cafe 에서 선택된 Portlet 값을 제공 하는 Service
 * 
 * @author 김종철
 * @version $Id: CafePortletLayoutServiceImpl.java 14298 2011-06-03 02:29:54Z
 *          jghong $
 */
@Service("cafePortletLayoutService")
public class CafePortletLayoutServiceImpl extends GenericDaoSqlmap<CafePortletLayout, String> implements
		CafePortletLayoutService {

	@Autowired
	public CafePortletLayoutDao cafePortletLayoutDao;

	@Autowired
	public CafePortletService cafePortletService;

	@Autowired
	public CafeDao cafeDao;

	/** The idgen service. */
	@Autowired
	private IdgenService idgenService;

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.
	 * Serializable)
	 */
	public CafePortletLayout read(String portletLayoutId) {
		CafePortletLayout cafePortletLayout = cafePortletLayoutDao.get(portletLayoutId);
		cafePortletLayout.setCafePortlet(cafePortletService.read(cafePortletLayout.getPortletId()));
		return cafePortletLayout;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.
	 * Serializable)
	 */
	public boolean exists(String portletLayoutId) {
		return cafePortletLayoutDao.exists(portletLayoutId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang
	 * .Object)
	 */
	public String create(CafePortletLayout object) {
		object.setPortletLayoutId(idgenService.getNextId());
		return cafePortletLayoutDao.create(object);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang
	 * .Object)
	 */
	public void update(CafePortletLayout object) {
		cafePortletLayoutDao.update(object);

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

	public List<CafePortletLayout> listLayoutByCafe(String cafeId) {

		List<CafePortletLayout> cafePortletLayoutList = cafePortletLayoutDao.listLayoutByCafe(cafeId);

		for (CafePortletLayout cafePortletLayout : cafePortletLayoutList) {
			cafePortletLayout.setCafePortlet(cafePortletService.read(cafePortletLayout.getPortletId()));
		}

		return cafePortletLayoutList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.workspace.service.CafePortletLayoutService
	 * #saveDefaultCafePortletLayout(java.lang.String)
	 */
	public void saveDefaultCafePortletLayout(String cafeId) {
		List<CafePortlet> cafePortletList = cafePortletService.listDefaultCafePortlet();
		int i = 1;
		for (CafePortlet cafePortlet : cafePortletList) {

			CafePortletLayout cafePortletLayout = new CafePortletLayout();
			cafePortletLayout.setPortletLayoutId(idgenService.getNextId());
			cafePortletLayout.setCafeId(cafeId);
			cafePortletLayout.setPortletId(cafePortlet.getPortletId());
			cafePortletLayout.setColIndex(1);
			cafePortletLayout.setRowIndex(i);

			this.create(cafePortletLayout);
			i++;
		}

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.workspace.service.CafePortletLayoutService
	 * #saveCafePortletLayout(java.lang.String, java.util.List)
	 */
	public void saveCafePortletLayout(Cafe cafe, List<CafePortletLayout> cafePortletLayoutList) {

		// 기존 데이타 삭제
		cafePortletLayoutDao.physicalDeleteByCafeId(cafe.getCafeId());

		// 신규 데이타입력
		for (CafePortletLayout cafePortletLayout : cafePortletLayoutList) {

			cafePortletLayout.setPortletLayoutId(idgenService.getNextId());

			cafePortletLayout.setCafeId(cafe.getCafeId());

			this.create(cafePortletLayout);
		}

		cafeDao.updateLayout(cafe);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public CafePortletLayout get(String id) {
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

	public void physicalDeleteByCafeId(String cafeId) {

		cafePortletLayoutDao.physicalDeleteByCafeId(cafeId);

	}

}
