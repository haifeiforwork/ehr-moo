/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardLinereplyDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardLinereply;
import com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardLinereplySearchCondition;

/**
 * 소셜블로그 게시글 관련 DAO Impl
 *
 * @author 이형운
 * @version $Id: SocialBoardLinereplyDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository("socialBoardLinereplyDao")
public class SocialBoardLinereplyDaoImpl extends GenericDaoSqlmap<SocialBoardLinereply, String> implements
		SocialBoardLinereplyDao {

	/**
	 * SOCIAL_BOARD_LINEREPLY_NAME_SPACE
	 */
	private static final String SOCIAL_BOARD_LINEREPLY_NAME_SPACE = "socialpack.socialblog.dao.SocialBoardLinereply.";
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public SocialBoardLinereply get(String linereplyId) {
		return (SocialBoardLinereply) sqlSelectForObject(SOCIAL_BOARD_LINEREPLY_NAME_SPACE + "get", linereplyId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String linereplyId) {
		Integer count = (Integer) sqlSelectForObject(SOCIAL_BOARD_LINEREPLY_NAME_SPACE + "exists", linereplyId);
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardLinereplyDao#listBySearchCondition(com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardLinereplySearchCondition)
	 */
	public List<SocialBoardLinereply> listBySearchCondition(
			SocialBoardLinereplySearchCondition socialBoardLinereplySearchCondition) {
		return sqlSelectForList(SOCIAL_BOARD_LINEREPLY_NAME_SPACE + "listBySearchCondition", socialBoardLinereplySearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardLinereplyDao#countBySearchCondition(com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardLinereplySearchCondition)
	 */
	public Integer countBySearchCondition(SocialBoardLinereplySearchCondition socialBoardLinereplySearchCondition) {
		return (Integer)sqlSelectForObject(SOCIAL_BOARD_LINEREPLY_NAME_SPACE + "countBySearchCondition", socialBoardLinereplySearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardLinereplyDao#countChildren(java.lang.String)
	 */
	public Integer countChildren(String linereplyId) {
		return (Integer)sqlSelectForObject(SOCIAL_BOARD_LINEREPLY_NAME_SPACE + "countChildren", linereplyId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(SocialBoardLinereply socialBoardLinereply) {
		sqlInsert(SOCIAL_BOARD_LINEREPLY_NAME_SPACE + "create", socialBoardLinereply);
		return socialBoardLinereply.getLinereplyId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(SocialBoardLinereply socialBoardLinereply) {
		sqlUpdate(SOCIAL_BOARD_LINEREPLY_NAME_SPACE + "update", socialBoardLinereply);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardLinereplyDao#updateIndentation(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardLinereply)
	 */
	public void updateStep(SocialBoardLinereply socialBoardLinereply) {
		sqlUpdate(SOCIAL_BOARD_LINEREPLY_NAME_SPACE + "updateStep", socialBoardLinereply);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardLinereplyDao#logicalDelete(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardLinereply)
	 */
	public void logicalDelete(SocialBoardLinereply socialBoardLinereply) {
		sqlUpdate(SOCIAL_BOARD_LINEREPLY_NAME_SPACE + "logicalDelete", socialBoardLinereply); 
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardLinereplyDao#physicalDelete(java.lang.String)
	 */
	public void physicalDelete(String linereplyId) {
		sqlDelete(SOCIAL_BOARD_LINEREPLY_NAME_SPACE + "physicalDelete", linereplyId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardLinereplyDao#physicalDeleteThreadByLinereplyId(java.lang.String)
	 */
	public void physicalDeleteThreadByLinereplyId(String linereplyId) {
		sqlDelete(SOCIAL_BOARD_LINEREPLY_NAME_SPACE + "physicalDeleteThreadByLinereplyId", linereplyId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardLinereplyDao#physicalDeleteThreadByItemId(java.lang.String)
	 */
	public void physicalDeleteThreadByItemId(String itemId) {
		sqlDelete(SOCIAL_BOARD_LINEREPLY_NAME_SPACE + "physicalDeleteThreadByItemId", itemId); 
	}

	public List<SocialBoardLinereply> listTopXLinereply(Map<String, Object> topXList) {
		return sqlSelectForList(SOCIAL_BOARD_LINEREPLY_NAME_SPACE + "listTopXLinereply", topXList);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

}
