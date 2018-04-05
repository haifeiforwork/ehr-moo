/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.common.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.collaboration.common.model.CommonCode;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * ColaboCommon DAO
 * 
 * @author pjh
 * @version $Id$
 */
public interface CollaboCommonDao extends GenericDao<CommonCode, String> {
	
	/**
	 * 메일,이름 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getUserMailAddrList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 메일주소 조회
	 * @param empNO
	 * @return
	 * @throws Exception
	 */
	public String getUserMailAddr(String empNO) throws Exception;
}
