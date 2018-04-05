/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.collpack.ideation.model.IdPolicy;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdPolicyDao.java 7008 2011-04-21 02:07:44Z loverfairy $
 */
public interface IdPolicyDao extends GenericDao<IdPolicy, String>  {
	
	/**
	 * 정책 조회
	 * @param policyType
	 * @param portalId
	 * @return
	 */
	public IdPolicy get(String policyType, String portalId);
	
	/**
	 * 정책 모든 포털 목록 가져오기
	 * @param policyType
	 * @return
	 */
	public List<IdPolicy>list(String policyType);
	
	
}
