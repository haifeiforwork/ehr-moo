/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.util.MessageConstance;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemCategoryDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemReaderDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardLinereplyDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardRecommendDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardReferenceDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardItemCategory;
import com.lgcns.ikep4.lightpack.board.model.BoardItemReader;
import com.lgcns.ikep4.lightpack.board.model.BoardRecommend;
import com.lgcns.ikep4.lightpack.board.model.BoardReference;
import com.lgcns.ikep4.lightpack.board.search.BoardItemReaderSearchCondition;
import com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.board.service.BoardItemCategoryService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemReaderService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.tagfree.util.MimeUtil;

/**
 * BoardItemService 구현체 클래스
 */
@Service
public class BoardItemReaderServiceImpl implements BoardItemReaderService {
	/** The board item dao. */
	@Autowired
	private BoardItemReaderDao boardItemReaderDao;
	
	@Autowired
	private IdgenService idgenService;

	public void createBoardItemReaderList(List<BoardItemReader> boardItemReaderList) {
	
		if (boardItemReaderList != null) {
			for (BoardItemReader boardItemReader : boardItemReaderList) {

			boardItemReader.setItemId(this.idgenService.getNextId());
			 this.boardItemReaderDao.create(boardItemReader);
			}
		}

	}

	public String createBoardItemReader(BoardItemReader boardItemReader) {
		
		boardItemReader.setItemId(this.idgenService.getNextId());
		return  this.boardItemReaderDao.create(boardItemReader);

	}

	public Integer readerFlag(BoardItemReader boardItemReader) {
	
		return  this.boardItemReaderDao.readerFlag(boardItemReader);

	}
	
	public Integer selectReaderCount(BoardItemReader boardItemReader) {
		
		return  this.boardItemReaderDao.selectReaderCount(boardItemReader);

	}
	
	public void deleteReader(String id) {
	  this.boardItemReaderDao.deleteReader(id);
	}
	
	public List<BoardItemReader> listBoardItemReader(String  boardItemId) {
		return  this.boardItemReaderDao.listBoardItemReader(boardItemId);
	}
	
	public List<BoardItemReader> listReaderAllMail(String  boardItemId) {
		return  this.boardItemReaderDao.listReaderAllMail(boardItemId);
	}
	
	public SearchResult<BoardItemReader> listReaderBySearchCondition(BoardItemReaderSearchCondition searchCondtion) {
		Integer readerCnt = this.boardItemReaderDao.checkReaderBySearchCondition(searchCondtion);
		if(readerCnt > 0){
			Integer count = this.boardItemReaderDao.countReaderBySearchCondition(searchCondtion);

			searchCondtion.terminateSearchCondition(count);

			SearchResult<BoardItemReader> searchResult = null;
			if(searchCondtion.isEmptyRecord()) {
				searchResult = new SearchResult<BoardItemReader>(searchCondtion);

			} else {

				List<BoardItemReader> boardItemReaderList = this.boardItemReaderDao.listReaderBySearchCondition(searchCondtion);
				searchResult = new SearchResult<BoardItemReader>(boardItemReaderList, searchCondtion);
			}
			return searchResult;
		}else{
			Integer count = this.boardItemReaderDao.countNoReaderBySearchCondition(searchCondtion);

			searchCondtion.terminateSearchCondition(count);

			SearchResult<BoardItemReader> searchResult = null;
			if(searchCondtion.isEmptyRecord()) {
				searchResult = new SearchResult<BoardItemReader>(searchCondtion);

			} else {

				List<BoardItemReader> boardItemReaderList = this.boardItemReaderDao.listNoReaderBySearchCondition(searchCondtion);
				searchResult = new SearchResult<BoardItemReader>(boardItemReaderList, searchCondtion);
			}
			return searchResult;
		}
		

		
	}
	
	public SearchResult<BoardItemReader> listBoardBySearchCondition(BoardItemReaderSearchCondition searchCondtion) {
			
		Integer count = this.boardItemReaderDao.countBoardBySearchCondition(searchCondtion);

		searchCondtion.terminateSearchCondition(count);

		SearchResult<BoardItemReader> searchResult = null;
		if(searchCondtion.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItemReader>(searchCondtion);

		} else {

			List<BoardItemReader> boardItemReaderList = this.boardItemReaderDao.listBoardBySearchCondition(searchCondtion);
			searchResult = new SearchResult<BoardItemReader>(boardItemReaderList, searchCondtion);
		}
		return searchResult;
	}
	
	public List<BoardItemReader> listBoardItemAllNoti(){	
		return  this.boardItemReaderDao.listBoardItemAllNoti();
	}
	
	public void updateMailWaitYn(String itemId) {
		
		this.boardItemReaderDao.updateMailWaitYn(itemId);
		
	}
	
	public List<BoardItemReader> listBoardItemAllNotiInstant(String itemId){	
		return  this.boardItemReaderDao.listBoardItemAllNotiInstant(itemId);
	}
}
