/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.gallery.dao;

import com.lgcns.ikep4.lightpack.gallery.model.BoardReference;
import com.lgcns.ikep4.lightpack.gallery.model.BoardReferencePK;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

// TODO: Auto-generated Javadoc
/**
 * 게시글 조회  DAO
 * 
 * 댓글 IKEP4_BD_REFERENCE 테이블 CRUD와  목록조회등 DB와의 연동을 담당하는 클래스이다.
 *
 * @author ${user}
 * @version $$Id: BoardReferenceDao.java 12153 2011-05-19 12:17:26Z designtker $$
 */
public interface BoardReferenceDao extends GenericDao<BoardReference, BoardReferencePK> {

	/**
	 * 게시판 조회 정보를 삭제한다.
	 *
	 * @param itemId 게시글 ID
	 */
	void removeByItemId(String itemId);
}
