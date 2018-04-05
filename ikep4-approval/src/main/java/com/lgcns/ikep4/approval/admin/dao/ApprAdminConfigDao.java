/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * TODO Javadoc주석작성
 * 
 * @author 서지혜
 * @version $Id$
 */

public interface ApprAdminConfigDao extends GenericDao<ApprAdminConfig, String> {
	/**
	 * 입력
	 * 
	 * @param apprAdminConfig
	 */
	void adminConfigCreate(ApprAdminConfig apprAdminConfig);

	/**
	 * 수정
	 * 
	 * @param apprAdminConfig
	 */
	void adminConfigUpdate(ApprAdminConfig apprAdminConfig);

	/**
	 * 상세
	 * 
	 * @param portalId
	 * @return
	 */
	ApprAdminConfig adminConfigDetail(String portalId);

}
