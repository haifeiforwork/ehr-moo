/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.profile.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.profile.model.Career;


/**
 * 경력 정보 관리용  DAO Interface
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: CareerDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface CareerDao extends GenericDao<Career, String> {

	/**
	 * 모든 경력 목록  반환
	 * @param career 경력 정보 객체
	 * @return 경력 전체 리스트
	 */
	public List<Career> selectAll(Career career);
	
	/**
	 *  최근 5개 경력 목록 반환
	 * @param career 경력 정보 객체
	 * @return 최근 5개 경력 리스트
	 */
	public List<Career> selectRecent5(Career career);
}
