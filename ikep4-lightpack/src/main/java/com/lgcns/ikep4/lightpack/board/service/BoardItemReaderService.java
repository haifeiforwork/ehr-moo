/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardItemCategory;
import com.lgcns.ikep4.lightpack.board.model.BoardItemReader;
import com.lgcns.ikep4.lightpack.board.model.BoardLinereply;
import com.lgcns.ikep4.lightpack.board.model.BoardRecommend;
import com.lgcns.ikep4.lightpack.board.model.BoardReference;
import com.lgcns.ikep4.lightpack.board.search.BoardItemReaderSearchCondition;
import com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 게시글 독서자 클래스
 */
@Transactional
public interface BoardItemReaderService{

	void createBoardItemReaderList(List<BoardItemReader> boardItemReaderList);
	
	String createBoardItemReader(BoardItemReader boardItemReader);
	
	Integer readerFlag(BoardItemReader boardItemReader);
	
	Integer selectReaderCount(BoardItemReader boardItemReader);
	
	void deleteReader(String id);
	
	 List<BoardItemReader> listBoardItemReader(String  boardItemId);
	 
	SearchResult<BoardItemReader> listReaderBySearchCondition(BoardItemReaderSearchCondition boardItemReaderSearchCondition);
	
	SearchResult<BoardItemReader> listBoardBySearchCondition(BoardItemReaderSearchCondition boardItemReaderSearchCondition);
	
	 List<BoardItemReader> listReaderAllMail(String  boardItemId);
	 
	 List<BoardItemReader> listBoardItemAllNoti();
	 
	 void updateMailWaitYn(String itemId);
	 
	 List<BoardItemReader> listBoardItemAllNotiInstant(String itemId);
}
