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

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeLayoutDao;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafeLayout;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafeLayoutService;


/**
 * Cafe 레이아웃 정보를 제공하는 Service Implement Class
 * 
 * @author 이형운
 * @version $Id: CafeLayoutServiceImpl.java 14767 2011-06-10 08:21:23Z handul32
 *          $
 */
@Service("cafeLayoutService")
public class CafeLayoutServiceImpl extends GenericServiceImpl<CafeLayout, String> implements CafeLayoutService {

	/**
	 * 블로그 레이아웃 정보 컨트롤용 Dao
	 */
	@Autowired
	public CafeLayoutDao cafeLayoutDao;

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.CafeLayoutService#
	 * cafeLayoutByOwnerId(com.lgcns.ikep4.socialpack.socialblog.model.Cafe)
	 */
	public CafeLayout cafeLayoutByOwnerId(Cafe cafe) {
		return cafeLayoutDao.get(cafe.getLayoutId());
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.CafeLayoutService#
	 * defaultCafeLayout()
	 */
	public CafeLayout defaultCafeLayout() {
		return cafeLayoutDao.getDefaultLayout();
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.CafeLayoutService#
	 * listCafeLayoutAll()
	 */
	public List<CafeLayout> listCafeLayoutAll() {
		return cafeLayoutDao.listCafeLayout();
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.
	 * Serializable)
	 */
	@Deprecated
	public CafeLayout read(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.
	 * Serializable)
	 */
	@Deprecated
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
	@Deprecated
	public String create(CafeLayout object) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang
	 * .Object)
	 */
	@Deprecated
	public void update(CafeLayout object) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.
	 * Serializable)
	 */
	@Deprecated
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

}
