/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.peopleconnection.service;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.socialpack.peopleconnection.model.ExternalExpert;

/**
 * 
 * people connection service (그중 외부 전문가 추천 리스트) 관련 서비스
 *
 * @author 최성우
 * @version $Id: PeopleConnectionService.java 16246 2011-08-18 04:48:28Z giljae $
 */

@Transactional
public interface PeopleConnectionService extends GenericService<ExternalExpert, String> {

	/**
	 * ProfileId에 해당하는 외부 전문가 정보를 조회하여 리턴한다. 
	 *
	 * @param externalExpertProfileIdList 외부전문가 ProfileId가 담긴 리스트
	 * @return 외부 전문가 정보 List
	 */
	public List<ExternalExpert> listByProfileIdList(List<String> externalExpertProfileIdList);
}
