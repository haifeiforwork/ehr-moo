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
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemReferenceDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemReference;

/**
 * 소셜블로그 블로깅 조회 직접 접속 정보 DAO Impl Class
 *
 * @author 이형운
 * @version $Id: SocialBoardItemReferenceDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository("socialBoardItemReferenceDao")
public class SocialBoardItemReferenceDaoImpl extends GenericDaoSqlmap<SocialBoardItemReference, String> implements
		SocialBoardItemReferenceDao {

	/**
	 * SOCIAL_BOARD_ITEM_REFERENCE_NAME_SPACE
	 */
	private static final String SOCIAL_BOARD_ITEM_REFERENCE_NAME_SPACE = "socialpack.socialblog.dao.SocialBoardItemReference.";
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemReferenceDao#get(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemReference)
	 */
	public SocialBoardItemReference get(SocialBoardItemReference socialBoardItemReference) {
		return (SocialBoardItemReference) sqlSelectForObject(SOCIAL_BOARD_ITEM_REFERENCE_NAME_SPACE + "get", socialBoardItemReference);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemReferenceDao#exists(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemReference)
	 */
	public boolean exists(SocialBoardItemReference socialBoardItemReference) {
		Integer count = (Integer) sqlSelectForObject(SOCIAL_BOARD_ITEM_REFERENCE_NAME_SPACE + "exists", socialBoardItemReference);
		return count > 0;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(SocialBoardItemReference socialBoardItemReference) {
		return (String) sqlInsert(SOCIAL_BOARD_ITEM_REFERENCE_NAME_SPACE + "create", socialBoardItemReference);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemReferenceDao#physicalDelete(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemReference)
	 */
	public void physicalDelete(SocialBoardItemReference socialBoardItemReference) {
		sqlDelete(SOCIAL_BOARD_ITEM_REFERENCE_NAME_SPACE + "physicalDelete", socialBoardItemReference);
	}
	

	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public SocialBoardItemReference get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(SocialBoardItemReference object) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

}