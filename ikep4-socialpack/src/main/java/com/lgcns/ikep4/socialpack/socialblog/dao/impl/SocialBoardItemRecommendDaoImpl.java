/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemRecommendDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemRecommend;

/**
 * 소셜블로그 블로깅 추천 정보 DAO Implement
 *
 * @author 이형운
 * @version $Id: SocialBoardItemRecommendDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository("socialBoardItemRecommendDao")
public class SocialBoardItemRecommendDaoImpl extends GenericDaoSqlmap<SocialBoardItemRecommend, String> implements SocialBoardItemRecommendDao {

	/**
	 * SOCIAL_BOARD_ITEM_RECOMMEND_NAME_SPACE
	 */
	private static final String SOCIAL_BOARD_ITEM_RECOMMEND_NAME_SPACE = "socialpack.socialblog.dao.SocialBoardItemRecommend.";
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(SocialBoardItemRecommend socialBoardItemRecommend) {
		Integer count = (Integer) sqlSelectForObject(SOCIAL_BOARD_ITEM_RECOMMEND_NAME_SPACE + "exists", socialBoardItemRecommend);
		return count > 0;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(SocialBoardItemRecommend socialBoardItemRecommend) {
		return (String) sqlInsert(SOCIAL_BOARD_ITEM_RECOMMEND_NAME_SPACE + "create", socialBoardItemRecommend);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemRecommendDao#physicalDelete(java.lang.Object)
	 */
	public void physicalDelete(SocialBoardItemRecommend socialBoardItemRecommend) {
		sqlDelete(SOCIAL_BOARD_ITEM_RECOMMEND_NAME_SPACE + "physicalDelete", socialBoardItemRecommend);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemRecommendDao#get(java.lang.Object)
	 */
	public SocialBoardItemRecommend get(SocialBoardItemRecommend socialBoardItemRecommend) {
		return (SocialBoardItemRecommend) sqlSelectForObject(SOCIAL_BOARD_ITEM_RECOMMEND_NAME_SPACE + "get", socialBoardItemRecommend);
	}	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public SocialBoardItemRecommend get(String id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(SocialBoardItemRecommend object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}
