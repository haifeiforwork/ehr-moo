/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.planner.model.Mandator;

/**
 * 일정 위임 관리
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: MandatorDao.java 16297 2011-08-19 07:52:43Z giljae $
 */
public interface MandatorDao extends GenericDao<Mandator, String> {
	String NAMESPACE = "lightpack.planner.dao.Mandator";
}
