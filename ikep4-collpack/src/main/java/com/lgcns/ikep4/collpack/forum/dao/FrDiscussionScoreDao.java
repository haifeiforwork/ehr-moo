/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.dao;

import com.lgcns.ikep4.collpack.forum.model.FrDiscussionScore;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrDiscussionScoreDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface FrDiscussionScoreDao extends GenericDao<FrDiscussionScore, String>  {
	
	
	/**
	 * 전체 지우기
	 *
	 */
	public void removeAll();
	
	/**
	 * 정책 개수 만큼만 지우기
	 * @param portalId
	 */
	public void removeCount(int count);
	
	
	
}
