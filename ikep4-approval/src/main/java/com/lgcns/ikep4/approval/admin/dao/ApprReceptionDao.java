/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.admin.model.ApprReception;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * 접수 담당자
 * 
 * @author 서지혜
 * @version $Id$
 */

public interface ApprReceptionDao extends GenericDao<ApprReception, String> {

	void createApprReceptionSave(ApprReception apprReception);
	
	List<ApprReception> listByReception(String groupId);
	
	void deleteApprReception(String groupId);
	
	boolean existReceptionUser(Map<String, String> map);
	
}
