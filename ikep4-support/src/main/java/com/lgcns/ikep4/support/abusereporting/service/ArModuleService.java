/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.abusereporting.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.abusereporting.model.ArModule;

/**
 * 
 * 모듈 관련 서비스
 *
 * @author 최성우
 * @version $Id: ArModuleService.java 16271 2011-08-18 07:06:14Z giljae $
 */
@Transactional
public interface ArModuleService extends GenericService<ArModule, String> {

	/**
	 * 전체 모듈 리스트 조회.
	 * 
	 * @param 모듈 목록
	 */
	public List<ArModule> list	();
}
