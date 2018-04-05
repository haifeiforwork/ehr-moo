/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementPviSymbolDao;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPviSymbol;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPviSymbolPK;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementPviSymbolService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Assessment Management AssessmentManagementPviSymbolService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementPviSymbolServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class AssessmentManagementPviSymbolServiceImpl extends GenericServiceImpl<AssessmentManagementPviSymbol, AssessmentManagementPviSymbolPK> implements AssessmentManagementPviSymbolService {

	private AssessmentManagementPviSymbolDao assessmentManagementPviSymbolDao;

	@Autowired
	public AssessmentManagementPviSymbolServiceImpl(AssessmentManagementPviSymbolDao dao) {
		super(dao);
		this.assessmentManagementPviSymbolDao = dao;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementPviSymbolService#listByPortalId(java.lang.String)
	 */
	public List<AssessmentManagementPviSymbol> listByPortalId(String portalId) {
		return assessmentManagementPviSymbolDao.listByPortalId(portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementPviSymbolService#saveVisualSymbol(java.util.Map)
	 */
	public void saveVisualSymbol(Map<String, String> symbolMap, String userId) {
		AssessmentManagementPviSymbol assessmentManagementPviSymbol = new AssessmentManagementPviSymbol();
		assessmentManagementPviSymbol.setRegisterId(userId);

		for (int i = 0; i < 10; i++) {
			assessmentManagementPviSymbol.setStep(i);
			assessmentManagementPviSymbol.setSymbolId(symbolMap.get("symbolId" + i));
			assessmentManagementPviSymbol.setSectionValue(Integer.parseInt(symbolMap.get("sectionValue" + i)));
			assessmentManagementPviSymbol.setSectionStartScore(Integer.parseInt(symbolMap.get("sectionStartScore" + i)));
			assessmentManagementPviSymbol.setSectionEndScore(Integer.parseInt(symbolMap.get("sectionEndScore" + i)));

			assessmentManagementPviSymbolDao.update(assessmentManagementPviSymbol);
		}
	}

}
