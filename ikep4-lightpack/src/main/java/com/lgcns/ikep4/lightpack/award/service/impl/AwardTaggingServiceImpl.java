/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.award.dao.AwardDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardItemDao;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.search.RelatedAwardItemSearchCondition;
import com.lgcns.ikep4.lightpack.award.service.AwardTaggingService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.service.TagService;

/**
 * TODO Javadoc주석작성
 *
 * @author 占쏙옙占쏙옙占쏙옙
 * @version $Id: AwardTaggingServiceImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Service
public class AwardTaggingServiceImpl implements AwardTaggingService {
	@Autowired
	private TagService tagService;

	@Autowired
	private AwardItemDao awardItemDao;


	@Autowired
	private AwardDao awardDao;

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardTaggingService#listRelatedAwardItemBySearchCondition(com.lgcns.ikep4.framework.web.SearchCondition)
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<AwardItem> listRelatedAwardItemBySearchCondition(RelatedAwardItemSearchCondition searchCondition) {
		//관련 글 목록 및 카운터 정보를 가져온다.
		Map<String, Object> qnaIdMap = this.tagService.listItemId(searchCondition.getItemId(),  TagConstants.ITEM_TYPE_BBS, searchCondition.getPageIndex(), searchCondition.getPagePerRecord());

		//키 목록을 가져온다.
		List<String> relatedItemIdList = (List<String>) qnaIdMap.get("list");

		//갯수를 가져온다.
		int count = (Integer)qnaIdMap.get("count");

		searchCondition.terminateSearchCondition(count);

		SearchResult<AwardItem> searchResult = null;


		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<AwardItem>(searchCondition);

		} else {
			//관련 게시글 ID 배열을 이용해서 관련 게시글을 가져온다.
			List<AwardItem> relatedAwardItemList = this.awardItemDao.listByItemIdArray(relatedItemIdList);

			//게시글의 게시판 정보를 넣는다.
			for(AwardItem awardItem : relatedAwardItemList) {
				awardItem.setAward(this.awardDao.get(awardItem.getAwardId()));
			}

			searchResult = new SearchResult<AwardItem>(relatedAwardItemList, searchCondition);
		}

		return searchResult;
	}

}
