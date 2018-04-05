/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardDao;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao;
import com.lgcns.ikep4.collpack.kms.board.model.Board;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItem;
import com.lgcns.ikep4.collpack.kms.board.search.RelatedBoardItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.board.service.BoardTaggingService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.service.TagService;


/**
 * TODO Javadoc주석작성
 * 
 * @author
 * @version $Id: BoardTaggingServiceImpl.java 10431 2011-05-11 09:17:20Z
 *          designtker $
 */
@Service("kmsBoardTaggingServiceImpl")
// @Transactional
public class BoardTaggingServiceImpl implements BoardTaggingService {
	@Autowired
	private TagService tagService;

	@Autowired
	private BoardItemDao boardItemDao;

	@Autowired
	private BoardDao boardDao;

	/**
	 * 관련글 목록
	 */
	public SearchResult<BoardItem> listRelatedBoardItemBySearchCondition(
			RelatedBoardItemSearchCondition searchCondition) {

		// 관련글 갯수를 가져온다.
		int count = this.boardItemDao.relatedCountBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		
		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(searchCondition);
		} else {
			List<BoardItem> boardItemList = this.boardItemDao.listRelatedListBySearchCondition(searchCondition);

			List<FileData> fileDataList = null;

			searchResult = new SearchResult<BoardItem>(boardItemList, searchCondition);
		}

		return searchResult;
	}
	
	/**
	 * 관련글 목록
	 */
	public List<BoardItem> listRelatedBoardItemBySearchCondition(Map<String, String> map) {

		return this.boardItemDao.listRelatedListBySearchCondition(map);
	}
}
