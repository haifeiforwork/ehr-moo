/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.award.dao.AwardLinereplyDao;
import com.lgcns.ikep4.lightpack.award.model.AwardLinereply;
import com.lgcns.ikep4.lightpack.award.search.AwardLinereplySearchCondition;

/**
 * AwardLinereplyDao 구현체 클래스
 */
@Repository
public class AwardLinereplyDaoImpl extends GenericDaoSqlmap<AwardLinereply, String> implements AwardLinereplyDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.award.dao.awardLinereply.";


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public AwardLinereply get(String id) {
		return (AwardLinereply)this.sqlSelectForObject(NAMESPACE + "get", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "exists", id);

		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(AwardLinereply object) {
		this.sqlInsert(NAMESPACE + "create", object);

		return object.getLinereplyId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(AwardLinereply object) {
		this.sqlInsert(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardLinereplyDao#updateStep(com.lgcns.ikep4.lightpack.award.model.AwardLinereply)
	 */
	public void updateStep(AwardLinereply awardLinereply) {
		this.sqlInsert(NAMESPACE + "updateStep", awardLinereply);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardLinereplyDao#logicalDelete(com.lgcns.ikep4.lightpack.award.model.AwardLinereply)
	 */
	public void logicalDelete(AwardLinereply awardLinereply) {
		this.sqlUpdate(NAMESPACE + "logicalDelete", awardLinereply);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardLinereplyDao#physicalDelete(java.lang.String)
	 */
	public void physicalDelete(String id) {
		this.sqlUpdate(NAMESPACE + "physicalDelete", id);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardLinereplyDao#physicalDeleteThreadByLinereplyId(java.lang.String)
	 */
	public void physicalDeleteThreadByLinereplyId(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThreadByLinereplyId", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardLinereplyDao#physicalDeleteThreadByItemThread(java.lang.String)
	 */
	public void physicalDeleteThreadByItemThread(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThreadByItemThread", id);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardLinereplyDao#physicalDeleteThreadByItemId(java.lang.String)
	 */
	public void physicalDeleteThreadByItemId(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThreadByItemId", id);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardLinereplyDao#listBySearchCondition(com.lgcns.ikep4.lightpack.award.search.AwardLinereplySearchCondition)
	 */
	public List<AwardLinereply> listBySearchCondition(AwardLinereplySearchCondition searchCondtion) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondtion);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardLinereplyDao#countBySearchCondition(com.lgcns.ikep4.lightpack.award.search.AwardLinereplySearchCondition)
	 */
	public Integer countBySearchCondition(AwardLinereplySearchCondition searchCondtion) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondtion);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardLinereplyDao#countChildren(java.lang.String)
	 */
	public Integer countChildren(String id) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countChildren", id);
	}
}
