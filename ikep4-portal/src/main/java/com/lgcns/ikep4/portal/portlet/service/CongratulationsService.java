/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.portlet.service;

import java.util.List;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.portlet.model.Congratulations;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 포틀릿 축하합니다 관리 서비스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: CongratulationsService.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface CongratulationsService extends GenericService<Congratulations, String> {
	
	/**
	 * 축하합니다 포틀릿의  리스트
	 * @param portletConfigId 포틀릿 설정 아이디
	 * @param user 사용자 정보
	 * @return List<Congratulations> 축하합니다 포틀릿의  리스트
	 */
	public List<Congratulations> list(String portletConfigId, User user);
	
	/**
	 * 축하합니다 포틀릿의 설정 정보
	 * @param portletConfigId 포틀릿 설정 아이디
	 * @param userId 사용자 아이디
	 * @return Congratulations 축하합니다 포틀릿의 설정 정보
	 */
	public Congratulations getCongratulationsConfig(String portletConfigId, String userId, String propertyName);

	/**
	 * 축하합니다 포틀릿의 설정 저장
	 * @param congratulations 설정 정보
	 * @return String 포틀릿 설정 아이디
	 */
	public String saveCongratulationsConfig(Congratulations congratulations);
	
}
