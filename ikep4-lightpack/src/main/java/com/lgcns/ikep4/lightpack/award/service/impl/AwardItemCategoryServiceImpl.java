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
import com.lgcns.ikep4.lightpack.award.dao.AwardLinereplyDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardRecommendDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardReferenceDao;
import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardItemCategory;
import com.lgcns.ikep4.lightpack.award.model.AwardRecommend;
import com.lgcns.ikep4.lightpack.award.model.AwardReference;
import com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition;
import com.lgcns.ikep4.lightpack.award.service.AwardItemCategoryService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.tagfree.util.MimeUtil;

/**
 * AwardItemService 구현체 클래스
 */
@Service
public class AwardItemCategoryServiceImpl implements AwardItemCategoryService {
	/** The award item dao. */
	@Autowired
	private AwardItemCategoryDao awardItemCategoryDao;
	
	@Autowired
	private IdgenService idgenService;

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#listRecentAwardItem(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public List<AwardItemCategory> listCategoryAwardItem(AwardItemCategory categoryAwardId) {
		return this.awardItemCategoryDao.listCategoryAwardItem(categoryAwardId);
	}
	
	public void insertCategoryNm(List<AwardItemCategory> receiveCategoryNmList) {
		for (int i = 0; i < receiveCategoryNmList.size(); i++) {
			AwardItemCategory awardItemCategory = receiveCategoryNmList.get(i);
			String addNameList = awardItemCategory.getAddNameList();
			String delIdList   = awardItemCategory.getDelIdList();
			String updateIdList   = awardItemCategory.getUpdateIdList();
			String updateNameList = awardItemCategory.getUpdateNameList();
			String awardId = awardItemCategory.getAwardId();
			String alignList   = awardItemCategory.getAlignList();
			
			String[] arrayReceive;
			arrayReceive  = addNameList.split(",");
			
			String[] arrayModifyId;
			arrayModifyId = updateIdList.split(",");
			
			String[] arrayModifyNm;
			arrayModifyNm = updateNameList.split(",");
			
			String[] arrayDelId;
			arrayDelId    = delIdList.split(",");

			
			
			String[] arrayAlignName;
			arrayAlignName    = alignList.split(",");
			
			
			if(!"".equals(addNameList) ){
				for (int j = 0; j < arrayReceive.length; j++) {		
					awardItemCategory.setCategoryId(idgenService.getNextId());
					awardItemCategory.setAddNameList(arrayReceive[j]);
					awardItemCategory.setAwardId(awardId);
					this.awardItemCategoryDao.createCategoryNm(awardItemCategory);
				}	
			}
			
			if(!"".equals(updateIdList) ){
				for (int j = 0; j < arrayModifyId.length; j++) {		
					awardItemCategory.setUpdateIdList(arrayModifyId[j]);
					awardItemCategory.setUpdateNameList(arrayModifyNm[j]);
					awardItemCategory.setAwardId(awardId);
					this.awardItemCategoryDao.updateCategoryNm(awardItemCategory);
				}	
			}
			
			if(!"".equals(delIdList) ){
				for (int j = 0; j < arrayDelId.length; j++) {		
					awardItemCategory.setDelIdList(arrayDelId[j]);
					awardItemCategory.setAwardId(awardId);
					this.awardItemCategoryDao.deleteCategoryNm(awardItemCategory);
				}	
			}
			
			if(!"".equals(arrayAlignName) ){
				for (int j = 0; j < arrayAlignName.length; j++) {	
					awardItemCategory.setCategorySeqId(""+j);
					awardItemCategory.setAlignList(arrayAlignName[j]);
					awardItemCategory.setAwardId(awardId);
					this.awardItemCategoryDao.updateCategoryAlign(awardItemCategory);
				}	
			}
			
		}
	}
}
