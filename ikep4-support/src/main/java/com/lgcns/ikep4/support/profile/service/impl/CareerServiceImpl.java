/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.profile.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.profile.dao.CareerDao;
import com.lgcns.ikep4.support.profile.model.Career;
import com.lgcns.ikep4.support.profile.service.CareerService;


/**
 * 경력 관리 Service Implement class
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: CareerServiceImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Service("careerService")
public class CareerServiceImpl extends GenericServiceImpl<Career, String> implements CareerService {

	/**
	 * 경력 정보 조회 용 DAO 생성
	 */
	@Autowired
	private CareerDao careeroDao;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.CareerService#list(com.lgcns.ikep4.lightpack.profile.model.Career)
	 */
	public List<Career> list(Career career) {
		return careeroDao.selectAll(career);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#get(java.io.Serializable)
	 */
	public Career read(String careerId) {
		return careeroDao.get(careerId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	public boolean exists(String careerId) {
		return careeroDao.exists(careerId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#create(com.lgcns.ikep4.lightpack.profile.model.Career)
	 */
	public String create(Career career) {
		return careeroDao.create(career);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#update(com.lgcns.ikep4.lightpack.profile.model.Career)
	 */
	public void update(Career career) {
		careeroDao.update(career);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#remove(java.io.Serializable)
	 */
	public void delete(String careerId) {
		careeroDao.remove(careerId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.CareerService#listRecent5(com.lgcns.ikep4.lightpack.profile.model.Career)
	 */
	public List<Career> listRecent5(Career career) {
		return careeroDao.selectRecent5(career);
	}

}
