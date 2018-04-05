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
import com.lgcns.ikep4.support.profile.model.ProfileExpertFellow;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 프로파일에서 사용자 정보를 업데이트 하기 위해  User Service 를 호출 하는 Profile Service Interface
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: ProfileService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface ProfileService extends GenericService<User, String> {

	/**
	 * 자신의 프로파일에서 정보 업데이트 하기 위한 부분 
	 * @param profile 사용자 정보 객체
	 */
	public void updateProfile(User profile);
	
	/**
	 * 프로파일에서 요즘은 (프로파일 대상자 현재 상태 )을 업데이트 한다.
	 * @param profile 사용자 정보 객체
	 */
	public void updateProfileStaus(User profile);
	
	/**
	 * 프로파일의 사진을 등록하기 위해 이미지 ID를 업데이트 한다.
	 * @param profile 사용자 정보 객체
	 */
	public void updatePictureId(User profile);
	
	/**
	 * 프로파일의 사진을 등록하기 위해 이미지 ID를 업데이트 한다.
	 * @param profile 사용자 정보 객체
	 */
	public void updateProfilePictureId(User profile);
	
	/**
	 * 트위트 계정과 인증 정보를 업데이트 한다.
	 * @param profile 사용자 정보 객체
	 */
	public void updateTwitterInfo(User profile);
	
	/**
	 * 페이스북 계정과 인증 정보를 업데이트 한다.
	 * @param profile 사용자 정보 객체
	 */
	public void updateFacebookInfo(User profile);

	/**
	 * 프로파일에서 자신의 전문가 정보의  상세 정보를 업데이트 한다.
	 * @param profile 사용자 정보 객체
	 */
	public void updateExportField(User profile);
	
	
	/**
	 * 관심분야 전문가 검색 10명 매칭 점수 순으로 조회 한다.
	 * @param userId 사용자 정보 객체
	 * @return 관심분야가 같은 전문가 리스트
	 */
	public List<ProfileExpertFellow> listProfileFellowExpert(String userId);

}
