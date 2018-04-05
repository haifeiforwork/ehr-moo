/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.board.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.cafe.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.cafe.board.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.cafe.board.search.RelatedBoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.cafe.board.service.BoardTaggingService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.service.TagService;


/**
 * TODO Javadoc주석작성
 * 
 * @author 占쏙옙占쏙옙占쏙옙
 * @version $Id: BoardTaggingServiceImpl.java 14298 2011-06-03 02:29:54Z jghong
 *          $
 */
@Service("cfBoardTaggingServiceImpl")
@Transactional
public class BoardTaggingServiceImpl implements BoardTaggingService {

	@Autowired
	private TagService tagService;

	@Autowired
	private BoardItemDao boardItemDao;

	@Autowired
	private BoardDao boardDao;

	/**
	 * 관련들 목록
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<BoardItem> listRelatedBoardItemBySearchCondition(RelatedBoardItemSearchCondition searchCondition) {
		// 관련 글 목록 및 카운터 정보를 가져온다.
		Map<String, Object> qnaIdMap = this.tagService.listItemId(searchCondition.getItemId(),
				TagConstants.ITEM_TYPE_CAFE, searchCondition.getPageIndex(), searchCondition.getPagePerRecord());

		// 갯수를 가져온다.
		int count = (Integer) qnaIdMap.get("count");

		searchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(searchCondition);

		} else {
			// 관련 게시글 ID 배열을 이용해서 관련 게시글을 가져온다.

			Map<String, Object> params = new HashMap<String, Object>();
			// 키 목록을 가져온다.
			params.put("itemIds", (List<String>) qnaIdMap.get("list"));	//relatedItemIdList
			params.put("userId", searchCondition.getUserId());
			
			List<BoardItem> relatedBoardItemList = this.boardItemDao.listByItemIdArray(params);

			// 게시글의 게시판 정보를 넣는다.
			for (BoardItem boardItem : relatedBoardItemList) {
				boardItem.setBoard(this.boardDao.get(boardItem.getBoardId()));
			}

			searchResult = new SearchResult<BoardItem>(relatedBoardItemList, searchCondition);
		}

		return searchResult;
	}

}
