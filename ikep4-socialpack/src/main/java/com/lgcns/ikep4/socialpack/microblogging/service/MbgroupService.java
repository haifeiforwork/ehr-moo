/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.socialpack.microblogging.model.Mbgroup;

/**
 * 
 * 마이크로블로깅 그룹 관련 서비스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MbgroupService.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Transactional
public interface MbgroupService extends GenericService<Mbgroup, String> {

	/**
	 * 사용자가 속한 그룹 List 반환
	 * 
	 * @param memberId
	 * @return Mbgroup객체 List
	 */
	public List<Mbgroup> myGroupList(String id);

	/**
	 * 블로그 주인과 사용자가 함께 속한 그룹 List 반환
	 * 
	 * @param userId, ownerId를 넣은 Map
	 * @return Mbgroup객체 List
	 */
	public List<Mbgroup> bothGroup(Map map);
}
