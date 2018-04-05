/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao;



import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.approval.work.model.ApprUserLine;

/**
 * Default 결재선 Dao 정의
 *
 * @author 
 * @version $Id$
 */
public interface ApprUserLineDao extends GenericDao<ApprUserLine, String> {


	List<ApprUserLine> listApprUserLineAll(String	userId);
	
	List<ApprUserLine> listApprUserLine(Map<String, String> map);
}
