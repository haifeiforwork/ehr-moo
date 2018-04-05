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
import com.lgcns.ikep4.support.profile.model.ProfileVisit;

/**
 * 프로파일 방문자 기록용 DAO
 *
 * @author 이형운
 * @version $Id: ProfileVisitDao.java 16273 2011-08-18 07:07:47Z giljae $
 */
public interface ProfileVisitDao extends GenericDao<ProfileVisit, String> {

	/**
	 * 방문자 목록을 조회 해 온다.
	 * @param profileVisit 방문자 정보 객체
	 * @return 방문자 목록 리스트
	 */
	public List<ProfileVisit> selectAllByOwnerId(ProfileVisit profileVisit);
	
	/**
	 * 방문자 수를 조회해 온다.
	 * @param profileVisit 방문자 정보 객체
	 * @return 방문자 수
	 */
	public Integer selectAllCountByOwnerId(ProfileVisit profileVisit);
	
	/**
	 * 방문자 정보 조회
	 * @param profileVisit 방문자 정보 객체
	 * @return 방문자 개별 정보
	 */
	public ProfileVisit get(ProfileVisit profileVisit);
	
	/**
	 * 개별 방문 로그를 지운다.
	 * @param profileVisit 방문자 정보 객체
	 */
	public void physicalDelete(ProfileVisit profileVisit);
	
	/**
	 * 해당 블로그에 전체 방문자 로그를 지운다.
	 * @param profileVisit 방문자 정보 객체
	 */
	public void physicalDeleteThreadByOwnerId(ProfileVisit profileVisit);
}
