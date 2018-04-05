/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.dao;

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPolicy;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPolicyPK;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Knowledge Map ExpertPolicyDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkPolicyDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ExpertNetworkPolicyDao extends GenericDao<ExpertNetworkPolicy, ExpertNetworkPolicyPK> {

	/**
	 * 포털ID별 정책 조회
	 * @param portalId
	 * @return ExpertPolicy
	 */
	ExpertNetworkPolicy getByPortalId(String portalId);

}
