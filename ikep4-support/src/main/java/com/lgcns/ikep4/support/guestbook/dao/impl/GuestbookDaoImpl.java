/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.guestbook.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.guestbook.dao.GuestbookDao;
import com.lgcns.ikep4.support.guestbook.model.Guestbook;
import com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition;

/**
 * 방명록 게시글 조회용 DaoImpl
 *
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: GuestbookDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository("guestbookDao")
public class GuestbookDaoImpl extends GenericDaoSqlmap<Guestbook, String> implements GuestbookDao {

	
	/**
	 * GUESTBOOK_NAME_SPACE
	 */
	private static final String GUESTBOOK_NAME_SPACE = "support.guestbook.dao.Guestbook.";
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.dao.impl.GuestbookDao#selectAll(com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition)
	 */
	public List<Guestbook> selectAll(GuestbookSearchCondition guestbookSearch) {
		return sqlSelectForList(GUESTBOOK_NAME_SPACE+"selectGuestbookAll",guestbookSearch);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.dao.impl.GuestbookDao#selectGuestbook(com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition)
	 */
	public Guestbook selectGuestbook(GuestbookSearchCondition guestbookSearch) {
		return (Guestbook) sqlSelectForObject(GUESTBOOK_NAME_SPACE+"selectGuestbook", guestbookSearch);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.dao.impl.GuestbookDao#get(com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition)
	 */
	public List<Guestbook> selectGuestbookByGroup(GuestbookSearchCondition guestbookSearch) {
		return sqlSelectForList(GUESTBOOK_NAME_SPACE+"selectGuestbookByGroup", guestbookSearch);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.dao.impl.GuestbookDao#selectAllMore(com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition)
	 */
	public List<Guestbook> selectAllMore(GuestbookSearchCondition guestbookSearch) {
		return sqlSelectForList(GUESTBOOK_NAME_SPACE+"selectGuestbookMore",guestbookSearch);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.dao.impl.GuestbookDao#get(com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition)
	 */
	public List<Guestbook> selectGuestbookByChild(GuestbookSearchCondition guestbookSearch) {
		return sqlSelectForList(GUESTBOOK_NAME_SPACE+"selectGuestbookByChild", guestbookSearch);
	}
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.dao.impl.GuestbookDao#exists(com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition)
	 */
	public Integer selectAllCount(GuestbookSearchCondition guestbookSearch) {
		return (Integer)sqlSelectForObject(GUESTBOOK_NAME_SPACE+"selectCountGuestbookAll", guestbookSearch);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		Integer count = (Integer) sqlSelectForObject(GUESTBOOK_NAME_SPACE+"selectCountGuestbook", id);
		return (count > 0);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(com.lgcns.ikep4.support.guestbook.model.Guestbook)
	 */
	public String create(Guestbook guestbook) {
		return (String) sqlInsert(GUESTBOOK_NAME_SPACE+"insertGuestbook", guestbook);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.dao.impl.GuestbookDao#updateStep(java.lang.String,int,java.lang.String)
	 */
	public void updateStep(String groupId, int step, String targetUserId) {
		Guestbook guestbook = new Guestbook();
		guestbook.setGroupId(groupId);
		guestbook.setStep(step);
		guestbook.setTargetUserId(targetUserId);
		
		sqlUpdate(GUESTBOOK_NAME_SPACE+"updateStep",guestbook);
		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.dao.impl.GuestbookDao#removeGuestbook(java.io.Serializable)
	 */
	public void removeGuestbook(String guestbookId) {
		sqlDelete(GUESTBOOK_NAME_SPACE+"deleteGuestbook", guestbookId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.dao.impl.GuestbookDao#readPortletPropertyValue(java.util.Map)
	 */
	public String readPortletPropertyValue(Map<String, Object> map) {
		return (String) sqlSelectForObject(GUESTBOOK_NAME_SPACE+"readPortletPropertyValue", map);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public Guestbook get(String id) {
		return (Guestbook) sqlSelectForObject(GUESTBOOK_NAME_SPACE+"selectGuestbook", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(com.lgcns.ikep4.support.guestbook.model.Guestbook)
	 */
	@Deprecated
	public void update(Guestbook guestbook) {
		sqlUpdate(GUESTBOOK_NAME_SPACE+"updateGuestbook", guestbook);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
		sqlDelete(GUESTBOOK_NAME_SPACE+"deleteGuestbook", id);

	}

}
