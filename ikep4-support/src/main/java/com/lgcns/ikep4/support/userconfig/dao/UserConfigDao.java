/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.userconfig.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.userconfig.model.UserConfig;

/**
 * 사용자 개인화 설정 DAO 인터페이스
 * 
 * @author 최현식
 * @version $$Id: UserConfigDao.java 16258 2011-08-18 05:37:22Z giljae $$
 */
public interface UserConfigDao extends GenericDao<UserConfig, String> {
	/**
	 * 개인화 설정 정보 목록을 가져온다.
	 * 
	 * @param userId 유저 ID
	 * @return
	 */
	List<UserConfig> listByUserId(String userId);

	/**
	 * 개인화 설정 정보가 존재하는지 확인한다.
	 * 
	 * @param  parameter 파라미터 개인화 설정 정보
	 * @return
	 */
	Boolean existsByUserIdAndModuleId(UserConfig parameter);


	/**
	 * 개인화 설정 정보를 불러온다.
	 * 
	 * @param parameter 파라미터 개인화 설정 정보
	 * @return
	 */
	UserConfig getByUserIdAndModuleId(UserConfig parameter);

	/**
	 * 개인화 설정 정보를 삭제한다.
	 * 
	 * @param parameter 파라미터 개인화 설정 정보
	 * @return
	 */
	void removeUserConfigByUserIdAndModuleId(UserConfig parameter);

	/**
	 * 개인화 설정 정보를 수정한다.
	 * 
	 * @param parameter 파라미터 개인화 설정 정보
	 * @return
	 */
	void updateByUserIdAndModuleId(UserConfig parameter);

}
