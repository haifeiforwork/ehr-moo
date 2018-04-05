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
import com.lgcns.ikep4.support.profile.model.Career;


/**
 * 경력 관리 Service Interface
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: CareerService.java 16273 2011-08-18 07:07:47Z giljae $
 */
@Transactional
public interface CareerService extends GenericService<Career, String> {
	
	/**
	 * 모든 경력 목록  반환
	 * @param career 경력 정보 객체
	 * @return 전체 경력 목록 반환
	 */
	public List<Career> list(Career career);
	
	/**
	 * 최근 5개 경력 목록 반환
	 * @param career 경력 정보 객체
	 * @return 최근 5개 경력 정보 목록
	 */
	public List<Career> listRecent5(Career career);
}
