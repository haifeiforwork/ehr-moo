/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.userconfig.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.userconfig.model.UserConfig;

/**
 * 개인화 설정 서비스 인터페이스 클래스.
 *
 * @author 최현식
 * @version $Id: UserConfigService.java 16276 2011-08-18 07:09:07Z giljae $
 */
@Transactional
public interface UserConfigService {

	/**
	 * 개인화 설정 정보 목록을 가져온다.
	 *
	 * @param userId 유저 ID
	 * @return 개인화 설정 모델 객체 목록
	 */
	public List<UserConfig> listUserConfigByUserId(String userId);

	/**
	 * 개인화 설정 정보 정보를 유저ID와 묘듈ID를 이용해서 가져온다.
	 *
	 * @param userId 유저 ID
	 * @param moduleId 묘듈 ID
	 * @return 개인화 설정 모델 객체
	 */
	public UserConfig readUserConfigByUserIdAndModuleId(String userId, String moduleId, String portalId);

	/**
	 * 개인화 설정 정보를 개인화 설정 ID를 이용해서 가져온다.
	 *
	 * @param id 개인화 설정 정보 ID
	 * @return 개인화 설정 모델 객체
	 */
	public UserConfig readUserConfigById(String id);

	/**
	 * 개인화 설정 정보를 생성한다.
	 *
	 * @param 개인화 설정 모델 객체
	 * @return 개인화 설정 정보 ID
	 */
	public String createUserConfig(UserConfig userConfig);

	/**
	 * 개인화 설정 정보를 수정한다.
	 *
	 * @param 개인화 설정 모델 객체
	 */
	public void updateUserConfig(UserConfig userConfig);

	/**
	 * 개인화 설정 정보를 개인화 설정 ID를 이용해서 삭제한다.
	 *
	 * @param id 개인화 설정 정보 ID
	 */
	public void deleteUserConfig(String id);

	/**
	 * 개인화 설정 정보를  유저ID와 묘듈ID를 이용해서 삭제한다.
	 *
	 * @param userId 유저 ID
	 * @param moduleId 묘듈 ID
	 */
	public void deleteUserConfigByUserIdAndModuleId(String userId, String moduleId, String portalId);

	/**
	 * 개인화 설정 정보가 존재하는지를  유저ID와 묘듈ID를 이용해서 확인한다.
	 *
	 * @param userId 유저 ID
	 * @param moduleId 묘듈 ID
	 * @return 존재 여부
	 */
	public Boolean existsUserConfigByUserIdAndModuleId(String userId, String moduleId, String portalId);

	/**
	 * 개인화 설정 정보 존재하는지를 ID를 이용해서 확인한다.
	 *
	 * @param id 개인화 설정 정보 ID
	 * @return 존재 여부
	 */
	public Boolean existsUserConfigById(String id);

}
