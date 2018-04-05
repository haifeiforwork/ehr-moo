/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.wfapproval.admin.dao.ApFormDao;
import com.lgcns.ikep4.wfapproval.admin.model.ApForm;
import com.lgcns.ikep4.wfapproval.admin.search.ApFormSearchCondition;


/**
 * 결재 양식관리 Master DAO 구현
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apFormDao")
public class ApFormDaoImpl extends GenericDaoSqlmap<ApForm, String> implements ApFormDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.wfapproval.admin.dao.ApForm.";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApForm get(String formId) {
		return (ApForm) sqlSelectForObject(NAMESPACE + "select", formId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String formId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "selectCount", formId);

		if (count > 0)
			return true;

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	//@TriggersRemove(cacheName = "apformCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public String create(ApForm apform) {
		return (String) sqlInsert(NAMESPACE + "create", apform);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	//@TriggersRemove(cacheName = "apformCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public void update(ApForm apform) {
		sqlUpdate(NAMESPACE + "update", apform);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	//@TriggersRemove(cacheName = "apformCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public void remove(String formId) {
		sqlDelete(NAMESPACE + "delete", formId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.dao.impl.maps.ApFormDao#selectAll()
	 */
	//@Cacheable(cacheName = "apformCache")
	// @Cacheable 어노테이션으로 캐시 대상을 명시한다.
	public List<ApForm> getApFormListAll() {
		return sqlSelectForList(NAMESPACE + "selectAll");
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.dao.impl.maps.ApFormDao#selectAll()
	 */
	//@Cacheable(cacheName = "apformCache")
	// @Cacheable 어노테이션으로 캐시 대상을 명시한다.
	public List<ApForm> getApFormList() {
		return sqlSelectForList(NAMESPACE + "selectFormList");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.wfapproval.admin.dao.ApFormDao#listByApFormAll(com.lgcns
	 * .ikep4.wfapproval.admin.search.ApFormSearchCondition)
	 */
	public List<ApForm> listApFormAll(ApFormSearchCondition apFormSearchCondition) {
		List<ApForm> boardItemList = (List<ApForm>) this.sqlSelectForList(NAMESPACE + "listApFormAll",
				apFormSearchCondition);

		return boardItemList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.wfapproval.admin.dao.ApFormDao#countByApFormAll(com.lgcns
	 * .ikep4.wfapproval.admin.search.ApFormSearchCondition)
	 */
	public Integer countApFormAll(ApFormSearchCondition apFormSearchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countApFormAll", apFormSearchCondition);

		return count;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.wfapproval.admin.dao.ApFormDao#listByApFormSelect(com.lgcns
	 * .ikep4.wfapproval.admin.search.ApFormSearchCondition)
	 */
	public List<ApForm> listApFormSelect(ApFormSearchCondition apFormSearchCondition) {
		List<ApForm> boardItemList = (List<ApForm>) this.sqlSelectForList(NAMESPACE + "listApFormSelect",
				apFormSearchCondition);

		return boardItemList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.wfapproval.admin.dao.ApFormDao#countByApFormSelect(com.
	 * lgcns.ikep4.wfapproval.admin.search.ApFormSearchCondition)
	 */
	public Integer countApFormSelect(ApFormSearchCondition apFormSearchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countApFormSelect", apFormSearchCondition);

		return count;
	}
}
