/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.abusereporting.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.abusereporting.model.ArKeyword;
import com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition;


/**
 * 
 * ArKeyword 관련 처리 DAO
 *
 * @author 최성우
 * @version $Id: ArKeywordDao.java 16271 2011-08-18 07:06:14Z giljae $
 */
public interface ArKeywordDao extends GenericDao<ArKeyword, ArKeyword> {

	/**
	 * 검색조건에 해당하는모든 Keyword 반환
	 * 
	 * @param moduleCode, searchWord 를 담은 ArAbuseSearchCondition 객체
	 * @return ArAbuseHistory List
	 */
	public List<String> list(ArAbuseSearchCondition arAbuseSearchCondition);

}
