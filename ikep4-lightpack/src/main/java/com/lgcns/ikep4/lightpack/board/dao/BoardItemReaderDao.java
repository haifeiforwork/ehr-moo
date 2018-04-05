/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItemReader;
import com.lgcns.ikep4.lightpack.board.search.BoardItemReaderSearchCondition;

/**
 * 게시판 독서자 관련 DAO
 * 
 */
public interface BoardItemReaderDao extends GenericDao<BoardItemReader, String> {

	
	public Integer readerFlag(BoardItemReader boardItemReader);
	
	
	public Integer selectReaderCount(BoardItemReader boardItemReader);
	
	public void deleteReader(String id);
	
	public List<BoardItemReader> listBoardItemReader(String  boardItemId);
	
	public Integer countReaderBySearchCondition(BoardItemReaderSearchCondition searchCondtion);
	
	public Integer countBoardBySearchCondition(BoardItemReaderSearchCondition searchCondtion);
	
	public Integer countNoReaderBySearchCondition(BoardItemReaderSearchCondition searchCondtion);
	
	public Integer checkReaderBySearchCondition(BoardItemReaderSearchCondition searchCondtion);
	
	public List<BoardItemReader> listReaderBySearchCondition(BoardItemReaderSearchCondition searchCondtion);
	
	public List<BoardItemReader> listBoardBySearchCondition(BoardItemReaderSearchCondition searchCondtion);
	
	public List<BoardItemReader> listNoReaderBySearchCondition(BoardItemReaderSearchCondition searchCondtion);
	
	public List<BoardItemReader> listReaderAllMail(String  boardItemId);
	
	public List<BoardItemReader> listBoardItemAllNoti();
	
	public void updateMailWaitYn(String itemId);
	
	public List<BoardItemReader> listBoardItemAllNotiInstant(String itemId);
	
}
