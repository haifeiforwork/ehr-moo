/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.dao;

import com.lgcns.ikep4.collpack.kms.board.model.BoardReference;
import com.lgcns.ikep4.collpack.kms.board.model.BoardReferencePK;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * 게시글 조회  DAO
 * 
 * 댓글 IKEP4_BD_REFERENCE 테이블 CRUD와  목록조회등 DB와의 연동을 담당하는 클래스이다.
 *
 * @author ${user}
 * @version $$Id: BoardReferenceDao.java 16236 2011-08-18 02:48:22Z giljae $$
 */
public interface BoardReferenceDao extends GenericDao<BoardReference, BoardReferencePK> {
	/**
	 * 게시물 ID의 조회정보 삭제
	 * @param itemId
	 */
	void removeByItemId(String itemId);
}
