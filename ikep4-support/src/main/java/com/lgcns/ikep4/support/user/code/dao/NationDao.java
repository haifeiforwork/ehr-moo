/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.Nation;


/**
 * 국가 코드 관리 DAO 정의
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: NationDao.java 16276 2011-08-18 07:09:07Z giljae $
 */
public interface NationDao extends GenericDao<Nation, String> {

	/**
	 * 국가 코드 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<Nation> selectAll(AdminSearchCondition searchCondition);

	/**
	 * 국가 코드 조회 카운트
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	Integer selectCount(AdminSearchCondition searchCondition);
	
	/**
	 * 해당 로케일의 모든 국가 코드 조회
	 * 
	 * @param localeCode 조회할 로케일 코드
	 * @return
	 */
	public List<Nation> listAll(String localeCode);
}
