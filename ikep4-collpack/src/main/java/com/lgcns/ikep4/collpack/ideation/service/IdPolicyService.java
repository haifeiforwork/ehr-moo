/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.collpack.ideation.model.IdPolicy;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdPolicyService.java 7008 2011-04-21 02:07:44Z loverfairy $
 */
@Transactional
public interface IdPolicyService extends GenericService<IdPolicy, String>  {
	
	
	/**
	 * 정책 조회
	 * @param policyType
	 * @param portalId
	 * @return
	 */
	public IdPolicy get(String policyType, String portalId);
	
	/**
	 * 삭제
	 * @param policyId
	 */
	public void remove(String policyId);
}
