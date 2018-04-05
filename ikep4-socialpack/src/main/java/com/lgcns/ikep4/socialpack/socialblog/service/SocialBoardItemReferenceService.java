/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.service;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemReference;

/**
 * 소셜블로그 블로깅 조회 직접 접속 정보 Service Interface
 * (현재는 페이지 직접 접속 링크를 통한 접속일때만 카운터 함)
 *
 * @author 이형운
 * @version $Id: SocialBoardItemReferenceService.java 16246 2011-08-18 04:48:28Z giljae $
 */
public interface SocialBoardItemReferenceService extends GenericService<SocialBoardItemReference, String> {

	/**
	 * 소셜 블로그 블로깅 조회 직접 접속 정보 기록
	 * @param socialBoardItemReference 블로깅 조회 직접 접속 정보 객체
	 */
	public void registerReference(SocialBoardItemReference socialBoardItemReference);
	
	/**
	 * 소셜블로그 블로깅 조회 직접 접속 정보 존재 여부 확인
	 * @param socialBoardItemReference 블로깅 조회 직접 접속 정보 객체
	 * @return 블로깅 조회 직접 접속 정보 존재 유무
	 */
	public boolean exists(SocialBoardItemReference socialBoardItemReference);
	
	/**
	 * 소셜블로그 블로깅 조회 직접 접속 정보  삭제
	 * @param socialBoardItemReference 블로깅 조회 직접 접속 정보 객체
	 */
	public void physicalDelete(SocialBoardItemReference socialBoardItemReference);
	
	/**
	 * 블로그 블로깅 조회 직접 접속 정보 확인
	 * @param socialBoardItemReference 블로깅 조회 직접 접속 정보 객체
	 * @return 블로깅 조회 직접 접속 정보 객체
	 */
	public SocialBoardItemReference get(SocialBoardItemReference socialBoardItemReference);
	
}
