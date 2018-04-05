/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtConfig;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtConfigKey;
import com.lgcns.ikep4.servicepack.usagetracker.util.SearchUtil;

/**
 * 
 * 사용량 통계 사용자 로그인 히스토리 service
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtConfigService.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Transactional
public interface UtConfigService extends GenericService<UtConfig,UtConfigKey> {
	/**
	 * 모든 config 저장및 수정
	 * @param utConfigList
	 */
	public void saveOrUpdate(List<UtConfig> utConfigList, String portalId);
	/**
	 * 모든 configlist select
	 * @param searchUtil
	 * @return
	 */
	public List<UtConfig> selectUtConfigList(SearchUtil searchUtil);
}
