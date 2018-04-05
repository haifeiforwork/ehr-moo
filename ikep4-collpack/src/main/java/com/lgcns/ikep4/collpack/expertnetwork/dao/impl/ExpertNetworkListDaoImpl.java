/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkListDao;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkList;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkListPK;
import com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkBlockPageCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Expert Network ExpertListDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkListDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class ExpertNetworkListDaoImpl extends GenericDaoSqlmap<ExpertNetworkList, ExpertNetworkListPK> implements ExpertNetworkListDao {
	private static final String NAMESPACE = "collpack.expertnetwork.dao.ExpertNetworkList.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ExpertNetworkList get(ExpertNetworkListPK id) {
		return (ExpertNetworkList)sqlSelectForObject(NAMESPACE + "get", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(ExpertNetworkListPK id) {
		ExpertNetworkList category = get(id);
		if (null == category) {
			return false;
		}
		return id.getCategoryId().equals(category.getCategoryId());
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public ExpertNetworkListPK create(ExpertNetworkList object) {
		sqlInsert(NAMESPACE + "create", object);
		return (ExpertNetworkListPK)object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ExpertNetworkList object) {
		sqlUpdate(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(ExpertNetworkListPK id) {
		sqlDelete(NAMESPACE + "remove", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkListDao#countByCategoryId(java.lang.String)
	 */
	public int countByCategoryId(String categoryId) {
		return (Integer)sqlSelectForObject(NAMESPACE + "countByCategoryId", categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkListDao#listByCategoryIdPage(com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkBlockPageCondition)
	 */
	public List<ExpertNetworkList> listByCategoryIdPage(ExpertNetworkBlockPageCondition pageCondition) {
		return (List<ExpertNetworkList>)this.sqlSelectForList(NAMESPACE + "listByCategoryIdPage", pageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkListDao#removeByCategoryId(java.lang.String)
	 */
	public void removeByCategoryId(String categoryId) {
		sqlDelete(NAMESPACE + "removeByCategoryId", categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkListDao#getByCategoryIdRandom(java.lang.String)
	 */
	public ExpertNetworkList getByCategoryIdRandom(String categoryId) {
		return (ExpertNetworkList)sqlSelectForObject(NAMESPACE + "getByCategoryIdRandom", categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkListDao#removeByAuthorized()
	 */
	public void removeByAuthorized() {
		sqlDelete(NAMESPACE + "removeByAuthorized");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkListDao#batchGatherExpert()
	 */
	public void batchGatherExpert() {
		sqlInsert(NAMESPACE + "batchGatherExpert");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkListDao#updateAuthorized(com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkList)
	 */
	public void updateAuthorized(ExpertNetworkList expertNetworkList) {
		sqlUpdate(NAMESPACE + "updateAuthorized", expertNetworkList);
	}

}
