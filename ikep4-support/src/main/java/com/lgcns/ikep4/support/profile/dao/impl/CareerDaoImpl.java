/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.profile.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.profile.dao.CareerDao;
import com.lgcns.ikep4.support.profile.model.Career;


/**
 * 경력 정보 관리용 Dao Implement Class
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: CareerDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository("careerDao")
public class CareerDaoImpl extends GenericDaoSqlmap<Career, String> implements CareerDao {

	/**
	 * CAREER_NAME_SPACE
	 */
	private static final String CAREER_NAME_SPACE = "support.profile.dao.Career.";
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.dao.CareerDao#selectAll()
	 */
	@Cacheable(cacheName = "careerCache")
	public List<Career> selectAll(Career career) {
		return sqlSelectForList(CAREER_NAME_SPACE + "selectAll",career);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Career get(String careerId) {
		return (Career) sqlSelectForObject(CAREER_NAME_SPACE + "select", careerId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String careerId) {
		Integer count = (Integer) sqlSelectForObject(CAREER_NAME_SPACE + "selectCount", careerId);
		return (count > 0);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	@TriggersRemove(cacheName = "careerCache", removeAll = true)
	public String create(Career career) {
		return (String) sqlInsert(CAREER_NAME_SPACE + "insert", career);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@TriggersRemove(cacheName = "careerCache", removeAll = true)
	public void update(Career career) {
		sqlUpdate(CAREER_NAME_SPACE + "update", career);
		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@TriggersRemove(cacheName = "careerCache", removeAll = true)
	public void remove(String careerId) {
		sqlDelete(CAREER_NAME_SPACE + "delete", careerId);
		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.dao.CareerDao#selectRecent5(com.lgcns.ikep4.lightpack.profile.model.Career)
	 */
	@Cacheable(cacheName = "careerCache")
	public List<Career> selectRecent5(Career career) {
		return sqlSelectForList(CAREER_NAME_SPACE + "selectRecent5",career);
	}

}
