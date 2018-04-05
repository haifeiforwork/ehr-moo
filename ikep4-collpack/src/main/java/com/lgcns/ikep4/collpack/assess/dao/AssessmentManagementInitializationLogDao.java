/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementInitializationLog;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementInitializationLogPK;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Assessment Management AssessmentManagementInitializationLogDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementInitializationLogDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface AssessmentManagementInitializationLogDao extends GenericDao<AssessmentManagementInitializationLog, AssessmentManagementInitializationLogPK> {

	/**
	 * portalId별 초기화이력 조회
	 * @param portalId
	 * @return List<AssessmentManagementInitializationLog>
	 */
	List<AssessmentManagementInitializationLog> listByPortalId(String portalId);

}
