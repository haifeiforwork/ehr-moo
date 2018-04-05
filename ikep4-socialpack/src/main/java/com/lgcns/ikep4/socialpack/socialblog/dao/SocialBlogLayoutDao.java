/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayout;

/**
 * 소셜 블로그  포틀릿_레이아웃 DAO Interface
 *
 * @author 이형운 
 * @version $Id: SocialBlogLayoutDao.java 16246 2011-08-18 04:48:28Z giljae $
 */
public interface SocialBlogLayoutDao extends GenericDao<SocialBlogLayout, String> {
	
	/**
	 * 소셜 블로그  포틀릿 레이아웃 리스트 조회
	 * @return 블로그 포틀릿의 레이아웃 리스트
	 */
	public List<SocialBlogLayout> listSocialBlogLayout();
	
	/**
	 * 소셜 블로그  포틀릿 레이아웃 리스트 건수 조회
	 * @return 블로그 포틀릿 레이아웃 리스트 건수
	 */
	public Integer countSocialBlogLayout();
	
	/**
	 * 초기 생성시 사용하게 되는 디폴트 레이아웃 값을 반환
	 * @return 초기 생성 디폴트 포틀릿
	 */
	public SocialBlogLayout getDefaultLayout();

	/**
	 * 해당 포들릿의 디폴트 유무를 확인 하는 메서드
	 * @param layoutId 레이아웃 ID
	 * @return 해당 레이아웃 포틸릿의 디폴트 유무
	 */
	public boolean isDefaultLayout(String layoutId);
	
	/**
	 * 포틀릿 레이아웃 삭제
	 * @param layoutId 레이아웃 ID
	 */
	public void physicalDelete(String layoutId);
}
