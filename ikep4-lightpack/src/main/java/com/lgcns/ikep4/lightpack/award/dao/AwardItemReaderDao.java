/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardItemReader;
import com.lgcns.ikep4.lightpack.award.search.AwardItemReaderSearchCondition;

/**
 * 게시판 독서자 관련 DAO
 * 
 */
public interface AwardItemReaderDao extends GenericDao<AwardItemReader, String> {

	
	public Integer readerFlag(AwardItemReader awardItemReader);
	
	
	public Integer selectReaderCount(AwardItemReader awardItemReader);
	
	public void deleteReader(String id);
	
	public List<AwardItemReader> listAwardItemReader(String  awardItemId);
	
	public Integer countReaderBySearchCondition(AwardItemReaderSearchCondition searchCondtion);
	
	public Integer countAwardBySearchCondition(AwardItemReaderSearchCondition searchCondtion);
	
	public Integer countNoReaderBySearchCondition(AwardItemReaderSearchCondition searchCondtion);
	
	public Integer checkReaderBySearchCondition(AwardItemReaderSearchCondition searchCondtion);
	
	public List<AwardItemReader> listReaderBySearchCondition(AwardItemReaderSearchCondition searchCondtion);
	
	public List<AwardItemReader> listAwardBySearchCondition(AwardItemReaderSearchCondition searchCondtion);
	
	public List<AwardItemReader> listNoReaderBySearchCondition(AwardItemReaderSearchCondition searchCondtion);
	
	public List<AwardItemReader> listReaderAllMail(String  awardItemId);
	
	public List<AwardItemReader> listAwardItemAllNoti();
	
	public void updateMailWaitYn(String itemId);
	
	public List<AwardItemReader> listAwardItemAllNotiInstant(String itemId);
	
}
