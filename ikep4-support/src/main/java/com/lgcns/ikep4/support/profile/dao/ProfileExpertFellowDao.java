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
import com.lgcns.ikep4.support.profile.model.ProfileExpertFellow;

/**
 * 프로파일에서 Expert 전문가 목록을 가져 오는 Interface
 *
 * @author 이형운
 * @version $Id: ProfileExpertFellowDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface ProfileExpertFellowDao extends GenericDao<ProfileExpertFellow, String> {

	/**
	 * 관심분야 전문가 검색 10명 매칭 점수 순 으로 관련 전문과 목록을 조회
	 * @param userId 조회할 사용자 ID
	 * @return 관심분야 같은 관련 전문가 리스트
	 */
	public List<ProfileExpertFellow> listProfileFellowExpert(String userId);
	
}
