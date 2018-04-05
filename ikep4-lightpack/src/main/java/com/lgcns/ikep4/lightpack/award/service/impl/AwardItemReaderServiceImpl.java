/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.service.impl;

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
import com.lgcns.ikep4.lightpack.award.dao.AwardDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardItemCategoryDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardItemReaderDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardLinereplyDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardRecommendDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardReferenceDao;
import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.model.AwardItemCategory;
import com.lgcns.ikep4.lightpack.award.model.AwardItemReader;
import com.lgcns.ikep4.lightpack.award.model.AwardRecommend;
import com.lgcns.ikep4.lightpack.award.model.AwardReference;
import com.lgcns.ikep4.lightpack.award.search.AwardItemReaderSearchCondition;
import com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition;
import com.lgcns.ikep4.lightpack.award.service.AwardItemCategoryService;
import com.lgcns.ikep4.lightpack.award.service.AwardItemReaderService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.tagfree.util.MimeUtil;

/**
 * AwardItemService 구현체 클래스
 */
@Service
public class AwardItemReaderServiceImpl implements AwardItemReaderService {
	/** The award item dao. */
	@Autowired
	private AwardItemReaderDao awardItemReaderDao;
	
	@Autowired
	private IdgenService idgenService;

	public void createAwardItemReaderList(List<AwardItemReader> awardItemReaderList) {
	
		if (awardItemReaderList != null) {
			for (AwardItemReader awardItemReader : awardItemReaderList) {

			awardItemReader.setItemId(this.idgenService.getNextId());
			 this.awardItemReaderDao.create(awardItemReader);
			}
		}

	}

	public String createAwardItemReader(AwardItemReader awardItemReader) {
		
		awardItemReader.setItemId(this.idgenService.getNextId());
		return  this.awardItemReaderDao.create(awardItemReader);

	}

	public Integer readerFlag(AwardItemReader awardItemReader) {
	
		return  this.awardItemReaderDao.readerFlag(awardItemReader);

	}
	
	public Integer selectReaderCount(AwardItemReader awardItemReader) {
		
		return  this.awardItemReaderDao.selectReaderCount(awardItemReader);

	}
	
	public void deleteReader(String id) {
	  this.awardItemReaderDao.deleteReader(id);
	}
	
	public List<AwardItemReader> listAwardItemReader(String  awardItemId) {
		return  this.awardItemReaderDao.listAwardItemReader(awardItemId);
	}
	
	public List<AwardItemReader> listReaderAllMail(String  awardItemId) {
		return  this.awardItemReaderDao.listReaderAllMail(awardItemId);
	}
	
	public SearchResult<AwardItemReader> listReaderBySearchCondition(AwardItemReaderSearchCondition searchCondtion) {
		Integer readerCnt = this.awardItemReaderDao.checkReaderBySearchCondition(searchCondtion);
		if(readerCnt > 0){
			Integer count = this.awardItemReaderDao.countReaderBySearchCondition(searchCondtion);

			searchCondtion.terminateSearchCondition(count);

			SearchResult<AwardItemReader> searchResult = null;
			if(searchCondtion.isEmptyRecord()) {
				searchResult = new SearchResult<AwardItemReader>(searchCondtion);

			} else {

				List<AwardItemReader> awardItemReaderList = this.awardItemReaderDao.listReaderBySearchCondition(searchCondtion);
				searchResult = new SearchResult<AwardItemReader>(awardItemReaderList, searchCondtion);
			}
			return searchResult;
		}else{
			Integer count = this.awardItemReaderDao.countNoReaderBySearchCondition(searchCondtion);

			searchCondtion.terminateSearchCondition(count);

			SearchResult<AwardItemReader> searchResult = null;
			if(searchCondtion.isEmptyRecord()) {
				searchResult = new SearchResult<AwardItemReader>(searchCondtion);

			} else {

				List<AwardItemReader> awardItemReaderList = this.awardItemReaderDao.listNoReaderBySearchCondition(searchCondtion);
				searchResult = new SearchResult<AwardItemReader>(awardItemReaderList, searchCondtion);
			}
			return searchResult;
		}
		

		
	}
	
	public SearchResult<AwardItemReader> listAwardBySearchCondition(AwardItemReaderSearchCondition searchCondtion) {
			
		Integer count = this.awardItemReaderDao.countAwardBySearchCondition(searchCondtion);

		searchCondtion.terminateSearchCondition(count);

		SearchResult<AwardItemReader> searchResult = null;
		if(searchCondtion.isEmptyRecord()) {
			searchResult = new SearchResult<AwardItemReader>(searchCondtion);

		} else {

			List<AwardItemReader> awardItemReaderList = this.awardItemReaderDao.listAwardBySearchCondition(searchCondtion);
			searchResult = new SearchResult<AwardItemReader>(awardItemReaderList, searchCondtion);
		}
		return searchResult;
	}
	
	public List<AwardItemReader> listAwardItemAllNoti(){	
		return  this.awardItemReaderDao.listAwardItemAllNoti();
	}
	
	public void updateMailWaitYn(String itemId) {
		
		this.awardItemReaderDao.updateMailWaitYn(itemId);
		
	}
	
	public List<AwardItemReader> listAwardItemAllNotiInstant(String itemId){	
		return  this.awardItemReaderDao.listAwardItemAllNotiInstant(itemId);
	}
}
