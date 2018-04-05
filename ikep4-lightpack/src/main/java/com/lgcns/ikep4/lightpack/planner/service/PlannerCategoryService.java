/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategory;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 일정 범주 서비스
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: PlannerCategoryService.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Transactional
public interface PlannerCategoryService extends GenericService<PlannerCategory, String> {
	final static String DEFAULT_CATEGORY = "1";
	
	/**
	 * 전체 범주 목록 읽기
	 * @return
	 */
	List<PlannerCategory> getList();
	
	/**
	 * 로케일에 해당하는 범주 목록 읽기
	 * @param string
	 * @return
	 */
	List<PlannerCategory> getList(String string);

	void delete(String[] cid);

	void create(User user, String portalId, PlannerCategory category);

	/**범주삭제, 삭제된 범주의 일정을 기본 범주로 갱신
	 * @param cid
	 */
	void deleteCategory(String[] cid);

	/**
	 * 로케일 목록 읽기
	 * @return
	 */
	List<Map<String, String>> getLocaleList();

	/**
	 * 범주 읽기(로케일 정보와 함께)
	 * @param categoryId
	 * @return
	 */
	PlannerCategory readWithLocale(String categoryId);

	/**
	 * 범주 이름 중복 체크(국가+범주명)
	 * @param category
	 * @return
	 */
	boolean isDuplicate(PlannerCategory category);
}
