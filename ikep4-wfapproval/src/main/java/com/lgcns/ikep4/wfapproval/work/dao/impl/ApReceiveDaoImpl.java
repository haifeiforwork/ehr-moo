/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.wfapproval.work.dao.ApReceiveDao;
import com.lgcns.ikep4.wfapproval.work.model.ApReceive;



/**
 * 수신처  DAO 구현
 * 
 * @author 이동겸(volcote@naver.com)
 * @version $Id: ApReceiveDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apreceiveDao")
public class ApReceiveDaoImpl extends GenericDaoSqlmap<ApReceive, Integer> implements ApReceiveDao {
	private static final String NAMESPACE = "com.lgcns.ikep4.wfapproval.work.dao.ApDoc.";
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.example.dao.BoardDao#selectAll()
	 */
	//@Cacheable(cacheName = "sampleCache")
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @Cacheable 어노테이션으로 캐시 대상을 명시한다.
	public List<ApReceive> selectAll(ApReceive apreceive) {
		
		return sqlSelectForList(NAMESPACE +"selectAll", apreceive);
	}
	
	public int selectCount(ApReceive apreceive) {
		return (Integer)sqlSelectForObject(NAMESPACE +"selectCount", apreceive);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public Integer create(ApReceive apreceive) {
		return (Integer) sqlInsert(NAMESPACE +"insertReceive", apreceive);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public Integer createAuthUser(ApReceive apreceive) {
		return (Integer) sqlInsert(NAMESPACE +"insertAuthUser", apreceive);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public Integer createRelations(ApReceive apreceive) {
		return (Integer) sqlInsert(NAMESPACE +"insertRelations", apreceive);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(Integer seq) {
		//Integer count = (Integer) sqlSelectForObject("board.selectCount", seq);
		//if (count > 0)
		//	return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApReceive get(Integer seq) {
		return (ApReceive) sqlSelectForObject(NAMESPACE +"select", seq);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public void remove(Integer seq) {
		//sqlDelete("board.delete", seq);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public void update(ApReceive apreceive) {
		//sqlUpdate("board.update", apreceive);
	}

}
