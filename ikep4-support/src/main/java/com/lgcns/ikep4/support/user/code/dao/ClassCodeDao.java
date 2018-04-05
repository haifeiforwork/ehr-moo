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
import com.lgcns.ikep4.support.user.code.model.ClassCode;


/**
 * 클래스 코드 관리 DAO 정의
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: ClassCodeDao.java 16276 2011-08-18 07:09:07Z giljae $
 */
public interface ClassCodeDao extends GenericDao<ClassCode, String> {

	/**
	 * 모든 클래스 코드 반환
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<ClassCode> selectAll(AdminSearchCondition searchCondition);

	/**
	 * 목록 카운트
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	Integer selectCount(AdminSearchCondition searchCondition);
	
	/**
	 * 동일 클래스명 확인 여부
	 * @param className
	 * @return
	 */
	public boolean checkName(String className);

}
