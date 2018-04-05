/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPviSymbol;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPviSymbolPK;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Assessment Management AssessmentManagementPviSymbolDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementPviSymbolDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface AssessmentManagementPviSymbolDao extends GenericDao<AssessmentManagementPviSymbol, AssessmentManagementPviSymbolPK> {

	/**
	 * portal별 Symbol정보 조회
	 * @param portalId - 포털ID
	 * @return List<AssessmentManagementPviSymbol>
	 */
	List<AssessmentManagementPviSymbol> listByPortalId(String portalId);
}
