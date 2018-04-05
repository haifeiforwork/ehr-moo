/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.award.model.AwardItemCategory;
import com.lgcns.ikep4.lightpack.award.model.AwardRecommend;
import com.lgcns.ikep4.lightpack.award.model.AwardReference;
import com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 게시글 서비스 클래스
 */
@Transactional
public interface AwardItemCategoryService{

	List<AwardItemCategory> listCategoryAwardItem(AwardItemCategory categoryAwardId);
	
	public void insertCategoryNm(List<AwardItemCategory> receiveCategoryNmList) ;
}
