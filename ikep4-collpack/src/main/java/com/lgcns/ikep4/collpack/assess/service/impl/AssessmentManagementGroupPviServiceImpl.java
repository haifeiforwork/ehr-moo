/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementGroupPviDao;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementGroupPvi;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementGroupPviPK;
import com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementGroupPviService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Assessment Management AssessmentManagementGroupPviService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementGroupPviServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class AssessmentManagementGroupPviServiceImpl extends GenericServiceImpl<AssessmentManagementGroupPvi, AssessmentManagementGroupPviPK> implements AssessmentManagementGroupPviService {

	private AssessmentManagementGroupPviDao assessmentManagementGroupPviDao;

	@Autowired
	public AssessmentManagementGroupPviServiceImpl(AssessmentManagementGroupPviDao dao) {
		super(dao);
		this.assessmentManagementGroupPviDao = dao;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementGroupPviService#getCompanyPvi(java.lang.String)
	 */
	public int getCompanyPvi(String groupId) {
		AssessmentManagementGroupPvi result = assessmentManagementGroupPviDao.get(new AssessmentManagementGroupPviPK(groupId));

		return result.getPvi();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementGroupPviService#listRootGroupByPortalId(java.lang.String)
	 */
	public List<AssessmentManagementGroupPvi> listRootGroupByPortalId(String portalId) {
		return assessmentManagementGroupPviDao.listRootGroupByPortalId(portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementGroupPviService#countByGroupIdPage(java.lang.String)
	 */
	public int countByGroupIdPage(String groupId) {
		return assessmentManagementGroupPviDao.countByGroupIdPage(groupId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementGroupPviService#listByGroupIdPage(com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition)
	 */
	public List<AssessmentManagementGroupPvi> listByGroupIdPage(
			AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition) {
		return assessmentManagementGroupPviDao.listByGroupIdPage(assessmentManagementBlockPageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementGroupPviService#listGroupHierarchy(java.lang.String)
	 */
	public List<AssessmentManagementGroupPvi> listGroupHierarchy(String groupId) {
		return assessmentManagementGroupPviDao.listGroupHierarchy(groupId);
	}

}
