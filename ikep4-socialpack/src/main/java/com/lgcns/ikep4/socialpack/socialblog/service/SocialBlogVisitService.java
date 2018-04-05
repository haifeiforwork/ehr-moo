/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogVisit;

/**
 * 소셜 블로그 방문자 정보용 Service Interface
 *
 * @author 이형운
 * @version $Id: SocialBlogVisitService.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Transactional
public interface SocialBlogVisitService extends GenericService<SocialBlogVisit, String> {

	/**
	 * 방문자 목록을 조회 해 온다.
	 * @param socialBlogVisit 블로그 방문자 정보 객체
	 * @return 방문자 정보 리스트
	 */
	public List<SocialBlogVisit> selectAllByblogId(SocialBlogVisit socialBlogVisit);
	
	/**
	 * 방문자 수를 조회해 온다.
	 * @param socialBlogVisit 블로그 방문자 정보 객체
	 * @return 방문자 수
	 */
	public Integer selectAllCountByblogId(SocialBlogVisit socialBlogVisit);
	
	/**
	 * 방문자 정보 조회
	 * @param socialBlogVisit 블로그 방문자 정보 객체
	 * @return 블로그 방문자 정보 객체
	 */
	public SocialBlogVisit get(SocialBlogVisit socialBlogVisit);
	
	/**
	 * 개별 방문 로그를 지운다.
	 * @param socialBlogVisit 블로그 방문자 정보 객체
	 */
	public void physicalDelete(SocialBlogVisit socialBlogVisit);
	
	/**
	 * 해당 블로그에 전체 방문자 로그를 지운다.
	 * @param socialBlogVisit 블로그 방문자 정보 객체
	 */
	public void physicalDeleteThreadByBlogId(SocialBlogVisit socialBlogVisit);
	
	/**
	 * 하루에 한번만 방문자 기록을 한다
	 * @param socialBlogVisit 블로그 방문자 정보 객체
	 */
	public void recordSocialBlogVisitByDay(SocialBlogVisit socialBlogVisit);
	
	/**
	 * 해당 블로그의 방문자 관리 정보를 조회 한다.
	 * @param socialBlogVisit 블로그 방문자 정보 객체
	 * @return 블로그 방문자 리스트
	 */
	public List<SocialBlogVisit> getVisitorManage(SocialBlogVisit socialBlogVisit);
	
}
