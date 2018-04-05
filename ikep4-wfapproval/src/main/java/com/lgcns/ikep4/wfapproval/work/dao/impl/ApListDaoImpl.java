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

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.wfapproval.work.dao.ApListDao;
import com.lgcns.ikep4.wfapproval.work.model.ApList;
import com.lgcns.ikep4.wfapproval.work.search.ApListSearchCondition;


/**
 * 결재 목록조회  Master DAO 구현
 * 
 * @author 장규진(mistpoet@paran.com)
 * @version $Id: ApListDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apListDao")
public class ApListDaoImpl extends GenericDaoSqlmap<ApList, String> implements ApListDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.wfapproval.work.dao.ApList.";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApList get(String registUserId) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String registUserId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "countApList", registUserId);

		if (count > 0)
			return true;

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.wfapproval.admin.dao.ApFormDao#listByApFormAll(com.lgcns
	 * .ikep4admin.search.ApFormSearchCondition)
	 */
	public List<ApList> getApSearchList(ApListSearchCondition apListSearchCondition) {
		List<ApList> boardItemList = (List<ApList>) this.sqlSelectForList(NAMESPACE + "searchApList",
				apListSearchCondition);

		return boardItemList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.wfapproval.admin.dao.ApFormDao#countByApFormAll(com.lgcns
	 * .ikep4admin.search.ApFormSearchCondition)
	 */
	public Integer getCountApList(ApListSearchCondition apListSearchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countApList", apListSearchCondition);

		return count;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	// @TriggersRemove(cacheName = "aplistCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public String create(ApList aplist) {
		//return (String) sqlInsert(NAMESPACE + "create", aplist);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	// @TriggersRemove(cacheName = "aplistCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public void update(ApList aplist) {
		//sqlUpdate(NAMESPACE + "update", aplist);
		return;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	// @TriggersRemove(cacheName = "aplistCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public void remove(String apprId) {
		//sqlDelete(NAMESPACE + "delete", apprId);
		return;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.wfapproval.admin.dao.ApFormDao#listByApFormSelect(com.lgcns
	 * .ikep4admin.search.ApFormSearchCondition)
	 */
	/*public List<ApForm> listApFormSelect(ApFormSearchCondition apFormSearchCondition) {
		List<ApForm> boardItemList = (List<ApForm>) this.sqlSelectForList(NAMESPACE + "listApFormSelect",
				apFormSearchCondition);

		return boardItemList;
	}*/

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.wfapproval.admin.dao.ApFormDao#countByApFormSelect(com.
	 * lgcns.ikep4admin.search.ApFormSearchCondition)
	 */
	/*public Integer countApFormSelect(ApFormSearchCondition apFormSearchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countApFormSelect", apFormSearchCondition);

		return count;
	}*/
}
