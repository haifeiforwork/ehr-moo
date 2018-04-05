/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.service;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.search.RelatedBoardItemSearchCondition;

/**
 * 게시판 태그 관련 서비스 클래스
 *
 * @author 최현식
 * @version $Id: BoardTaggingService.java 16240 2011-08-18 04:08:15Z giljae $
 */
public interface BoardTaggingService {
	/**
	 * 관련글을 가져온다.
	 *
	 * @param boardLinereplySearchCondition 댓글  검색조건
	 * @return 댓글 목록
	 */
	SearchResult<BoardItem> listRelatedBoardItemBySearchCondition(RelatedBoardItemSearchCondition searchCondition);

}
