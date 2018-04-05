/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementGroupPviDao;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementGroupPvi;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementGroupPviPK;
import com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Assessment Management AssessmentManagementGroupPviDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementGroupPviDaoImpl.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Repository
public class AssessmentManagementGroupPviDaoImpl extends GenericDaoSqlmap<AssessmentManagementGroupPvi, AssessmentManagementGroupPviPK> implements AssessmentManagementGroupPviDao {
	private static final String NAMESPACE = "collpack.assess.dao.AssessmentManagementGroupPvi.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public AssessmentManagementGroupPvi get(AssessmentManagementGroupPviPK id) {
		return (AssessmentManagementGroupPvi)sqlSelectForObject(NAMESPACE + "get", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(AssessmentManagementGroupPviPK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	@Deprecated
	public AssessmentManagementGroupPviPK create(AssessmentManagementGroupPvi object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(AssessmentManagementGroupPvi object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(AssessmentManagementGroupPviPK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementGroupPviDao#listRootGroupByPortalId(java.lang.String)
	 */
	public List<AssessmentManagementGroupPvi> listRootGroupByPortalId(String portalId) {
		return sqlSelectForList(NAMESPACE + "listRootGroupByPortalId", portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementGroupPviDao#countByGroupIdPage(java.lang.String)
	 */
	public int countByGroupIdPage(String groupId) {
		return (Integer)sqlSelectForObject(NAMESPACE + "countByGroupIdPage", groupId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementGroupPviDao#listByGroupIdPage(com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition)
	 */
	public List<AssessmentManagementGroupPvi> listByGroupIdPage(
			AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition) {
		return sqlSelectForList(NAMESPACE + "listByGroupIdPage", assessmentManagementBlockPageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementGroupPviDao#listGroupHierarchy(java.lang.String)
	 */
	public List<AssessmentManagementGroupPvi> listGroupHierarchy(String groupId) {
		return sqlSelectForList(NAMESPACE + "listGroupHierarchy", groupId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementGroupPviDao#initScoreByPortalId(java.lang.String, java.lang.String)
	 */
	public void initScoreByPortalId(String portalId, String registerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("portalId", portalId);
		map.put("registerId", registerId);

		sqlUpdate(NAMESPACE + "initScoreByPortalId", map);
	}

}
