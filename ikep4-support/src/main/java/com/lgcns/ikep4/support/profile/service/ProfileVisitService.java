/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.profile.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.profile.model.ProfileVisit;

/**
 * 프로파일 방문자 기록용 Service
 *
 * @author 이형운
 * @version $Id: ProfileVisitService.java 16273 2011-08-18 07:07:47Z giljae $
 */
@Transactional
public interface ProfileVisitService extends GenericService<ProfileVisit, String> {

	/**
	 * 방문자 목록을 조회 해 온다.
	 * @param profileVisit 프로파일 방문자 정보 객체
	 * @return
	 */
	public List<ProfileVisit> selectAllByOwnerId(ProfileVisit profileVisit);
	
	/**
	 * 방문자 수를 조회해 온다.
	 * @param profileVisit 프로파일 방문자 정보 객체
	 * @return
	 */
	public Integer selectAllCountByOwnerId(ProfileVisit profileVisit);
	
	/**
	 * 방문자 정보 조회
	 * @param profileVisit 프로파일 방문자 정보 객체
	 * @return
	 */
	public ProfileVisit get(ProfileVisit profileVisit);
	
	/**
	 * 개별 방문 로그를 지운다.
	 * @param profileVisit 프로파일 방문자 정보 객체
	 */
	public void physicalDelete(ProfileVisit profileVisit);
	
	/**
	 * 해당 블로그에 전체 방문자 로그를 지운다.
	 * @param profileVisit 프로파일 방문자 정보 객체
	 */
	public void physicalDeleteThreadByOwnerId(ProfileVisit profileVisit);
	
	
	/**
	 * 하루에 한번만 방문자 기록을 한다
	 * @param profileVisit 프로파일 방문자 정보 객체
	 */
	public void recordProfileVisitByDay(ProfileVisit profileVisit);
}
