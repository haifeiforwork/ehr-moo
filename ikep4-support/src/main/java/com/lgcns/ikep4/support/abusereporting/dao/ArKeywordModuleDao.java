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
import com.lgcns.ikep4.support.abusereporting.model.ArKeywordModule;
import com.lgcns.ikep4.support.abusereporting.model.ArModule;


/**
 * 
 * ArKeywordModule 관련 처리 DAO
 *
 * @author 최성우
 * @version $Id: ArKeywordModuleDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface ArKeywordModuleDao extends GenericDao<ArKeywordModule, ArKeywordModule> {

	/**
	 * keyword에 해당하는 모듈 연결 모두 삭제
	 * 
	 * @param keyword, portalId 가 있는 ArKeywordModule 객체
	 */
	public void deleteByKeyword(ArKeywordModule arKeywordModule);

	/**
	 * keyword에 해당하는 모듈 리스트 조회
	 * 
	 * @param keyword, portalId 가 있는 ArKeywordModule 객체
	 * @return ArModule List
	 */
	public List<ArModule> listByKeyword(ArKeywordModule arKeywordModule);

	/**
	 * moduleCode 에 해당하는 keyword 리스트 조회
	 * 
	 * @param keyword, portalId 가 있는 ArKeywordModule 객체
	 * @return keyword 리스트
	 */
	public List<String> listByModuleCode(ArKeywordModule arKeywordModule);
	
}
