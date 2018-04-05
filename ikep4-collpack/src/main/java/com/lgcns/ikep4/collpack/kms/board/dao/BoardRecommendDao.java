/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.dao;

import com.lgcns.ikep4.collpack.kms.board.model.BoardRecommend;
import com.lgcns.ikep4.collpack.kms.board.model.BoardRecommendPK;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * 게시글 추천 DAO
 * 
 * 댓글 IKEP4_BD_RECOMMEND 테이블 CRUD와  목록조회등 DB와의 연동을 담당하는 클래스이다.
 *
 * @author ${user}
 * @version $$Id: BoardRecommendDao.java 16236 2011-08-18 02:48:22Z giljae $$
 */
public interface BoardRecommendDao extends GenericDao<BoardRecommend, BoardRecommendPK> {
	/**
	 * 게시물 ID의 추천정보 삭제
	 * @param itemId
	 */
	void removeByItemId(String itemId);
	
	public boolean scoreExists(BoardRecommendPK id);
	
	public BoardRecommendPK insertScore(BoardRecommend object);

}
