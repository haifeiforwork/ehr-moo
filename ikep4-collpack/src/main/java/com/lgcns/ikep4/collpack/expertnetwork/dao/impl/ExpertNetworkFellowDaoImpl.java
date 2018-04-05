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

import com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkFellowDao;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkFellow;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkFellowPK;
import com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkPageCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Expert Network ExpertFellowDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkFellowDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class ExpertNetworkFellowDaoImpl extends GenericDaoSqlmap<ExpertNetworkFellow, ExpertNetworkFellowPK> implements ExpertNetworkFellowDao {
	private static final String NAMESPACE = "collpack.expertnetwork.dao.ExpertNetworkFellow.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public ExpertNetworkFellow get(ExpertNetworkFellowPK id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(ExpertNetworkFellowPK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	@Deprecated
	public ExpertNetworkFellowPK create(ExpertNetworkFellow object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(ExpertNetworkFellow object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(ExpertNetworkFellowPK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkFellowDao#countByUserId(java.lang.String)
	 */
	public int countByUserId(String userId) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countByUserId", userId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkFellowDao#listByUserIdPage(com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkPageCondition)
	 */
	public List<ExpertNetworkFellow> listByUserIdPage(ExpertNetworkPageCondition pageCondition) {
		return (List<ExpertNetworkFellow>)this.sqlSelectForList(NAMESPACE + "listByUserIdPage", pageCondition);
	}

}
