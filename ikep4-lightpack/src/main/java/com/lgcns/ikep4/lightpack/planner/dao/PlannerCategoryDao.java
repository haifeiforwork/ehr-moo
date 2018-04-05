/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategory;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategoryLocale;

/**
 * 일정 범주(카테고리) Dao
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: PlannerCategoryDao.java 16297 2011-08-19 07:52:43Z giljae $
 */
public interface PlannerCategoryDao extends GenericDao<PlannerCategory, String> {
	
	String NAMESPACE = "lightpack.planner.dao.PlannerCategory";
	
	List<PlannerCategory> getList();

	void delete(String[] cid);
	
	// 포털 로케일 정보 읽기
	List<Map<String, String>> getLocaleList();

	/**
	 * 범주 로케일 정보 insert
	 * @param plannerCategoryLocale
	 */
	void createCategoryLocale(PlannerCategoryLocale plannerCategoryLocale);

	/**
	 * 범주 로케일 row 삭제
	 * @param categoryId
	 */
	void deletePlannerCategoryLocaleList(String categoryId);

	/**
	 * 범주 로케일 row 삭제
	 * @param cid
	 */
	void deletePlannerCategoryLocaleList(String[] cid);

	/**
	 * 범주 읽기(로케일 정보와 함께)
	 * @param categoryId
	 * @return
	 */
	PlannerCategory readWithLocale(String categoryId);

	/**
	 * 전체 범주 목록 읽기
	 * @return
	 */
	List<PlannerCategory> getList(String string);

	/**범주 이름 중복 체크(국가+범주명)
	 * @param plannerCategoryLocale
	 * @return 
	 */
	int isDuplicate(PlannerCategoryLocale plannerCategoryLocale);
}
