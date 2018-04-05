/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItem;
import com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardItemSearchCondition;

/**
 * 소셜블로그 게시글 관련 DAO Impl
 *
 * @author 이형운
 * @version $Id: SocialBoardItemDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository("socialBoardItemDao")
public class SocialBoardItemDaoImpl extends GenericDaoSqlmap<SocialBoardItem, String> implements SocialBoardItemDao {

	/**
	 * SOCIAL_BOARD_ITEM_NAME_SPACE
	 */
	private static final String SOCIAL_BOARD_ITEM_NAME_SPACE = "socialpack.socialblog.dao.SocialBoardItem.";
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemDao#listBySearchCondition(com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardItemSearchCondition)
	 */
	public List<SocialBoardItem> listBySearchCondition(SocialBoardItemSearchCondition socialBoardItemSearchCondition) {
		return sqlSelectForList(SOCIAL_BOARD_ITEM_NAME_SPACE + "selectItemBySearchCondition", socialBoardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemDao#countBySearchCondition(com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardItemSearchCondition)
	 */
	public Integer countBySearchCondition(SocialBoardItemSearchCondition socialBoardItemSearchCondition) {
		return (Integer) sqlSelectForObject(SOCIAL_BOARD_ITEM_NAME_SPACE + "selectItemCountBySearchCondition", socialBoardItemSearchCondition);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(SocialBoardItem socialBoardItem) {
		return (String) sqlInsert(SOCIAL_BOARD_ITEM_NAME_SPACE + "create", socialBoardItem);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(SocialBoardItem socialBoardItem) {
		sqlUpdate(SOCIAL_BOARD_ITEM_NAME_SPACE + "update", socialBoardItem);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemDao#updateRecommendCount(java.lang.String)
	 */
	public void updateRecommendCount(String itemId) {
		sqlUpdate(SOCIAL_BOARD_ITEM_NAME_SPACE + "updateRecommendCount", itemId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemDao#updateLinereplyCount(java.lang.String)
	 */
	public void updateLinereplyCount(String itemId) {
		sqlUpdate(SOCIAL_BOARD_ITEM_NAME_SPACE + "updateLinereplyCount", itemId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemDao#updateMailCount(java.lang.String)
	 */
	public void updateMailCount(String itemId) {
		sqlUpdate(SOCIAL_BOARD_ITEM_NAME_SPACE + "updateMailCount", itemId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemDao#updateMBlogCount(java.lang.String)
	 */
	public void updateMBlogCount(String itemId) {
		sqlUpdate(SOCIAL_BOARD_ITEM_NAME_SPACE + "updateMBlogCount", itemId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemDao#updateHgitCount(java.lang.String)
	 */
	public void updateHitCount(String itemId) {
		sqlUpdate(SOCIAL_BOARD_ITEM_NAME_SPACE + "updateHitCount", itemId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemDao#physicalDelete(java.lang.String)
	 */
	public void physicalDelete(String itemId) {
		sqlDelete(SOCIAL_BOARD_ITEM_NAME_SPACE + "physicalDelete", itemId);
	}
	
	
	/**
	 * 블로그 글에 추천 정보 확인
	 * @param socialBoardItemRecommend
	 * @return
	 */
	public SocialBoardItem get(SocialBoardItem socialBoardItem){
		return (SocialBoardItem) sqlSelectForObject(SOCIAL_BOARD_ITEM_NAME_SPACE + "get", socialBoardItem);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#selectPostingDate(java.lang.Object)
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	public List<String> selectPostingDate(SocialBoardItemSearchCondition socialBoardItemSearchCondition) {
		return (List)sqlSelectForListOfObject(SOCIAL_BOARD_ITEM_NAME_SPACE + "selectPostingDate", socialBoardItemSearchCondition);
	}
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public SocialBoardItem get(String itemId) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(String id) {
		return false;
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
	}

}
