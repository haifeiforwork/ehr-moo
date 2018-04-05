/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.group.service;

import java.util.List;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.group.model.GroupType;


/**
 * 그룹타입 관리 서비스
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: GroupTypeService.java 16276 2011-08-18 07:09:07Z giljae $
 */
public interface GroupTypeService extends GenericService<GroupType, String> {

	/**
	 * 그룹타입 코드 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public SearchResult<GroupType> list(AdminSearchCondition searchCondition);

	/**
	 * 검색 조건없이 목록 가져오기
	 * 
	 * @param param localeCode, portalId
	 * @return
	 */
	public List<GroupType> selectForList();
}