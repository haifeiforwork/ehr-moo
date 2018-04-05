/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.peopleconnection.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.peopleconnection.model.ExternalExpert;

/**
 * 
 * people connection service에서 필요한 각종 데이터 조회.
 *
 * @author 최성우
 * @version $Id: PeopleConnectionDao.java 16246 2011-08-18 04:48:28Z giljae $
 */
public interface PeopleConnectionDao extends GenericDao<ExternalExpert, String>  {
}
